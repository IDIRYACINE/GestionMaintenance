package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class InventoryCellWriter implements IExcelCellWriter<InventoryProduct> {

    @Override
    public void writeData(Collection<InventoryProduct> data) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setup(Workbook workbook) {
        // TODO Auto-generated method stub
        
    }


    
}
