package idir.embag.Infrastructure.DataConverters.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.IDataConverter;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class Excel implements IDataConverter {

    private Workbook workbook ;

    private Sheet sheet ;


    private IExcelCellWriter cellWriter;
    private IExcelCellReader cellReader;

    private ExportWrapper exportWrapper;
    private ImportWrapper importWrapper;

    @Override
    public void exportData(Map<EEventDataKeys,Object> data) {
        

        File file = new File(exportWrapper.getOutputFile());
                    
        try (FileOutputStream content = new FileOutputStream(file)) {
            workbook.write(content);

            cellWriter.writeData(data);

            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
               
        
    }


    @Override
    public void importData() {
        try
        {
            FileInputStream file = new FileInputStream(new File(importWrapper.getInputFile()));
 
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            
            
            workbook.close();
            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }

    @Override
    public void setupExport(ExportWrapper exportWrapper, IExcelCellWriter cellWriter) {
        this.exportWrapper = exportWrapper;
        this.cellWriter = cellWriter;

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(exportWrapper.getTargetTable().toString());
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        cellWriter.setup(workbook);
        
    }


    @Override
    public void setupImport(ImportWrapper importWrapper, IExcelCellReader cellReader) {
        this.importWrapper = importWrapper;
        this.cellReader = cellReader;
        
       
    }

    

}
