package org.monjasa.lab04.service;

import org.monjasa.lab04.model.Graph;
import org.monjasa.lab04.model.Edge;
import org.monjasa.lab04.model.Vertex;

import java.util.*;
import java.util.stream.IntStream;

public class DijkstraAlgorithm {

    private static final int INITIAL_DISTANCE = 0;

    private final Graph graph;
    private final Queue<Vertex> queue;
    private final Queue<Vertex> visited;
    private final List<Integer> distances;

    public static DijkstraAlgorithm to(Graph graph) {
        return new DijkstraAlgorithm(graph);
    }

    private DijkstraAlgorithm(Graph graph) {

        this.graph = graph;
        this.queue = new ArrayDeque<>(graph.getSize());
        this.visited = new ArrayDeque<>(graph.getSize());

        this.distances = new ArrayList<>(graph.getSize());
        IntStream.range(0, graph.getSize())
                .forEach(i -> distances.add(i, Integer.MAX_VALUE));

        distances.set(graph.getSourceVertex().getIndex(), INITIAL_DISTANCE);
        queue.add(graph.getSourceVertex());
    }

    public List<Integer> solve() {

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.remove();
            if (!visited.contains(currentVertex)) {
                visited.add(currentVertex);
                processNeighbours(currentVertex);
            }
        }

        return distances;
    }

    private void processNeighbours(Vertex vertex) {

        List<Edge> neighbourEdges = graph.getEdgesWithFirstEndpoint(vertex);

        for (Edge edge : neighbourEdges) {
            if (!visited.contains(edge.getSecondEndpoint()) && edge.getDistance() > INITIAL_DISTANCE) {
                int newDistance = distances.get(vertex.getIndex()) + edge.getDistance();
                if (newDistance < distances.get(edge.getSecondEndpoint().getIndex())) {
                    distances.set(edge.getSecondEndpoint().getIndex(), newDistance);
                    queue.add(edge.getSecondEndpoint());
                }
            }
        }
    }
}
