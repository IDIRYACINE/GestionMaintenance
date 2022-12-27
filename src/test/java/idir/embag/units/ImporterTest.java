package idir.embag.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;

import idir.embag.Infrastructure.DataConverters.Excel.CellReaders.InventoryCellReaderV2;
import idir.embag.Types.Generics.EExportSessionKeys;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class ImporterTest {


    @Test
    public void importTest() throws Exception {
        Collection<AttributeWrapper[]> result = new ArrayList<AttributeWrapper[]>();

        try
        {   
            File sFile = new File("/home/idir/Desktop/TABLEAU DES INVESTISSEMENTS.xls");
            FileInputStream file = new FileInputStream(sFile);
            

            Map<EExportSessionKeys,Object> data = new HashMap<>();
            data.put(EExportSessionKeys.ImportSessionCurrentRow, 0);
            data.put(EExportSessionKeys.ImportSessionStartRow, 9);
            data.put(EExportSessionKeys.ImportSessionEndRow, 20);
            data.put(EExportSessionKeys.ImportSessionStartColumn, 1);
            data.put(EExportSessionKeys.ImportSessionEndColumn, 11);
            data.put(EExportSessionKeys.ImportSessionInputFile, sFile.getAbsolutePath());
            data.put(EExportSessionKeys.ImportSessionTargetTable, EStoreEvents.InventoryEvent);


            ImportWrapper importWrapper = new ImportWrapper(data);
            Workbook workbook = WorkbookFactory.create(file);
                        
            IExcelCellReader cellReader = new InventoryCellReaderV2();
            
            cellReader.setup(workbook);

            result = cellReader.readData(importWrapper);

            workbook.close();
            file.close();

        } 
        catch (Exception e) 
        {
           throw e;
        }

        assertEquals(9, result.size());

    }

}
