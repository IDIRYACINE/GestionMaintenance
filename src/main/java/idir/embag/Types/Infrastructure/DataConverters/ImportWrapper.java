package idir.embag.Types.Infrastructure.DataConverters;

import java.util.HashMap;
import java.util.Map;
import idir.embag.Types.Generics.EExportSessionKeys;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class ImportWrapper {
    private int currentRow, startRow , endRow , startColumn , endColumn ,step;
    private String inputFile;
    private EStoreEvents targetTable;

    
    public ImportWrapper(int loadedElementPerStep,EStoreEvents targetTable) {
        step = loadedElementPerStep;
        this.targetTable = targetTable;
    }

    public ImportWrapper(Map<EExportSessionKeys,Object> data){
        currentRow = (int)data.get(EExportSessionKeys.ImportSessionCurrentRow);
        startRow = (int)data.get(EExportSessionKeys.ImportSessionStartRow);
        endRow = (int)data.get(EExportSessionKeys.ImportSessionEndRow);
        startColumn = (int)data.get(EExportSessionKeys.ImportSessionStartColumn);
        endColumn = (int)data.get(EExportSessionKeys.ImportSessionEndColumn);
        inputFile = (String)data.get(EExportSessionKeys.ImportSessionInputFile);
        targetTable = (EStoreEvents)data.get(EExportSessionKeys.ImportSessionTargetTable);
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

    public Map<EExportSessionKeys,Object> getMap() {
        Map<EExportSessionKeys, Object> data = new HashMap<>();
        data.put(EExportSessionKeys.ImportSessionCurrentRow, currentRow);
        data.put(EExportSessionKeys.ImportSessionStartRow, startRow);
        data.put(EExportSessionKeys.ImportSessionEndRow, endRow);
        data.put(EExportSessionKeys.ImportSessionStartColumn, startColumn);
        data.put(EExportSessionKeys.ImportSessionEndColumn, endColumn);
        data.put(EExportSessionKeys.ImportSessionInputFile, inputFile);
        data.put(EExportSessionKeys.ImportSessionTargetTable, targetTable);
        data.put(EExportSessionKeys.ImportSessionStep, step);

        return data;
    }

}
