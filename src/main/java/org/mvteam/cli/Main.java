package org.mvteam.cli;

import org.mvteam.analyzer.DependencyAnalyzer;
import org.mvteam.exception.DependencyAnalysisException;
import org.mvteam.exception.VulnerabilityCheckException;
import org.mvteam.model.Dependency;
import org.mvteam.report.HTMLReportGenerator;
import org.mvteam.utils.ConsoleUtils;
import org.mvteam.version.VersionChecker;
import org.mvteam.vulnerability.LocalVulnerabilityChecker;
import org.mvteam.vulnerability.NvdVulnerabilityChecker;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Command(name = "dependguard", description = "")
public class Main implements Runnable {

    // TODO: Code review

    @Parameters(index = "0", description = "Project directory with pom.xml")
    private Path projectDir;

    @Option(names = {"--debug"}, description = "Enable debug mode")
    private boolean debug;

    @Option(names = {"--api-key", "-k"}, description = "NVD API Key (optional)", defaultValue = "")
    private String apiKey;

    @Option(names = {"--offline"}, description = "Use only local vulnerability database")
    private boolean offline;

    @Option(names = {"--check-updates"}, description = "Check for dependency updates")
    private boolean checkUpdates;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {

        try {
            ConsoleUtils.printHeader("DependGuard Analysis Started");

            List<Dependency> allDependencies = DependencyAnalyzer.analyze(projectDir, debug);
            List<Dependency> vulnerableDeps = new ArrayList<>();
            List<Dependency> outdatedDeps = new ArrayList<>();

            if (!offline) {
                vulnerableDeps = NvdVulnerabilityChecker.check(allDependencies, apiKey);
            }
            if (vulnerableDeps.isEmpty() || offline) {
                List<Dependency> localVulns = LocalVulnerabilityChecker.check(allDependencies);
                vulnerableDeps.addAll(localVulns);
            }

            if (checkUpdates) {
                outdatedDeps = VersionChecker.check(allDependencies);
            }

            HTMLReportGenerator.generate(vulnerableDeps, projectDir.resolve("report.html").toFile(), allDependencies, outdatedDeps, debug);
            System.out.println("\nReport generated: " + projectDir.resolve("report.html"));

            ConsoleUtils.printHeader("Analysis Completed");

        } catch (Exception e) {
            ConsoleUtils.printError("Critical error: " + e.getMessage());
            if (debug) {
                e.printStackTrace();
            }
        }
    }
}