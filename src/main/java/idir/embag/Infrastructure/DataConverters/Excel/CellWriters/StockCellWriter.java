package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Products.StockProduct;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class StockCellWriter implements IExcelCellWriter<StockProduct> {

    @Override
    public void writeData(Collection<StockProduct> data) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setup(Workbook workbook) {
        // TODO Auto-generated method stub
        
    }

    
    
}
