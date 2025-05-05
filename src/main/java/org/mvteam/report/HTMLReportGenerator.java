package org.mvteam.report;

import org.mvteam.model.Dependency;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

public class HTMLReportGenerator {

    // There's nothing complicated here, just take a html file and transfer it to a coffee wrapper.

    public static void generate(List<Dependency> vulnerableDeps, File outputFile, List<Dependency> allDeps, List<Dependency> outdatedDeps, boolean debug) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writeHeader(writer);
            writeSummary(writer, allDeps.size(), vulnerableDeps.size(), outdatedDeps.size());
            writeDependenciesTable(writer, allDeps, vulnerableDeps, outdatedDeps);
            writeFooter(writer);

            if (debug) {
                System.out.println(" HTML report saved to: " + outputFile.getAbsolutePath());
            }
        }
    }

    private static void writeHeader(PrintWriter writer) {
        writer.println("<!DOCTYPE html>");
        writer.println("<html lang=\"en\">");
        writer.println("<head>");
        writer.println("    <meta charset=\"UTF-8\">");
        writer.println("    <title>DependGuard Security Report</title>");
        writer.println("    <style>");
        writer.println("        body { font-family: Arial, sans-serif; background: #f4f4f4; color: #333; padding: 20px; }");
        writer.println("        h1, h2 { color: #2c3e50; }");
        writer.println("        table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }");
        writer.println("        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
        writer.println("        .safe { color: green; }");
        writer.println("        .vulnerable { color: red; }");
        writer.println("        .outdated { color: orange; }");
        writer.println("        .summary { margin-bottom: 20px; padding: 15px; background: #ecf0f1; border-radius: 5px; }");
        writer.println("    </style>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("    <h1>DependGuard Security Report</h1>");
    }

    private static void writeSummary(PrintWriter writer, int total, int vulns, int outdated) {
        writer.println("    <div class=\"summary\">");
        writer.println("        <p><strong>Total Dependencies:</strong> " + total + "</p>");
        writer.println("        <p class=\"vulnerable\"><strong>Vulnerable:</strong> " + vulns + "</p>");
        writer.println("        <p class=\"outdated\"><strong>Outdated:</strong> " + outdated + "</p>");
        writer.println("    </div>");
    }

    private static void writeDependenciesTable(PrintWriter writer, List<Dependency> allDeps, List<Dependency> vulnerableDeps, List<Dependency> outdatedDeps) {
        writer.println("    <h2>Dependencies</h2>");
        writer.println("    <table>");
        writer.println("        <tr><th>Group</th><th>Artifact</th><th>Current Version</th><th>Latest Version</th><th>Status</th><th>Vulnerability</th></tr>");

        for (Dependency dep : allDeps) {
            String statusClass = "safe";
            String statusText = "Safe";

            if (vulnerableDeps.contains(dep)) {
                statusClass = "vulnerable";
                statusText = "Vulnerable";
            } else if (outdatedDeps.contains(dep)) {
                statusClass = "outdated";
                statusText = "Outdated";
            }

            String latestVersion = dep.latestVersion != null ? dep.latestVersion : "-";
            String vulnerability = dep.vulnerabilities != null && !dep.vulnerabilities.isEmpty()
                    ? dep.vulnerabilities.get(0) : "-";

            writer.println("        <tr>");
            writer.println("            <td>" + dep.groupId + "</td>");
            writer.println("            <td>" + dep.artifactId + "</td>");
            writer.println("            <td>" + dep.version + "</td>");
            writer.println("            <td>" + latestVersion + "</td>");
            writer.println("            <td class=\"" + statusClass + "\">" + statusText + "</td>");
            writer.println("            <td>" + vulnerability + "</td>");
            writer.println("        </tr>");
        }

        writer.println("    </table>");
    }

    private static void writeFooter(PrintWriter writer) {
        writer.println("</body>");
        writer.println("</html>");
    }
}