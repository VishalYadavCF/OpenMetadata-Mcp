package com.redcat.tutorials.openmetadatamcpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.openmetadata.client.gateway.OpenMetadata;
import org.openmetadata.schema.services.connections.metadata.OpenMetadataConnection;

public class CustomOpenMetadata extends OpenMetadata {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomOpenMetadata(OpenMetadataConnection config) {
        super(config);
    }

    public CustomOpenMetadata(OpenMetadataConnection config, boolean validateVersion) {
        super(config, validateVersion);
    }

    @Override
    public void initClient(OpenMetadataConnection config) {
        Feign.Builder builder =
                Feign.builder()
                        .encoder(new FormEncoder(new JacksonEncoder(objectMapper)))
                        .decoder(new JacksonDecoder(objectMapper))
                        .logger(new Slf4jLogger())
                        .client(new OkHttpClient());
        super.initClient(config, builder);
    }
}
