package sde.virginia.edu.hw3;

import java.util.*;

public class BenefitFormat implements RepresentationFormat{
    private DisplayOrder displayOrder;

    public BenefitFormat() { this(DisplayOrder.DESCENDING);}

    public BenefitFormat(DisplayOrder displayOrder) {setDisplayOrder(displayOrder);}

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
        stringBuilder.append("State           | Reps|Benefit\n");
        Map<State, Double> stateBenefitHashMap = new HashMap<>();

        var states = new ArrayList<>(representation.getStates());
        double divisor = getDivisor(states, representation);
        calculateBenefitForStates(representation, stateBenefitHashMap, states, divisor);
        getStateStrings(representation, stringBuilder, stateBenefitHashMap);

        return stringBuilder.toString();
    }

    private void getStateStrings(Representation representation, StringBuilder stringBuilder, Map<State, Double> stateBenefitHashMap) {
        var sortedStateBenefitList = sortByDisplayOrder(displayOrder, stateBenefitHashMap);
        for (Map.Entry<State, Double> stateBenefit: sortedStateBenefitList){
            var stateString = getRepresentationStringForState(representation, stateBenefit.getKey(), stateBenefit.getValue());
            stringBuilder.append(stateString);
        }
    }

    private static String getRepresentationStringForState(Representation representation, State state, double benefit) {
        String roundedBenefit = String.format("%.3f", benefit);
        String formattedBenefit;
        if (Double.parseDouble(roundedBenefit) > 0){
            formattedBenefit = "+" + roundedBenefit;
        } else if (Double.parseDouble(roundedBenefit) < 0){
            formattedBenefit = roundedBenefit;
        } else{
            formattedBenefit = "0.000";
        }
        return String.format("%-16s|%5d|%7s\n",
                state.name(), representation.getRepresentativesFor(state), formattedBenefit);
    }

    private void calculateBenefitForStates(Representation representation, Map<State, Double> stateBenefitHashMap, ArrayList<State> states, double divisor) {
        for (State state : states){
            double quota = state.population()/ divisor;
            double benefit = representation.getRepresentativesFor(state) - quota;
            stateBenefitHashMap.put(state, benefit);
        }
    }

    private static List<Map.Entry<State, Double>> sortByDisplayOrder(DisplayOrder displayOrder, Map<State, Double> stateBenefitHashMap){
        List<Map.Entry<State, Double>> stateBenefitList = new ArrayList<>(stateBenefitHashMap.entrySet());
        stateBenefitList.sort(getBenefitComparator(displayOrder));
        return stateBenefitList;
    }

    private static Comparator<Map.Entry<State, Double>> getBenefitComparator(DisplayOrder displayOrder) {
        var comparator = Comparator.<Map.Entry<State, Double>>comparingDouble(Map.Entry::getValue)
                .reversed()
                .thenComparing(entry -> entry.getKey().name(), Comparator.reverseOrder());
        if (displayOrder == DisplayOrder.ASCENDING) {
            comparator = Comparator.<Map.Entry<State, Double>>comparingDouble(Map.Entry::getValue)
                    .thenComparing(entry -> entry.getKey().name());
        }
        return comparator;
    }

    private static double getDivisor(ArrayList<State> states, Representation representation){
        var totalPopulation = getTotalPopulation(states);
        var totalRepresentatives = representation.getAllocatedRepresentatives();
        return (double) totalPopulation/totalRepresentatives;
    }

    private static int getTotalPopulation(ArrayList<State> states){
        int totalPopulation = 0;
        for (var state : states) {
            totalPopulation += state.population();
        }
        return totalPopulation;
    }
}
