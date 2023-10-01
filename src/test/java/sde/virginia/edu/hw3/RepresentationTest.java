/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AdamsMethod
 *
 * @author Will-McBurney
 */
class RepresentationTest {
    @Test
    void getRepresentativesFor_present() {
        var ohio = new State("Ohio", 10);
        var inputMap = new HashMap<>(Map.of(ohio, 4));
        var representation = new Representation(inputMap);

        assertEquals(4, representation.getRepresentativesFor(ohio));
    }

    @Test
    void getRepresentativesFor_notPresent() {
        var ohio = new State("Ohio", 10);
        var inputMap = new HashMap<State, Integer>(Map.of());
        var representation = new Representation(inputMap);

        assertEquals(0, representation.getRepresentativesFor(ohio));
    }

    @Test
    void setRepresentativesFor_notPresent() {
        var ohio = new State("Ohio", 10);
        var inputMap = new HashMap<State, Integer>(Map.of());
        var representation = new Representation(inputMap);

        representation.setRepresentativesFor(ohio, 4);
        assertEquals(4, representation.getRepresentativesFor(ohio));
    }

    @Test
    void setRepresentativesFor_present() {
        var ohio = new State("Ohio", 10);
        var inputMap = new HashMap<>(Map.of(ohio, 10));
        var representation = new Representation(inputMap);

        representation.setRepresentativesFor(ohio, 4);
        assertEquals(4, representation.getRepresentativesFor(ohio));
    }

    @Test
    void getStates() {
        var ohio = new State("Ohio", 10);
        var virginia = new State("Virginia", 15);
        var representation = new Representation(new HashMap<>(Map.of(
                ohio, 5, virginia, 2
        )));

        var stateSet = representation.getStates();
        assertEquals(2, stateSet.size());
        assertTrue(stateSet.contains(ohio));
        assertTrue(stateSet.contains(virginia));
    }

    @Test
    void getAllocatedRepresentatives_nonEmpty() {
        var ohio = new State("Ohio", 10);
        var virginia = new State("Virginia", 15);
        var representation = new Representation(new HashMap<>(Map.of(
                ohio, 5, virginia, 2
        )));

        assertEquals(7, representation.getAllocatedRepresentatives());
    }


    @Test
    void getAllocatedRepresentatives_empty() {
        var representation = new Representation();

        assertEquals(0, representation.getAllocatedRepresentatives());
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