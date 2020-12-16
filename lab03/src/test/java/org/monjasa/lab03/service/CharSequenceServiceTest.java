package org.monjasa.lab03.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
class CharSequenceServiceTest {

    @Test
    void shouldExcludeWordsFromSequence() {

        String input = "A car in not the same as a carriage, and some or planes can carry cars inside the car!";
        List<String> wordsToExclude = List.of("a", "the", "or", "are", "on", "in", "out");

        String result = CharSequenceService.excludeWordsFromSequence(input, wordsToExclude);

        assertThat(result).isNotNull()
                .isEqualTo("car not same as carriage, and some planes can carry cars inside car!");

        log.info(result);
    }
}