package com.redcat.tutorials.openmetadatamcpserver.tools.impl;

import com.redcat.tutorials.openmetadatamcpserver.tools.OpenMetadataToolsService;
import feign.Param;
import org.openmetadata.client.api.DatabasesApi;
import org.openmetadata.client.model.Database;
import org.openmetadata.client.model.EntityHistory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OpenMetadataDatabaseToolsService extends OpenMetadataToolsService {

    private final DatabasesApi databasesApi;

    public OpenMetadataDatabaseToolsService(DatabasesApi databasesApi) {
        this.databasesApi = databasesApi;
    }

    @Tool(name = "list_databases", description = "Get a list of databases, optionally filtered by `service` it " +
            "belongs to. Use `fields` parameter to get only necessary fields. Use cursor-based pagination to limit the" +
            " number entries in the list using `limit` and (`before` or `after`) query params. Pass either before or after. " +
            "If both passed error will be thrown ")
    public String listDatabases(@Param("fields") String fields, @Param("service") String service, @Param("limit") Integer limit,
                                      @Param("before") String before, @Param("after") String after, @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        DatabasesApi.ListDatabasesQueryParams queryParams = null;
        if(!"".equals(after) && after != null) {
            queryParams = new DatabasesApi.ListDatabasesQueryParams()
                    .fields(fields)
                    .service(service)
                    .limit(limit)
                    .after(after)
                    .include(include);
        }
        if(!"".equals(before) && before != null) {
            queryParams = new DatabasesApi.ListDatabasesQueryParams()
                    .fields(fields)
                    .service(service)
                    .limit(limit)
                    .before(before)
                    .include(include);
        }
        return databasesApi.listDatabases(queryParams).toString();
    }

    @Tool(name = "get_database_by_fqn", description = "Get a database by fully qualified name")
    public Database getDatabaseByFQN(@Param("fqn") String fqn, @Param("fields") String fields, @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        DatabasesApi.GetDatabaseByFQNQueryParams queryParams = new DatabasesApi.GetDatabaseByFQNQueryParams()
                .fields(fields)
                .include(include);
        return databasesApi.getDatabaseByFQN(fqn, queryParams);
    }

    @Tool(name = "get_database_by_id", description = "Get a database by Id")
    public String getDatabaseById(@Param("id") UUID id, @Param("fields") String fields, @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        DatabasesApi.GetDatabaseByIDQueryParams queryParams = new DatabasesApi.GetDatabaseByIDQueryParams()
                .fields(fields)
                .include(include);
        return databasesApi.getDatabaseByID(id, queryParams).toString();
    }

//    @Tool(name = "export_database", description = "Export database in CSV format")
//    public String exportDatabase(@Param("name") String name) {
//        return databasesApi.exportDatabase1(name, true);
//    }

//    @Tool(name = "export_database_async", description = "Export database in CSV format asynchronously")
//    public CSVExportResponse exportDatabaseAsync(@Param("name") String name, @Param("recursive") Boolean recursive) {
//        return databasesApi.exportDatabase1(name, recursive);
//    }

    @Tool(name = "get_data_profiler_config", description = "Get database profile config")
    public String getDataProfilerConfig(@Param("id") String id) {
        return databasesApi.getDataProfilerConfig(UUID.fromString(id)).toString();
    }

    @Tool(name = "list_all_database_versions", description = "Get a list of all the versions of a database")
    public EntityHistory listAllDatabaseVersions(@Param("id") UUID id) {
        return databasesApi.listAllDatabaseVersion(id);
    }

    @Tool(name = "get_specific_database_version", description = "Get a version of the database by given Id")
    public String getSpecificDatabaseVersion(@Param("id") UUID id, @Param("version") String version) {
        return databasesApi.getSpecificDatabaseVersion(id, version).toString();
    }

    @Tool(name = "get_all_databases_from_list_of_id", description = "Get a list of databases provided a list of id is given in input")
    public List<String> getAllDatabasesByListOfIds(@Param("ids") List<String> ids, @Param("fields") String fields, @Param(value="include") String include) {
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        List<String> databases = new ArrayList<>();
        for (String id : ids) {
            try {
                String database = getDatabaseById(UUID.fromString(id), fields, include);
                databases.add(database);
            } catch (Exception e) {
                // Log error and continue with the next ID
                throw new RuntimeException("Recieved Error: " + e.getMessage());
            }
        }
        return databases;
    }
}
