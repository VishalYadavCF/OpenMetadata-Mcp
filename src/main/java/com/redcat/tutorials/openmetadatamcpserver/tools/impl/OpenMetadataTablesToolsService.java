package com.redcat.tutorials.openmetadatamcpserver.tools.impl;

import com.redcat.tutorials.openmetadatamcpserver.tools.OpenMetadataToolsService;
import feign.Param;
import org.openmetadata.client.api.TablesApi;
import org.openmetadata.client.model.EntityHistory;
import org.openmetadata.client.model.Table;
import org.openmetadata.client.model.TableList;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OpenMetadataTablesToolsService extends OpenMetadataToolsService {

    private final TablesApi tablesApi;

    public OpenMetadataTablesToolsService(TablesApi tablesApi) {
        this.tablesApi = tablesApi;
    }


    @Tool(name = "list_tables", description = "Get a list of tables, optionally filtered by `database` it belongs to. " +
            "Use `fields` parameter to get only necessary fields. Use cursor-based pagination to limit the number " +
            "entries in the list using `limit` and (`before` or `after`) query params. Pass either before or after. " +
            "If both passed error will be thrown")
    public TableList listTables(@Param("fields") String fields,
                                @Param("database") String database,
                                @Param("databaseSchema") String databaseSchema,
                                @Param("includeEmptyTestSuite") Boolean includeEmptyTestSuite,
                                @Param("limit") Integer limit,
                                @Param("before") String before,
                                @Param("after") String after,
                                @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        TablesApi.ListTablesQueryParams queryParams = null;
        if(!"".equals(after) && after != null) {
            queryParams = new TablesApi.ListTablesQueryParams()
                    .fields(fields)
                    .database(database)
                    .databaseSchema(databaseSchema)
                    .includeEmptyTestSuite(includeEmptyTestSuite)
                    .limit(limit)
                    .after(after)
                    .include(include);
        }
        if(!"".equals(before) && before != null) {
            queryParams = new TablesApi.ListTablesQueryParams()
                    .fields(fields)
                    .database(database)
                    .databaseSchema(databaseSchema)
                    .includeEmptyTestSuite(includeEmptyTestSuite)
                    .limit(limit)
                    .before(before)
                    .include(include);
        }
        return tablesApi.listTables(queryParams);
    }

    @Tool(name = "get_table_by_fully_qualified_name", description = "Get a table by fully qualified name")
    public Table getTableByFullyQualifiedName(@Param("fqn") String fqn,
                             @Param("fields") String fields,
                             @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        TablesApi.GetTableByFQNQueryParams queryParams = new TablesApi.GetTableByFQNQueryParams()
                .fields(fields)
                .include(include);

        return tablesApi.getTableByFQN(fqn, queryParams);
    }

    @Tool(name = "export_table", description = "Export table in CSV format")
    public String exportTable(@Param("name") String name) {
        return tablesApi.exportTable(name);
    }

    @Tool(name = "export_table_async", description = "Export table in CSV format asynchronously")
    public String exportTableAsync(@Param("name") String name) {
        return tablesApi.exportTable(name);
    }

    @Tool(name = "list_column_profiles", description = "Get a list of all column profiles for a table within a time range")
    public String listColumnProfiles(@Param("fqn") String fqn,
                                              @Param("startTs") Long startTs,
                                              @Param("endTs") Long endTs) {
        return tablesApi.listColumnProfiles(fqn, BigDecimal.valueOf(startTs), BigDecimal.valueOf(endTs)).toString();
    }

    @Tool(name = "list_system_profiles", description = "Get a list of all system profiles for a table within a time range")
    public String listSystemProfiles(@Param("fqn") String fqn,
                                              @Param("startTs") Long startTs,
                                              @Param("endTs") Long endTs) {
        return tablesApi.listSystemProfiles(fqn, BigDecimal.valueOf(startTs), BigDecimal.valueOf(endTs)).toString();
    }

    @Tool(name = "list_table_profiles", description = "Get a list of all table profiles for a table within an optional time range")
    public String listTableProfiles(@Param("fqn") String fqn,
                                            @Param("startTs") Long startTs,
                                            @Param("endTs") Long endTs) {
        return tablesApi.listProfiles(fqn, BigDecimal.valueOf(startTs), BigDecimal.valueOf(endTs)).toString();
    }

    @Tool(name = "get_latest_table_profile", description = "Get the latest table and column profile")
    public String getLatestTableProfile(@Param("fqn") String fqn) {
        return tablesApi.getTheLatestTableAndColumnProfile(fqn).toString();
    }

    @Tool(name = "get_table_by_id", description = "Get a table by ID")
    public String getTableById(@Param("id") String id,
                            @Param("fields") String fields,
                            @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        TablesApi.GetTableByIDQueryParams queryParams = new TablesApi.GetTableByIDQueryParams()
                .fields(fields)
                .include(include);

        return tablesApi.getTableByID(UUID.fromString(id), queryParams).toString();
    }

    @Tool(name = "get_sample_data", description = "Get sample data from a table")
    public Table getSampleData(@Param("id") String id) {
        return tablesApi.getSampleData(UUID.fromString(id));
    }

    @Tool(name = "get_table_profiler_config", description = "Get table profile configuration")
    public Table getTableProfilerConfig(@Param("id") String id) {
        return tablesApi.getDataProfilerConfig2(UUID.fromString(id));
    }

    @Tool(name = "list_table_versions", description = "Get a list of all versions of a table")
    public EntityHistory listTableVersions(@Param("id") String id) {
        return tablesApi.listAllTableVersion(id);
    }

    @Tool(name = "get_table_version", description = "Get a specific version of a table")
    public Table getTableVersion(@Param("id") String id, @Param("version") String version) {
        return tablesApi.getSpecificDatabaseVersion1(UUID.fromString(id), version);
    }

    @Tool(name = "get_all_tables_from_list_of_id", description = "Get a list of tables provided a list of id in the input")
    public List<Table> getAllTablesByListOfIds(@Param("ids") List<String> ids,
                                   @Param("fields") String fields,
                                   @Param("include") String include) {
        // Set default value for include parameter
        if (include == null || "".equals(include) || " ".equals(include)) {
            include = "all";
        }

        List<Table> tables = new ArrayList<>();
        for (String id : ids) {
            try {
                TablesApi.GetTableByIDQueryParams queryParams = new TablesApi.GetTableByIDQueryParams()
                        .fields(fields)
                        .include(include);
                Table table = tablesApi.getTableByID(UUID.fromString(id), queryParams);
                if (table != null) {
                    tables.add(table);
                }
            } catch (Exception e) {
                // Log the error but continue with other IDs
                System.err.println("Error retrieving table with ID: " + id + " - " + e.getMessage());
            }
        }
        return tables;
    }
}
