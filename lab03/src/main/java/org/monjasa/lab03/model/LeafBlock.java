package org.monjasa.lab03.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LeafBlock<T extends CharSequence> implements Block<T> {

    @Builder.Default private CharSequence openingToken = "(";
    @Builder.Default private CharSequence closingToken = ")";

    private T sequence;

    @Override
    public int length() {
        return openingToken.length()
                + sequence.length()
                + closingToken.length();
    }

    public CharSequence formatSequence(char formattingCharacter) {

        String result = String.format("%s%s%s", openingToken, sequence, closingToken);

        return result.chars()
                .map(ch -> Character.isDigit(ch) ? formattingCharacter : ch)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
    }

    @Override
    public List<T> extractLeafSequences() {
        return List.of(sequence);
    }
}