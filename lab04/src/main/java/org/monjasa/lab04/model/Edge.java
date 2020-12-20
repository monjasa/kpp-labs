package org.monjasa.lab04.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Edge implements Comparable<Edge> {

    @NonNull private Vertex firstEndpoint;
    @NonNull private Vertex secondEndpoint;
    @NonNull private int distance;

    @Override
    public int compareTo(Edge edge) {
        return Integer.compare(this.distance, edge.distance);
    }
}
