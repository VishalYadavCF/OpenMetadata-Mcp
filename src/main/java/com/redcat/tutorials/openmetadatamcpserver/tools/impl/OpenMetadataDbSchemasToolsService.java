package com.redcat.tutorials.openmetadatamcpserver.tools.impl;

import com.redcat.tutorials.openmetadatamcpserver.tools.OpenMetadataToolsService;
import org.openmetadata.client.api.DatabaseSchemasApi;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OpenMetadataDbSchemasToolsService extends OpenMetadataToolsService {

    private final DatabaseSchemasApi dbSchemasApi;

    public OpenMetadataDbSchemasToolsService(DatabaseSchemasApi dbSchemasApi) {
        this.dbSchemasApi = dbSchemasApi;
    }

    @Tool(name = "get_database_schema_by_fqn", description = "Get a database schema by fully qualified name")
    public String getDatabaseSchemaByFQN(String fqn, String fields, String include) {
        try {
            // Set default value for include parameter
            if (include == null || "".equals(include) || " ".equals(include)) {
                include = "all";
            }

            return dbSchemasApi.getDBSchemaByFQN(fqn, fields, include).toString();
        } catch (Exception e) {
            return "Error retrieving database schema by FQN: " + e.getMessage();
        }
    }

    @Tool(name = "export_database_schema", description = "Export database schema in CSV format")
    public String exportDatabaseSchema(String name, Boolean recursive) {
        try {
            return dbSchemasApi.exportDatabaseSchema(name);
        } catch (Exception e) {
            return "Error exporting database schema: " + e.getMessage();
        }
    }

    @Tool(name = "export_database_schema_async", description = "Export database schema in CSV format asynchronously")
    public String exportDatabaseSchemaAsync(String name) {
        try {
            return dbSchemasApi.exportDatabaseSchema(name);
        } catch (Exception e) {
            return "Error exporting database schema asynchronously: " + e.getMessage();
        }
    }

    @Tool(name = "get_database_schema_by_id", description = "Get a database schema by Id")
    public String getDatabaseSchemaById(String id, String fields, String include) {
        try {
            // Set default value for include parameter
            if (include == null || "".equals(include) || " ".equals(include)) {
                include = "all";
            }

            return dbSchemasApi.getDBSchemaByID(UUID.fromString(id), fields, include).toString();
        } catch (Exception e) {
            return "Error retrieving database schema by ID: " + e.getMessage();
        }
    }

    @Tool(name = "get_database_schema_profiler_config", description = "Get databaseSchema profile config")
    public String getDatabaseSchemaProfilerConfig(String id) {
        try {
            return dbSchemasApi.getDataProfilerConfig1(UUID.fromString(id)).toString();
        } catch (Exception e) {
            return "Error retrieving database schema profiler config: " + e.getMessage();
        }
    }

    @Tool(name = "list_database_schema_versions", description = "List all versions of a database schema")
    public String listAllDatabaseSchemaVersions(String id) {
        try {
            return dbSchemasApi.listAllDBSchemaVersion(UUID.fromString(id)).toString();
        } catch (Exception e) {
            return "Error listing database schema versions: " + e.getMessage();
        }
    }

    @Tool(name = "get_specific_database_schema_version", description = "Get a specific version of the database schema")
    public String getSpecificDatabaseSchemaVersion(String id, String version) {
        try {
            return dbSchemasApi.getSpecificDBSchemaVersion(UUID.fromString(id), version).toString();
        } catch (Exception e) {
            return "Error retrieving specific database schema version: " + e.getMessage();
        }
    }

    @Tool(name = "get_all_database_schemas_by_list_of_id", description = "Get multiple database schemas when a list of id are provided")
    public List<Object> getAllDatabaseSchemasByListOfIds(String ids, String fields, String include) {
        try {
            // Set default value for include parameter
            if (include == null || "".equals(include) || " ".equals(include)) {
                include = "all";
            }

            List<String> idList = Arrays.asList(ids.split(","));
            List<Object> resultList = new ArrayList<>();

            for (String id : idList) {
                try {
                    String schema = dbSchemasApi.getDBSchemaByID(UUID.fromString(id.trim()), fields, include).toString();
                    resultList.add(schema);
                } catch (Exception e) {
                    List<String> errorObj = new ArrayList<>();
                    errorObj.add(e.getMessage());
                }
            }

            return resultList;
        } catch (Exception e) {
            return List.of("Error retrieving database schemas by IDs: " + e.getMessage());
        }
    }
}
