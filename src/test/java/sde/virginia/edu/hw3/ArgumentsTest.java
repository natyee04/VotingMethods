/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.Objects;

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
        var filename = Objects.requireNonNull(classLoader.getResource("mixedColumns.csv")).getFile();
        var args = new String[] {filename};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(CSVStateReader.class, arguments.getStateSupplier());
    }

    @Test
    void getStateSupplier_xls() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("excelExampleNormal.xls")).getFile();
        var args = new String[] {filename};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(SpreadsheetStateReader.class, arguments.getStateSupplier());
    }

    @Test
    void getStateSupplier_xlsx() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("excelExampleNormal.xlsx")).getFile();
        var args = new String[] {filename};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(SpreadsheetStateReader.class, arguments.getStateSupplier());
    }

    @Test
    void getStateSupplier_fileNotFound() {
        var args = new String[] {"notARealFile.csv"};
        Arguments arguments = new Arguments(args);
        assertThrows(IllegalArgumentException.class, arguments::getStateSupplier);
    }

    @Test
    void getRepresentatives_oneArg() {
        String[] args = {"populations.csv"};
        Arguments arguments = new Arguments(args);
        assertEquals(435, arguments.getRepresentatives());
    }

    @Test
    void getRepresentatives_twoArgs() {
        String[] args = {"populations.csv", "25"};
        Arguments arguments = new Arguments(args);
        assertEquals(25, arguments.getRepresentatives());
    }

    @Test
    void getRepresentatives_negativeArg() {
        String[] args = {"populations.csv", "-25"};
        Arguments arguments = new Arguments(args);
        assertThrows(IllegalArgumentException.class, arguments::getRepresentatives);
    }

    @Test
    void getRepresentatives_nonNumber() {
        String[] args = {"populations.csv", "some string"};
        Arguments arguments = new Arguments(args);
        assertEquals(435, arguments.getRepresentatives());
    }

    @Test
    void getApportionmentMethod_Adams_noRepCount() {
        String[] args = {"populations.csv", "--adams"};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(AdamsMethod.class, arguments.getApportionmentMethod());
    }

    @Test
    void getApportionmentMethod_Adams_withRepCount() {
        String[] args = {"populations.csv", "400", "--adams"};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(AdamsMethod.class, arguments.getApportionmentMethod());
    }

    @Test
    void getApportionmentMethod_default() {
        String[] args = {"populations.csv"};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(JeffersonMethod.class, arguments.getApportionmentMethod());
    }

    @Test
    void getRepresentationFormat_default() {
        String[] args = {"populations.csv"};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(AlphabeticalFormat.class, arguments.getRepresentationFormat());
    }

    @Test
    void getRepresentationFormat_populationAscending() {
        String[] args = {"populations.csv"};
        Arguments arguments = new Arguments(args);
        assertInstanceOf(AlphabeticalFormat.class, arguments.getRepresentationFormat());
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