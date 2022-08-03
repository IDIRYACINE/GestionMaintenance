package idir.embag.Infrastructure.DataConverters.Excel.CellWriters;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.DataModels.Session.Session;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public class SessionCellWriter implements IExcelCellWriter<Session> {

    @Override
    public void writeData(Collection<Session> data) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setup(Workbook workbook) {
        // TODO Auto-generated method stub
        
    }
    
}
