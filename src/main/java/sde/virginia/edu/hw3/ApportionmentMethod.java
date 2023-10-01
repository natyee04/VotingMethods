/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface how an apportionment is calculated. Specifically, the {@link
 * ApportionmentMethod#getRepresentation(List, int)} method describes the process where a given list of {@link State
 * states} are apportioned representatives such that the total number of representatives allocated to all states is
 * some target number. <br>
 * <br>
 * Apportionment is the process by which each state is allocated a number of representatives in the US House of
 * Representatives. Larger states generally receive more delegates than smaller states. The United States has used
 * at least 4 different algorithms for Apportionment at different times. They are:
 * <ol>
 *     <li>{@link JeffersonMethod Jefferson Method}</li>
 *     <li>Hamilton/Vinton Method</li>
 *     <li>Webster Method</li>
 *     <li>Huntington-Hill Method (aka Equal Proportions Method)</li>
 * </ol>
 *
 * @author Will-McBurney
 */

public interface ApportionmentMethod {
    /**
     * This method returns a {@link Representation} object that contains an apportionment of representatives to
     * each state, such that the total number of representatives matches some target number.
     *
     * @param states                the states to be apportioned representatives
     * @param targetRepresentatives the total number of representatives to allocate
     * @return a {@link Representation} object that contains an apportionment of representatives to each state
     */
    Representation getRepresentation(List<State> states, int targetRepresentatives);


    /**
     * Enforces validation rules on the inputs to {@link ApportionmentMethod#getRepresentation(List, int)}. The
     * enforced rules are that:
     * <ul>
     *     <li><code>states</code> must not be empty</li>
     *     <li><code>targetRepresentatives</code> must not be negative</li>
     * </ul>
     * Different apportionment methods may have other validation rules that are enforced within their own class.
     *
     * @param states                the list of states to apportion, which must not be empty
     * @param targetRepresentatives the number of representatives to allocate, which must not be negative
     * @throws IllegalArgumentException if at least validation rule is violated.
     */
    default void validateInputs(List<State> states, int targetRepresentatives) {
        if (states.isEmpty()) {
            throw new IllegalArgumentException("Cannot apportion representatives to an empty list of states");
        }
        if (targetRepresentatives < 0) {
            throw new IllegalArgumentException("Cannot apportion a negative number of representatives");
        }
    }

    /**
     * Gets the <b>divisor</b>, or the average population-per-representative of all the states combined.
     *
     * @param states          the states to be apportioned
     * @param representatives the total number of representatives to allocate
     * @return the average number of residents-per-representative of the entire country
     */
    default double getDivisor(List<State> states, int representatives) {
        return (double) getTotalPopulation(states) / representatives;
    }

    /**
     * Gets the total population of all states.
     *
     * @param states a list of states
     * @return the total population of all the states combined
     */
    default int getTotalPopulation(List<State> states) {
        int totalPopulation = 0;
        for (var state : states) {
            totalPopulation += state.population();
        }
        return totalPopulation;
    }

    /**
     * Get the quotas for each state rounded <b>down</b> to the nearest whole number.
     *
     * @param states  the states to be apportioned representatives by {@link ApportionmentMethod#getRepresentation(List, int)}
     * @param divisor the divisor to use (see {@link ApportionmentMethod#getDivisor(List, int)}
     * @return a {@link Map} where each state (key) maps to its quota <b>rounded down</b> to the nearest whole number.
     * @see ApportionmentMethod#getQuotas(List, double)
     * @see JeffersonMethod#getRepresentation(List, int)
     */
    default Map<State, Integer> getRoundedDownQuotas(List<State> states, double divisor) {
        var roundedDownQuotas = new HashMap<State, Integer>();
        var quotas = getQuotas(states, divisor);
        for (var state : quotas.keySet()) {
            roundedDownQuotas.put(state, roundDown(quotas.get(state)));
        }
        return roundedDownQuotas;
    }

    /**
     * Returns a {@link Map} of each state to it's <b>quota</b>, which is a state's population divided
     * by the {@link ApportionmentMethod#getDivisor(List, int) divisor}.
     *
     * @param states  the list of states being apportioned representatives
     * @param divisor the divisor to use (see {@link ApportionmentMethod#getDivisor(List, int)})
     * @return A {@link Map} where each state (key) maps to its quota (value)
     * @see ApportionmentMethod#getDivisor(List, int)
     */
    default Map<State, Double> getQuotas(List<State> states, double divisor) {
        var quotas = new HashMap<State, Double>();
        for (var state : states) {
            quotas.put(state, state.population() / divisor);
        }
        return quotas;
    }

    /**
     * Returns a number rounded <b>down</b>. Specifically used when rounding quotas.
     *
     * @param quota a state's quota
     * @return the state's quota rounded down to the nearest whole number
     * @see ApportionmentMethod#getRoundedDownQuotas(List, double)
     */
    default int roundDown(double quota) {
        return (int) Math.floor(quota);
    }

    /**
     * Get the quotas for each state rounded <b>up</b> to the nearest whole number.
     *
     * @param states  the states to be apportioned representatives by {@link ApportionmentMethod#getRepresentation(List, int)}
     * @param divisor the divisor to use (see {@link ApportionmentMethod#getDivisor(List, int)}
     * @return a {@link Map} where each state (key) maps to its quota <b>rounded up</b> to the nearest whole number.
     * @see ApportionmentMethod#getQuotas(List, double)
     * @see AdamsMethod#getRepresentation(List, int)
     */
    default Map<State, Integer> getRoundedUpQuotas(List<State> states, double divisor) {
        var roundedDownQuotas = new HashMap<State, Integer>();
        var quotas = getQuotas(states, divisor);
        for (var state : quotas.keySet()) {
            roundedDownQuotas.put(state, roundUp(quotas.get(state)));
        }
        return roundedDownQuotas;

    }

    /**
     * Returns a number rounded <b>up</b>. Specifically used when rounding quotas.
     *
     * @param quota a state's quota
     * @return the state's quota rounded up to the nearest whole number
     * @see ApportionmentMethod#getRoundedUpQuotas(List, double)
     */
    default int roundUp(double quota) {
        return (int) Math.ceil(quota);
    }
}

/*
 * Copyright (c) 2023. Paul "Will" McBurney <br>
 *
 * This software was written as part of an education experience by Prof. Paul "Will" McBurney at the University of Virginia, for the course CS 3140, Software Development Essentials. This source code, or any derivative source code (such as the student's own work building off this source code) is subject to the CS 3140 collaboration policy which can be found here: <a href="https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy">https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy</a>
 *
 * This source code and any derivative work may not be shared publicly through any means. This includes a prohibition on posting this work or derivative work on a public GitHub repository, course help website, file sharing platform, email, job application, etc. Sharing this code or derivative works with other students may be subject to referral to UVA Student Honor, as well as additional penalties.
 *
 * THE SOFTWARE IS PROVIDED &ldquo;AS IS&rdquo;, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */