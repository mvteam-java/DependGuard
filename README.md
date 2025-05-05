# DependGuard — Java Project Dependency Analysis Tool

[![License](https://img.shields.io/github/license/mvteam-java/dependguard)](https://github.com/mvteam-java/dependguard/blob/main/LICENSE)
[![Java Version](https://img.shields.io/badge/java-17+-blue)](https://adoptium.net/)

DependGuard is a CLI tool for analyzing dependencies in Java projects. It checks for **vulnerabilities (CVE)**, identifies **outdated dependencies**, and generates **HTML reports** with results.

---

## 📌 About the Project

DependGuard helps developers:
- Check dependencies for **security vulnerabilities** using the NVD API.
- Identify **outdated library versions** using Maven Central.
- Generate **HTML reports** with color-coded results.
- Work in **offline mode** with a local vulnerability database.

---

**Analyze dependencies:**
```
java -jar dependguard.jar /path/to/project --check-updates
```
**Example output:**
```
 Safe: junit:junit:4.12
 Vulnerable: org.apache.commons:commons-lang3:3.5 → CVE-2019-10086
```

---

# 🧰 Installation
## 1. Requirements:
**Java 17+**
**Maven (for building from source)**
## 2. Build from Source:
```
git clone https://github.com/mvteam-java/dependguard.git
cd dependguard
mvn clean package
```
*Compiled JAR will be in* ``` target/dependguard.jar/ ```

---

# 📋 Usage
## Basic Command:
```
java -jar dependguard.jar /path/to/project [options]
```
# Vulnerability check
```
java -jar dependguard.jar ~/projects/my-java-app
```
# With NVD API key and update check
```
java -jar dependguard.jar ~/projects/my-java-app --api-key YOUR_NVD_KEY --check-updates
```
# Offline mode
```
java -jar dependguard.jar ~/projects/my-java-app --offline
```

---

## ⚙️ CLI Parameters
```
--debug
Enable detailed logs
```
```
--api-key <key>
NVD API key (increases request limit)
```
```
--offline
Use vulnerabilities.csv instead of NVD
```
```
--check-updates
Check for newer dependency versions
```
```
-h, --help
Show help
```

---

# 🤝 Contribute
## Fork the repo
Create a branch: 
```
git checkout -b feature/name
```
Commit changes: 
```
git commit -m 'Add feature'
```
Push: 
```
git push origin feature/name
```
Open a Pull Request

---

# 📪 Report an Issue
Open an [ISSUE](https://github.com/mvteam-java/dependguard/issues) with a description of the problem and steps to reproduce.
