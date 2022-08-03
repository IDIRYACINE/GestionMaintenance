package idir.embag.Types.Infrastructure.DataConverters.Excel;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public interface IExcelCellReader {

    public Collection<AttributeWrapper[]> readData(ImportWrapper importWrapper);

    public void setup(Workbook workbook);
}
