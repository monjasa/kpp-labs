package org.monjasa.lab03.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Spliterator;

@Data
@Builder
public class CompositeBlock<T extends CharSequence> implements Block<T> {

    @Singular
    private List<Block<T>> innerBlocks;
    @Singular
    private List<T> sequences;

    @Builder.Default
    private CharSequence openingToken = "(";
    @Builder.Default
    private CharSequence closingToken = ")";

    @Override
    public int length() {

        int sum = innerBlocks.stream().reduce(
                openingToken.length() + closingToken.length(),
                (accumulator, block) -> accumulator + block.length(),
                Integer::sum
        );

        sum = sum + sequences.stream()
                .map(T::length)
                .reduce(Integer::sum).orElse(0);

        return sum;
    }

    public CharSequence getSequence() {

        StringBuilder result = new StringBuilder(openingToken);

        Spliterator<Block<T>> spliterator = innerBlocks.spliterator();
        sequences.forEach(sequence -> {
            result.append(sequence);
            spliterator.tryAdvance(block -> result.append(block.getSequence()));
        });

        return result.append(closingToken);
    }
}