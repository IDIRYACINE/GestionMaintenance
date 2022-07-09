package idir.embag.Utility.DataConverters;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class Excel {
    private Workbook workbook ;
    private Sheet sheet ;
    
    public Excel(){
        setUpWorkSheet("Liste des Check");
        Row  header = sheet.createRow(0);
        CellStyle headerStyle = setUpHeaderStyle();
        setUpHeaderCells(header, headerStyle);
    }


    private void setUpWorkSheet(String title){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(title);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
    }

    private void setUpHeaderCells(Row headerRow , CellStyle headerStyle){
        Cell headCell ;
        String[] titles = {"Date","Numero Check" , "Receveur" ,"Location" , "Montant" ,"Status" };
        for (int i = 0 ; i < titles.length ; i++){
            headCell = headerRow.createCell(i);
            headCell.setCellValue(titles[i]);
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

    public <T> void setUpCellsData(ArrayList<T> data){
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        Object[] rawData  = new Object[data.size()];

        for(int i = 0 ; i < data.size() ; i++){
            Row row = sheet.createRow(i+1);

            for (int j = 0 ; j < rawData.length ; j++){
            // {"Date","Numero Check" , "Receveur" ,"Location" , "Montant" ,"Status" }
                Cell cell = row.createCell(j);
                cell.setCellValue((double) rawData[j]);
                cell.setCellStyle(style);
                
        }
    }
    }

    public void saveSheet(Window window  ){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xslx");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);

        
        File file = fileChooser.showSaveDialog(window);
 
            if (file != null) {
                try {
                    
                    FileOutputStream content = new FileOutputStream(file);
                    workbook.write(content);
                    content.close();
                } 
                catch (IOException ex) {
                }
            }
    }


}
