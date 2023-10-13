package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class HuntingtonHillMethodTest {

        @Test
        void getRepresentation() {
            var de = new State("Delaware", 989948);
            var md = new State("Maryland", 6177224);
            var pa = new State("Pennsylvania", 13002700);
            var va = new State("Virginia", 8631393);
            var wv = new State("West Virginia", 1793716);
            var states = List.of(de, md, pa, va, wv);
            var huntingtonHillMethod = new HuntingtonHillMethod();
            var representation = huntingtonHillMethod.getRepresentation(states, 25);
            assertEquals(5, representation.getStates().size());
            assertEquals(1, representation.getRepresentativesFor(de));
            assertEquals(5, representation.getRepresentativesFor(md));
            assertEquals(10, representation.getRepresentativesFor(pa));
            assertEquals(7, representation.getRepresentativesFor(va));
            assertEquals(2, representation.getRepresentativesFor(wv));
        }
}
