/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AdamsMethod
 *
 * @author Will-McBurney
 */
class ArgumentsTest {
    @Test
    void constructor_zeroArg_exception() {
        String[] args = {};
        assertThrows(IllegalArgumentException.class, () -> new Arguments(args));
    }

    @Test
    void getStateSupplier_csv() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var args = new String[] {"mixedColumns.csv"};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(CSVStateReader.class, arguments.getStateSupplier());
    }

    @Test
    void getStateSupplier_xls() {
    }

    @Test
    void getStateSupplier_xlsx() {
    }

    @Test
    void getStateSupplier_fileNotFound() {
    }

    @Test
    void getRepresentatives_oneArg() {
        String[] args = {"populations.csv"};
        Arguments arguments = new Arguments(args);
        assertEquals(435, arguments.getRepresentatives());
    }

    @Test
    void getRepresentatives_twoArgs_longOption() {
        String[] args = {"populations.csv", "--representatives", "25"};
        Arguments arguments = new Arguments(args);
        assertEquals(25, arguments.getRepresentatives());
    }
    @Test
    void getRepresentatives_twoArgs_shortOption() {
        String[] args = {"populations.csv", "-r", "5"};
        Arguments arguments = new Arguments(args);
        assertEquals(5, arguments.getRepresentatives());
    }
    @Test
    void getRepresentatives_negativeArg() {
        String[] args = {"populations.csv", "--representatives", "-25"};
        Arguments arguments = new Arguments(args);
        assertThrows(IllegalArgumentException.class, arguments::getRepresentatives);
    }

    @Test
    void getRepresentatives_nonNumber() {
        String[] args = {"populations.csv", "--representatives", "some string"};
        Arguments arguments = new Arguments(args);
        assertThrows(NumberFormatException.class, arguments::getRepresentatives);
    }

    @Test
    void getApportionmentMethod_Adams_noRepCount() {
    }

    @Test
    void getApportionmentMethod_Adams_withRepCount() {
    }

    @Test
    void getApportionmentMethod_default() {
    }

    @Test
    void getRepresentationFormat_default() {
    }

    @Test
    void getRepresentationFormat_populationAscending() {
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