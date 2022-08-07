package idir.embag.Types.Infrastructure.DataConverters;

import java.util.HashMap;
import java.util.Map;

import idir.embag.Types.Generics.EExportSessionKeys;
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

    public ExportWrapper(Map<EExportSessionKeys,Object> data){
        currentRow = (int)data.get(EExportSessionKeys.ExportSessionCurrentRow);
        startRow = (int)data.get(EExportSessionKeys.ExportSessionStartRow);
        endRow = (int)data.get(EExportSessionKeys.ExportSessionEndRow);
        startColumn = (int)data.get(EExportSessionKeys.ExportSessionStartColumn);
        endColumn = (int)data.get(EExportSessionKeys.ExportSessionEndColumn);
        outputFile = (String)data.get(EExportSessionKeys.ExportSessionOutputFile);
        targetTable = (EStoreEvents)data.get(EExportSessionKeys.ExportSessionTargetTable);

        int offset = (int)data.get(EExportSessionKeys.ExportSessionOffset);
        int step = (int)data.get(EExportSessionKeys.ExportSessionStep);

        loadWrapper = new LoadWrapper(step,offset);
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

    public Map<EExportSessionKeys,Object> getMap() {
        Map<EExportSessionKeys, Object> data = new HashMap<>();
        data.put(EExportSessionKeys.ExportSessionCurrentRow, currentRow);
        data.put(EExportSessionKeys.ExportSessionStartRow, startRow);
        data.put(EExportSessionKeys.ExportSessionEndRow, endRow);
        data.put(EExportSessionKeys.ExportSessionStartColumn, startColumn);
        data.put(EExportSessionKeys.ExportSessionEndColumn, endColumn);
        data.put(EExportSessionKeys.ExportSessionOutputFile, outputFile);
        data.put(EExportSessionKeys.ExportSessionTargetTable, targetTable);
        data.put(EExportSessionKeys.ExportSessionStep, loadWrapper.getLimit());
        data.put(EExportSessionKeys.ExportSessionOffset, loadWrapper.getOffset());

        return data;
    }
    
}
