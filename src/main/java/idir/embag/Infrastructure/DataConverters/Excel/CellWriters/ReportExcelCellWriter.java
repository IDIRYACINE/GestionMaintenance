package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import idir.embag.Types.Infrastructure.DataConverters.ReportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class ReportExcelCellWriter implements IExcelCellWriter<ReportWrapper>{
    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Article Id","Family Code", "Article Name","Article Codebar","Affectaion","Quantity","Article Price"};

    int currentRowIndex;

    CellStyle cellStyle;
    
    @Override
    public void writeData(Collection<ReportWrapper> data) {
        
    }

    @Override
    public void setup(Workbook workbook) {
        
        this.workbook = workbook;
        sheet = workbook.getSheetAt(0);
        currentRowIndex = 1;
        cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
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
