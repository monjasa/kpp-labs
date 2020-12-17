package org.monjasa.lab04.service;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.monjasa.lab04.model.Edge;
import org.monjasa.lab04.model.Graph;
import org.monjasa.lab04.model.Vertex;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Data
@Builder
public class DijkstraThread implements Runnable {

    private static final int INITIAL_DISTANCE = 0;

    private final Graph graph;
    private final Queue<Edge> localQueue;
    private final Queue<Vertex> visitedVertices;
    private final List<Integer> distances;

    private final int startIndex;
    private final int endIndex;
    private final AtomicReference<Vertex> currentVertex;

    private final AtomicBoolean finished;
    private final CyclicBarrier cyclicBarrier;

    @Override
    public void run() {
        while (!finished.get()) {

            visitAdjacentVertices(currentVertex.get());

            try {
                Thread.sleep(100);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void visitAdjacentVertices(Vertex vertex) {

        List<Edge> adjacentEdges = graph.getEdgesWithFirstEndpoint(vertex).stream()
                .filter(edge -> edge.getSecondEndpoint().getIndex() >= startIndex && edge.getSecondEndpoint().getIndex() < endIndex)
                .collect(Collectors.toList());

        for (Edge edge : adjacentEdges) {
            Vertex endpoint = edge.getSecondEndpoint();
            if (!visitedVertices.contains(endpoint) && edge.getDistance() > INITIAL_DISTANCE) {
                int currentDistance = distances.get(vertex.getIndex()) + edge.getDistance();
                if (currentDistance < distances.get(endpoint.getIndex())) {
                    distances.set(endpoint.getIndex(), currentDistance);
                    localQueue.add(new Edge(vertex, edge.getSecondEndpoint(), currentDistance));
                }
            }
        }
    }
}
