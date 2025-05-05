package org.mvteam.analyzer;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.mvteam.exception.DependencyAnalysisException;
import org.mvteam.model.Dependency;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DependencyAnalyzer {
    public static List<Dependency> analyze(Path projectDir, boolean debug) throws DependencyAnalysisException {
        File pomFile = projectDir.resolve("pom.xml").toFile();
        if (!pomFile.exists()) {
            throw new DependencyAnalysisException("pom.xml not found in " + projectDir); // dir in args
        }

        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new java.io.FileReader(pomFile));
            List<Dependency> dependencies = new ArrayList<>();

            for (org.apache.maven.model.Dependency dep : model.getDependencies()) {
                dependencies.add(new Dependency(
                        dep.getGroupId(),
                        dep.getArtifactId(),
                        dep.getVersion()
                ));
            }

            if (debug) {
                System.out.println("[DEBUG] Dependencies parsed from pom.xml");
            }

            return dependencies;
        } catch (Exception e) {
            throw new DependencyAnalysisException("Failed to parse pom.xml", e);
        }
    }
}