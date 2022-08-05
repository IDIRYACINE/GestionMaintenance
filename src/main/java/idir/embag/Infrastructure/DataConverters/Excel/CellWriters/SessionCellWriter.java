package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import idir.embag.DataModels.Session.Session;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class SessionCellWriter implements IExcelCellWriter<Session> {

    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Session Id","Date Start", "Date End","Price Shift","Quantity Shift"};

    int currentRowIndex;

    CellStyle cellStyle;

    @Override
    public void writeData(Collection<Session> data) {
        
        Iterator<Session> it = data.iterator();

        while (it.hasNext()){

            Session session = it.next();
            Row row = sheet.createRow(currentRowIndex);

            createAndSetCellValue(0, row, session.getSessionId());
            createAndSetCellValue(1, row, session.getSessionStartDate());
            createAndSetCellValue(2, row, session.getSessionEndDate());
            createAndSetCellValue(3, row, session.getQuantityShift());
            createAndSetCellValue(4, row, session.getPriceShift());

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

    private Cell createAndSetCellValue(int columnIndex , Row row , double value){
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        return cell;
    }

}
