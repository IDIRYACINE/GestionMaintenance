package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Others.FamilyCode;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class FamilyCodeCellWriter implements IExcelCellWriter<FamilyCode> {

    Workbook workbook;
    Sheet sheet;

    String[] columns = {"Family Code", "Family Name"};

    @Override
    public void writeData(Collection<FamilyCode> data) {
        
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        
        Iterator<FamilyCode> it = data.iterator();
        int currentRowIndex = 0;

        while (it.hasNext()){

            FamilyCode familyCode = it.next();
            Row row = sheet.createRow(currentRowIndex);

            Cell cell = row.createCell(0);
            cell.setCellValue(familyCode.getFamilyCode());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(familyCode.getArticleName());
            cell.setCellStyle(style);

            currentRowIndex++;

        }
      

    }

    @Override
    public void setup(Workbook workbook) {
        this.workbook = workbook;
        sheet = workbook.getSheetAt(0);
    }




}
    

