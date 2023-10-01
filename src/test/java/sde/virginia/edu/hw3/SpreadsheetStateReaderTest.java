/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for AdamsMethod
 *
 * @author Will-McBurney
 */
class SpreadsheetStateReaderTest {
    @Test
    void getStates_normal_xlsx() {
        var classLoader = SpreadsheetStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("excelExampleNormal.xlsx")).getFile();
        var excelStateReader = new SpreadsheetStateReader(filename);

        var states = excelStateReader.getStates();

        assertEquals(5, states.size());
        var firstState = states.get(0);
        assertEquals("Ohio", firstState.name());
        assertEquals(11808848, firstState.population());
        var secondState = states.get(1);
        assertEquals("Virginia", secondState.name());
        assertEquals(8654542, secondState.population());
        var lastState = states.get(4);
        assertEquals("Maryland", lastState.name());
        assertEquals(6185278, lastState.population());
    }

    @Test
    void getStates_normal_xls() {
        var classLoader = SpreadsheetStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("excelExampleNormal.xls")).getFile();
        var excelStateReader = new SpreadsheetStateReader(filename);

        var states = excelStateReader.getStates();

        assertEquals(5, states.size());
        var firstState = states.get(0);
        assertEquals("Ohio", firstState.name());
        assertEquals(11808848, firstState.population());
        var secondState = states.get(1);
        assertEquals("Virginia", secondState.name());
        assertEquals(8654542, secondState.population());
        var lastState = states.get(4);
        assertEquals("Maryland", lastState.name());
        assertEquals(6185278, lastState.population());
    }

    @Test
    void getStates_badLines_xlsx() {
        var classLoader = SpreadsheetStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("excelExampleBadLines.xlsx")).getFile();
        var excelStateReader = new SpreadsheetStateReader(filename);

        var states = excelStateReader.getStates();

        assertEquals(3, states.size());
        var firstState = states.get(0);
        assertEquals("Ohio", firstState.name());
        assertEquals(11808848, firstState.population());
        var secondState = states.get(1);
        assertEquals("Virginia", secondState.name());
        assertEquals(8654542, secondState.population());
        var lastState = states.get(2);
        assertEquals("Delaware", lastState.name());
        assertEquals(990837, lastState.population());
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