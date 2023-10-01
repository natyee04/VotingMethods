package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BenefitFormatTest {

    @Test
    void getFormattedString_ascending() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("Pennsylvania", 13002700), 12,
                        new State("Maryland", 6177224), 5,
                        new State("Virginia", 8631393), 7,
                        new State("West Virginia", 1793716), 1,
                        new State("Delaware", 989948), 0
                )));

        var format = new BenefitFormat(DisplayOrder.ASCENDING);
        var expected = """
                State           | Reps| Benefit
                Pennsylvania    |   12|  +1.375
                Maryland        |    5|  -0.048
                Virginia        |    7|  -0.053
                West Virginia   |    1|  -0.466
                Delaware        |    0|  -0.809
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }
}
