package idir.embag.Infrastructure.DataConverters.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.IDataConverter;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public class Excel implements IDataConverter {

    private Workbook workbook ;

    private Sheet sheet ;

    private ExportWrapper exportWrapper;
    private ImportWrapper importWrapper;

    @Override
    public <T> void exportData(IExcelCellWriter<T> cellWriter , Collection<T> data) {

        File file = new File(exportWrapper.getOutputFile());
                    
        try (FileOutputStream content = new FileOutputStream(file)) {

            cellWriter.setup(workbook);
            cellWriter.writeData(data);
            
            workbook.write(content);

            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
               
        
    }


    @Override
    public Collection<AttributeWrapper[]> importData(IExcelCellReader cellReader) {
        Collection<AttributeWrapper[]> data = null;

        try
        {
            FileInputStream file = new FileInputStream(new File(importWrapper.getInputFile()));
            
            Workbook workbook = WorkbookFactory.create(file);
                        
            cellReader.setup(workbook);
            data = cellReader.readData(importWrapper);

            workbook.close();
            file.close();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public void setupExport(ExportWrapper exportWrapper) {
        this.exportWrapper = exportWrapper;

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(exportWrapper.getTargetTable().toString());
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        
    }


    @Override
    public void setupImport(ImportWrapper importWrapper) {
        this.importWrapper = importWrapper;
    }

    

}
