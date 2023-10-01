/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for AdamsMethod
 *
 * @author Will-McBurney
 */
public class ApportionmentMethodTest {
    @Test
    void getTotalPopulation() {
        var states = List.of(new State("a", 25),
                new State("b", 10),
                new State("c", 5));
        var apportionmentMethod = new JeffersonMethod();
        assertEquals(40, apportionmentMethod.getTotalPopulation(states));
    }

    @Test
    void getDivisor() {
        var states = List.of(new State("a", 25),
                new State("b", 10),
                new State("c", 5));
        var apportionmentMethod = new JeffersonMethod();
        assertEquals(8.0, apportionmentMethod.getDivisor(states, 5), 1e-12);
    }

    @ParameterizedTest
    @ValueSource(doubles = {4.0, 4.1, 4.5, 4.99})
    void roundDown(double quota) {
        var apportionmentMethod = new JeffersonMethod();
        assertEquals(4, apportionmentMethod.roundDown(quota));
    }

    @ParameterizedTest
    @ValueSource(doubles = {4.01, 4.5, 4.99, 5.0})
    void roundUp(double input) {
        var apportionmentMethod = new AdamsMethod();
        assertEquals(5, apportionmentMethod.roundUp(input));
    }


    @Test
    void getQuotas() {
        var ohio = new State("Ohio", 27);
        var virginia = new State("Virginia", 14);
        var maryland = new State("Maryland", 6);
        var states = List.of(ohio, virginia, maryland);
        var apportionmentMethod = new JeffersonMethod();

        var quotas = apportionmentMethod.getQuotas(states, 5);
        assertEquals(3, quotas.size());
        assertEquals(5.4, quotas.get(ohio), 1e-12);
        assertEquals(2.8, quotas.get(virginia), 1e-12);
        assertEquals(1.2, quotas.get(maryland), 1e-12);
    }

    @Test
    void getRoundedDownQuotas() {
        var ohio = new State("Ohio", 27);
        var virginia = new State("Virginia", 14);
        var maryland = new State("Maryland", 6);
        var states = List.of(ohio, virginia, maryland);
        var apportionmentMethod = new AdamsMethod();

        var roundedDownQuotas = apportionmentMethod.getRoundedDownQuotas(states, 5);
        assertEquals(3, roundedDownQuotas.size());
        assertEquals(5, roundedDownQuotas.get(ohio));
        assertEquals(2, roundedDownQuotas.get(virginia));
        assertEquals(1, roundedDownQuotas.get(maryland));
    }

    @Test
    void getRoundedUpQuotas() {
        var ohio = new State("Ohio", 27);
        var virginia = new State("Virginia", 14);
        var maryland = new State("Maryland", 5);
        var states = List.of(ohio, virginia, maryland);
        var apportionmentMethod = new AdamsMethod();

        var roundedUpQuotas = apportionmentMethod.getRoundedUpQuotas(states, 5);
        assertEquals(3, roundedUpQuotas.size());
        assertEquals(6, roundedUpQuotas.get(ohio));
        assertEquals(3, roundedUpQuotas.get(virginia));
        assertEquals(1, roundedUpQuotas.get(maryland));
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