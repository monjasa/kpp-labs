package org.monjasa.lab04.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.monjasa.lab04.DijkstraApplication;
import org.monjasa.lab04.model.Graph;
import org.monjasa.lab04.util.ThreadScannerTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParallelDijkstraAlgorithmTest {

    @Test
    void shouldSolveGraphWithGivenEdgesUsingSingleThread() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream resourceAsStream = DijkstraApplication.class.getResourceAsStream("/data.json");
        Graph graph = objectMapper.readValue(resourceAsStream, Graph.class);

        ThreadScannerTask threadScannerTask = new ThreadScannerTask(null);
        new Timer().schedule(threadScannerTask, 0, 10);

        List<Integer> result = ParallelDijkstraAlgorithm.to(graph, threadScannerTask).solve(1);

        assertThat(result).isNotNull()
                .asList()
                .containsExactly(0, 3, 1, 3 ,4);
    }

    @Test
    void shouldSolveGraphWithGivenEdgesUsingMultipleThreads() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream resourceAsStream = DijkstraApplication.class.getResourceAsStream("/data.json");
        Graph graph = objectMapper.readValue(resourceAsStream, Graph.class);

        ThreadScannerTask threadScannerTask = new ThreadScannerTask(null);
        new Timer().schedule(threadScannerTask, 0, 10);

        List<Integer> result = ParallelDijkstraAlgorithm.to(graph, threadScannerTask).solve(3);

        assertThat(result).isNotNull()
                .asList()
                .containsExactly(0, 3, 1, 3 ,4);
    }
}