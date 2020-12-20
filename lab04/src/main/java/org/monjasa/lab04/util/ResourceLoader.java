package org.monjasa.lab04.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.monjasa.lab04.model.Graph;

import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    public static Graph loadGraph(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, Graph.class);
    }
}
