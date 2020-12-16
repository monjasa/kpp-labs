package org.monjasa.lab03.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

@Data
@Builder
public class CompositeBlock<T extends CharSequence> implements Block<T> {

    @Builder.Default private CharSequence openingToken = "(";
    @Builder.Default private CharSequence closingToken = ")";

    @Singular private List<Block<T>> innerBlocks;
    @Singular private List<T> sequences;

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

    public CharSequence formatSequence(char formattingCharacter) {

        StringBuilder result = new StringBuilder(openingToken);

        Spliterator<Block<T>> spliterator = innerBlocks.spliterator();
        sequences.forEach(sequence -> {
            result.append(sequence);
            spliterator.tryAdvance(block -> result.append(block.formatSequence(formattingCharacter)));
        });

        result.append(closingToken);

        return result.toString().chars()
                .map(ch -> Character.isDigit(ch) ? formattingCharacter : ch)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
    }

    @Override
    public List<T> extractLeafSequences() {
        return innerBlocks.stream()
                .flatMap(block -> block.extractLeafSequences().stream())
                .collect(Collectors.toList());
    }

    public CharSequence getSequence() {
        return String.join(" ", sequences);
    }
}