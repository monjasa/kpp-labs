package org.monjasa.lab03.model;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LeafBlockTest {

    @Test
    void shouldCreateLeafBlockWithDefaultTokens() {

        String validSequence = "sequence";

        LeafBlock<String> block = LeafBlock.<String>builder()
                .sequence(validSequence)
                .build();

        assertThat(block.formatSequence('#')).isNotNull()
                .startsWith("(")
                .contains(validSequence)
                .endsWith(")");

        log.info(block.toString());
    }

    @Test
    void shouldReturnLengthOfBlock() {

        String validSequence = "sequence";
        String validToken = "|";
        String expectedBlockContent = validToken + validSequence + validToken;

        LeafBlock<String> block = LeafBlock.<String>builder()
                .sequence(validSequence)
                .openingToken(validToken)
                .closingToken(validToken)
                .build();

        assertThat(block.length()).isNotNull()
                .isEqualTo(expectedBlockContent.length());

        log.info(block.toString());
    }

    @Test
    void shouldExtractLeafSequence() {

        String validSequence = "sequence";

        LeafBlock<String> block = LeafBlock.<String>builder()
                .sequence(validSequence)
                .build();

        assertThat(block.extractLeafSequences()).isNotNull()
                .singleElement(InstanceOfAssertFactories.STRING)
                .isEqualTo(validSequence);

        log.info(block.toString());
    }
}