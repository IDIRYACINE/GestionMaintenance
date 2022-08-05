package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class WorkerCellWriter implements IExcelCellWriter<Worker> {

    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Worker Id","Worker Name", "Worker Phone","Worker Email"};

    int currentRowIndex;

    CellStyle cellStyle;

    @Override
    public void writeData(Collection<Worker> data) {
        
        Iterator<Worker> it = data.iterator();

        while (it.hasNext()){

            Worker worker = it.next();
            Row row = sheet.createRow(currentRowIndex);

            createAndSetCellValue(0, row, worker.getId());
            createAndSetCellValue(1, row, worker.getName());
            createAndSetCellValue(2, row, worker.getPhone());
            createAndSetCellValue(3, row, worker.getEmail());

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