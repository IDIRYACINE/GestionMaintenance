package idir.embag.Infrastructure.DataConverters.Excel.CellReaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public class FamilyCodeCellReader implements IExcelCellReader {

    Workbook workbook;
    EEventDataKeys[] attrbs = { EEventDataKeys.FamilyCode,EEventDataKeys.FamilyName };

    @Override
    public Collection<AttributeWrapper[]> readData(ImportWrapper importWrapper) {
        Sheet sheet = workbook.getSheetAt(0);

        Collection<AttributeWrapper[]> data = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            data.add(readCells(row));
            
        }
        return data;
        
    }

    @Override
    public void setup(Workbook workbook) {
        this.workbook = workbook;
    }
    
    private AttributeWrapper[] readCells(Row row){
        AttributeWrapper[] attributes = new AttributeWrapper[attrbs.length];

        for (int i = 0; i < attrbs.length; i++) {
            attributes[i] = new AttributeWrapper(attrbs[i], row.getCell(i).getStringCellValue());
        }

        return attributes;
    }
}
