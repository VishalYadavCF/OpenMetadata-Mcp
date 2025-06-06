package com.redcat.tutorials.openmetadatamcpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redcat.tutorials.openmetadatamcpserver.tools.OpenMetadataToolsService;
import com.redcat.tutorials.openmetadatamcpserver.tools.impl.OpenMetadataDatabaseToolsService;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class OpenmetadataMcpServerApplication {

    @Autowired
    private List<OpenMetadataToolsService> openMetaDataToolsServicesList;

    public static void main(String[] args) {
        SpringApplication.run(OpenmetadataMcpServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandRunner(McpSyncServer mcpSyncServer) {
        return args -> {
            for(Object toolService : openMetaDataToolsServicesList) {
                List<McpServerFeatures.SyncToolSpecification> newTools = McpToolUtils
                        .toSyncToolSpecifications(ToolCallbacks.from(toolService));
                for (McpServerFeatures.SyncToolSpecification newTool : newTools) {
                    mcpSyncServer.addTool(newTool);
                }
            }
        };
    }
}
