package idir.embag.Types.Infrastructure.DataConverters;

import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class ExportWrapper {
    private LoadWrapper loadWrapper;
    private EStoreEvents targetTable;
    private int currentRow, startRow , endRow , startColumn , endColumn;
    private String outputFile;
    private String[] headers;

    public ExportWrapper(LoadWrapper loadWrapper,EStoreEvents targetTable) {
        this.loadWrapper = loadWrapper;
        this.targetTable = targetTable;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
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
        currentRow += loadWrapper.getLimit();
        loadWrapper.setOffset(currentRow);
    }

    public LoadWrapper getLoadWrapper() {
        return loadWrapper;
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

    public String getOutputFile() {
        return outputFile;
    }

    public String[] getHeaders() {
        return headers;
    }

    public EStoreEvents getTargetTable() {
        return targetTable;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }
    
}
