package idir.embag.Infrastructure.DataConverters.Excel.CellReaders;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public class StockCellReader implements IExcelCellReader {

    @Override
    public Collection<AttributeWrapper[]> readData(ImportWrapper importWrapper) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setup(Workbook workbook) {
        // TODO Auto-generated method stub
        
    }
    
}
