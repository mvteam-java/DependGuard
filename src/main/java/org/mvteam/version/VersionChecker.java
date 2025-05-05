package org.mvteam.version;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.mvteam.model.Dependency;
import org.mvteam.utils.ConsoleUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VersionChecker {
    private static final String MAVEN_CENTRAL_API = "https://search.maven.org/solrsearch/select";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();

    public static List<Dependency> check(List<Dependency> dependencies) {
        List<Dependency> outdated = new ArrayList<>();

        for (Dependency dep : dependencies) {
            try {
                String query = String.format("?q=g:\"%s\"+AND+a:\"%s\"&rows=20&wt=json", dep.groupId, dep.artifactId);
                Request request = new Request.Builder()
                        .url(MAVEN_CENTRAL_API + query)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) continue;

                if (response.body() == null) continue;

                JsonNode root = mapper.readTree(response.body().string());
                JsonNode docs = root.get("response").get("docs");

                if (docs != null && docs.size() > 0) {
                    JsonNode latestDoc = docs.get(0);
                    if (latestDoc.has("v")) {
                        String latestVersion = latestDoc.get("v").asText();
                        if (!latestVersion.equals(dep.version)) {
                            dep.latestVersion = latestVersion;
                            outdated.add(dep);
                        }
                    } else {
                        ConsoleUtils.printWarning("No version field found for " + dep);
                    }
                } else {
                    ConsoleUtils.printWarning("No results from Maven Central for " + dep);
                }

            } catch (Exception e) {
                ConsoleUtils.printError("Error checking version for " + dep + ": " + e.getMessage());
            }
        }
        return outdated;
    }
}