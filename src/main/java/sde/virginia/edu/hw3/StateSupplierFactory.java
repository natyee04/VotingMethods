package sde.virginia.edu.hw3;

public class StateSupplierFactory {
    public StateSupplier getStateSupplier(String filename) {
        if (filename.toLowerCase().endsWith("csv")) {
            return new CSVStateReader(filename);
        }
        if (filename.toLowerCase().endsWith("xlsx") || filename.toLowerCase().endsWith("xls")) {
            return new SpreadsheetStateReader(filename);
        }
        throw new UnsupportedFileFormatException(filename);
    }
}
