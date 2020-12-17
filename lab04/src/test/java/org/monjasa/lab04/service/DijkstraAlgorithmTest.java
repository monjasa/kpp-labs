package org.monjasa.lab04.service;

import org.junit.jupiter.api.Test;
import org.monjasa.lab04.model.Edge;
import org.monjasa.lab04.model.Graph;
import org.monjasa.lab04.model.Vertex;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DijkstraAlgorithmTest {

    @Test
    void shouldSolveGraphWithGivenEdges() {

        Vertex a = new Vertex(0);
        Vertex b = new Vertex(1);
        Vertex c = new Vertex(2);
        Vertex d = new Vertex(3);
        Vertex e = new Vertex(4);

        List<Edge> edges = new ArrayList<>(List.of(
                new Edge(a, b, 3),
                new Edge(a, c, 1),
                new Edge(b, a, 3),
                new Edge(b, c, 7),
                new Edge(b, d, 5),
                new Edge(b, e, 1),
                new Edge(c, a, 1),
                new Edge(c, b, 7),
                new Edge(c, d, 2),
                new Edge(d, b, 5),
                new Edge(d, c, 2),
                new Edge(d, e, 7),
                new Edge(e, b, 1),
                new Edge(e, d, 7)
        ));

        Graph graph = new Graph(a, edges);

        List<Integer> result = DijkstraAlgorithm.to(graph).solve();

        assertThat(result).isNotNull()
                .asList()
                .containsExactly(0, 3, 1, 3 ,4);
    }
}