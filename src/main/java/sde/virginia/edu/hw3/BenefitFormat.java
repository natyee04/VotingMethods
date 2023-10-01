package sde.virginia.edu.hw3;

import java.util.ArrayList;

public class BenefitFormat implements RepresentationFormat{
    private DisplayOrder displayOrder;

    /**
     * Creates a population format in ascending order
     */
    public BenefitFormat() { this(DisplayOrder.DESCENDING);}

    public BenefitFormat(DisplayOrder displayOrder) {setDisplayOrder(displayOrder);}

    /**
     * Returns the display order
     * @return Either {@link DisplayOrder#ASCENDING ascending} or {@link DisplayOrder#DESCENDING descending}
     */
    public DisplayOrder getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(DisplayOrder displayOrder) {
        if (displayOrder == null) {
            throw new IllegalArgumentException("The display order cannot be null!");
        }
        this.displayOrder = displayOrder;
    }

    @Override
    public String getFormattedString(Representation representation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("State           | Reps| Benefit\n");
        var states = new ArrayList<>(representation.getStates());

        double divisor = getDivisor(states, representation);
        for (State state : states) {
            double quota = state.population()/divisor;
            double benefit = representation.getRepresentativesFor(state) - quota;
            var stateString = getRepresentationStringForState(representation, state, benefit);
            stringBuilder.append(stateString);
        }
        return stringBuilder.toString();
    }

    private static String getRepresentationStringForState(Representation representation, State state, double benefit) {
        return String.format("%-16s|%5d|%+7f\n",
                state.name(), representation.getRepresentativesFor(state), benefit);
    }

    private static double getDivisor(ArrayList<State> states, Representation representation){
        var total_population = getTotalPopulation(states);
        var total_representatives = representation.getAllocatedRepresentatives();
        var divisor = total_population/total_representatives;
        return divisor;
    }

    private static int getTotalPopulation(ArrayList<State> states){
        int totalPopulation = 0;
        for (var state : states) {
            totalPopulation += state.population();
        }
        return totalPopulation;
    }
}
