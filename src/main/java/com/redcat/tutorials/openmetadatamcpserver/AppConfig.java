package com.redcat.tutorials.openmetadatamcpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmetadata.client.ApiClient;
import org.openmetadata.schema.security.client.OpenMetadataJWTClientConfig;
import org.openmetadata.schema.services.connections.metadata.AuthProvider;
import org.openmetadata.schema.services.connections.metadata.OpenMetadataConnection;
import org.openmetadata.client.gateway.OpenMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${openmetadata.jwt.token}")
    private String jwtToken;

    @Value("${openmetadata.server.host}")
    private String host;

    @Bean
    public OpenMetadataConnection openMetadataServerConnection() {
        OpenMetadataJWTClientConfig jwtClientConfig = new OpenMetadataJWTClientConfig();
        jwtClientConfig.setJwtToken(jwtToken);

        OpenMetadataConnection server = new OpenMetadataConnection();
        server.setHostPort(host);
        server.setApiVersion("v1");
        server.setAuthProvider(AuthProvider.OPENMETADATA);
        server.setSecurityConfig(jwtClientConfig);
        return server;
    }

    @Bean
    public OpenMetadata openMetadata() {
        return new OpenMetadata(openMetadataServerConnection());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }



}
