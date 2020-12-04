package org.monjasa.lab03.model;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CompositeBlockTest {

    @Mock
    private LeafBlock<String> leafBlock;

    @Mock
    private LeafBlock<String> alternativeLeafBlock;

    @Test
    void shouldCreateLeafBlockWithDefaultTokens() {

        String firstSequence = "first";
        String secondSequence = "second";
        String leafSequence = "sequence";

        when(leafBlock.formatSequence('#')).thenReturn("(" + leafSequence + ")");

        CompositeBlock<String> block = CompositeBlock.<String>builder()
                .sequences(List.of(firstSequence, secondSequence))
                .innerBlock(leafBlock)
                .build();

        assertThat(block.formatSequence('#')).isNotNull()
                .startsWith("(" + firstSequence)
                .contains("(" + leafSequence + ")")
                .endsWith(secondSequence + ")");

        log.info(block.toString());
    }

    @Test
    void shouldReturnLengthOfBlock() {

        String validSequence = "sequence";
        String validToken = "|";
        String expectedBlockContent = validToken + validSequence + validToken;

        when(leafBlock.length()).thenReturn(expectedBlockContent.length());

        CompositeBlock<String> block = CompositeBlock.<String>builder()
                .sequences(List.of(validSequence, ""))
                .innerBlock(leafBlock)
                .openingToken(validToken)
                .closingToken(validToken)
                .build();

        assertThat(block.length()).isNotNull()
                .isEqualTo(expectedBlockContent.length() * 2);

        log.info(block.toString());
    }

    @Test
    void shouldExtractLeafSequence() {

        String leafSequence = "sequence";

        when(leafBlock.extractLeafSequences()).thenReturn(List.of(leafSequence));

        CompositeBlock<String> block = CompositeBlock.<String>builder()
                .innerBlock(leafBlock)
                .build();

        assertThat(block.extractLeafSequences()).isNotNull()
                .singleElement(InstanceOfAssertFactories.STRING)
                .isEqualTo(leafSequence);

        log.info(block.toString());
    }

    @Test
    void shouldExtractLeafSequences() {

        String leafSequence = "sequence";
        String alternativeLeafSequence = "alternative sequence";

        when(leafBlock.extractLeafSequences()).thenReturn(List.of(leafSequence));
        when(alternativeLeafBlock.extractLeafSequences()).thenReturn(List.of(alternativeLeafSequence));

        CompositeBlock<String> block = CompositeBlock.<String>builder()
                .innerBlocks(List.of(leafBlock, alternativeLeafBlock))
                .build();

        assertThat(block.extractLeafSequences()).isNotNull()
                .hasSize(2)
                .hasSameElementsAs(List.of(leafSequence, alternativeLeafSequence));

        log.info(block.toString());
    }
}