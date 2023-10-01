/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents the results of an apportionment. Apportionment is the process where each state is allocated
 * a set number of representatives in the US House of Representatives every 10 years based on Census results. Generally,
 * larger states receive more representatives, while smaller states receiver fewer representatives.
 *
 * @author Will-McBurney
 */
public class Representation {
    private final Map<State, Integer> stateRepresentatives;

    /**
     * Creates an empty representation. That is, no states have been given any representatives.
     */
    public Representation() {
        this(new HashMap<>());
    }

    /**
     * Allows for direct injection of a Map for testing
     *
     * @param stateRepresentatives a map of states and their allocated representatives
     */
    protected Representation(Map<State, Integer> stateRepresentatives) {
        this.stateRepresentatives = stateRepresentatives;
    }

    /**
     * Get the set of all states apportioned representatives.
     *
     * @return an unmodifiable copy of the set of states apportioned representatives
     */
    public Set<State> getStates() {
        return Collections.unmodifiableSet(stateRepresentatives.keySet());
    }

    /**
     * Get the number of representatives apportioned to a specific state.
     *
     * @param state a US state
     * @return the number of representatives apportioned to the state. If the state is not in the set of States (see
     * {@link Representation#getStates()}), then return 0, since the state has not received representatives.
     * @see Representation#setRepresentativesFor(State, int)
     */
    public int getRepresentativesFor(State state) {
        if (!stateRepresentatives.containsKey(state)) {
            return 0;
        }
        return stateRepresentatives.get(state);
    }

    /**
     * Sets the number of representatives for a state. This will <b>overwrite</b> a state's previous value if the state
     * is already in the {@link Representation} object.
     *
     * @param state           a state to allocate a set number of representatives to
     * @param representatives the number of representatives that state receives in the US House of Representatives
     * @see Representation#getRepresentativesFor(State)
     */
    public void setRepresentativesFor(State state, int representatives) {
        stateRepresentatives.put(state, representatives);
    }

    /**
     * Gives the total number of representatives that have been allocated to all states combined
     *
     * @return the total number of apportioned representatives combined
     */
    public int getAllocatedRepresentatives() {
        return stateRepresentatives.values().stream().mapToInt(number -> number).sum();
    }

    /**
     * Generate a formatted {@link String} to display a Representation
     *
     * @param format the format of the Representation String, including what information is displayed and
     *               sorting order.
     * @return a {@link String} describing the {@link Representation} object.
     * @see RepresentationFormat#getFormattedString(Representation)
     * @see AlphabeticalFormat#getFormattedString(Representation)
     */
    public String getFormattedString(RepresentationFormat format) {
        return format.getFormattedString(this);
    }

    @Override
    public String toString() {
        return stateRepresentatives.entrySet().stream().map(Object::toString).collect(Collectors.joining("\n"));
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