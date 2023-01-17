package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.DataConverters.ReportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class ReportExcelCellWriter implements IExcelCellWriter<ReportWrapper> {
    Workbook workbook;

    Sheet sheet;

    String[] columns = { "Article Id", "Family Code", "Article Name", "Article Codebar", "Affectaion", "Quantity",
            "Article Price" };

    int currentRowIndex;

    static int articleIdColumnIndex = 1;
    static int familyCodeColumnIndex = 2;
    static int articleNameColumnIndex = 3;
    static int articleCodebarColumnIndex = 4;
    static int affectationColumnIndex = 5;
    static int quantityColumnIndex = 6;
    static int articlePriceColumnIndex = 7;

    int[] columnIndexes = { articleIdColumnIndex, familyCodeColumnIndex, articleNameColumnIndex,
            articleCodebarColumnIndex, affectationColumnIndex, quantityColumnIndex, articlePriceColumnIndex };

    CellStyle normalCellStyle;
    CellStyle totalCellStyle;

    @Override
    public void writeData(Collection<ReportWrapper> data) {
        ReportWrapper reportWrapper = data.iterator().next();

        InventoryProduct[] positiveItems = (InventoryProduct[]) reportWrapper.getScannedInventoryItems().toArray();
        InventoryProduct[] negativeItems = (InventoryProduct[]) reportWrapper.getNegativeInventoryItems().toArray();

        int positiveItemsIndex = 0;
        int negativeItemsIndex = 0;

        int currentFamilyCode = positiveItems[0].getFamilyCode();

        boolean workCompleted = false;

        InventoryProduct currentPositiveItem = positiveItems[0];
        InventoryProduct currentNegativeItem = negativeItems[0];

        while (!workCompleted) {
            currentFamilyCode = getNextFamilyCode(positiveItems, positiveItemsIndex, negativeItems, negativeItemsIndex);

            int totalQuantity = 0;
            int totalPrice = 0;

            boolean isSamePositiveFamily = false;
            while (isSamePositiveFamily && (positiveItemsIndex < positiveItems.length)) {
                totalPrice += currentPositiveItem.getPrice();
                totalQuantity += currentPositiveItem.getQuantity();

                writeRow(currentPositiveItem, true);

                positiveItemsIndex++;
                if (positiveItemsIndex < positiveItems.length) {
                    currentPositiveItem = positiveItems[positiveItemsIndex];
                    isSamePositiveFamily = stillOnSameFamily(currentFamilyCode, currentPositiveItem);
                }

            }

            boolean isSameNegativeFamily = false;
            while (isSameNegativeFamily && (negativeItemsIndex < negativeItems.length)) {
                totalPrice -= currentNegativeItem.getPrice();
                totalQuantity -= currentNegativeItem.getQuantity();

                writeRow(currentNegativeItem, false);

                negativeItemsIndex++;
                if (negativeItemsIndex < negativeItems.length) {
                    currentNegativeItem = negativeItems[negativeItemsIndex];
                    isSameNegativeFamily = stillOnSameFamily(currentFamilyCode, currentNegativeItem);
                }
            }

            writeTotalRow(totalQuantity, totalPrice);
            writeRowSeperator();

            workCompleted = isWorkCompleted(positiveItemsIndex, negativeItemsIndex, positiveItems.length,
                    negativeItems.length);
        }

    }

    @Override
    public void setup(Workbook workbook) {

        this.workbook = workbook;
        sheet = workbook.getSheetAt(0);
        currentRowIndex = 1;
        normalCellStyle = workbook.createCellStyle();
        normalCellStyle.setWrapText(true);

        totalCellStyle = workbook.createCellStyle();
        totalCellStyle.setWrapText(true);
        totalCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        setupColumns();
    }

    private void setupColumns() {
        Row row = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    private boolean isWorkCompleted(int positiveItemsIndex, int negativeItemsIndex, int positiveItemsLength,
            int negativeItemsLength) {
        return positiveItemsIndex == positiveItemsLength && negativeItemsIndex == negativeItemsLength;
    }

    private int getNextFamilyCode(InventoryProduct[] positiveItems, int positiveItemsIndex,
            InventoryProduct[] negativeItems, int negativeItemsIndex) {
        if (positiveItemsIndex < positiveItems.length) {
            return positiveItems[positiveItemsIndex].getFamilyCode();
        } else {
            return negativeItems[negativeItemsIndex].getFamilyCode();
        }
    }

    private void writeRow(InventoryProduct product, boolean isPositive) {
        Row row = sheet.createRow(currentRowIndex);
        row.setRowStyle(normalCellStyle);

        createAndSetCellValue(articleIdColumnIndex, row, product.getArticleId());
        createAndSetCellValue(familyCodeColumnIndex, row, product.getFamilyCode());
        createAndSetCellValue(articleNameColumnIndex, row, product.getArticleName());
        createAndSetCellValue(articleCodebarColumnIndex, row, product.getArticleCode());
        createAndSetCellValue(affectationColumnIndex, row, product.getDesignationId());

        if (isPositive) {
            createAndSetCellValue(quantityColumnIndex, row, product.getQuantity());
            createAndSetCellValue(articlePriceColumnIndex, row, product.getPrice());
        } else {
            createAndSetCellValue(quantityColumnIndex, row, -product.getQuantity());
            createAndSetCellValue(articlePriceColumnIndex, row, -product.getPrice());
        }

        currentRowIndex++;
    }

    private void writeTotalRow(int totalQuantity, double totalPrice) {
        Row row = sheet.createRow(currentRowIndex);
        row.setRowStyle(totalCellStyle);

        createAndSetCellValue(quantityColumnIndex, row, totalQuantity);
        createAndSetCellValue(articlePriceColumnIndex, row, totalPrice);

        currentRowIndex++;
    }

    private void writeRowSeperator() {
        currentRowIndex++;
    }

    private boolean stillOnSameFamily(int currentFamilyCode, InventoryProduct product) {
        return currentFamilyCode == product.getFamilyCode();
    }

    private Cell createAndSetCellValue(int columnIndex, Row row, String value) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        return cell;
    }

    private Cell createAndSetCellValue(int columnIndex, Row row, int value) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        return cell;
    }

    private Cell createAndSetCellValue(int columnIndex, Row row, double value) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        return cell;
    }

}
