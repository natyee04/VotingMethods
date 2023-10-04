package sde.virginia.edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuntingtonHillMethod implements ApportionmentMethod{
    @Override
    public Representation getRepresentation(List<State> states, int targetRepresentatives) {
        Map<State, Integer> representation = new HashMap<>();
        //implement HuntingtonHill algo here
        return new Representation(representation);
    }

}
