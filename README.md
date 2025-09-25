# GhostNetFishing

Prototypische Webanwendung zur Meldung, Disposition und Bergung von Geisternetzen.

## Features (aktueller Stand)
- Meldung von Geisternetzen (anonym oder mit Kontakt)
- Zuordnung einer bergenden Person je Netz
- Statusführung (*GEMELDET* → *BERGUNG_BEVORSTEHEND* → *GEBORGEN* / *VERSCHOLLEN*)
- Listenansicht (offen/geborgen), einfache Kartenansicht
- Schlanke Fehlerrückmeldungen (Controller Advice)

## Tech-Stack
- Java 25, Spring Boot 3.5.x
- Spring MVC, Thymeleaf
- Spring Data JPA, Hibernate, MySQL
- Gradle (Wrapper)
- Docker Compose (MySQL)

## Voraussetzungen
- JDK 25 (oder per Gradle Toolchains/JDK 21 anpassen)
- Docker & Docker Compose
- (Optional) Git

## Setup

### 1.Datenbank starten
```bash
docker compose up -d
```

### 2. Anwendung bauen & starten
```bash
./gradlew clean build
```

# Lokal starten
```bash
./gradlew bootRun
```

### 4. Tests
Services (Unit-Tests):
```bash
./gradlew test --tests ghostnetfishing.demo.service.GhostNetServiceTest
```
