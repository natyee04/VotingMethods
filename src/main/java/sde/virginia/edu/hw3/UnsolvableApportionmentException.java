/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;


import java.util.List;

/**
 * This exception is thrown when a given apportionment is not solvable by a particular apportionment method for
 * a given list of states and a target number of representatives. This can be because of an unresolvable tie, for
 * example.
 *
 * @author Will-McBurney
 */
public class UnsolvableApportionmentException extends RuntimeException {
    /**
     * Throws an exception denoting an unsolvable apportionment situation.
     *
     * @param apportionmentMethod   the apportionment method used
     * @param states                the list of states to be apportioned
     * @param targetRepresentatives the total number of representatives to allocate to all states combined
     */
    public UnsolvableApportionmentException(ApportionmentMethod apportionmentMethod,
                                            List<State> states, int targetRepresentatives) {
        super(String.format("""
                For the following inputs, a %s is impossible:
                    target representatives: %d
                    state list: %s
                """, apportionmentMethod.getClass().getName(), targetRepresentatives, states));
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