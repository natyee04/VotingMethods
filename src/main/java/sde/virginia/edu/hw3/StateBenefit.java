package sde.virginia.edu.hw3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StateBenefit {
    Map<State, Double> stateBenefitMap = new HashMap<>();

    public void setStateBenefitForState(State state, double benefit){
        stateBenefitMap.put(state, benefit);
    }

//    private static final Comparator<StateBenefit> STATE_BENEFIT_COMPARATOR =
//            Comparator.comparing()
}
