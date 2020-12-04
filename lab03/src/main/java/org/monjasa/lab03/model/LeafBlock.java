package org.monjasa.lab03.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LeafBlock<T extends CharSequence> implements Block<T> {

    private T sequence;

    @Builder.Default
    private CharSequence openingToken = "(";
    @Builder.Default
    private CharSequence closingToken = ")";

    @Override
    public int length() {
        return openingToken.length()
                + sequence.length()
                + closingToken.length();
    }

    public CharSequence formatSequence() {

        StringBuilder result = new StringBuilder(openingToken);

        result.append(sequence);

        return result.append(closingToken);
    }

    @Override
    public List<T> extractLeafSequences() {
        return List.of(sequence);
    }
}