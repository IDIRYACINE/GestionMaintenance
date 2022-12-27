package idir.embag.Infrastructure.DataConverters.Excel.CellReaders;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;

public class SessionRecordsCellReader implements IExcelCellReader {

    Workbook workbook;
    ESessionRecordAttributes[] attrbs;

    public SessionRecordsCellReader() {
        attrbs = ESessionRecordAttributes.values();
    }

    @Override
    public Collection<AttributeWrapper[]> readData(ImportWrapper importWrapper) {
        Sheet sheet = workbook.getSheetAt(0);

        Collection<AttributeWrapper[]> data = new ArrayList<>();

        int rowIndex = importWrapper.getStartRow();

        while (rowIndex <= importWrapper.getEndRow()) {
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

    private AttributeWrapper[] readCells(Row row) {
        AttributeWrapper[] attributes = new AttributeWrapper[attrbs.length];

        int[] numericCells = { 0, 1, 2, 3, 4, 6, 7, 8, 9 };
        int[] stringCells = { 5 };

        for (int i = 0; i < numericCells.length; i++) {
            attributes[i] = new AttributeWrapper(attrbs[i], row.getCell(numericCells[i]).getNumericCellValue());
        }

        for (int i = 0; i < stringCells.length; i++) {
            attributes[i] = new AttributeWrapper(attrbs[i], row.getCell(stringCells[i]).getStringCellValue());
        }

        return attributes;
    }

}
