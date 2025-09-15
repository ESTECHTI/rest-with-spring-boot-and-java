# Copilot Instructions for AI Agents

## Project Overview

This is a RESTful Spring Boot application for managing `Person` entities. The codebase follows standard layered architecture with clear separation of concerns:

- **Controllers** (`controllers/`): Handle HTTP requests and responses. Example: `PersonController.java`.
- **Services** (`services/`): Business logic and orchestration. Example: `PersonServices.java`.
- **Repositories** (`repository/`): Data access layer using Spring Data JPA. Example: `PersonRepository.java`.
- **Models/DTOs** (`model/`, `data/dto/`): Domain objects and data transfer objects.
- **Exception Handling** (`exception/`): Custom exceptions and global handler.
- **Configuration** (`config/`): App-wide config, e.g., `WebConfig.java`.
- **Database Migrations** (`resources/db/migration/`): Flyway SQL scripts for schema and seed data.

## Key Workflows

- **Build:** Use Maven (`mvn clean install`) from the project root (`pom.xml`).
- **Run:** Start with `mvn spring-boot:run` or run the built JAR in `target/`.
- **Test:** Unit tests are in `src/test/java/`. Run with `mvn test`.
- **Database:** Uses Flyway for migrations. SQL scripts are in `src/main/resources/db/migration/`.

## Project-Specific Patterns

- **Exception Handling:** Custom exceptions in `exception/`, with a global handler in `exception/hadler/CustomEntityResponseHandler.java`.
- **DTO Mapping:** Use `mapper/ObjectMapper.java` for converting between entities and DTOs.
- **Configuration:** Centralized in `config/WebConfig.java`.
- **ResourceNotFound:** Use `ResourceNotFoundException` for missing entities.

## Integration Points

- **Spring Boot:** Main entry point is `Startup.java`.
- **JPA/Hibernate:** Data access via Spring Data JPA repositories.
- **Flyway:** Automatic DB migrations on startup.

## Conventions

- **Package Structure:** Follows `br.com.estech` root, with subfolders for each layer.
- **Naming:** Classes and files are named for their roles (e.g., `PersonController`, `PersonServices`).
- **Testing:** Mirror main package structure in `src/test/java/`.

## Examples

- To add a new entity, create model, repository, service, controller, and migration SQL script.
- For new endpoints, add methods to the relevant controller and service.
- For custom error responses, extend `ExceptionResponse` and update the handler.

## References

- Main config: `src/main/resources/application.yml`
- Main class: `src/main/java/br/com/estech/Startup.java`
- Example controller: `src/main/java/br/com/estech/controllers/PersonController.java`
- Example migration: `src/main/resources/db/migration/V1__Create_Table_Person.sql`

---

For unclear or missing conventions, ask the user for clarification before making assumptions.
