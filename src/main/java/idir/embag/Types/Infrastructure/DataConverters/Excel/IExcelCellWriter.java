package idir.embag.Types.Infrastructure.DataConverters.Excel;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

public interface IExcelCellWriter<T> {
    public void writeData(Collection<T> data);
    public void setup(Workbook workbook);
}
