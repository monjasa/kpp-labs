package org.monjasa.lab03;

import lombok.Setter;
import org.monjasa.lab03.model.Block;
import org.monjasa.lab03.model.CompositeBlock;
import org.monjasa.lab03.model.LeafBlock;

import java.util.ArrayList;
import java.util.List;

public class RecursiveDescentParser {

    @Setter
    public static CharSequence openingToken = "(";
    @Setter
    public static CharSequence closingToken = ")";

    public static <T extends CharSequence> Block<String> parseToStringHierarchy(T sequence, int position) {

        List<Block<String>> innerBlocks = new ArrayList<>();
        List<String> sequences = new ArrayList<>();

        StringBuilder sequenceBuilder = new StringBuilder();

        while (!sequence.subSequence(position, position + closingToken.length()).equals(closingToken)) {

            if (sequence.subSequence(position, position + openingToken.length()).equals(openingToken)) {
                Block<String> innerBlock = RecursiveDescentParser.parseToStringHierarchy(sequence, position + 1);
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

        if (innerBlocks.size() > 0) {

            return CompositeBlock.<String>builder()
                    .openingToken(openingToken)
                    .closingToken(closingToken)
                    .sequences(sequences)
                    .innerBlocks(innerBlocks)
                    .build();
        } else {
            return LeafBlock.<String>builder()
                    .openingToken(openingToken)
                    .closingToken(closingToken)
                    .sequence(sequences.get(0))
                    .build();
        }
    }

    public static <T extends CharSequence> Block<String> parseToStringHierarchy(T sequence) {
        return parseToStringHierarchy(sequence, openingToken.length());
    }
}