# 🔐 Modul Hands-on Lab: Web Security dengan Spring Boot 3.2+ (Java 21)
### Berbasis GitHub Codespaces — Tanpa Instalasi Lokal!

> **Target Audience:** Developer / Mahasiswa Tingkat Menengah
> **Stack:** Spring Boot 3.2+, Java 21, Thymeleaf, Bootstrap Admin, H2 Database, Spring Security
> **Platform Lab:** GitHub Codespaces (cloud-based, browser-based IDE)
> **Estimasi Durasi:** 8–10 Jam (4 Modul Lab)

---

## 📋 DAFTAR ISI

1. [PRE-REQUISITE: Persiapan Akun & Environment GitHub](#pre-requisite-persiapan-akun--environment-github)
2. [Modul 0 – Setup Project di GitHub Codespaces](#modul-0-setup-project-di-github-codespaces)
3. [Modul 1 – OWASP Top 10: Pengenalan & Relevansi di Ekosistem Spring](#modul-1-owasp-top-10)
4. [Modul 2 – CVE: Deteksi & Mitigasi Dependency Vulnerability](#modul-2-cve-dependency-vulnerability)
5. [Modul 3 – CORS: Miskonfigurasi & Konfigurasi Aman](#modul-3-cors-cross-origin-resource-sharing)
6. [Modul 4 – SQL Injection: Simulasi Serangan & Pencegahan](#modul-4-sql-injection)
7. [Panduan Submit Tugas via GitHub](#panduan-submit-tugas-via-github)

---

---

## PRE-REQUISITE: Persiapan Akun & Environment GitHub

> ⏱️ **Estimasi waktu bagian ini: 30–45 menit**
> Selesaikan bagian ini SEBELUM memulai Modul Lab apapun!

---

### A. Buat Akun GitHub (Jika Belum Punya)

**Langkah 1: Registrasi Akun GitHub**

1. Buka browser dan kunjungi **https://github.com**
2. Klik tombol **"Sign up"** di pojok kanan atas
3. Isi form registrasi:
   - **Username:** Gunakan format `nama-npm` (contoh: `budi-2021001`) agar mudah diidentifikasi dosen
   - **Email:** Gunakan email kampus (format `.ac.id`) untuk kemudahan verifikasi
   - **Password:** Minimal 8 karakter, kombinasi huruf besar, kecil, angka, dan simbol
4. Selesaikan verifikasi (puzzle/CAPTCHA)
5. Pilih plan **"Free"** — sudah cukup untuk kebutuhan lab ini
6. Verifikasi email kamu (cek inbox)

> 💡 **Tips untuk Mahasiswa:** GitHub Student Developer Pack (https://education.github.com/pack) memberikan akses GitHub Codespaces yang lebih luas secara gratis. Daftar menggunakan email kampus untuk mendapatkan benefit ini!

**Langkah 2: Setup Profil GitHub**

```
Settings (klik avatar) → Profile:
- Name: Nama lengkap kamu
- Bio: "Mahasiswa [Prodi] - [Universitas]"
- Pastikan email kampus sudah terverifikasi
```

---

### B. Aktifkan & Pahami GitHub Codespaces

**Apa itu GitHub Codespaces?**

GitHub Codespaces adalah **IDE berbasis cloud** yang berjalan langsung di browser. Kamu mendapat mesin virtual Linux lengkap dengan VS Code, terminal, dan semua tools development — tanpa perlu install apapun di laptop!

```
Keuntungan untuk Lab ini:
✅ Tidak perlu install Java, Maven, atau IDE di laptop
✅ Environment semua mahasiswa IDENTIK (tidak ada masalah "di laptop saya jalan")
✅ Otomatis tersimpan di GitHub — mudah di-review dosen
✅ Bisa dilanjutkan dari mana saja (laptop kampus, rumah, warnet)
✅ Free tier: 120 jam/bulan untuk akun Personal
```

**Langkah 1: Cek Kuota Codespaces Kamu**

1. Login ke GitHub
2. Klik avatar → **Settings**
3. Scroll ke **"Codespaces"** di sidebar kiri
4. Lihat penggunaan di **"Usage this month"**

> ℹ️ Free tier GitHub memberikan **120 core-hours/bulan**. Dengan machine type 2-core (default), itu setara **60 jam aktif** — lebih dari cukup untuk lab ini.

---

### C. Fork Repository Template Lab

> ⚠️ **PENTING:** Setiap mahasiswa WAJIB melakukan fork ke akun GitHub masing-masing. Jangan kerjakan langsung di repository dosen!

**Langkah 1: Fork Repository**

1. Buka URL repository template yang diberikan dosen (contoh: `https://github.com/dosen/websecurity-lab-template`)
2. Klik tombol **"Fork"** di pojok kanan atas halaman
3. Pada dialog fork:
   - **Owner:** Pilih username kamu sendiri
   - **Repository name:** `websecurity-lab-[NPM_KAMU]` (contoh: `websecurity-lab-2021001`)
   - Centang ✅ "Copy the main branch only"
4. Klik **"Create fork"**

```
Hasil:
github.com/[USERNAME_KAMU]/websecurity-lab-[NPM_KAMU]
                ↑
         Repository milikmu sendiri
```

**Langkah 2: Verifikasi Fork Berhasil**

Di halaman repository hasil fork, kamu akan melihat:
```
[USERNAME_KAMU]/websecurity-lab-[NPM_KAMU]
forked from dosen/websecurity-lab-template
```

---

### D. Buat Codespace dari Repository Kamu

**Langkah 1: Buka Codespace**

1. Pastikan kamu sudah berada di repository hasil fork milikmu
2. Klik tombol hijau **"< > Code"**
3. Pilih tab **"Codespaces"**
4. Klik **"Create codespace on main"**

```
Tunggu 1–3 menit untuk proses:
[████████░░] Preparing your codespace...
[██████████] Setting up your environment...
→ VS Code akan terbuka di browser!
```

**Langkah 2: Kenali Tampilan Codespace**

```
┌─────────────────────────────────────────────────────────┐
│  GITHUB CODESPACES — VS Code di Browser                 │
├──────────────┬──────────────────────────────────────────┤
│              │                                          │
│  EXPLORER    │         EDITOR (tengah)                  │
│  (kiri)      │   Tempat kamu menulis kode               │
│              │                                          │
│  - src/      │                                          │
│  - pom.xml   │                                          │
│  - ...       │                                          │
│              │                                          │
├──────────────┴──────────────────────────────────────────┤
│  TERMINAL (bawah) — Ctrl+` untuk buka/tutup             │
│  $ _                                                    │
└─────────────────────────────────────────────────────────┘
```

**Langkah 3: Buka Terminal**

Tekan **Ctrl + `** (backtick) atau klik menu **Terminal → New Terminal**

---

### E. Instalasi Java 21 di Codespaces

> ℹ️ Codespaces biasanya sudah memiliki beberapa versi Java. Namun kita perlu memastikan Java 21 terpasang dan menjadi versi aktif.

**Langkah 1: Cek Versi Java yang Sudah Ada**

```bash
# Cek versi Java yang sedang aktif
java -version

# Lihat semua versi Java yang tersedia di sistem
ls /usr/lib/jvm/

# Atau jika menggunakan SDKMAN (biasanya sudah terpasang di Codespaces):
sdk list java | grep "21"
```

**Langkah 2A: Install Java 21 via SDKMAN (Rekomendasi)**

SDKMAN adalah tool manajemen versi untuk Java — mirip seperti `nvm` untuk Node.js.

```bash
# Cek apakah SDKMAN sudah terpasang
sdk version

# Jika belum ada SDKMAN, install dulu:
curl -s "https://get.sdkman.io" | bash

# Reload terminal setelah install SDKMAN
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Verifikasi SDKMAN berhasil
sdk version
# Output: SDKMAN 5.x.x

# Lihat daftar versi Java 21 yang tersedia
sdk list java | grep "21\."

# Install Java 21 LTS (Temurin/Eclipse Adoptium - gratis & open source)
sdk install java 21.0.3-tem

# Konfirmasi sebagai default: ketik "Y" saat ditanya
# "Do you want java 21.0.3-tem to be set as default? (Y/n):"
```

```bash
# Verifikasi instalasi berhasil
java -version
# Output yang diharapkan:
# openjdk version "21.0.3" 2024-04-16 LTS
# OpenJDK Runtime Environment Temurin-21.0.3+9 (build 21.0.3+9-LTS)
# OpenJDK 64-Bit Server VM Temurin-21.0.3+9 (build 21.0.3+9-LTS, mixed mode, sharing)
```

**Langkah 2B: Install Java 21 via APT (Alternatif jika SDKMAN tidak tersedia)**

```bash
# Update package list
sudo apt-get update

# Install Java 21 (OpenJDK)
sudo apt-get install -y openjdk-21-jdk

# Set Java 21 sebagai default jika ada beberapa versi
sudo update-alternatives --config java
# Ketik nomor yang sesuai dengan Java 21

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Verifikasi
java -version
javac -version
echo $JAVA_HOME
```

**Langkah 2C: Install via Homebrew (Jika tersedia di Codespaces)**

```bash
# Cek apakah Homebrew tersedia
brew --version

# Install Java 21 via Homebrew
brew install openjdk@21

# Tambahkan ke PATH
echo 'export PATH="/home/linuxbrew/.linuxbrew/opt/openjdk@21/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

# Verifikasi
java -version
```

**Langkah 3: Set JAVA_HOME dengan Benar**

```bash
# Cek path Java 21 yang terinstall
which java
readlink -f $(which java)

# Set JAVA_HOME secara permanen (sesuaikan path dengan output di atas)
# Untuk SDKMAN, JAVA_HOME sudah di-set otomatis

# Untuk instalasi manual, tambahkan ke ~/.bashrc:
echo 'export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))' >> ~/.bashrc
source ~/.bashrc

# Verifikasi JAVA_HOME
echo $JAVA_HOME
# Contoh output: /home/codespace/.sdkman/candidates/java/21.0.3-tem
```

**Langkah 4: Verifikasi Maven Terdeteksi Java 21**

```bash
# Cek versi Maven
mvn -version

# Output yang diharapkan:
# Apache Maven 3.9.x (...)
# Maven home: /usr/share/maven
# Java version: 21.0.3, vendor: Eclipse Adoptium, ...  ← Harus 21!
# Java home: /home/codespace/.sdkman/candidates/java/21.0.3-tem
```

> ❗ **Troubleshooting:** Jika Maven masih menunjukkan Java versi lain, jalankan:
> ```bash
> export JAVA_HOME=$(sdk home java 21.0.3-tem)
> export PATH=$JAVA_HOME/bin:$PATH
> mvn -version  # Cek lagi
> ```

---

### F. Konfigurasi `.devcontainer` untuk Konsistensi Environment

Agar semua mahasiswa punya environment yang identik (termasuk Java 21), kita perlu membuat file konfigurasi Codespaces.

**Langkah 1: Buat folder `.devcontainer`**

```bash
mkdir -p .devcontainer
```

**Langkah 2: Buat file `devcontainer.json`**

```bash
cat > .devcontainer/devcontainer.json << 'EOF'
{
  "name": "WebSecurity Lab - Java 21",
  "image": "mcr.microsoft.com/devcontainers/java:21-bullseye",
  "features": {
    "ghcr.io/devcontainers/features/java:1": {
      "version": "21",
      "jdkDistro": "tem",
      "installMaven": true,
      "mavenVersion": "3.9.6",
      "installGradle": false
    }
  },
  "customizations": {
    "vscode": {
      "extensions": [
        "vmware.vscode-spring-boot",
        "vscjava.vscode-spring-boot-dashboard",
        "vscjava.vscode-java-pack",
        "vscjava.vscode-maven",
        "humao.rest-client",
        "redhat.java",
        "ms-azuretools.vscode-docker",
        "github.vscode-github-actions"
      ],
      "settings": {
        "java.home": "/usr/local/sdkman/candidates/java/current",
        "java.configuration.runtimes": [
          {
            "name": "JavaSE-21",
            "path": "/usr/local/sdkman/candidates/java/current",
            "default": true
          }
        ],
        "editor.formatOnSave": true,
        "editor.tabSize": 4
      }
    }
  },
  "forwardPorts": [8080],
  "portsAttributes": {
    "8080": {
      "label": "Spring Boot App",
      "onAutoForward": "openPreview"
    }
  },
  "postCreateCommand": "mvn dependency:resolve -q && echo '✅ Java 21 & Maven siap digunakan!'",
  "remoteUser": "vscode"
}
EOF
```

**Langkah 3: Commit konfigurasi devcontainer**

```bash
git add .devcontainer/
git commit -m "chore: add devcontainer config for Java 21 Codespaces"
git push origin main
```

> 💡 **Setelah push**, jika kamu membuat Codespace baru, environment Java 21 akan otomatis terkonfigurasi tanpa perlu instalasi manual lagi!

---

### G. Verifikasi Final Environment

Jalankan semua cek ini di terminal Codespaces sebelum mulai lab:

```bash
echo "=== Verifikasi Environment Lab ==="

echo ""
echo "1. Java Version:"
java -version

echo ""
echo "2. Java Compiler:"
javac -version

echo ""
echo "3. JAVA_HOME:"
echo $JAVA_HOME

echo ""
echo "4. Maven Version:"
mvn -version

echo ""
echo "5. Git Version:"
git --version

echo ""
echo "6. cURL:"
curl --version | head -1

echo ""
echo "7. Git Config:"
git config user.name
git config user.email

echo ""
echo "=== Semua tools siap! Lanjut ke Modul 0 ==="
```

**Output yang diharapkan:**
```
=== Verifikasi Environment Lab ===

1. Java Version:
openjdk version "21.0.3" 2024-04-16 LTS
OpenJDK Runtime Environment Temurin-21.0.3+9

2. Java Compiler:
javac 21.0.3

3. JAVA_HOME:
/usr/local/sdkman/candidates/java/current

4. Maven Version:
Apache Maven 3.9.6
Java version: 21.0.3

5. Git Version:
git version 2.43.x

6. cURL:
curl 7.88.x

7. Git Config:
Nama Mahasiswa
mahasiswa@kampus.ac.id

=== Semua tools siap! Lanjut ke Modul 0 ===
```

---

### H. Konfigurasi Git Identity

```bash
# Set nama dan email untuk setiap commit yang kamu buat
git config --global user.name "Nama Lengkap Kamu"
git config --global user.email "email@kampus.ac.id"

# Verifikasi
git config --global --list
```

---

---

## MODUL 0: Setup Project di GitHub Codespaces

> ⏱️ **Estimasi waktu: 30 menit**
> Dilakukan di dalam Codespaces yang sudah kamu buat di bagian Pre-Requisite.

---

### Alur Kerja Lab (Wajib Diikuti!)

```
┌────────────────────────────────────────────────────────────────┐
│                   ALUR KERJA SETIAP MODUL                      │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  1. Buat branch baru                                           │
│     git checkout -b lab/modul-[nomor]-[nama]                  │
│                          ↓                                     │
│  2. Tulis VULNERABLE CODE (kode rentan)                        │
│     git add . && git commit -m "lab: add vulnerable code"     │
│                          ↓                                     │
│  3. Jalankan & buktikan exploit (screenshot/log)               │
│     git add . && git commit -m "lab: add exploit evidence"    │
│                          ↓                                     │
│  4. Tulis SECURE CODE FIX (kode perbaikan)                     │
│     git add . && git commit -m "fix: implement secure code"   │
│                          ↓                                     │
│  5. Push branch ke GitHub                                      │
│     git push origin lab/modul-[nomor]-[nama]                  │
│                          ↓                                     │
│  6. Buat Pull Request ke branch main milikmu                   │
│     (isi deskripsi PR dengan analisis keamanan)               │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

---

### Struktur Project

```
websecurity-lab/
├── .devcontainer/
│   └── devcontainer.json          ← Konfigurasi Codespaces
├── .github/
│   └── workflows/
│       └── security-scan.yml      ← CI/CD untuk scan CVE
├── src/
│   ├── main/
│   │   ├── java/com/lab/security/
│   │   │   ├── WebSecurityLabApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java
│   │   │   │   └── ApiController.java
│   │   │   ├── entity/
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       └── UserService.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── layout.html
│   │       │   ├── index.html
│   │       │   └── users.html
│   │       ├── static/css/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
├── evidence/                      ← Folder screenshot/log bukti exploit
│   ├── modul-2/
│   ├── modul-3/
│   └── modul-4/
└── pom.xml
```

---

### Inisialisasi Project dari Terminal Codespaces

```bash
# Pastikan kamu berada di root repository
pwd
# Output: /workspaces/websecurity-lab-[NPM_KAMU]

# Buat struktur direktori yang dibutuhkan
mkdir -p src/main/java/com/lab/security/{config,controller,entity,repository,service}
mkdir -p src/main/resources/{templates,static/css}
mkdir -p src/test/java/com/lab/security
mkdir -p evidence/{modul-2,modul-3,modul-4}
mkdir -p .github/workflows
```

---

### `pom.xml` — Dependensi Lengkap

Buat file `pom.xml` di root project:

```bash
cat > pom.xml << 'POMEOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/>
    </parent>

    <groupId>com.lab</groupId>
    <artifactId>websecurity-lab</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>WebSecurity Lab</name>
    <description>Hands-on Lab: Web Security dengan Spring Boot - GitHub Codespaces</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Spring Web MVC -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Thymeleaf Template Engine -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <!-- Thymeleaf + Spring Security Integration -->
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>

        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- H2 In-Memory Database (untuk simulasi lab) -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Spring Boot Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Security Test -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>

            <!-- OWASP Dependency-Check Plugin (untuk Modul 2) -->
            <plugin>
                <groupId>org.owasp</groupId>
                <artifactId>dependency-check-maven</artifactId>
                <version>9.1.0</version>
                <configuration>
                    <failBuildOnCVSS>7</failBuildOnCVSS>
                    <format>HTML</format>
                    <outputDirectory>${project.build.directory}/dependency-check-report</outputDirectory>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
POMEOF
```

---

### File Konfigurasi & Data Awal

**`src/main/resources/application.properties`**

```bash
cat > src/main/resources/application.properties << 'EOF'
# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:securitylab;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Initial data
spring.sql.init.mode=always

# CORS Allowed Origins (diubah per modul)
app.cors.allowed-origins=http://localhost:3000,http://localhost:4200
EOF
```

**`src/main/resources/data.sql`**

```bash
cat > src/main/resources/data.sql << 'EOF'
INSERT INTO users (id, username, email, password, role) VALUES
(1, 'admin', 'admin@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'ADMIN'),
(2, 'alice', 'alice@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'USER'),
(3, 'bob', 'bob@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'USER');
-- Password untuk semua user: "password123"
EOF
```

**`src/main/java/com/lab/security/entity/User.java`**

```java
package com.lab.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
```

**`src/main/java/com/lab/security/WebSecurityLabApplication.java`**

```java
package com.lab.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSecurityLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSecurityLabApplication.class, args);
    }
}
```

---

### Build & Jalankan Pertama Kali

```bash
# Download dependencies (perlu koneksi internet, ~2 menit pertama kali)
mvn dependency:resolve

# Compile untuk verifikasi tidak ada error
mvn compile

# Jalankan aplikasi
mvn spring-boot:run
```

**Akses Aplikasi dari Codespaces:**

Setelah aplikasi berjalan, Codespaces akan otomatis mendeteksi port 8080 dan menawarkan untuk membuka di browser. Klik **"Open in Browser"** pada notifikasi yang muncul, atau:

1. Klik tab **"PORTS"** di panel bawah (sebelah tab Terminal)
2. Cari port `8080`
3. Klik ikon 🌐 (Globe) untuk membuka di browser

> 💡 URL aplikasimu di Codespaces akan berbentuk: `https://[nama-codespace]-8080.app.github.dev`

---

### Commit Setup Awal

```bash
# Tambahkan semua file ke git
git add .

# Commit awal
git commit -m "feat: initial project setup - Spring Boot 3.2 Java 21

- Setup pom.xml dengan semua dependency lab
- Konfigurasi H2 database dan Spring Security dasar
- Tambah devcontainer.json untuk Codespaces"

# Push ke GitHub
git push origin main
```

---

---

## MODUL 1: OWASP Top 10

### Apa Itu OWASP Top 10?

OWASP (Open Worldwide Application Security Project) merilis daftar **10 risiko keamanan web paling kritis** yang diperbarui secara berkala. Versi terkini adalah **OWASP Top 10 – 2021**.

---

### OWASP Top 10 (2021) dan Relevansinya di Spring Boot

| # | Kategori | Deskripsi Singkat | Relevansi di Spring |
|---|----------|-------------------|---------------------|
| A01 | **Broken Access Control** | Pengguna bisa mengakses resource yang seharusnya tidak boleh | Salah konfigurasi `@PreAuthorize`, role-based access di Spring Security |
| A02 | **Cryptographic Failures** | Data sensitif tidak dienkripsi dengan benar | Password tidak di-hash dengan BCrypt, koneksi tanpa HTTPS |
| A03 | **Injection** | Input berbahaya dieksekusi sebagai perintah/query | SQL Injection via JPQL/Native Query, LDAP Injection |
| A04 | **Insecure Design** | Tidak ada mekanisme keamanan sejak tahap desain | Tidak ada rate limiting, tidak ada validasi input di layer service |
| A05 | **Security Misconfiguration** | Konfigurasi default tidak aman | H2 Console terbuka di production, CORS terlalu permisif, debug mode aktif |
| A06 | **Vulnerable & Outdated Components** | Library/dependency mengandung CVE | Menggunakan versi Spring/Log4j yang sudah ada CVE-nya |
| A07 | **Identification & Authentication Failures** | Mekanisme autentikasi lemah | Session fixation, password tidak di-hash, tidak ada brute-force protection |
| A08 | **Software & Data Integrity Failures** | Update atau CI/CD pipeline tidak diverifikasi | Dependency dari sumber tidak terpercaya tanpa checksum |
| A09 | **Security Logging & Monitoring Failures** | Tidak ada log untuk kejadian penting | Login gagal tidak di-log, tidak ada alerting |
| A10 | **Server-Side Request Forgery (SSRF)** | Server melakukan request ke URL yang dikontrol attacker | `RestTemplate` / `WebClient` dengan input URL dari user |

---

### Peta OWASP Top 10 → Lab yang Akan Kamu Kerjakan

```
OWASP A03: Injection          ──▶  Modul 4 (SQL Injection)
OWASP A05: Misconfiguration   ──▶  Modul 3 (CORS Misconfiguration)
OWASP A06: Vulnerable Comps   ──▶  Modul 2 (CVE Dependency-Check)
```

> **Catatan:** Modul-modul berikutnya adalah implementasi langsung dari risiko OWASP. Kamu tidak hanya belajar teori, tapi akan mensimulasikan serangan nyata dan memperbaikinya langsung di GitHub kamu sendiri.

---

---

## MODUL 2: CVE Dependency Vulnerability

### TUJUAN BELAJAR

Setelah menyelesaikan lab ini, kamu akan mampu:
- Memahami apa itu CVE dan CVSS Score
- Mendeteksi dependency Spring Boot yang mengandung kerentanan menggunakan **OWASP Dependency-Check**
- Membaca laporan vulnerability dan memitigasi celah dengan upgrade/exclude dependency
- Mengintegrasikan security scan ke GitHub Actions CI/CD

---

### SKENARIO KERENTANAN

Bayangkan kamu join ke sebuah tim dan mewarisi project Spring Boot yang sudah lama tidak diupdate. Project ini masih menggunakan beberapa library lama. Salah satunya mungkin mengandung **CVE** — celah keamanan yang sudah terdokumentasi secara publik. Jika tidak dideteksi, attacker bisa mengeksploitasi library tersebut untuk melakukan Remote Code Execution (RCE), Data Breach, atau serangan lainnya.

**Contoh kasus nyata:**
- **CVE-2021-44228 (Log4Shell)** — RCE kritis pada Log4j 2.x yang mempengaruhi ratusan ribu aplikasi Java
- **CVE-2022-22965 (Spring4Shell)** — RCE pada Spring Framework sebelum versi 5.3.18

---

### LANGKAH 1: Buat Branch untuk Modul 2

```bash
# Di terminal Codespaces, buat branch baru
git checkout -b lab/modul-2-cve-detection

# Verifikasi kamu sudah di branch yang benar
git branch
# Output: * lab/modul-2-cve-detection
```

---

### VULNERABLE CODE

**Simulasi: Tambahkan dependency rentan (Log4j versi lama) ke `pom.xml`**

Di VS Code Codespaces, buka file `pom.xml` dan tambahkan dependency berikut di dalam tag `<dependencies>`:

```xml
<!-- ⚠️ SIMULASI LAB — Jangan gunakan di production! -->
<!-- Tambahkan dependency Log4j versi rentan -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.14.1</version>
    <!-- ↑ CVE-2021-44228 (Log4Shell) — CVSS Score: 10.0 CRITICAL -->
</dependency>

<!-- Jackson versi lama dengan beberapa CVE -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
    <!-- ↑ CVE-2019-17267, CVE-2019-14379 — CVSS Score: 9.8 CRITICAL -->
</dependency>
```

```bash
# Commit kode rentan
git add pom.xml
git commit -m "lab(modul2): add vulnerable dependencies for CVE scanning simulation

VULNERABLE DEPENDENCIES ADDED (simulation only):
- log4j-core 2.14.1 → CVE-2021-44228 (Log4Shell) CVSS 10.0
- jackson-databind 2.9.8 → CVE-2019-17267 CVSS 9.8

This commit simulates a real-world scenario where legacy dependencies
contain known CVEs that need to be detected and remediated."
```

---

### EXPLOITATION STEP (Deteksi CVE)

**Langkah 1: Jalankan OWASP Dependency-Check**

```bash
# Jalankan scan (pertama kali akan download NVD database ~5-10 menit)
# Gunakan DfailBuildOnCVSS=11 agar scan selesai meski ada CVE kritis
mvn dependency-check:check -DfailBuildOnCVSS=11 -Dformat=HTML

# Pantau progress di terminal:
# [INFO] Checking for updates...
# [INFO] NVD CVE requires several updates...
# [INFO] Download Progress: 100%
# [INFO] Processing References...
```

**Langkah 2: Lihat Laporan di Codespaces**

```bash
# Cek apakah report sudah dibuat
ls -la target/dependency-check-report/
# Output: dependency-check-report.html

# Di Codespaces, buka file report melalui:
# Explorer (panel kiri) → target/dependency-check-report/dependency-check-report.html
# Klik kanan → "Open with Live Preview" atau "Open in Browser"
```

**Langkah 3: Screenshot Laporan sebagai Bukti**

```bash
# Buat folder bukti
mkdir -p evidence/modul-2

# Salin report ke folder evidence
cp target/dependency-check-report/dependency-check-report.html evidence/modul-2/

# Buat file catatan temuan
cat > evidence/modul-2/TEMUAN.md << 'EOF'
# Bukti Lab Modul 2 - CVE Detection

## Temuan CVE

### 1. log4j-core-2.14.1.jar
- **CVE ID:** CVE-2021-44228
- **Nama:** Log4Shell
- **CVSS Score:** 10.0 (CRITICAL)
- **Dampak:** Remote Code Execution (RCE) tanpa autentikasi
- **Deskripsi:** Apache Log4j2 JNDI lookup dapat dieksploitasi attacker untuk eksekusi kode arbitrer

### 2. jackson-databind-2.9.8.jar
- **CVE ID:** CVE-2019-17267, CVE-2019-14379
- **CVSS Score:** 9.8 (CRITICAL)
- **Dampak:** Remote Code Execution melalui deserialization gadget chains

## Analisis
[Isi dengan analisis kamu sendiri]

## Mitigasi yang Dilakukan
[Isi setelah fix]
EOF
```

**Langkah 4: Commit Bukti Temuan**

```bash
git add evidence/modul-2/
git commit -m "lab(modul2): add CVE scan evidence and findings documentation

Scan Results:
- CVE-2021-44228 (CRITICAL 10.0): log4j-core 2.14.1
- CVE-2019-17267 (CRITICAL 9.8): jackson-databind 2.9.8
- Full HTML report saved to evidence/modul-2/"
```

---

### SECURE CODE FIX

**Langkah 1: Fix `pom.xml` — Hapus/Upgrade Dependency Rentan**

```xml
<!-- HAPUS dependency log4j versi rentan, atau upgrade: -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.21.1</version>  <!-- ✅ Versi sudah dipatch -->
</dependency>

<!-- Untuk jackson-databind: HAPUS penentuan versi manual -->
<!-- Biarkan Spring Boot BOM yang menentukan versi aman -->
<!-- <version>2.9.8</version>  ← HAPUS baris ini -->
```

**Langkah 2: Tambahkan Konfigurasi Plugin yang Lebih Lengkap**

Update bagian `<plugin>` di `pom.xml`:

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>9.1.0</version>
    <configuration>
        <!-- Build GAGAL jika ada CVE dengan CVSS >= 7 -->
        <failBuildOnCVSS>7</failBuildOnCVSS>
        <format>HTML</format>
        <outputDirectory>${project.build.directory}/dependency-check-report</outputDirectory>
        <suppressionFiles>
            <suppressionFile>src/main/resources/dependency-check-suppressions.xml</suppressionFile>
        </suppressionFiles>
        <cveValidForHours>4</cveValidForHours>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Langkah 3: Buat File Suppression**

```bash
cat > src/main/resources/dependency-check-suppressions.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<suppressions xmlns="https://jeremylong.github.io/DependencyCheck/dependency-suppression.1.3.xsd">
    <!--
        Tambahkan suppression di sini hanya untuk false positive
        yang sudah terverifikasi tidak relevan dengan aplikasi ini.
        Selalu tambahkan justifikasi!
    -->
</suppressions>
EOF
```

**Langkah 4: Buat GitHub Actions Workflow untuk Scan Otomatis**

```bash
cat > .github/workflows/security-scan.yml << 'EOF'
name: 🔒 Security Dependency Scan

on:
  push:
    branches: [ main, lab/* ]
  pull_request:
    branches: [ main ]
  schedule:
    # Setiap Senin pukul 06:00 UTC
    - cron: '0 6 * * 1'

jobs:
  dependency-check:
    name: OWASP Dependency Check
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up Java 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: 'maven'

      - name: Run OWASP Dependency Check
        run: |
          mvn dependency-check:check \
            -DfailBuildOnCVSS=7 \
            -Dformat=HTML \
            -DskipTests
        continue-on-error: true

      - name: Upload Dependency Check Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: dependency-check-report-${{ github.run_number }}
          path: target/dependency-check-report/
          retention-days: 30

      - name: Comment PR with results
        if: github.event_name == 'pull_request'
        uses: actions/github-script@v7
        with:
          script: |
            github.rest.issues.createComment({
              issue_number: context.issue.number,
              owner: context.repo.owner,
              repo: context.repo.repo,
              body: '## 🔒 Security Scan Results\nDependency check report has been uploaded as an artifact.\nCheck the Actions tab for the full report.'
            })
EOF
```

**Langkah 5: Scan Ulang untuk Verifikasi Fix**

```bash
# Scan ulang setelah fix
mvn clean dependency-check:check -DfailBuildOnCVSS=7

# Jika build SUCCESS → dependency rentan sudah dihapus ✅
# Jika build FAILURE → masih ada CVE yang perlu difix
```

**Langkah 6: Update Bukti dan Commit Fix**

```bash
# Update catatan temuan
cat >> evidence/modul-2/TEMUAN.md << 'EOF'

## Mitigasi yang Dilakukan
1. **log4j-core:** Di-upgrade dari 2.14.1 ke 2.21.1 (versi yang sudah dipatch)
2. **jackson-databind:** Dihapus penentuan versi manual, diserahkan ke Spring Boot BOM
3. **GitHub Actions:** Ditambahkan workflow otomatis untuk scan setiap push/PR
4. **failBuildOnCVSS=7:** Build akan otomatis gagal jika ada CVE High/Critical

## Hasil Setelah Fix
- Tidak ada CVE dengan score >= 7 yang terdeteksi
- BUILD SUCCESS ✅
EOF

git add .
git commit -m "fix(modul2): remediate CVE findings and add automated scanning

Changes:
- Upgraded log4j-core from 2.14.1 to 2.21.1 (fixes CVE-2021-44228)
- Removed manual jackson-databind version (using Spring Boot BOM)
- Added GitHub Actions workflow for automated dependency scanning
- Added suppression file template
- Updated evidence documentation"

# Push branch ke GitHub
git push origin lab/modul-2-cve-detection
```

**Langkah 7: Buat Pull Request di GitHub**

1. Buka repository kamu di GitHub (`github.com/[USERNAME]/websecurity-lab-[NPM]`)
2. Kamu akan melihat notifikasi **"Compare & pull request"** → klik tombol itu
3. Isi form Pull Request:

```
Title: [Modul 2] CVE Detection & Remediation

## Ringkasan
Lab Modul 2: CVE Dependency Vulnerability - Deteksi dan mitigasi
kerentanan pada dependency Spring Boot menggunakan OWASP Dependency-Check.

## CVE yang Ditemukan
| CVE ID | Severity | Library | Versi Rentan | Status |
|--------|----------|---------|-------------|--------|
| CVE-2021-44228 | CRITICAL (10.0) | log4j-core | 2.14.1 | ✅ Fixed |
| CVE-2019-17267 | CRITICAL (9.8) | jackson-databind | 2.9.8 | ✅ Fixed |

## Mitigasi
- Upgrade log4j-core ke 2.21.1
- Hapus versi manual jackson-databind (gunakan Spring BOM)
- Tambah GitHub Actions workflow untuk automated scanning

## Evidence
Laporan HTML lengkap tersimpan di evidence/modul-2/
```

4. Klik **"Create pull request"**

---

### ANALISIS REVIU

**Mengapa pendekatan ini aman?**

**1. Spring Boot BOM sebagai Safety Net**
Spring Boot menggunakan BOM (Bill of Materials) — daftar versi library yang sudah diuji secara keamanan. Dengan tidak menentukan versi secara manual, kamu otomatis mendapat versi yang kompatibel dan aman ketika upgrade Spring Boot parent.

**2. Fail-Fast dengan `failBuildOnCVSS=7`**
Mengatur batas CVSS 7 memastikan build otomatis gagal jika ada kerentanan High atau Critical. Ini mencegah kode bermasalah masuk ke production tanpa disadari.

**3. GitHub Actions sebagai Automated Security Gate**
Dengan integrasi ke CI/CD, setiap push ke GitHub akan otomatis memicu scan keamanan. Tim langsung mendapat notifikasi jika ada CVE baru ditemukan pada dependency yang sudah ada.

---

---

## MODUL 3: CORS (Cross-Origin Resource Sharing)

### TUJUAN BELAJAR

Setelah menyelesaikan lab ini, kamu akan mampu:
- Memahami cara kerja mekanisme CORS di browser dan server
- Mengidentifikasi miskonfigurasi CORS yang umum di Spring Boot
- Mensimulasikan serangan akibat CORS yang terlalu permisif
- Mengimplementasikan konfigurasi CORS yang aman menggunakan Spring Security

---

### SKENARIO KERENTANAN

Kamu membangun REST API untuk aplikasi mobile banking. Frontend React-nya di-host di `https://app.mybank.com`. Seorang developer, karena frustasi dengan error CORS saat development, menambahkan `allowedOrigins("*")` ke konfigurasi. Config ini tidak sempat dihapus saat deploy ke production.

Sekarang, sebuah website jahat bisa membuat request AJAX ke API kamu **atas nama user yang sedang login**, dan mencuri data atau bahkan melakukan transaksi!

---

### LANGKAH 1: Buat Branch untuk Modul 3

```bash
# Kembali ke main terlebih dahulu
git checkout main
git pull origin main

# Buat branch baru untuk modul 3
git checkout -b lab/modul-3-cors-misconfiguration
```

---

### VULNERABLE CODE

**Buat file `src/main/java/com/lab/security/config/VulnerableSecurityConfig.java`**

```java
package com.lab.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// ⚠️ KODE RENTAN — Hanya untuk simulasi lab!
@Configuration
public class VulnerableSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(vulnerableCorsConfig()))
            .csrf(csrf -> csrf.disable())  // ← Bahaya: CSRF dimatikan
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    // ❌ SANGAT BERBAHAYA: Semua origin diizinkan
    @Bean
    CorsConfigurationSource vulnerableCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");      // ← Bahaya #1: Semua domain
        config.addAllowedMethod("*");      // ← Bahaya #2: Semua HTTP method
        config.addAllowedHeader("*");      // ← Bahaya #3: Semua header
        // config.setAllowCredentials(true); // ← Ini bahkan tidak bisa dikombinasikan dengan "*"

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
```

**Buat file `src/main/java/com/lab/security/controller/UserApiController.java`**

```java
package com.lab.security.controller;

import com.lab.security.entity.User;
import com.lab.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// ❌ @CrossOrigin terlalu permisif di level controller
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")  // ← BERBAHAYA!
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String role) {
        return userRepository.findByRole(role);
    }
}
```

```bash
# Commit kode rentan
git add src/
git commit -m "lab(modul3): add vulnerable CORS configuration for simulation

VULNERABILITIES INTRODUCED (simulation only):
- allowedOrigins(*): Allows ANY domain to make cross-origin requests
- allowedMethods(*): All HTTP methods permitted from any origin  
- allowedHeaders(*): All headers accepted from any origin
- CSRF disabled: Removes cross-site request forgery protection
- @CrossOrigin(*) on controller level: Redundant but shows bad practice"
```

---

### EXPLOITATION STEP

**Langkah 1: Jalankan Aplikasi**

```bash
mvn spring-boot:run
```

Tunggu hingga muncul:
```
Started WebSecurityLabApplication in 3.x seconds
```

**Langkah 2: Buat Halaman "Penyerang" di Codespaces**

Buat file `evidence/modul-3/evil-page.html`:

```bash
mkdir -p evidence/modul-3

cat > evidence/modul-3/evil-page.html << 'HTMLEOF'
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <title>🔴 Simulasi Halaman Attacker - Lab CORS</title>
    <style>
        body { font-family: monospace; background: #1a1a1a; color: #00ff00; padding: 20px; }
        pre { background: #000; padding: 15px; border: 1px solid #333; overflow-x: auto; }
        .success { color: #ff4444; }
        .safe { color: #00ff00; }
        button { background: #ff4444; color: white; border: none; padding: 10px 20px;
                 cursor: pointer; margin: 5px; border-radius: 4px; }
    </style>
</head>
<body>
    <h2>🔴 Simulasi Halaman Penyerang (evil-site.com)</h2>
    <p>Halaman ini berada di origin BERBEDA dari API target.</p>
    <p>Target API: <strong id="targetUrl">http://localhost:8080/api/users</strong></p>
    <hr>

    <button onclick="attackVulnerable()">🎯 Serang Endpoint Rentan</button>
    <button onclick="attackSecure()">🛡️ Coba Serang Endpoint Aman</button>
    <button onclick="clearOutput()">🗑️ Clear</button>

    <h3>Output:</h3>
    <pre id="output">Klik tombol di atas untuk memulai simulasi...</pre>

    <script>
        const TARGET_URL = 'http://localhost:8080';

        function log(msg, isError) {
            const el = document.getElementById('output');
            const time = new Date().toLocaleTimeString();
            el.innerHTML += `[${time}] ${msg}\n`;
            el.scrollTop = el.scrollHeight;
        }

        function clearOutput() {
            document.getElementById('output').innerHTML = '';
        }

        async function attackVulnerable() {
            log('🎯 Mencoba akses ke endpoint rentan dari evil-site.com...');
            log('📤 Mengirim request GET ke: ' + TARGET_URL + '/api/users');

            try {
                const response = await fetch(TARGET_URL + '/api/users', {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' }
                });

                if (response.ok) {
                    const data = await response.json();
                    log('');
                    log('⚠️  PERINGATAN: REQUEST BERHASIL! DATA BOCOR!');
                    log('📦 Data yang berhasil dicuri:');
                    log(JSON.stringify(data, null, 2));
                    log('');
                    log('💀 CORS misconfiguration memungkinkan data dicuri!');
                } else {
                    log('❌ Request ditolak server: HTTP ' + response.status);
                }
            } catch (err) {
                log('');
                log('✅ AMAN: Browser memblokir request!');
                log('Error: ' + err.message);
                log('CORS policy mencegah data bocor ke origin lain.');
            }
        }

        async function attackSecure() {
            log('🛡️ Mencoba serang endpoint aman...');
            log('📤 Request ke: ' + TARGET_URL + '/api/secure/users');

            try {
                const response = await fetch(TARGET_URL + '/api/secure/users');
                if (response.ok) {
                    log('⚠️ Endpoint aman berhasil diserang! (Konfigurasi masih salah)');
                } else {
                    log('✅ AMAN: Server menolak request dari origin tidak dikenal.');
                }
            } catch (err) {
                log('✅ AMAN: CORS policy memblokir request.');
                log('Pesan: ' + err.message);
            }
        }
    </script>
</body>
</html>
HTMLEOF
```

**Langkah 3: Serve Evil Page dari Port Berbeda**

Buka terminal BARU di Codespaces (klik `+` di panel terminal):

```bash
# Di terminal kedua, serve evil page dari port 9999
cd evidence/modul-3
python3 -m http.server 9999
```

Di Codespaces, forward port 9999:
1. Klik tab **"PORTS"**
2. Klik **"Forward a Port"**
3. Masukkan `9999`
4. Buka URL port 9999 di browser

**Langkah 4: Uji via cURL (di terminal pertama)**

```bash
# Test 1: Preflight request dari evil origin
echo "=== TEST 1: Preflight dari origin berbeda ==="
curl -v -X OPTIONS http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  -H "Access-Control-Request-Method: GET" \
  -H "Access-Control-Request-Headers: Content-Type" \
  2>&1 | grep -E "(Access-Control|HTTP/|<)"

# Jika rentan, output akan mengandung:
# Access-Control-Allow-Origin: *   ← BERBAHAYA!

echo ""
echo "=== TEST 2: Request langsung dari evil origin ==="
curl -s http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  -H "Accept: application/json" | python3 -m json.tool
```

**Langkah 5: Simpan Output sebagai Bukti**

```bash
# Simpan output cURL sebagai bukti
curl -v -X OPTIONS http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  -H "Access-Control-Request-Method: GET" \
  2>&1 > evidence/modul-3/exploit-preflight-output.txt

curl -s http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  > evidence/modul-3/exploit-data-stolen.json

# Buat catatan temuan
cat > evidence/modul-3/TEMUAN.md << 'EOF'
# Bukti Lab Modul 3 - CORS Misconfiguration

## Temuan
Endpoint `/api/users` menerima request dari SEMUA origin (allowedOrigins="*").

## Bukti Eksploitasi
- File `exploit-preflight-output.txt`: Response header dari Preflight request
- File `exploit-data-stolen.json`: Data yang berhasil diambil dari origin lain

## Dampak
- Seluruh data user bisa diakses dari website manapun
- Potensi CSRF attack jika dikombinasikan dengan session cookie

## Mitigasi yang Diterapkan
[Isi setelah fix]
EOF

git add evidence/modul-3/
git commit -m "lab(modul3): add CORS exploit evidence

Evidence includes:
- evil-page.html: Simulated attacker page
- exploit-preflight-output.txt: Shows Access-Control-Allow-Origin: *
- exploit-data-stolen.json: Data successfully stolen from different origin"
```

---

### SECURE CODE FIX

**Ganti `VulnerableSecurityConfig.java` dengan konfigurasi yang aman:**

```java
package com.lab.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // ✅ CORS dengan konfigurasi yang sudah kita definisikan
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // ✅ CSRF aktif (jangan dimatikan sembarangan)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/public/**")
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/public/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()  // Dev only
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    // ✅ Konfigurasi CORS yang aman dan eksplisit
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Hanya origin yang terdaftar
        config.setAllowedOrigins(allowedOrigins);

        // ✅ Hanya method yang dibutuhkan
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // ✅ Hanya header yang diperlukan
        config.setAllowedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"
        ));

        // ✅ Header yang bisa dibaca browser dari response
        config.setExposedHeaders(Arrays.asList("Authorization", "X-Custom-Header"));

        // ✅ Izinkan credentials HANYA karena origin sudah spesifik
        config.setAllowCredentials(true);

        // ✅ Cache preflight 1 jam
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // ✅ Hanya terapkan untuk /api/** bukan semua path
        source.registerCorsConfiguration("/api/**", config);

        return source;
    }
}
```

**Update `application.properties`:**

```properties
# CORS - Hanya izinkan origin yang spesifik
app.cors.allowed-origins=http://localhost:3000,http://localhost:4200
```

**Hapus `@CrossOrigin` dari Controller:**

```java
@RestController
@RequestMapping("/api/users")
// ✅ TIDAK ada @CrossOrigin — konfigurasi terpusat di SecurityConfig
public class UserApiController {
    // ... (kode sama, hanya hapus annotation @CrossOrigin)
}
```

**Verifikasi Fix:**

```bash
# Restart aplikasi
# Ctrl+C untuk hentikan, lalu:
mvn spring-boot:run &

# Tunggu startup, lalu test:
echo "=== TEST: Request dari origin DIIZINKAN ==="
curl -s -v http://localhost:8080/api/users \
  -H "Origin: http://localhost:3000" \
  2>&1 | grep "Access-Control"
# Expected: Access-Control-Allow-Origin: http://localhost:3000 ✅

echo ""
echo "=== TEST: Request dari origin TIDAK DIIZINKAN ==="
curl -s -v http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  2>&1 | grep "Access-Control"
# Expected: (tidak ada header Access-Control) ✅

# Simpan output verifikasi
curl -v http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  2>&1 > evidence/modul-3/fix-verification.txt
```

```bash
cat >> evidence/modul-3/TEMUAN.md << 'EOF'

## Mitigasi yang Diterapkan
1. Whitelist origin eksplisit via `app.cors.allowed-origins` di properties
2. Konfigurasi terpusat di `SecurityConfig.java` (hapus @CrossOrigin di controller)
3. Hanya method dan header yang diperlukan yang diizinkan
4. `allowCredentials(true)` hanya valid karena origin sudah spesifik
5. CORS hanya diterapkan pada `/api/**` path

## Hasil Setelah Fix
- Request dari evil-site.com → tidak ada header CORS → browser blokir ✅
- Request dari localhost:3000 → diizinkan dengan header yang tepat ✅
EOF

git add .
git commit -m "fix(modul3): implement secure CORS configuration

Security improvements:
- Replace wildcard (*) with explicit origin whitelist
- Configure CORS globally in SecurityConfig (remove @CrossOrigin)
- Enable CSRF protection
- Restrict allowed methods and headers to minimum required
- Apply CORS only to /api/** paths
- Use application properties for environment-specific origins"

git push origin lab/modul-3-cors-misconfiguration
```

**Buat Pull Request di GitHub** dengan deskripsi analisis kamu.

---

### ANALISIS REVIU

**Mengapa konfigurasi ini aman?**

**1. Origin Whitelist yang Eksplisit**
Server hanya merespons request dari domain terpercaya. Browser akan memblokir semua request dari origin lain sebelum data dikirim ke website penyerang.

**2. `allowCredentials=true` + Origin Spesifik**
Kombinasi ini adalah yang paling kritis. Browser menolak mengirimkan cookie/session ke server jika origin tidak cocok dengan yang dikonfigurasi — mencegah serangan CSRF via CORS.

**3. Konfigurasi Terpusat**
Satu konfigurasi global di `SecurityConfig` memastikan konsistensi dan memudahkan audit keamanan — tidak ada celah yang terlewat di controller mana pun.

**4. Least Privilege pada Method & Header**
Hanya izinkan yang benar-benar dibutuhkan. Ini menerapkan prinsip least privilege pada level protokol HTTP.

---

---

## MODUL 4: SQL Injection

### TUJUAN BELAJAR

Setelah menyelesaikan lab ini, kamu akan mampu:
- Memahami bagaimana SQL Injection bekerja di aplikasi Spring Boot
- Mensimulasikan serangan SQLi pada JPQL dan Native Query yang rentan
- Mencegah SQLi menggunakan Spring Data JPA dengan Parameterized Query
- Menerapkan validasi input sebagai defense-in-depth

---

### SKENARIO KERENTANAN

Sebuah aplikasi manajemen karyawan punya fitur pencarian user berdasarkan nama. Developer junior yang terburu-buru membuat query dengan cara string concatenation — langsung menggabungkan input user ke dalam query SQL. Ini membuka pintu lebar untuk attacker memanipulasi logika database.

---

### LANGKAH 1: Buat Branch untuk Modul 4

```bash
git checkout main
git pull origin main
git checkout -b lab/modul-4-sql-injection
```

---

### VULNERABLE CODE

**Buat `src/main/java/com/lab/security/repository/VulnerableUserRepository.java`**

```java
package com.lab.security.repository;

import com.lab.security.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class VulnerableUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // ❌ RENTAN: String concatenation langsung ke Native Query
    public List<User> searchByUsernameVulnerable(String username) {
        // Bayangkan input: ' OR '1'='1
        // Query yang terbentuk: SELECT * FROM users WHERE username = '' OR '1'='1'
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        return entityManager.createNativeQuery(query, User.class).getResultList();
    }

    // ❌ RENTAN: String concatenation di JPQL
    public List<User> searchByEmailVulnerable(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = '" + email + "'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    // ❌ RENTAN: LIKE query tidak aman
    public List<User> searchByRoleVulnerable(String role) {
        String jpql = "SELECT u FROM User u WHERE u.role LIKE '%" + role + "%'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }
}
```

**Buat `src/main/java/com/lab/security/controller/VulnerableController.java`**

```java
package com.lab.security.controller;

import com.lab.security.entity.User;
import com.lab.security.repository.VulnerableUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vulnerable")
public class VulnerableController {

    @Autowired
    private VulnerableUserRepository vulnerableRepo;

    // ❌ Endpoint rentan — tidak ada validasi input sama sekali
    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String username) {
        return vulnerableRepo.searchByUsernameVulnerable(username);
    }

    @GetMapping("/search-email")
    public List<User> searchByEmail(@RequestParam String email) {
        return vulnerableRepo.searchByEmailVulnerable(email);
    }

    @GetMapping("/search-role")
    public List<User> searchByRole(@RequestParam String role) {
        return vulnerableRepo.searchByRoleVulnerable(role);
    }
}
```

```bash
git add src/
git commit -m "lab(modul4): add vulnerable SQL query implementations

VULNERABILITIES INTRODUCED (simulation only):
- Native Query with string concatenation: susceptible to Classic SQLi
- JPQL with string concatenation: susceptible to JPQL injection
- LIKE query without parameterization: susceptible to wildcard injection
- No input validation in controller layer"
```

---

### EXPLOITATION STEP

**Langkah 1: Jalankan Aplikasi**

```bash
mvn spring-boot:run &
# Tunggu startup selesai
```

**Langkah 2: Jalankan Serangan dari Terminal Codespaces**

```bash
mkdir -p evidence/modul-4

echo "======================================"
echo "MODUL 4: SQL INJECTION ATTACK DEMO"
echo "======================================"

# ============================================================
# SERANGAN 1: Classic OR Injection — Bypass filter, ambil semua data
# ============================================================
echo ""
echo "=== SERANGAN 1: OR Injection ==="
echo "Payload: ' OR '1'='1"
echo "Mengirim request..."

curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=' OR '1'='1" \
  -H "Accept: application/json" | python3 -m json.tool \
  > evidence/modul-4/attack-1-or-injection.json

echo "Hasil disimpan di evidence/modul-4/attack-1-or-injection.json"
cat evidence/modul-4/attack-1-or-injection.json

# ============================================================
# SERANGAN 2: UNION-Based Injection — Ekstrak data sensitif
# ============================================================
echo ""
echo "=== SERANGAN 2: UNION Injection ==="
echo "Payload: ' UNION SELECT * FROM USERS --"
echo "Mencoba mengekstrak semua data termasuk password hash..."

curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=' UNION SELECT ID, USERNAME, PASSWORD, EMAIL, ROLE FROM USERS --" \
  -H "Accept: application/json" \
  > evidence/modul-4/attack-2-union-injection.json

cat evidence/modul-4/attack-2-union-injection.json

# ============================================================
# SERANGAN 3: Blind Boolean Injection — Inferensi data dengan True/False
# ============================================================
echo ""
echo "=== SERANGAN 3: Blind Boolean Injection ==="

echo "Payload TRUE (user ada):"
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND '1'='1" \
  -H "Accept: application/json"

echo ""
echo "Payload FALSE (user tidak ada):"
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND '1'='2" \
  -H "Accept: application/json"

# Simpan hasil
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND '1'='1" \
  > evidence/modul-4/attack-3-blind-true.json

curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND '1'='2" \
  > evidence/modul-4/attack-3-blind-false.json

# ============================================================
# SERANGAN 4: Wildcard Injection pada LIKE Query
# ============================================================
echo ""
echo "=== SERANGAN 4: LIKE Query Injection ==="
curl -s -G "http://localhost:8080/api/vulnerable/search-role" \
  --data-urlencode "role=USER' OR '1'='1" \
  > evidence/modul-4/attack-4-like-injection.json

cat evidence/modul-4/attack-4-like-injection.json

echo ""
echo "======================================"
echo "Semua bukti serangan disimpan di evidence/modul-4/"
echo "======================================"
```

**Langkah 3: Buat Laporan Temuan**

```bash
cat > evidence/modul-4/TEMUAN.md << 'EOF'
# Bukti Lab Modul 4 - SQL Injection

## Temuan

### Endpoint: GET /api/vulnerable/search?username=

| Serangan | Payload | Hasil | Dampak |
|---------|---------|-------|--------|
| OR Injection | `' OR '1'='1` | Semua user dikembalikan | Data seluruh user bocor |
| UNION Injection | `' UNION SELECT * FROM USERS --` | Password hash terekspos | Credential theft |
| Blind True | `admin' AND '1'='1` | User admin dikembalikan | Konfirmasi username valid |
| Blind False | `admin' AND '1'='2` | Empty result | Basis blind extraction |
| LIKE Injection | `USER' OR '1'='1` | Semua data | Filter bypass |

## Analisis Root Cause
Query dibangun dengan string concatenation:
```java
String query = "SELECT * FROM users WHERE username = '" + username + "'";
```
Input user diperlakukan sebagai bagian dari instruksi SQL, bukan sebagai data.

## Bukti File
- attack-1-or-injection.json
- attack-2-union-injection.json
- attack-3-blind-true.json / attack-3-blind-false.json
- attack-4-like-injection.json

## Mitigasi
[Isi setelah fix]
EOF

git add evidence/modul-4/
git commit -m "lab(modul4): add SQL injection attack evidence

Attack results demonstrate:
1. OR injection returns ALL users (data exposure)
2. UNION injection extracts password hashes (credential theft)
3. Blind injection confirms valid usernames (reconnaissance)
4. LIKE injection bypasses role filter"
```

---

### SECURE CODE FIX

**Buat `src/main/java/com/lab/security/repository/SecureUserRepository.java`**

```java
package com.lab.security.repository;

import com.lab.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecureUserRepository extends JpaRepository<User, Long> {

    // ✅ METHOD DERIVATION — Spring generate parameterized query otomatis
    Optional<User> findByUsername(String username);

    // ✅ Aman untuk LIKE query
    List<User> findByUsernameContainingIgnoreCase(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    // ✅ @Query dengan Named Parameter — tidak bisa di-inject
    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> findByUsernameSecure(@Param("username") String username);

    // ✅ JPQL LIKE yang aman
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    // ✅ Native Query tetap aman dengan parameter binding
    @Query(value = "SELECT * FROM users WHERE username = :username AND role = :role",
           nativeQuery = true)
    List<User> findByUsernameAndRole(@Param("username") String username,
                                     @Param("role") String role);

    // ✅ Query dinamis yang tetap aman
    @Query("SELECT u FROM User u WHERE " +
           "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
           "(:role IS NULL OR u.role = :role)")
    List<User> searchSecure(@Param("username") String username,
                             @Param("role") String role);
}
```

**Buat `src/main/java/com/lab/security/service/SecureUserService.java`**

```java
package com.lab.security.service;

import com.lab.security.entity.User;
import com.lab.security.repository.SecureUserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class SecureUserService {

    private final SecureUserRepository userRepository;

    public SecureUserService(SecureUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Validasi input sebelum query ke database
    public List<User> searchUsers(
        @NotBlank(message = "Username tidak boleh kosong")
        @Size(min = 2, max = 50, message = "Username harus antara 2-50 karakter")
        @Pattern(regexp = "^[a-zA-Z0-9_\\-\\.]+$",
                 message = "Username hanya boleh huruf, angka, underscore, dash, dan titik")
        String username
    ) {
        // Input sudah divalidasi — lanjut ke repository dengan parameterized query
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    public List<User> searchByRole(
        @NotBlank
        @Pattern(regexp = "^(ADMIN|USER|MODERATOR)$",
                 message = "Role tidak valid")
        String role
    ) {
        return userRepository.findByRole(role);
    }
}
```

**Buat `src/main/java/com/lab/security/controller/SecureController.java`**

```java
package com.lab.security.controller;

import com.lab.security.entity.User;
import com.lab.security.service.SecureUserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/secure")
@Validated
public class SecureController {

    private final SecureUserService userService;

    public SecureController(SecureUserService userService) {
        this.userService = userService;
    }

    // ✅ Input divalidasi, lalu dikirim ke parameterized query
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
        @RequestParam @NotBlank @Size(min = 2, max = 50) String username
    ) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }

    @GetMapping("/search-role")
    public ResponseEntity<List<User>> searchByRole(
        @RequestParam @NotBlank String role
    ) {
        return ResponseEntity.ok(userService.searchByRole(role));
    }
}
```

**Verifikasi Fix:**

```bash
# Hentikan dan restart aplikasi
# Ctrl+C lalu:
mvn spring-boot:run &

echo ""
echo "=== VERIFIKASI FIX: Serangan pada Endpoint AMAN ==="

echo ""
echo "Test 1: OR Injection (seharusnya GAGAL)"
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=' OR '1'='1" \
  -H "Accept: application/json"
# Expected: [] atau 400 Bad Request

echo ""
echo "Test 2: Query Normal (seharusnya BERHASIL)"
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=alice" \
  -H "Accept: application/json" | python3 -m json.tool
# Expected: data alice saja

echo ""
echo "Test 3: UNION Injection (seharusnya GAGAL)"
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=' UNION SELECT * FROM USERS --" \
  -H "Accept: application/json"

# Simpan hasil verifikasi
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=' OR '1'='1" \
  > evidence/modul-4/fix-verification-or-injection.json

curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=alice" \
  > evidence/modul-4/fix-verification-normal-query.json
```

```bash
cat >> evidence/modul-4/TEMUAN.md << 'EOF'

## Mitigasi yang Diterapkan

### 1. Spring Data JPA Method Derivation
```java
List<User> findByUsernameContainingIgnoreCase(String username);
```
Spring otomatis generate: `WHERE LOWER(username) LIKE LOWER(?)` — parameterized.

### 2. @Query dengan Named Parameter
```java
@Query("SELECT u FROM User u WHERE u.username = :username")
List<User> findByUsernameSecure(@Param("username") String username);
```
Input user tidak pernah digabung ke string query, selalu dikirim sebagai parameter terpisah.

### 3. Input Validation di Service Layer
```java
@Pattern(regexp = "^[a-zA-Z0-9_\\-\\.]+$")
```
Karakter SQL berbahaya diblokir sebelum menyentuh layer database.

## Hasil Setelah Fix
- OR injection: [] (tidak ada data bocor) ✅
- UNION injection: 400 Bad Request (validasi gagal) ✅
- Query normal alice: Data alice saja (filter bekerja) ✅
EOF

git add .
git commit -m "fix(modul4): implement parameterized queries and input validation

Security improvements:
- Replace string concatenation with Spring Data JPA method derivation
- Use @Query with @Param for complex queries (no string concatenation)
- Add input validation at service layer (@Pattern, @Size, @NotBlank)
- Separate vulnerable and secure controller/repository for comparison"

git push origin lab/modul-4-sql-injection
```

**Buat Pull Request di GitHub** dengan deskripsi analisis kamu.

---

### ANALISIS REVIU

**Mengapa pendekatan ini aman?**

**1. Parameterized Query — Inti Pertahanan**
Ketika kamu menulis `WHERE username = :username` dan memanggil `.setParameter("username", input)`, JPA tidak pernah memasukkan nilai ke dalam string query. Nilai dikirimkan ke database sebagai data — bukan instruksi SQL. Database engine memperlakukan nilai parameter murni sebagai data literal, sehingga karakter seperti `'`, `--`, `;` tidak bisa mengubah struktur query.

**2. Spring Data JPA Method Derivation — Aman secara Default**
Method seperti `findByUsername(String username)` selalu menggunakan prepared statement di balik layar. Ini adalah pendekatan paling direkomendasikan karena aman dan tidak perlu menulis query sama sekali.

**3. Input Validation — Defense in Depth**
Validasi dengan `@Pattern(regexp = "^[a-zA-Z0-9_\\-\\.]+$")` menambahkan lapisan pertahanan ekstra. Meskipun parameterized query sudah cukup untuk mencegah SQLi, validasi input mencegah karakter berbahaya bahkan sebelum menyentuh layer database — prinsip **defense in depth**.

---

---

## Panduan Submit Tugas via GitHub

### Cara Submit yang Benar

Setiap mahasiswa wajib memiliki struktur berikut di repository GitHub-nya:

```
websecurity-lab-[NPM]/
├── .devcontainer/devcontainer.json       ← Konfigurasi Codespaces
├── .github/workflows/security-scan.yml  ← CI/CD pipeline
├── evidence/
│   ├── modul-2/
│   │   ├── TEMUAN.md                    ← Laporan analisis
│   │   └── dependency-check-report.html ← Report scan
│   ├── modul-3/
│   │   ├── TEMUAN.md
│   │   ├── exploit-preflight-output.txt
│   │   └── fix-verification.txt
│   └── modul-4/
│       ├── TEMUAN.md
│       ├── attack-*.json                ← Bukti serangan
│       └── fix-verification-*.json     ← Bukti fix berhasil
├── src/                                 ← Semua kode lab
└── pom.xml
```

### Daftar Pull Request yang Harus Dibuat

| # | Branch | PR Title | Status |
|---|--------|----------|--------|
| 1 | `lab/modul-2-cve-detection` | [Modul 2] CVE Detection & Remediation | ⬜ Belum |
| 2 | `lab/modul-3-cors-misconfiguration` | [Modul 3] CORS Misconfiguration & Fix | ⬜ Belum |
| 3 | `lab/modul-4-sql-injection` | [Modul 4] SQL Injection Attack & Prevention | ⬜ Belum |

### Format Deskripsi Pull Request

Setiap PR WAJIB memiliki deskripsi dengan format berikut:

```markdown
## 📋 Ringkasan Modul
[Jelaskan singkat apa yang dikerjakan di modul ini]

## 🔍 Kerentanan yang Ditemukan
[Deskripsi kerentanan yang disimulasikan]

## 💣 Bukti Exploit
[Screenshot atau paste output dari eksploitasi]

## 🛡️ Perbaikan yang Dilakukan
[Jelaskan pendekatan perbaikan dan mengapa aman]

## ✅ Checklist
- [ ] Kode rentan sudah di-commit dengan pesan yang jelas
- [ ] Bukti exploit ada di folder evidence/
- [ ] Kode perbaikan sudah diimplementasikan
- [ ] Verifikasi fix sudah dijalankan dan hasilnya di-commit
- [ ] TEMUAN.md sudah diisi lengkap
```

### Submit ke Dosen

Setelah semua PR dibuat, kirimkan link repository ke dosen:

```
Format pengiriman tugas:
- Nama    : [Nama Lengkap]
- NPM     : [NPM]
- GitHub  : https://github.com/[USERNAME]/websecurity-lab-[NPM]
- PR #1   : [URL Pull Request Modul 2]
- PR #2   : [URL Pull Request Modul 3]
- PR #3   : [URL Pull Request Modul 4]
```

---

---

## 📝 RINGKASAN & CHECKLIST KEAMANAN

### Quick Reference: Do's and Don'ts

| Kategori | ❌ Jangan Lakukan | ✅ Lakukan Ini |
|----------|-----------------|--------------|
| **Dependency** | Biarkan library tidak diupdate | Scan rutin dengan Dependency-Check + CI/CD |
| **Dependency** | Fix versi library secara manual tanpa alasan | Gunakan Spring Boot BOM |
| **CORS** | `allowedOrigins("*")` di API yang pakai session | Daftarkan origin secara eksplisit |
| **CORS** | `@CrossOrigin` di setiap controller | Konfigurasi global di `SecurityConfig` |
| **SQL** | String concatenation di query | Selalu gunakan parameterized query |
| **SQL** | Native query tanpa parameter binding | Gunakan `@Query` dengan `@Param` |
| **Input** | Percaya semua input dari user | Validasi & sanitasi di service layer |
| **Git** | Commit credential/secret ke repository | Gunakan environment variable / GitHub Secrets |

---

### Checklist Keamanan Sebelum Submit

```
Security Lab Checklist
======================

SETUP
[ ] Java 21 terinstall dan aktif (java -version menunjukkan 21)
[ ] Maven mendeteksi Java 21 (mvn -version)
[ ] devcontainer.json dikonfigurasi dengan benar
[ ] Repository di-fork ke akun GitHub sendiri

MODUL 2 - CVE
[ ] Dependency rentan di-commit dengan pesan jelas
[ ] Dependency-Check berhasil dijalankan
[ ] Laporan HTML tersimpan di evidence/modul-2/
[ ] Fix dependency sudah dilakukan
[ ] Build SUCCESS setelah fix
[ ] GitHub Actions workflow ditambahkan
[ ] PR dibuat dengan deskripsi lengkap

MODUL 3 - CORS
[ ] Konfigurasi CORS rentan di-commit
[ ] Bukti exploit tersimpan (cURL output / evil-page.html)
[ ] Konfigurasi aman diimplementasikan (whitelist origin)
[ ] @CrossOrigin(*) dihapus dari semua controller
[ ] Verifikasi fix berhasil (cURL output disimpan)
[ ] PR dibuat dengan deskripsi lengkap

MODUL 4 - SQL INJECTION
[ ] Query rentan (string concatenation) di-commit
[ ] Minimal 3 jenis serangan berhasil dibuktikan
[ ] Bukti serangan tersimpan di evidence/modul-4/
[ ] Parameterized query diimplementasikan
[ ] Input validation ditambahkan
[ ] Verifikasi: serangan gagal pada endpoint aman
[ ] PR dibuat dengan deskripsi lengkap

GITHUB
[ ] Semua branch sudah di-push ke GitHub
[ ] Semua PR sudah dibuat (3 PR)
[ ] Deskripsi PR sudah diisi lengkap
[ ] Link repository dikirim ke dosen
```

---

### Referensi Lanjutan

- [OWASP Top 10 Official](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [OWASP Dependency-Check](https://jeremylong.github.io/DependencyCheck/)
- [NVD (National Vulnerability Database)](https://nvd.nist.gov/)
- [OWASP SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
- [OWASP CORS Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CORS_Cheat_Sheet.html)
- [GitHub Codespaces Documentation](https://docs.github.com/en/codespaces)
- [GitHub Student Developer Pack](https://education.github.com/pack)
- [SDKMAN — Java Version Manager](https://sdkman.io/)

---

*Modul ini dibuat untuk tujuan edukasi. Semua simulasi serangan hanya boleh dilakukan di environment lab terisolasi (GitHub Codespaces / repository pribadi kamu). Jangan pernah melakukan pengujian penetrasi pada sistem yang bukan milikmu tanpa izin tertulis. Pelanggaran terhadap sistem komputer orang lain merupakan tindakan ilegal.*
