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
class CSVStateReaderTest {

    @Test
    public void getStates_mixedColumns() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("mixedColumns.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);

        var outputStateList = csvStateReader.getStates();

        assertEquals(3, outputStateList.size());
        var firstState = outputStateList.get(0);
        assertEquals("Ohio", firstState.name());
        assertEquals(100, firstState.population());
        var secondState = outputStateList.get(1);
        assertEquals("Virginia", secondState.name());
        assertEquals(125, secondState.population());
        var thirdState = outputStateList.get(2);
        assertEquals("West Virginia", thirdState.name());
        assertEquals(3, thirdState.population());
    }

    @Test
    void getStateFromLine() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        csvStateReader.setStateColumnIndex(0);
        csvStateReader.setPopulationColumnIndex(1);

        var optionalState = csvStateReader.getStateFromLine("Arizona,125", 0);

        assertTrue(optionalState.isPresent());
        var state = optionalState.get();
        assertEquals("Arizona", state.name());
        assertEquals(125, state.population());
    }

    @Test
    void getStateFromLine_mixedColumns() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        csvStateReader.setStateColumnIndex(1);
        csvStateReader.setPopulationColumnIndex(3);

        var optionalState = csvStateReader.getStateFromLine("1,Arizona,AZ,125", 0);

        assertTrue(optionalState.isPresent());
        var state = optionalState.get();
        assertEquals("Arizona", state.name());
        assertEquals(125, state.population());
    }

    @Test
    void getStateFromLine_withExtraWhitespace() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        csvStateReader.setStateColumnIndex(0);
        csvStateReader.setPopulationColumnIndex(1);

        var optionalState = csvStateReader.getStateFromLine("  Arizona , 125   ", 0);

        assertTrue(optionalState.isPresent());
        var state = optionalState.get();
        assertEquals("Arizona", state.name());
        assertEquals(125, state.population());
    }

    @Test
    void getStateFromLine_noComma() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        csvStateReader.setStateColumnIndex(0);
        csvStateReader.setPopulationColumnIndex(1);

        var optionalState = csvStateReader.getStateFromLine("Arizona 125", 0);

        assertFalse(optionalState.isPresent());
    }

    @Test
    void getStateFromLine_populationNotNumber() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        csvStateReader.setStateColumnIndex(0);
        csvStateReader.setPopulationColumnIndex(1);

        var optionalState = csvStateReader.getStateFromLine("Arizona, five", 0);

        assertFalse(optionalState.isPresent());
    }

    @Test
    void getStateFromLine_populationNegative() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        csvStateReader.setStateColumnIndex(0);
        csvStateReader.setPopulationColumnIndex(1);

        var optionalState = csvStateReader.getStateFromLine("Arizona, -37", 0);

        assertFalse(optionalState.isPresent());
    }

    @Test
    void getStates() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);

        var outputStateList = csvStateReader.getStates();

        assertEquals(3, outputStateList.size());
        var firstState = outputStateList.get(0);
        assertEquals("Ohio", firstState.name());
        assertEquals(100, firstState.population());
        var secondState = outputStateList.get(1);
        assertEquals("Virginia", secondState.name());
        assertEquals(125, secondState.population());
        var thirdState = outputStateList.get(2);
        assertEquals("West Virginia", thirdState.name());
        assertEquals(3, thirdState.population());
    }

    @Test
    void getState_BadFile() {
        assertThrows(IllegalArgumentException.class,
                () -> new CSVStateReader("nonExistentFile.csv"));

    }

    @Test
    void findTargetColumns() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        var headerRow = "ID,State,Postal,Population,Have I Lived There";

        csvStateReader.findTargetColumns(headerRow);

        assertEquals(1, csvStateReader.getStateColumnIndex());
        assertEquals(3, csvStateReader.getPopulationColumnIndex());
    }

    @Test
    void findTargetColumns_exception_state() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        var headerRow = "ID,Postal,Population,Have I Lived There";

        assertThrows(MissingStateColumnException.class, () -> csvStateReader.findTargetColumns(headerRow));
    }

    @Test
    void findTargetColumns_exception_population() {
        var classLoader = CSVStateReaderTest.class.getClassLoader();
        var filename = Objects.requireNonNull(classLoader.getResource("csvReaderSample.csv")).getFile();
        var csvStateReader = new CSVStateReader(filename);
        var headerRow = "ID,State,Postal,Have I Lived There";

        assertThrows(MissingStateColumnException.class, () -> csvStateReader.findTargetColumns(headerRow));
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