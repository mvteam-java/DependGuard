package org.mvteam.model;

import java.util.ArrayList;
import java.util.List;

public class Dependency {
    public String groupId;
    public String artifactId;
    public String version;
    public String latestVersion; // Новое поле для версии
    public List<String> vulnerabilities = new ArrayList<>();

    public Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }
}