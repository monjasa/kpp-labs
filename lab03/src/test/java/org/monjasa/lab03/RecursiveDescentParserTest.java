package org.monjasa.lab03;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.monjasa.lab03.model.Block;
import org.monjasa.lab03.model.CompositeBlock;
import org.monjasa.lab03.model.LeafBlock;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
class RecursiveDescentParserTest {

    @Test
    void shouldParseLeafBlock() {

        String leafSequence = "A";

        Block<String> result = RecursiveDescentParser.parseAsString(leafSequence);

        assertThat(result).isNotNull()
                .isExactlyInstanceOf(LeafBlock.class)
                .hasFieldOrPropertyWithValue("sequence", "A");

        log.info(result.toString());
    }

    @Test
    void shouldParseCompositeBlockWithLeftSequence() {

        String compositeSequence = "A(B)";

        Block<String> result = RecursiveDescentParser.parseAsString(compositeSequence);

        assertThat(result).isNotNull()
                .isExactlyInstanceOf(CompositeBlock.class)
                .extracting("sequences", InstanceOfAssertFactories.list(String.class))
                .hasSameElementsAs(List.of("A", ""));

        log.info(result.toString());
    }

    @Test
    void shouldParseCompositeBlockWithLeftAndRightSequences() {

        String compositeSequence = "A1(B)A2";

        Block<String> result = RecursiveDescentParser.parseAsString(compositeSequence);

        assertThat(result).isNotNull()
                .isExactlyInstanceOf(CompositeBlock.class)
                .extracting("sequences", InstanceOfAssertFactories.list(String.class))
                .hasSameElementsAs(List.of("A1", "A2"));

        log.info(result.toString());
    }

    @Test
    void shouldParseCompositeBlockWithIntermediateSequence() {

        String compositeSequence = "A1(B)A2(C)A3";

        Block<String> result = RecursiveDescentParser.parseAsString(compositeSequence);

        assertThat(result).isNotNull()
                .isExactlyInstanceOf(CompositeBlock.class)
                .extracting("sequences", InstanceOfAssertFactories.list(String.class))
                .hasSameElementsAs(List.of("A1", "A2", "A3"));

        log.info(result.toString());
    }

    @Test
    void shouldParseCompositeHierarchySequence() {

        String compositeSequence = "A1(B1(C)B2)А2(D)A3";

        Block<String> result = RecursiveDescentParser.parseAsString(compositeSequence);

        assertThat(result).isNotNull()
                .isExactlyInstanceOf(CompositeBlock.class)
                .extracting(stringBlock -> stringBlock.formatSequence('#'), InstanceOfAssertFactories.CHAR_SEQUENCE)
                .containsSequence("A#(B#(C)B#)А#(D)A#");

        log.info(result.toString());
    }
}