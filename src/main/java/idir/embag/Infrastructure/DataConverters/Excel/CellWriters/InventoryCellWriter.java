package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class InventoryCellWriter implements IExcelCellWriter<InventoryProduct> {

    Workbook workbook;

    Sheet sheet;

    String[] columns = {"Article Id","Stock Id","Family Code", "Article Name","Article Codebar","Article Price"};

    int currentRowIndex;

    CellStyle cellStyle;

    @Override
    public void writeData(Collection<InventoryProduct> data) {
        
        Iterator<InventoryProduct> it = data.iterator();

        while (it.hasNext()){
            InventoryProduct product = it.next();
            Row row = sheet.createRow(currentRowIndex);

            createAndSetCellValue(0, row, product.getArticleId());
            createAndSetCellValue(1, row, product.getDesignationId());
            createAndSetCellValue(2, row, product.getFamilyCode());
            createAndSetCellValue(3, row, product.getArticleName());
            createAndSetCellValue(4, row, product.getArticleCode());
            createAndSetCellValue(5, row, product.getPrice());


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