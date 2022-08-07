package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Products.FamilyCode;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class FamilyCodeCellWriter implements IExcelCellWriter<FamilyCode> {

    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Family Code", "Family Name"};

    int currentRowIndex;

    @Override
    public void writeData(Collection<FamilyCode> data) {
        
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        
        Iterator<FamilyCode> it = data.iterator();
        while (it.hasNext()){

            FamilyCode familyCode = it.next();
            Row row = sheet.createRow(currentRowIndex);

            Cell cell = row.createCell(0);
            cell.setCellValue(familyCode.getFamilyCode());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(familyCode.getFamilyName());
            cell.setCellStyle(style);

            currentRowIndex++;

        }
      

    }

    @Override
    public void setup(Workbook workbook) {
        this.workbook = workbook;
        sheet = workbook.getSheetAt(0);
        currentRowIndex = 1;
        setupColumns();
    }


    private void setupColumns() {
        Row row = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

}
    

