package sde.virginia.edu.hw3;

public class RepresentationFormatFactory {
    public RepresentationFormat getDefaultFormat() {
        return new AlphabeticalFormat();
    }

    public RepresentationFormat getFormat(String name) {
        if (name.equalsIgnoreCase("alphabet")) {
            return new AlphabeticalFormat();
        }
        if (name.equalsIgnoreCase("benefit")) {
            return new BenefitFormat(DisplayOrder.DESCENDING);
        }
        if (name.equalsIgnoreCase("population")) {
            return new PopulationFormat(DisplayOrder.ASCENDING);
        }

        throw new IllegalArgumentException("Invalid input, please choose from the following valid inputs: alphabet, benefit, or population.");
    }

    public RepresentationFormat getFormat(String name, DisplayOrder order) {
        if (name.equalsIgnoreCase("alphabet")) {
            return new AlphabeticalFormat();
        }
        if (name.equalsIgnoreCase("benefit")) {
            return new BenefitFormat(order);
        }
        if (name.equalsIgnoreCase("population")) {
            return new PopulationFormat(order);
        }

        throw new IllegalArgumentException("Invalid input, please choose from the following valid inputs: alphabet, benefit, or population.");
    }
}
