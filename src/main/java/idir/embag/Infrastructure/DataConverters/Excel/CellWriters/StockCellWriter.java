package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class StockCellWriter implements IExcelCellWriter<StockProduct> {

    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Article Id","Family Code", "Article Name","Article Price","Article Quantity"};

    int currentRowIndex;

    CellStyle cellStyle;

    @Override
    public void writeData(Collection<StockProduct> data) {
        
        Iterator<StockProduct> it = data.iterator();

        while (it.hasNext()){

            StockProduct product = it.next();
            Row row = sheet.createRow(currentRowIndex);

            createAndSetCellValue(0, row, product.getArticleId());
            createAndSetCellValue(1, row, product.getFamilyCode());
            createAndSetCellValue(2, row, product.getArticleName());
            createAndSetCellValue(3, row, product.getPrice());
            createAndSetCellValue(4, row, product.getQuantity());

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
