/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class parses CSV files to extract a list of {@link State states} via {@link CSVStateReader#getStates()}.
 * Some example formats can be seen here:
 * <ul>
 *     <li><a href="https://drive.google.com/drive/folders/1hzDwjIsp4ygLlEORwqx29TmiDnlH8nct?usp=drive_link">
 *         Set of input examples including CSV files</a></li>
 * </ul>
 * <br>
 * The expected CSV file format requires two specific column headers:
 * <ul>
 *     <li><b>"State"</b> - the name of each state</li>
 *     <li><b>"Population"</b> - the population of that state</li>
 * </ul>
 * Column heads are case-insensitive.<br>
 * All other columns are ignored. <br>
 * If a badly formatted line is encountered, a warning is printed to the console, and
 * the line is skipped (and not returned by {@link CSVStateReader#getStates()}).
 *
 * @author Will-McBurney
 */
public class CSVStateReader implements StateSupplier {
    /**
     * The required column header for state names (normalized to lowercase)
     */
    public static final String STATE_COLUMN_HEADER = "state";
    /**
     * The required column header for state populations (normalized to lowercase)
     */
    public static final String POPULATION_COLUMN_HEADER = "population";

    private static final int NOT_FOUND = -1;
    private final String filename;
    private int stateColumnIndex = NOT_FOUND;
    private int populationColumnIndex = NOT_FOUND;

    /**
     * Create a CSVStateReader from a .csv filename
     *
     * @param filename the name of the CSV file including the path
     * @throws IllegalArgumentException if the filename doesn't end with CSV or the file does not exist
     */
    public CSVStateReader(String filename) {
        if (!filename.toLowerCase().endsWith("csv")) {
            throw new IllegalArgumentException("Non-CSV file provided: " + filename);
        }
        File file = new File(filename);
        if (!file.exists()) {
            throw new IllegalArgumentException("Bad filename - " + filename + " does not exist");
        }
        this.filename = filename;
    }

    /**
     * Gets the list of states from a CSV file.
     *
     * @return a {@link List} of {@link State states} extracted from the CSV file.
     * @throws RuntimeException            if an IOException is encountered
     * @throws MissingStateColumnException if a required column is missing (see {@link
     *                                     CSVStateReader#STATE_COLUMN_HEADER} and
     *                                     {@link CSVStateReader#POPULATION_COLUMN_HEADER})
     */
    public List<State> getStates() {
        try (FileReader fileReader = new FileReader(filename); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            var headerRow = bufferedReader.readLine();
            findTargetColumns(headerRow);
            var lineNumber = 1;
            var stateList = new ArrayList<State>();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                var optionalState = getStateFromLine(line, lineNumber);
                optionalState.ifPresent(stateList::add);
                lineNumber++;
            }
            return stateList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void findTargetColumns(String headerRow) {
        var splitHeader = headerRow.split(",");
        for (int index = 0; index < splitHeader.length; index++) {
            if (splitHeader[index].equalsIgnoreCase(STATE_COLUMN_HEADER)) {
                stateColumnIndex = index;
            }
            if (splitHeader[index].equalsIgnoreCase(POPULATION_COLUMN_HEADER)) {
                populationColumnIndex = index;
            }
        }
        if (stateColumnIndex == NOT_FOUND || populationColumnIndex == NOT_FOUND) {
            throwMissingColumnException();
        }
    }

    protected Optional<State> getStateFromLine(String csvLine, int lineNumber) {
        try {
            var splitLine = csvLine.split(",");
            var name = splitLine[stateColumnIndex].strip();
            var population = Integer.parseInt(splitLine[populationColumnIndex].strip());
            if (population < 0) {
                printBadLineWarning(csvLine, lineNumber);
                return Optional.empty();
            }
            return Optional.of(new State(name, population));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            printBadLineWarning(csvLine, lineNumber);
            return Optional.empty();
        }
    }

    private void throwMissingColumnException() {
        List<String> missingColumnHeaders = new ArrayList<>(2);
        if (stateColumnIndex == NOT_FOUND) {
            missingColumnHeaders.add(STATE_COLUMN_HEADER);
        }
        if (populationColumnIndex == NOT_FOUND) {
            missingColumnHeaders.add(POPULATION_COLUMN_HEADER);
        }
        throw new MissingStateColumnException(filename, missingColumnHeaders.toArray(new String[0]));
    }

    private void printBadLineWarning(String csvLine, int lineNumber) {
        System.out.println("Bad Line - Line # " + lineNumber + " - contents: " + csvLine);
    }

    protected int getStateColumnIndex() {
        return stateColumnIndex;
    }

    protected void setStateColumnIndex(int stateColumnIndex) {
        this.stateColumnIndex = stateColumnIndex;
    }

    protected int getPopulationColumnIndex() {
        return populationColumnIndex;
    }

    protected void setPopulationColumnIndex(int populationColumnIndex) {
        this.populationColumnIndex = populationColumnIndex;
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