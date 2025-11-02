# 📂 Acceso-a-Datos — Java File Access Practice

<p align="center">
  <img src="https://img.shields.io/badge/Project-Acceso%20a%20Datos-52796F?style=for-the-badge&logoColor=white" alt="Project Badge">
  <img src="https://img.shields.io/badge/Language-Java-9B5DE5?style=for-the-badge&logo=java&logoColor=white" alt="Java Badge">
  <img src="https://img.shields.io/badge/Database-MySQL-00758F?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL Badge">
  <img src="https://img.shields.io/badge/Topic-Data%20Access%20%26%20Persistence-84A59D?style=for-the-badge" alt="Topic Badge">
  <img src="https://img.shields.io/badge/Environment-JDBC%20%7C%20DAO%20Pattern-FEC89A?style=for-the-badge" alt="Environment Badge">
  <img src="https://img.shields.io/badge/Status-In%20Progress-FD6F96?style=for-the-badge" alt="Status Badge">
</p>

This repository contains my coursework for the *Acceso a Datos* (**Data Access**) subject in the second year of the *DAM* (**Multiplatform Application Development**) program.

Each project focuses on a different aspect of **Java I/O** — from the fundamentals of the *File class* to advanced buffered reading/writing, random access, and applied exercises such as **log systems**, **JSON parsing**, **environment loading**, and **incremental backups**.

All exercises are written entirely in **Java**:

- 📄 Primera práctica de Acceso a Datos → Basic file management (File, directories, URIs).

- 📄 Segunda práctica de Acceso a Ficheros en Java → Stream I/O system (Reader, Writer, buffering, RandomAccessFile`).

*The repository is continuously updated as the course progresses.*

### 🗂️ Project Structure
```
Acceso-a-Datos/
│
├── resources/                      # Input/output sample files
│
└── src/edu/thepower/accesodatos/
    ├── primerapractica/            # First practice: basic File handling
    │   ├── Fichero1.java           # FileReader example
    │   ├── Fichero2.java           # FileWriter example
    │   ├── Fichero3.java           # BufferedReader example
    │   ├── Fichero4.java           # BufferedWriter example
    │   ├── Fichero5.java           # RandomAccessFile example
    │   ├── Fichero7.java
    │   ├── Fichero8.java
    │   └── Fichero9.java
    │
    └── segundapractica/            # Second practice: stream-based exercises
        ├── ejercicio1/
        |   |  └── optional/               # Optional extended exercises
        |   │     ├── EjercicioOpcional1Json.java
        |   │     └── JsonSimple.java
        |   |
        │   ├── AnalizadorTexto.java
        │   ├── EstadisticasTexto.java
        │   └── Ejercicio1Contador.java
        │
        ├── ejercicio2/
        |   |  └── optional/               # Optional extended exercises
        |   │     ├── EjercicioOpcional2Env.java
        |   │     └── EnvLoader.java
        |   |
        │   ├── MergeArchivos.java
        │   └── Ejercicio2Merge.java
        │
        ├── ejercicio3/
        |   |  └── optional/               # Optional extended exercises
        |   │     ├── EjercicioOpcional3Backup.java
        |   │     └── BackupIncremental.java
        |   |
        │   ├── NivelLog.java
        │   ├── SistemaLog.java
        │   └── Ejercicio3Log.java
        │
        ├── EjemploFileReader.java
        ├── EjemploFileWriter.java
        ├── EjemploBufferedReader.java
        ├── EjemploBufferedWriter.java
        └── EjemploRandomAccessFile.java
```
## ⚙️ Technologies and Tools
| Category | Description |
|-----------|-------------|
| **Language** | Java 17+ |
| **Libraries** | Standard `java.io`, `java.nio.charset.StandardCharsets` |
| **Encoding** | UTF-8 (explicitly set in all readers/writers) |
| **IDE** | IntelliJ IDEA |
| **Version Control** | Git + GitHub |

## 📚 Summary of Practices
### 🧱 First Practice — Basic File Management

> *Based on Primera práctica de Acceso a Datos*

Covers:

- Use of the File class (exists(), isDirectory(), getAbsolutePath(), etc.)

- Directory and file creation with mkdir() and createNewFile()

- Path and URI conversion

- Exploration of folders and file attributes

- Small utilities simulating a file assistant and library organizer

Classes: `Fichero1` – `Fichero9`


### 💾 Second Practice — Java I/O Streams

> *Based on Segunda práctica de Acceso a Ficheros en Java*

Focuses on text-based input/output using `Reader` and `Writer` streams, with try-with-resources, buffering, and exception handling.

### Exercise 1 — Word Counter & Statistics

Reads a text file and generates:

- Number of lines, words, and characters

- Longest word and its length
Displays results in console and writes them to a file.

### Exercise 2 — File Merge with Filtering

Combines multiple files into one output file, keeping only the lines containing a specific keyword.
Shows per-file statistics and total lines written.

### Exercise 3 — Logging System with Rotation

Implements a lightweight log writer that:

- Adds timestamps and levels (INFO, WARNING, ERROR)

- Rotates the log when reaching a size limit (e.g., 1 KB)

### 🧩 Optional Exercises
### Optional 1 — Simple JSON Parser

Reads and writes flat JSON key–value pairs without external libraries.
Demonstrates string parsing, escaping, and file writing with formatted output.

### Optional 2 — Environment Variable Loader

Parses a `.env` file into a `Map<String, String>`, ignoring blank lines and comments.
Provides a helper `getEnv()` method with default values.

### Optional 3 — Incremental Backup

Performs backup only for files new or modified since the last recorded backup timestamp.
Stores backup metadata in `.lastbackup`.

## 🧠 Learning Objectives

- Understand the Java I/O hierarchy (Reader, Writer, InputStream, OutputStream)

- Manage files and directories using the File API

- Perform efficient reading and writing with buffering

- Work safely with character encoding (UTF-8)

- Use RandomAccessFile for non-sequential access

- Apply good practices:

  - try-with-resources

  - clear variable names and comments

  - proper exception handling

## ▶️ How to Run
```
# Clone repository
git clone https://github.com/yourusername/Acceso-a-Datos.git

# Compile all sources
javac src/edu/thepower/accesodatos/**/*.java

# Run any exercise
java edu.thepower.accesodatos.segundapractica.ejercicio1.Ejercicio1Contador
java edu.thepower.accesodatos.segundapractica.ejercicio2.Ejercicio2Merge
java edu.thepower.accesodatos.segundapractica.ejercicio3.Ejercicio3Log
```
## 🧾 Author

### Génesis Vaca Palma
### *🎓 2º DAM – Desarrollo de Aplicaciones Multiplataforma*
💡 All exercises written manually.

## 🔄 Future Updates

This repository will continue evolving throughout the academic year.

*Stay tuned — each new delivery will expand this repository with cleaner examples and detailed documentation.*
