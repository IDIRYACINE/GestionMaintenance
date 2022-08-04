package idir.embag.Infrastructure.DataConverters.Excel.CellReaders;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EFamilyCodeAttributes;

public class FamilyCodeCellReader implements IExcelCellReader {

    Workbook workbook;
    EFamilyCodeAttributes[] attrbs ;

    

    public FamilyCodeCellReader() {
        attrbs = EFamilyCodeAttributes.values();
    }

    @Override
    public Collection<AttributeWrapper[]> readData(ImportWrapper importWrapper) {
        Sheet sheet = workbook.getSheetAt(0);

        Collection<AttributeWrapper[]> data = new ArrayList<>();

        int rowIndex = importWrapper.getStartRow();

        while (rowIndex <= importWrapper.getEndRow()) 
        {
            Row row = sheet.getRow(rowIndex);
            data.add(readCells(row));

            rowIndex++;
            
        }
        
        return data;
        
    }

    @Override
    public void setup(Workbook workbook) {
        this.workbook = workbook;
    }
    
    private AttributeWrapper[] readCells(Row row){
        AttributeWrapper[] attributes = new AttributeWrapper[attrbs.length];

        int i = 0;

        attributes[i] = new AttributeWrapper(attrbs[i], row.getCell(i).getNumericCellValue());
        i++;
        attributes[i] = new AttributeWrapper(attrbs[i], row.getCell(i).getStringCellValue());

        return attributes;
    }
}
