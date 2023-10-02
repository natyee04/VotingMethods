package sde.virginia.edu.hw3;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (Map.Entry<State, Double> state: sortedStateBenefitList){
            var stateString = getRepresentationStringForState(representation, state.getKey(), state.getValue());
            stringBuilder.append(stateString);
        }
    }

    private static String getRepresentationStringForState(Representation representation, State state, double benefit) {
        String sign = (benefit > 0) ? "+" : (benefit < 0) ? "-" : "";
        return String.format("%-16s|%5d|%s%7.3f\n",
                state.name(), representation.getRepresentativesFor(state), sign, Math.abs(benefit));
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
        if (displayOrder == DisplayOrder.DESCENDING) {
            stateBenefitList.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
        }
        else {
            stateBenefitList.sort((entry1, entry2) -> Double.compare(entry1.getValue(), entry2.getValue()));
        }
        return stateBenefitList;
    }

    private static double getDivisor(ArrayList<State> states, Representation representation){
        var total_population = getTotalPopulation(states);
        var total_representatives = representation.getAllocatedRepresentatives();
        return (double) total_population/total_representatives;
    }

    private static int getTotalPopulation(ArrayList<State> states){
        int totalPopulation = 0;
        for (var state : states) {
            totalPopulation += state.population();
        }
        return totalPopulation;
    }
}
