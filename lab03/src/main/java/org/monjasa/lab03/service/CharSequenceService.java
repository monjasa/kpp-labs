package org.monjasa.lab03.service;

import org.apache.commons.lang3.RegExUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CharSequenceService {

    public static <T extends CharSequence> String excludeWordsFromSequence(T sequence, List<String> words) {

        String regexPattern = words.stream()
                .map(String::strip)
                .distinct()
                .collect(Collectors.joining("|", "(?i)\\b(?:", ")\\b\\s+"));

        return RegExUtils.removeAll(sequence.toString(), regexPattern);
    }
}
