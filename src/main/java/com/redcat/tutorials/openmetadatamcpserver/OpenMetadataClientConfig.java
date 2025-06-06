package com.redcat.tutorials.openmetadatamcpserver;

import org.openmetadata.client.api.*;

import org.openmetadata.client.gateway.OpenMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenMetadataClientConfig {

    private final OpenMetadata openMetadataGateway;

    public OpenMetadataClientConfig(OpenMetadata openMetadataGateway) {
        this.openMetadataGateway = openMetadataGateway;
    }

    @Bean
    public DashboardsApi dashboardsApi() {
        return openMetadataGateway.buildClient(DashboardsApi.class);
    }

    @Bean
    public DatabasesApi databasesApi() {
        return openMetadataGateway.buildClient(DatabasesApi.class);
    }

    @Bean
    public TablesApi tablesApi() {
        return openMetadataGateway.buildClient(TablesApi.class);
    }

    @Bean
    public PipelinesApi pipelinesApi() {
        return openMetadataGateway.buildClient(PipelinesApi.class);
    }

    @Bean
    public TopicsApi topicsApi() {
        return openMetadataGateway.buildClient(TopicsApi.class);
    }

    @Bean
    public ChartsApi chartsApi() {
        return openMetadataGateway.buildClient(ChartsApi.class);
    }

    @Bean
    public MlModelsApi mlModelsApi() {
        return openMetadataGateway.buildClient(MlModelsApi.class);
    }

    @Bean
    public UsersApi usersApi() {
        return openMetadataGateway.buildClient(UsersApi.class);
    }

    @Bean
    public TeamsApi teamsApi() {
        return openMetadataGateway.buildClient(TeamsApi.class);
    }

    @Bean
    public TestSuitesApi testsApi() {
        return openMetadataGateway.buildClient(TestSuitesApi.class);
    }

    // Governance & Collaboration
    @Bean
    public GlossariesApi glossaryApi() {
        return openMetadataGateway.buildClient(GlossariesApi.class);
    }

    @Bean
    public ClassificationsApi classificationsApi() {
        return openMetadataGateway.buildClient(ClassificationsApi.class);
    }

    @Bean
    public PoliciesApi policyApi() {
        return openMetadataGateway.buildClient(PoliciesApi.class);
    }

    // Metadata Management
    @Bean
    public LineageApi lineageApi() {
        return openMetadataGateway.buildClient(LineageApi.class);
    }

    @Bean
    public SearchApi searchApi() {
        return openMetadataGateway.buildClient(SearchApi.class);
    }


    @Bean
    public FeedsApi feedsApi() {
        return openMetadataGateway.buildClient(FeedsApi.class);
    }

    // Administration
    @Bean
    public RolesApi rolesApi() {
        return openMetadataGateway.buildClient(RolesApi.class);
    }

    @Bean
    public BotsApi botApi() {
        return openMetadataGateway.buildClient(BotsApi.class);
    }

    @Bean
    public QueriesApi queriesApi() {
        return openMetadataGateway.buildClient(QueriesApi.class);
    }

    @Bean
    public DatabaseSchemasApi databaseSchemasApi() {
        return openMetadataGateway.buildClient(DatabaseSchemasApi.class);
    }

    @Bean
    public MetadataApi metadataApi() {
        return openMetadataGateway.buildClient(MetadataApi.class);
    }

    @Bean
    public ObjectStoreServicesApi objectStoreServicesApi() {
        return openMetadataGateway.buildClient(ObjectStoreServicesApi.class);
    }

    @Bean
    public PermissionsApi permissionsApi() {
        return openMetadataGateway.buildClient(PermissionsApi.class);
    }
}
