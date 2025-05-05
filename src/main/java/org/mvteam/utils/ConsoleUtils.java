package org.mvteam.utils;

import org.mvteam.model.Dependency;

public class ConsoleUtils {

    // TODO: Fix colors

    // ANSI... not working :(
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String GRAY = "\u001B[90m";

    public static void printDivider() {
        System.out.println(CYAN + "──────────────────────────────────────────────" + RESET);
    }

    // header ???
    public static void printHeader(String text) {
        printDivider();
        System.out.println(CYAN + "=== " + text + " ===" + RESET);
        printDivider();
    }
    public static void printVulnerable(Dependency dep) {
        System.out.println(RED + "️ Vulnerable: " + dep + RESET);
        for (String vuln : dep.vulnerabilities) {
            System.out.println("   → " + vuln);
        }
        System.out.println();
    }
    public static void printUpgradable(Dependency dep) {
        System.out.println(YELLOW + " Outdated: " + dep + " → " + dep.latestVersion + RESET);
        System.out.println();
    }
    public static void printSafe(Dependency dep) {
        System.out.println(GREEN + "Safe: " + dep + RESET);
        System.out.println();
    }
    public static void printWarning(String message) {
        System.out.println(YELLOW + " Warning: " + message + RESET);
        System.out.println();
    }
    public static void printError(String message) {
        System.out.println(RED + " Error: " + message + RESET);
        System.out.println();
    }
    public static void printInfo(String message) {
        System.out.println(GRAY + "ℹ️ " + message + RESET);
        System.out.println();
    }
}