package idir.embag.Types.Infrastructure.DataConverters;

import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class ImportWrapper {
    private int currentRow, startRow , endRow , startColumn , endColumn ,step;
    private String inputFile;
    private EStoreEvents targetTable;

    
    public ImportWrapper(int loadedElementPerStep,EStoreEvents targetTable) {
        step = loadedElementPerStep;
        this.targetTable = targetTable;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setSheetBounds(int startRow, int endRow, int startColumn, int endColumn) {
        currentRow = startRow;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void nextRowPatch() {
        currentRow += step;
    }


    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public String getInputFile() {
        return inputFile;
    }

    public EStoreEvents getTargetTable() {
        return targetTable;
    }

}
