

## 💻 Running the Executable JAR File

This is the most universal and simplest method after you've built your application. This assumes you have already packaged your application into an executable JAR file (usually named something like `your-application-name.jar` or similar, located in the `target/` or `build/libs/` directory).

  * **Prerequisites:** You must have the **Java Runtime Environment (JRE)** installed.
  * **Command (All OSs):**
    ```bash
    java -jar your-application-name.jar
    ```
    > **Note:** Replace `your-application-name.jar` with the actual name of your compiled JAR file.

-----

## 🛠️ Using Build Tools (Maven & Gradle)

This method lets you run the application directly from the project source code using the respective build tool's wrapper script, which handles dependency management and execution.

### 🐘 Maven (Recommended for most IDE projects)

  * **Prerequisites:** Your project must be a Maven project, and you should be in the root directory of the project where the `pom.xml` file is located.
  * **Command (macOS/Linux):**
    ```bash
    ./mvnw spring-boot:run
    ```
  * **Command (Windows - Command Prompt/PowerShell):**
    ```powershell
    .\mvnw spring-boot:run
    ```
    > **Note:** `mvnw` is the Maven Wrapper, which means you don't need to have Maven installed globally. If you have Maven installed globally, you can also use `mvn spring-boot:run`.

### 🏗️ Gradle

  * **Prerequisites:** Your project must be a Gradle project, and you should be in the root directory of the project where the `build.gradle` file is located.
  * **Command (macOS/Linux):**
    ```bash
    ./gradlew bootRun
    ```
  * **Command (Windows - Command Prompt/PowerShell):**
    ```powershell
    .\gradlew bootRun
    ```
    > **Note:** `gradlew` is the Gradle Wrapper, which means you don't need to have Gradle installed globally. If you have Gradle installed globally, you can also use `gradle bootRun`.

-----

## 💡 Quick Summary Table

| Method | Command (macOS/Linux) | Command (Windows) | Notes |
| :--- | :--- | :--- | :--- |
| **Executable JAR** | `java -jar app.jar` | `java -jar app.jar` | Requires a built JAR and JRE. |
| **Maven** | `./mvnw spring-boot:run` | `.\mvnw spring-boot:run` | Runs from source using the Maven Wrapper. |
| **Gradle** | `./gradlew bootRun` | `.\gradlew bootRun` | Runs from source using the Gradle Wrapper. |

