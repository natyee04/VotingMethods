package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PopulationFormatTest {
    @Test
    void getDisplayOrder_default() {
        var format = new PopulationFormat();
        assertEquals(DisplayOrder.ASCENDING, format.getDisplayOrder());
    }

    @Test
    void getDisplayOrder_ascending() {
        var format = new PopulationFormat(DisplayOrder.ASCENDING);
        assertEquals(DisplayOrder.ASCENDING, format.getDisplayOrder());
    }

    @Test
    void getDisplayOrder_descending() {
        var format = new PopulationFormat(DisplayOrder.DESCENDING);
        assertEquals(DisplayOrder.DESCENDING, format.getDisplayOrder());
    }

    @Test
    void setDisplayOrder() {
        var format = new PopulationFormat();
        format.setDisplayOrder(DisplayOrder.DESCENDING);
        assertEquals(DisplayOrder.DESCENDING, format.getDisplayOrder());
    }

    @Test
    void setDisplayOrder_null_Exception() {
        var format = new PopulationFormat();
        assertThrows(IllegalArgumentException.class, () -> format.setDisplayOrder(null));
    }

    @Test
    void getFormattedString_ascending() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("West Virginia", 5), 0,
                        new State("Maryland", 8), 1,
                        new State("Virginia", 20), 4,
                        new State("Ohio", 15), 16)));

        var format = new PopulationFormat(DisplayOrder.ASCENDING);
        var expected = """
                State           |Population| Reps
                West Virginia   |         5|    0
                Maryland        |         8|    1
                Ohio            |        15|   16
                Virginia        |        20|    4
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }

    @Test
    void getFormattedString_descending() {
        var representation = new Representation(new HashMap<>(
                Map.of(new State("West Virginia", 5), 0,
                        new State("Maryland", 8), 1,
                        new State("Virginia", 20), 4,
                        new State("Ohio", 15), 16)));

        var format = new PopulationFormat(DisplayOrder.DESCENDING);
        var expected = """
                State           |Population| Reps
                Virginia        |        20|    4
                Ohio            |        15|   16
                Maryland        |         8|    1
                West Virginia   |         5|    0
                """;
        assertEquals(expected, format.getFormattedString(representation));
    }
}

/*
 * Copyright (c) 2023.
 *
 * This software was written as part of an education experience by Prof. Paul "Will" McBurney at the University of Virginia, for the course CS 3140, Software Development Essentials. This source code, or any derivative source code (such as the student's own work building off this source code) is subject to the CS 3140 collaboration policy which can be found here: <a href="https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy">https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy</a>
 *
 * This source code and any derivative work may not be shared publicly through any means. This includes a prohibition on posting this work or derivative work on a public GitHub repository, course help website, file sharing platform, email, job application, etc. Sharing this code or derivative works with other students may be subject to referral to UVA Student Honor, as well as additional penalties.
 *
 * THE SOFTWARE IS PROVIDED &ldquo;AS IS&rdquo;, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */