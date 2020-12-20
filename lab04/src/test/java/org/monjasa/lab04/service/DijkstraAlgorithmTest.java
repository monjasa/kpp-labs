package org.monjasa.lab04.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.monjasa.lab04.DijkstraApplication;
import org.monjasa.lab04.model.Graph;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DijkstraAlgorithmTest {

    @Test
    void shouldSolveGraphWithGivenEdges() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream resourceAsStream = DijkstraApplication.class.getResourceAsStream("/data.json");
        Graph graph = objectMapper.readValue(resourceAsStream, Graph.class);

        List<Integer> result = DijkstraAlgorithm.to(graph).solve();

        assertThat(result).isNotNull()
                .asList()
                .containsExactly(0, 3, 1, 3 ,4);
    }
}