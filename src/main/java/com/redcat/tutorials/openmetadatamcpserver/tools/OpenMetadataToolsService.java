package com.redcat.tutorials.openmetadatamcpserver.tools;

import org.springframework.ai.tool.annotation.Tool;

public abstract class OpenMetadataToolsService {

    @Tool(name="dummy_tool", description = "Ignore")
    public String getOpenMetadataUrl() {
        return "";
    }
}
