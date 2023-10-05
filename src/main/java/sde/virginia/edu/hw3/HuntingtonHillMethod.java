package sde.virginia.edu.hw3;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class HuntingtonHillMethod implements ApportionmentMethod{
    @Override
    public Representation getRepresentation(List<State> states, int targetRepresentatives) {
        validateInputs(states, targetRepresentatives);
        Map<State, Integer> representation = new HashMap<>();
        for (State state: states){
            representation.put(state, 1);
        }

        int remainRepresentatives = targetRepresentatives - states.size();
        while(remainRepresentatives>0){
            double maxPriorityScore = 0.0;
            State maxPriorityState = null;
            for(State state:states){
                double priorityScore = state.population()/(Math.sqrt((representation.get(state))*(representation.get(state) + 1)));
                if(priorityScore>maxPriorityScore){
                    maxPriorityState = state;
                    maxPriorityScore = priorityScore;
                }
            }
            representation.put(maxPriorityState, 1 + representation.get(maxPriorityState));
            remainRepresentatives -= 1;
        }
        return new Representation(representation);

    }
    @Override
    public void validateInputs(List<State> states, int targetRepresentatives) {
        if (states.isEmpty()) {
            throw new IllegalArgumentException("Cannot apportion representatives to an empty list of states");
        }
        if (targetRepresentatives < 0) {
            throw new IllegalArgumentException("Cannot apportion a negative number of representatives");
        }

        if (states.size() > targetRepresentatives) {
            throw new IllegalArgumentException("Cannot have more states than representatives");
        }
    }
}
