/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class parses spreadsheet files of the format .xls and .xlsx to extract a list of {@link State states}
 * via {@link SpreadsheetStateReader#getStates()}. Some example formats can be seen here:
 * <ul>
 *     <li><a href="https://drive.google.com/drive/folders/1hzDwjIsp4ygLlEORwqx29TmiDnlH8nct?usp=drive_link">
 *         Set of input examples including spreadsheet files</a></li>
 * </ul>
 * <br>
 * The expected spreadsheet file format requires two specific column headers:
 * <ul>
 *     <li><b>"State"</b> - the name of each state</li>
 *     <li><b>"Population"</b> - the population of that state</li>
 * </ul>
 * Column heads are case-insensitive.<br>
 * All other columns are ignored. <br>
 * If a badly formatted line is encountered, a warning is printed to the console, and
 * the line is skipped (and not returned by {@link SpreadsheetStateReader#getStates()}).
 *
 * @author Will-McBurney
 */

public class SpreadsheetStateReader implements StateSupplier {
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
     * @throws IllegalArgumentException if the filename doesn't end with .xlsx or .xls or the file
     *                                  does not exist.
     */
    public SpreadsheetStateReader(String filename) {
        if (!isSpreadsheetFilename(filename)) {
            throw new IllegalArgumentException(
                    String.format("Bad file format. %s only supports .xls and .xlsx files", getClass().getName()));
        }
        File file = new File(filename);
        if (!file.exists()) {
            throw new IllegalArgumentException("File: " + filename + " does not exist");
        }
        this.filename = filename;
    }

    private static boolean isSpreadsheetFilename(String filename) {
        return filename.toLowerCase().endsWith(".xlsx") || filename.toLowerCase().endsWith(".xls");
    }

    public List<State> getStates() {
        try {
            var workbook = getWorkbookFromFilename(filename);
            var worksheet = workbook.getSheetAt(0);
            var rowIterator = worksheet.rowIterator();
            if (!rowIterator.hasNext()) {
                throw new IllegalArgumentException("Empty Excel file");
            }
            var headerRow = rowIterator.next();
            findTargetColumns(headerRow);
            var states = new ArrayList<State>();
            while (rowIterator.hasNext()) {
                var row = rowIterator.next();
                var optionalState = getStateFromRow(row);
                optionalState.ifPresent(states::add);
            }
            return states;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Workbook getWorkbookFromFilename(String filename) throws IOException {
        if (filename.toLowerCase().endsWith("xlsx")) {
            return new XSSFWorkbook(filename);
        }
        if (filename.toLowerCase().endsWith("xls")) {
            return new HSSFWorkbook(new FileInputStream(filename));
        }
        throw new IllegalArgumentException("Bad workbook filename");
    }

    private void findTargetColumns(Row headerRow) {
        var cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            var cell = cellIterator.next();
            if (cell.getCellType() == CellType.STRING &&
                    cell.getStringCellValue().equalsIgnoreCase(STATE_COLUMN_HEADER)) {
                stateColumnIndex = cell.getColumnIndex();
            }
            if (cell.getCellType() == CellType.STRING &&
                    cell.getStringCellValue().equalsIgnoreCase(POPULATION_COLUMN_HEADER)) {
                populationColumnIndex = cell.getColumnIndex();
            }
        }
        if (stateColumnIndex == NOT_FOUND || populationColumnIndex == NOT_FOUND) {
            throwMissingColumnException();
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

    private Optional<State> getStateFromRow(Row row) {
        var stateNameCell = row.getCell(stateColumnIndex);
        if (stateNameCell.getCellType() != CellType.STRING) {
            return getEmptyFromBadLine(row);
        }
        var stateName = stateNameCell.getStringCellValue();
        var populationCell = row.getCell(populationColumnIndex);
        if (populationCell.getCellType() != CellType.NUMERIC) {
            return getEmptyFromBadLine(row);
        }
        var populationDouble = populationCell.getNumericCellValue();
        var populationInt = (int) populationDouble;
        if (populationInt < 0 || populationDouble != populationInt) {
            return getEmptyFromBadLine(row);
        }
        return Optional.of(new State(stateName, populationInt));
    }

    private static Optional<State> getEmptyFromBadLine(Row row) {
        System.out.printf("Bad Row - rowNumber %d \n", row.getRowNum());
        return Optional.empty();
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