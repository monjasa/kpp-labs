package org.monjasa.lab02.service;

import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;

@ToString
public class FacilityStatistics<T> implements Consumer<T> {

    @Getter
    @ToString.Exclude
    private final Comparator<T> comparator;

    @Getter
    private T minValue;

    @Getter
    private T maxValue;

    public FacilityStatistics(Comparator<T> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    public static <T> Collector<T, ?, FacilityStatistics<T>> collector(Comparator<T> comparator) {
        return Collector.of(
                () -> new FacilityStatistics<>(comparator),
                FacilityStatistics<T>::accept,
                FacilityStatistics<T>::combine
        );
    }

    @Override
    public void accept(T currentValue) {
        if (minValue == null && maxValue == null) {
            minValue = currentValue;
            maxValue = currentValue;
        } else {
            if (comparator.compare(minValue, currentValue) > 0) minValue = currentValue;
            if (comparator.compare(maxValue, currentValue) < 0) maxValue = currentValue;
        }
    }

    public FacilityStatistics<T> combine(FacilityStatistics<T> statistics) {

        if (statistics.minValue != null && statistics.maxValue != null) {
            if (minValue == null && maxValue == null) {
                minValue = statistics.minValue;
                maxValue = statistics.maxValue;
            } else {
                if (comparator.compare(minValue, statistics.minValue) > 0) minValue = statistics.minValue;
                if (comparator.compare(maxValue, statistics.maxValue) < 0) maxValue = statistics.maxValue;
            }
        }

        return this;
    }
}
