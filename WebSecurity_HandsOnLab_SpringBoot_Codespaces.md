# 🔐 Modul Hands-on Lab: Web Security dengan Spring Boot 3.2+ (Java 21)
### Berbasis GitHub Codespaces — Tanpa Instalasi Lokal!

> **Target Audience:** Developer / Mahasiswa 
> **Stack:** Spring Boot 3.2+, Java 21, Thymeleaf, Bootstrap Admin, H2 Database, Spring Security
> **Platform Lab:** GitHub Codespaces (cloud-based, browser-based IDE)

---

## 📋 DAFTAR ISI

1. [PRE-REQUISITE: Persiapan Akun & Environment GitHub](#pre-requisite-persiapan-akun--environment-github)
2. [Modul 0 – Setup Project di GitHub Codespaces](#modul-0-setup-project-di-github-codespaces)
3. [Modul 1 – OWASP Top 10: Pengenalan & Relevansi di Ekosistem Spring](#modul-1-owasp-top-10)
5. [Modul 2 – CORS: Miskonfigurasi & Konfigurasi Aman](#modul-3-cors-cross-origin-resource-sharing)
6. [Modul 3 – SQL Injection: Simulasi Serangan & Pencegahan](#modul-4-sql-injection)

---

---

## PRE-REQUISITE: Persiapan Akun & Environment GitHub


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

1. Buka URL repository template yang diberikan dosen https://github.com/sinagajunior/web-security-handson
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


curl -s http://localhost:8080/api/users \
  -H "Origin: https://evil-site.com" \
  



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
 
curl -s -G "http://localhost:8080/api/vulnerable/search" \
  --data-urlencode "username=admin' AND '1'='2" 
 

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



## Mitigasi
[Isi setelah fix]
EOF



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
  --data-urlencode "username=' OR '1'='1" 

curl -s -G "http://localhost:8080/api/secure/search" \
  --data-urlencode "username=alice" 
```


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


Security improvements:
- Replace string concatenation with Spring Data JPA method derivation
- Use @Query with @Param for complex queries (no string concatenation)
- Add input validation at service layer (@Pattern, @Size, @NotBlank)
- Separate vulnerable and secure controller/repository for comparison"


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
