package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class SessionRecordsWriter implements IExcelCellWriter<SessionRecord> {

    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Record Id","Record Date", "Worker Name","Article Id",
    "Article Name" , "Invetory Quantity", "Stock Quantity", "Price Shift" , "Quantity Shift"};

    int currentRowIndex;

    CellStyle cellStyle;

    @Override
    public void writeData(Collection<SessionRecord> data) {
        
        Iterator<SessionRecord> it = data.iterator();

        while (it.hasNext()){

            SessionRecord record = it.next();
            Row row = sheet.createRow(currentRowIndex);
            createAndSetCellValue(0, row, record.getRecordId());
            createAndSetCellValue(1, row, record.getDate());
            createAndSetCellValue(2, row, record.getworkerName());
            createAndSetCellValue(3, row, record.getArticleId());
            createAndSetCellValue(4, row, record.getArticleName());
            createAndSetCellValue(5, row, record.getQuantityInventory());
            createAndSetCellValue(6, row, record.getQuantityStock());
            createAndSetCellValue(7, row, record.getPriceShift());
            createAndSetCellValue(8, row, record.getQuantityShift());

            currentRowIndex++;

        }
      

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

    private Cell createAndSetCellValue(int columnIndex , Row row , String value){
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        return cell;
    }

    private Cell createAndSetCellValue(int columnIndex , Row row , int value){
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        return cell;
    }

}
