package com.redcat.tutorials.openmetadatamcpserver.tools.impl;

import com.redcat.tutorials.openmetadatamcpserver.tools.OpenMetadataToolsService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenMetadataSearchToolsService extends OpenMetadataToolsService {

    @Value("${openmetadata.server.host}")
    private String serverHost;

    @Value("${openmetadata.jwt.token}")
    private String jwtToken;

    private final RestTemplate restTemplate = new RestTemplate();

    @Tool(name="search for tables and schemas and databases", description = "Search for tables, schemas, databases, etc. in OpenMetadata using a query string. Supports pagination with 'from' and 'size' parameters. Specify the index to search in.")
    public ResponseEntity<String> search(String query, int from, int size, String index) {
        String url = UriComponentsBuilder.fromHttpUrl(serverHost)
                .path("/v1/search/query")
                .queryParam("q", query)
                .queryParam("from", from)
                .queryParam("size", size)
                .queryParam("index", index)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json, text/plain, */*");
        headers.set("authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}

