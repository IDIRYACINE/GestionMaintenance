package idir.embag.Infrastructure.DataConverters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel implements IDataConverter {

    private Workbook workbook ;

    private Sheet sheet ;

    private String title;

    private String[] columns;

    private Object[] data;

    private void setUpWorkSheet(String title){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(title);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
    }

    private void setUpHeaderCells(Row headerRow , CellStyle headerStyle){
        Cell headCell ;

        for (int i = 0 ; i < columns.length ; i++){
            headCell = headerRow.createCell(i);
            headCell.setCellValue(columns[i]);
            headCell.setCellStyle(headerStyle);
        }
    }

    private CellStyle setUpHeaderStyle(){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        return headerStyle ;
    }

    @Override
    public <T> void setData(T[] data){
        this.data = data ;
    }

    private void populateRowsWithData(){
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        Object[] rawData  = new Object[data.length];

        for(int i = 0 ; i < data.length ; i++){
            Row row = sheet.createRow(i+1);

            for (int j = 0 ; j < rawData.length ; j++){
                Cell cell = row.createCell(j);
                cell.setCellValue((double) rawData[j]);
                cell.setCellStyle(style);
                
            }
        }
    }

    @Override
    public void exportData(String outputPath) {
        
        populateRowsWithData();

        File file = new File(outputPath);
                    
        try (FileOutputStream content = new FileOutputStream(file)) {
            workbook.write(content);
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
               
        
    }


    @Override
    public void importData(String sourcePath) {
        try
        {
            FileInputStream file = new FileInputStream(new File(sourcePath));
 
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                 
                while (cellIterator.hasNext()) 
                {
                    Cell cell = cellIterator.next();
                    //TODO: do something with the cell
                }
                //TODO: do something with the row
            }
            
            workbook.close();
            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }


    @Override
    public void setTitle(String title) {
       this.title = title ;
    }


    @Override
    public <T> void setColumns(T[] columns) {
        String[] columnsNames = new String[columns.length];

        for (int i = 0 ; i < columns.length ; i++){
            columnsNames[i] = columns[i].toString();
        }

        this.columns = columnsNames;
    }


    @Override
    public void setupNewPage() {
        setUpWorkSheet(title);
        Row  header = sheet.createRow(0);
        CellStyle headerStyle = setUpHeaderStyle();
        setUpHeaderCells(header, headerStyle);
    }


}
