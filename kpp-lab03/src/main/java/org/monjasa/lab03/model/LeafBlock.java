ackage org.monjasa.lab03.model;

import lombok.Builder;
import lombok.Data;

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

    public CharSequence getSequence() {

        StringBuilder result = new StringBuilder(openingToken);

        result.append(sequence);

        return result.append(closingToken);
    }
}