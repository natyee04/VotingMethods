/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.*;


/**
 * This class handles interpreting the command-line arguments in {@link Main#main(String[])}.
 * Currently, the following arguments format is supported:<br>
 * <code>&lt;filename&gt; [&lt;representatives>&gt;] [--adams] [--population] [--descending]</code>
 *
 * <ul>
 *     <li><code>&lt;filename&gt;</code> is a required text argument to specify the input file. Currently, .csv, .xlsx, and .xls are
 *     supported. See {@link Arguments#getStateSupplier()}</li>
 *     <li><code>[&lt;representatives&gt;]</code> is an <b>optional</b> numeric argument to specify the number of
 *     representatives to allocate. This number must be a positive integer.
 *     See {@link Arguments#getRepresentatives()}</li>
 *     <li>[--adams] is an <b>optional</b> flag argument to specify that you want to use the {@link AdamsMethod Adams
 *     apportionment Method}. By default, the {@link JeffersonMethod Jefferson apportionment method} is used.</li>
 *     <li>[--population] is an <b>optional</b> flag argument to specify that you want the output displayed sorted by
 *     population (by default in ascending order).
 *     <li>[--descending] is an <b>optional</b> flag argument that, when present with --population, specifies that you
 *     want the states printed in descending order. This argument is ignored if --population is missing, and it does not
 *     affect printing in alphabetical order.
 * </ul>
 *
 * Examples:
 * <ul>
 *     <li><code>java -jar Apportionment.jar filename.csv</code> : Apportion with Jefferson method using 435
 *     representatives, displaying in alphabetical order, using data from filename.csv</li>
 *     <li><code>java -jar Apportionment.jar filename.xlsx 1000</code> : Apportion with Jefferson method using 1000
 *       representatives, displaying in alphabetical order, using data from filename.xlsx</li>
 *     <li><code>java -jar Apportionment.jar filename.xlsx --adams --population</code> : Apportion with Adams method
 *     using 1000 representatives, displaying in ascending population order, using data from filename.xlsx</li>
 *     <li><code>java -jar Apportionment.jar filename.csv 100 --population --descending</code> : Apportion with Jefferson
 *     method using 100 representatives, displaying in descending population order, using data from filename.xlsx</li>
 * </ul>
 *
 * @author Will-McBurney
 */
public class Arguments {
    /**
     * The number of representatives in the US House of Representatives since 1913
     */
    public static final int DEFAULT_REPRESENTATIVE_COUNT = 435;
    private List<String> arguments;
    private final List<String> shortOptions = Arrays.asList("-r", "-f", "-m", "-a", "-d");
    /**
     * Constructor for {@link Arguments}
     *
     * @param args the command-line arguments passed into {@link Main#main(String[])}
     */
    public Arguments(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("""
                    This program requires command line arguments:
                       java -jar Apportionment.jar <filename.csv> [number of representatives to allocate]""");
        }

        arguments = Arrays.asList(args);


        List<String> modifiedArguments = new ArrayList<>();

        for (String options: arguments) {
            List<String> longOptions = Arrays.asList("--representatives", "--format", "--method", "--ascending", "--descending");
            if (options.startsWith("--") && !longOptions.contains(options)) {
                throw new IllegalArgumentException("Cannot have invalid long command option(s).");
            }
        }

        for (String options : arguments) {
            if (options.startsWith("-") && !options.matches("-?\\d+(\\.\\d+)?") && options.charAt(1) != '-') {

                if (options.length() > 2) {

                    HashMap<Integer, String> combineArguments = new HashMap<>();
                    HashMap<Integer, String> combineOptions= new HashMap<>();

                    List<String> deconstructedCombinedOptions = new ArrayList<>();

                    var optionWithoutDash = options.substring(1);

                    boolean allOptionsAreValid = optionWithoutDash.chars().allMatch(shortCommand -> shortOptions.contains("-" + ((char) shortCommand)));

                    boolean ascendingFlag = options.chars().anyMatch(shortCommand -> Character.toString((char) shortCommand).equals("a"));
                    boolean descendingFlag = options.chars().anyMatch(shortCommand -> Character.toString((char) shortCommand).equals("d"));

                    int combinedOptionsIndex = arguments.indexOf(options);

                    if (allOptionsAreValid) {
                        int endOfOptionArgumentsIndex;

                        int index = 0;
                        for (char c : optionWithoutDash.toCharArray()) {
                            System.out.println("This is the letter: "+ c);
                            if (c == 'a' || c == 'd') {
                                combineOptions.put(-1, "-"+c);
                            }
                            else {
                                combineOptions.put(index, "-" + c);
                            }

                            index++;
                        }


                        try {
                             if (descendingFlag || ascendingFlag) {
                                 endOfOptionArgumentsIndex = combinedOptionsIndex + options.length() - 2;
                             }
                             else {
                                 endOfOptionArgumentsIndex = combinedOptionsIndex + options.length() - 1;
                             }

                            index= 0;
                            for (int j = combinedOptionsIndex + 1; j <= endOfOptionArgumentsIndex; j++) {
                                combineArguments.put(index, arguments.get(j));

                                index++;
                            }

                            List<String> sub = arguments.subList(combinedOptionsIndex, endOfOptionArgumentsIndex + 1);

                            for (String argument : arguments) {
                                if (!sub.contains(argument)) {
                                    modifiedArguments.add(argument);
                                }
                            }

                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new ArrayIndexOutOfBoundsException("Missing arguments for the combined short-hand commands.");
                        }


                        for (int key: combineOptions.keySet()) {
                            deconstructedCombinedOptions.add(combineOptions.get(key));
                            deconstructedCombinedOptions.add(combineArguments.get(key));
                        }

                            deconstructedCombinedOptions.remove(null);

                        index = 0;
                        for (int j = combinedOptionsIndex; j < combinedOptionsIndex + deconstructedCombinedOptions.size(); j++) {
                            modifiedArguments.add(j, deconstructedCombinedOptions.get(index));

                            index++;
                        }


                        arguments = new ArrayList<>(modifiedArguments);
                    }

               }
                else if(!shortOptions.contains(options)){
                    throw new IllegalArgumentException("Cannot have invalid short command option(s).");
                }

            }
        }
    }

    /**
     * Gets a {@link StateSupplier} from the command-line arguments. {@link Arguments} currently supports
     * the following file formats:
     * <ul>
     *     <li>.csv - comma separated values (see {@link CSVStateReader}</li>
     *     <li>.xlsx and .xls - Excel, Google Sheets, and OpenOffice Calc (see {@link SpreadsheetStateReader}</li>
     * </ul>
     *
     * @return a StateSupplier associated with that file.
     * @throws UnsupportedFileFormatException if an invalid filename argument is provided
     */
    public StateSupplier getStateSupplier() {
        return new StateSupplierFactory().getStateSupplier(arguments.get(0));
    }


    public void bothOptionsArePresent(String shortOption, String longOption) {
        if (arguments.contains(shortOption) && arguments.contains(longOption)) {
            throw new IllegalArgumentException("Cannot have both " + shortOption + " and " + longOption + ".");
        }
    }

    public void optionOccurrences(String option) {
        if (arguments.stream().filter(arguments -> arguments.equals(option)).count() > 1) {
            throw new IllegalArgumentException("Cannot have multiple " + option + ".");
        }
    }

    public void isTheOptionTheLastArgument(String option) {
        if (arguments.indexOf(option) == arguments.size() - 1) {
            throw new IllegalArgumentException("No value was given for the command " + option + ".");
        }
    }

    public String setToPresentedOption(String shortOption, String longOption) {
        if (arguments.contains(shortOption)) {
            return shortOption;
        }
            return longOption;
    }
    /**
     * Determine the number of representatives to allocate form the command-line arguments.
     *
     * @return the number of representatives to allocate, based on the command-line arguments. If
     * <code>[representatives]</code> is not specified, then {@link Arguments#DEFAULT_REPRESENTATIVE_COUNT return the
     * default number of representatives}.
     *
     * @see Main#main(String[])
     */

    public int getRepresentatives() {

        String shortOption = "-r";
        String longOption = "--representatives";

       bothOptionsArePresent(shortOption, longOption);
       optionOccurrences(longOption);
       optionOccurrences(shortOption);

       try {
           if (arguments.contains(shortOption) || arguments.contains(longOption)) {
               String option = setToPresentedOption(shortOption, longOption);

               isTheOptionTheLastArgument(option);

               var targetRepresentatives = Integer.parseInt(arguments.get(arguments.indexOf(option) + 1));

               if (targetRepresentatives <= 0) {
                   throw new IllegalArgumentException("Number of representatives argument must be a positive integer.");
               }
               return targetRepresentatives;
           }

       } catch (NumberFormatException e) {
           throw new NumberFormatException("The given value for representatives is not a number.");
       } catch (NullPointerException e) {
           throw new NullPointerException("No value is given for the representatives.");
       }

        return DEFAULT_REPRESENTATIVE_COUNT;
    }

    /**
     * Determine the {@link ApportionmentMethod apportionment method} to use from the command-line arguments.
     * Specifically, if the arguments contain <code>--adams</code>, then the {@link AdamsMethod Adams apportionment
     * algorithm} is used. Otherwise, the {@link JeffersonMethod Jefferson apportionment method is used.}
     *
     * @return the {@link ApportionmentMethod apportionment method} to use when allocating representatives.
     * @see Main#main(String[])
     */
    public ApportionmentMethod getApportionmentMethod() {
        ApportionmentMethodFactory factory = new ApportionmentMethodFactory();
        String shortOption = "-m";
        String longOption = "--method";

        bothOptionsArePresent(shortOption, longOption);
        optionOccurrences(longOption);
        optionOccurrences(shortOption);

        if (arguments.contains(shortOption) || arguments.contains(longOption)) {
            String option = setToPresentedOption(shortOption, longOption);

            isTheOptionTheLastArgument(option);

            var targetMethod = arguments.get(arguments.indexOf(option) + 1);

            return factory.getMethod(targetMethod);
        }

        return factory.getDefaultMethod();
    }

    /**
     * Determine the {@link RepresentationFormat representation format} to use from the command-line arguments.
     * Specifically, if the arguments contains the following:
     * <ul>
     *     <li><code>--population</code> : display sorted by population in ascending order </li>
     *     <li><code>--population AND --descending</code> : display sorted by population in ascending order</li>
     *     <li>Otherwise, display sorted by state name in alphabetical order. (The --descending tag does not affect
     *     alphabetical order)</li>
     * </ul>
     *
     * @return a {@link RepresentationFormat} used in to print the Apportionment based on the command-line arguments
     * used.
     * @see Main#main(String[])
     */

    public ArrayList<Object> displayOrderGiven() {
        ArrayList<Object> result = new ArrayList<>();

        String shortOptionA = "-a";
        String longOptionA = "--ascending";
        String shortOptionD = "-d";
        String longOptionD = "--descending";

        bothOptionsArePresent(shortOptionA, longOptionA);
        bothOptionsArePresent(shortOptionD, longOptionD);
        bothOptionsArePresent(shortOptionD, shortOptionA);
        bothOptionsArePresent(longOptionA, longOptionD);
        bothOptionsArePresent(shortOptionD, longOptionA);
        bothOptionsArePresent(shortOptionA, longOptionD);
        optionOccurrences(longOptionA);
        optionOccurrences(longOptionD);
        optionOccurrences(shortOptionA);
        optionOccurrences(shortOptionD);

        if (arguments.contains(shortOptionA) || arguments.contains(longOptionA)) {
            result.add(true);
            result.add(DisplayOrder.ASCENDING);

            return result;
        }
        if (arguments.contains(shortOptionD) || arguments.contains(longOptionD)) {
                result.add(true);
                result.add(DisplayOrder.DESCENDING);

                return result;
            }

        result.add(false);
        return result;
    }
    public RepresentationFormat getRepresentationFormat() {
        RepresentationFormatFactory factory = new RepresentationFormatFactory();

        String shortOption = "-f";
        String longOption = "--format";

        bothOptionsArePresent(shortOption, longOption);
        optionOccurrences(longOption);
        optionOccurrences(shortOption);

        var displayArray = displayOrderGiven();

        if (arguments.contains(shortOption) || arguments.contains(longOption)) {
            String option = setToPresentedOption(shortOption, longOption);

            isTheOptionTheLastArgument(option);

            var targetFormat = arguments.get(arguments.indexOf(option) + 1);

            if (targetFormat.equals("alphabet")) {
                return factory.getFormat(targetFormat);
            }

            if ((boolean) displayArray.get(0)) {
                return factory.getFormat(targetFormat, (DisplayOrder) displayArray.get(1));
            }

            return factory.getFormat(targetFormat);
        }

        return factory.getDefaultFormat();
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