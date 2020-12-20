package org.monjasa.lab04.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Graph {

    @NonNull private Vertex sourceVertex;
    @NonNull private List<Edge> edges;

    public int getSize() {
        return edges.stream()
                .collect(Collectors.groupingBy(Edge::getFirstEndpoint))
                .size();
    }

    public List<Edge> getEdgesWithFirstEndpoint(Vertex vertex) {
        return edges.stream()
                .filter(edge -> edge.getFirstEndpoint().equals(vertex))
                .collect(Collectors.toList());
    }
}
