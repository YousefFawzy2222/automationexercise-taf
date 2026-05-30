# Test Automation Framework

A Java-based UI test automation framework built with Selenium WebDriver, TestNG, Maven, and Allure Reports.  
The framework follows a layered structure with drivers, page objects, reusable actions, validations, listeners, logging, screenshots, screen recording, and report generation.

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Allure Reports
- AspectJ Weaver
- Log4j2
- Apache Commons IO

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.automationexercise
│   │       ├── drivers          # Browser driver factories and GUI driver wrapper
│   │       ├── listeners        # TestNG listeners for reporting and artifacts
│   │       ├── media            # Screenshots and screen recording
│   │       ├── pages            # Page objects and components
│   │       ├── utils            # Waits, logs, reports, file utils, readers
│   │       └── validations      # Assertions and verifications
│   └── resources
│       ├── webapp.properties
│       ├── waits.properties
│       ├── video.properties
│       ├── allure.properties
│       └── META-INF/services/org.testng.ITestNGListener
└── test
    └── java
        └── com.automationexercise.tests
