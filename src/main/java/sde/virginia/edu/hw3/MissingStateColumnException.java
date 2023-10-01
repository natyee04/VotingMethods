/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

/**
 * This exception is thrown when a tabular input file (such as a {@link CSVStateReader csv}, {@link
 * SpreadsheetStateReader .xlsx, or .xls} is missing a required to generate {@link State objects}
 *
 * @author Will-McBurney
 */
public class MissingStateColumnException extends RuntimeException {
    /**
     * Throws an exception to signal that at least one required column is missing from an input file.
     *
     * @param filename             the name of the file with the missing heading(s)
     * @param missingColumnHeaders the required heading(s) that is missing
     * @see CSVStateReader
     * @see SpreadsheetStateReader
     */
    public MissingStateColumnException(String filename, String... missingColumnHeaders) {
        super(getErrorMessage(filename, missingColumnHeaders));
    }

    private static String getErrorMessage(String filename, String[] missingColumnHeaders) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Your file %s the following column names:\n", filename));
        for (String missingColumn : missingColumnHeaders) {
            stringBuilder.append(String.format("\t- %s", missingColumn));
        }
        return stringBuilder.toString();
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