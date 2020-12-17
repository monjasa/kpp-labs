package org.monjasa.lab04.service;

import lombok.Data;
import lombok.NonNull;
import org.monjasa.lab04.model.Edge;
import org.monjasa.lab04.model.Graph;
import org.monjasa.lab04.model.Vertex;
import org.monjasa.lab04.util.ThreadScannerTask;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Data
public class ParallelDijkstraAlgorithm {

    private static final int INITIAL_DISTANCE = 0;

    private ThreadScannerTask timerTask;

    private final Graph graph;
    private final List<Queue<Edge>> queues;
    private final Queue<Vertex> visitedVertices;
    private final List<Integer> distances;

    private final AtomicBoolean finished;
    private final AtomicReference<Vertex> currentVertex;

    public static ParallelDijkstraAlgorithm to(Graph graph, ThreadScannerTask timerTask) {
        return new ParallelDijkstraAlgorithm(graph, timerTask);
    }

    private ParallelDijkstraAlgorithm(Graph graph, ThreadScannerTask timerTask) {

        this.timerTask = timerTask;

        this.graph = graph;
        this.queues = new ArrayList<>();
        this.visitedVertices = new ArrayDeque<>(graph.getSize());

        this.distances = new ArrayList<>(graph.getSize());
        IntStream.range(0, graph.getSize())
                .forEach(i -> distances.add(i, Integer.MAX_VALUE));

        distances.set(graph.getSourceVertex().getIndex(), INITIAL_DISTANCE);

        finished = new AtomicBoolean(false);
        currentVertex = new AtomicReference<>(graph.getSourceVertex());
    }

    public List<Integer> solve(int threadsNumber) {

        List<Thread> threads = new ArrayList<>(threadsNumber);
        IntStream.range(0, threadsNumber)
                .forEach(i -> queues.add(i, new ArrayDeque<>()));

        Runnable barrierAction = new BarrierAction(queues, visitedVertices, finished, currentVertex);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadsNumber, barrierAction);

        int startIndex = 0;
        int endIndex = 0;
        int chunkSize = graph.getSize() / threadsNumber;
        int chunkRemainder = graph.getSize() % threadsNumber;

        for (int i = 0; i < threadsNumber; i++) {

            startIndex = endIndex;
            endIndex = endIndex + chunkSize;

            if (chunkRemainder > 0) {
                endIndex++;
                chunkRemainder--;
            }

            threads.add(new Thread(DijkstraThread.builder()
                    .graph(graph)
                    .localQueue(queues.get(i))
                    .visitedVertices(visitedVertices)
                    .distances(distances)
                    .startIndex(startIndex)
                    .endIndex(endIndex)
                    .currentVertex(currentVertex)
                    .finished(finished)
                    .cyclicBarrier(cyclicBarrier)
                    .build()
            ));
        }

        threads.forEach(timerTask::registerThread);
        threads.forEach(Thread::start);

        IntStream.range(0, threadsNumber)
                .forEach(i -> {
                    try {
                        threads.get(i).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        return distances;
    }

    @Data
    public static class BarrierAction implements Runnable {

        @NonNull private List<Queue<Edge>> queues;
        @NonNull private Queue<Vertex> visitedVertices;

        @NonNull private AtomicBoolean finished;
        @NonNull private AtomicReference<Vertex> currentVertex;

        @Override
        public void run() {
            while (true) {

                Edge shortestEdge = null;
                int queueIndex = 0;

                for (int i = 0; i < queues.size(); i++) {
                    if (!queues.get(i).isEmpty()) {
                        Edge edge = queues.get(i).peek();
                        if (shortestEdge == null || Objects.requireNonNull(edge).compareTo(shortestEdge) < 0) {
                            shortestEdge = edge;
                            queueIndex = i;
                        }
                    }
                }

                if (shortestEdge == null) {
                    finished.set(true);
                    return;
                }

                Vertex endpoint = shortestEdge.getSecondEndpoint();
                if (!visitedVertices.contains(endpoint)) {
                    visitedVertices.add(endpoint);
                    currentVertex.set(endpoint);
                    queues.get(queueIndex).remove();
                    return;
                }

                queues.get(queueIndex).remove();
            }
        }
    }
}
