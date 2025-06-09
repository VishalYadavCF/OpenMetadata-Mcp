# OpenMetadata MCP Server

This project provides a Spring Boot application that integrates with the OpenMetadata Java client and exposes a set of tools and APIs for interacting with OpenMetadata resources. It is designed to be used as a backend service for managing and querying metadata entities such as databases, tables, schemas, and more, leveraging the Model Context Protocol (MCP) and Spring AI tooling.

## Features
- **OpenMetadata Integration**: Connects to an OpenMetadata server using JWT authentication.
- **Spring Boot**: Built with Spring Boot for easy configuration and deployment.
- **Tooling Services**: Exposes a variety of metadata management tools as injectable Spring services, including database, schema, and table operations.
- **MCP Server**: Integrates with Spring AI's MCP server for tool registration and management.
- **Extensible**: Easily add new tools/services for additional OpenMetadata APIs.

## Project Structure
```
openmetadata-mcp-server/
├── src/
│   ├── main/
│   │   ├── java/com/redcat/tutorials/openmetadatamcpserver/
│   │   │   ├── AppConfig.java                # Spring configuration for OpenMetadata client
│   │   │   ├── CustomOpenMetadata.java       # Custom Feign client builder for OpenMetadata
│   │   │   ├── OpenMetadataClientConfig.java # Beans for OpenMetadata API clients
│   │   │   ├── OpenmetadataMcpServerApplication.java # Main Spring Boot application
│   │   │   └── tools/
│   │   │       ├── OpenMetadataToolsService.java      # Abstract base for tool services
│   │   │       └── impl/
│   │   │           ├── OpenMetadataDatabaseToolsService.java
│   │   │           ├── OpenMetadataDbSchemasToolsService.java
│   │   │           └── OpenMetadataTablesToolsService.java
│   │   └── resources/
│   │       └── application.properties        # Application configuration
├── pom.xml                                  # Maven build file
└── README.md                                 # Project documentation
```

## Dependency Summary
This project uses the following main dependencies (see `pom.xml` for details):

- **Spring Boot Starter Web**: Provides core Spring Boot web application features.
- **Spring AI MCP Server WebMVC**: Integrates Spring AI's Model Context Protocol (MCP) server for tool registration and management.
- **OpenMetadata Java Client**: Official Java client for interacting with OpenMetadata APIs (version 1.3.3).
- **Lombok**: Reduces boilerplate code in Java (optional, used for annotations).
- **Gson**: For JSON serialization/deserialization.
- **Spring Boot Starter Test**: For testing support (test scope).
- **Spring AI BOM**: Manages Spring AI dependency versions.

All dependencies are managed via Maven, and Java 17 is required.

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Access to an OpenMetadata server

### Configuration
Set the following properties in `src/main/resources/application.properties`:
```
openmetadata.jwt.token=YOUR_JWT_TOKEN
openmetadata.server.host=https://your-openmetadata-server
```

### Build and Run
```
mvn clean install
java -jar target/openmetadata-mcp-server-0.0.1-SNAPSHOT.jar
```

### Tool Services
The following tool services are available and can be extended:
- **OpenMetadataDatabaseToolsService**: Database operations (list, get by FQN/ID, etc.)
- **OpenMetadataDbSchemasToolsService**: Database schema operations
- **OpenMetadataTablesToolsService**: Table operations (list, get, export, profiles, etc.)

Each service exposes methods annotated with `@Tool` for integration with Spring AI MCP.

## Extending
To add new OpenMetadata tools:
1. Create a new service class in `tools/impl/` extending `OpenMetadataToolsService`.
2. Inject the relevant OpenMetadata API client.
3. Annotate methods with `@Tool` and implement the logic.
4. The service will be auto-registered at startup.

## License
This project is for educational/tutorial purposes. See [OpenMetadata](https://github.com/open-metadata/OpenMetadata) for upstream licensing.

## Author
VishalYadavCF a.k.a (Gato_Malo)
