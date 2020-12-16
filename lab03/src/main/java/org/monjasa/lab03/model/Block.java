package org.monjasa.lab03.model;

import java.util.List;

public interface Block<T extends CharSequence> {

    int length();

    CharSequence formatSequence(char formattingCharacter);

    List<T> extractLeafSequences();

    CharSequence getSequence();

    CharSequence getOpeningToken();

    CharSequence getClosingToken();
}