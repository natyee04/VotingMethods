package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BenefitFormatTest {

    @Test
    void getDisplayOrder_default() {
        var format = new BenefitFormat();
        assertEquals(DisplayOrder.DESCENDING, format.getDisplayOrder());
    }

    @Test
    void getDisplayOrder_ascending() {
        var format = new BenefitFormat(DisplayOrder.ASCENDING);
        assertEquals(DisplayOrder.ASCENDING, format.getDisplayOrder());
    }

    @Test
    void getDisplayOrder_descending() {
        var format = new BenefitFormat(DisplayOrder.DESCENDING);
        assertEquals(DisplayOrder.DESCENDING, format.getDisplayOrder());
    }

    @Test
    void setDisplayOrder() {
        var format = new BenefitFormat();
        format.setDisplayOrder(DisplayOrder.DESCENDING);
        assertEquals(DisplayOrder.DESCENDING, format.getDisplayOrder());
    }

    @Test
    void setDisplayOrder_null_Exception() {
        var format = new BenefitFormat();
        assertThrows(IllegalArgumentException.class, () -> format.setDisplayOrder(null));
    }

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
                State           | Reps|Benefit
                Delaware        |    0| -0.809
                West Virginia   |    1| -0.466
                Virginia        |    7| -0.053
                Maryland        |    5| -0.048
                Pennsylvania    |   12| +1.375
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }

    @Test
    void getFormattedString_ascending_sameBenefit() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("Pennsylvania", 13002700), 12,
                        new State("Maryland", 3985470), 3,
                        new State("Virginia", 8631393), 7,
                        new State("West Virginia", 3985470), 3,
                        new State("Delaware", 989948), 0
                )));

        var format = new BenefitFormat(DisplayOrder.ASCENDING);
        var expected = """
                State           | Reps|Benefit
                Delaware        |    0| -0.809
                Maryland        |    3| -0.257
                West Virginia   |    3| -0.257
                Virginia        |    7| -0.053
                Pennsylvania    |   12| +1.375
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }

    @Test
    void getFormattedString_descending() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("Pennsylvania", 13002700), 12,
                        new State("Maryland", 6177224), 5,
                        new State("Virginia", 8631393), 7,
                        new State("West Virginia", 1793716), 1,
                        new State("Delaware", 989948), 0
                )));

        var format = new BenefitFormat(DisplayOrder.DESCENDING);
        var expected = """
                State           | Reps|Benefit
                Pennsylvania    |   12| +1.375
                Maryland        |    5| -0.048
                Virginia        |    7| -0.053
                West Virginia   |    1| -0.466
                Delaware        |    0| -0.809
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }

    @Test
    void getFormattedString_descending_sameBenefit() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("Pennsylvania", 13002700), 12,
                        new State("Maryland", 3985470), 3,
                        new State("Virginia", 8631393), 7,
                        new State("West Virginia", 3985470), 3,
                        new State("Delaware", 989948), 0
                )));

        var format = new BenefitFormat(DisplayOrder.DESCENDING);
        var expected = """
                State           | Reps|Benefit
                Pennsylvania    |   12| +1.375
                Virginia        |    7| -0.053
                West Virginia   |    3| -0.257
                Maryland        |    3| -0.257
                Delaware        |    0| -0.809
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }

    @Test
    void getFormattedString_zeroRepresentatives() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("Pennsylvania", 1), 0,
                        new State("Maryland", 1), 0,
                        new State("West Virginia", 1), 0,
                        new State("Virginia", 1), 0,
                        new State("Delaware", 1), 0
                )));

        var format = new BenefitFormat(DisplayOrder.DESCENDING);
        var expected = """
                State           | Reps|Benefit
                Delaware        |    0|  0.000
                Maryland        |    0|  0.000
                Pennsylvania    |    0|  0.000
                Virginia        |    0|  0.000
                West Virginia   |    0|  0.000
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }
}
