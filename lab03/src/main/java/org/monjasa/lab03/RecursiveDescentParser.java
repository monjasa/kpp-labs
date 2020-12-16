package org.monjasa.lab03;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.monjasa.lab03.model.Block;
import org.monjasa.lab03.model.CompositeBlock;
import org.monjasa.lab03.model.LeafBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecursiveDescentParser {

    @Setter public static String openingToken = "(";
    @Setter public static String closingToken = ")";

    public static <T extends CharSequence> Block<String> parseAsString(T sequence, int position) {

        List<Block<String>> innerBlocks = new ArrayList<>();
        List<String> sequences = new ArrayList<>();

        StringBuilder sequenceBuilder = new StringBuilder();

        while (position < sequence.length()) {

            if (StringUtils.indexOf(sequence, closingToken, position) == position) {
                break;
            }

            if (StringUtils.indexOf(sequence, openingToken, position) == position) {

                Block<String> innerBlock = RecursiveDescentParser.parseAsString(sequence, position + openingToken.length());
                innerBlocks.add(innerBlock);
                position = position + innerBlock.length();

                sequences.add(sequenceBuilder.toString());
                sequenceBuilder = new StringBuilder();
            } else {
                sequenceBuilder.append(sequence.charAt(position));
                position++;
            }
        }

        sequences.add(sequenceBuilder.toString());

        if (innerBlocks.isEmpty()) {
            return createLeafBlock(sequenceBuilder.toString());
        } else {
            return createCompositeBlock(sequences, innerBlocks);
        }
    }

    public static <T extends CharSequence> Block<String> parseAsString(T sequence) {
        return parseAsString(sequence, 0);
    }

    public static <T extends CharSequence> Block<T> createLeafBlock(T sequence) {
        return LeafBlock.<T>builder()
                .openingToken(openingToken)
                .closingToken(closingToken)
                .sequence(sequence)
                .build();
    }

    public static <T extends CharSequence> Block<T> createCompositeBlock(
            Collection<? extends T> sequences, Collection<Block<T>> innerBlocks
    ) {
        return CompositeBlock.<T>builder()
                .openingToken(openingToken)
                .closingToken(closingToken)
                .sequences(sequences)
                .innerBlocks(innerBlocks)
                .build();
    }
}