package idir.embag.Types.Infrastructure.DataConverters;

import java.util.Collection;

import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public interface IDataConverter {
    public <T> void exportData(IExcelCellWriter<T> cellWriter , Collection<T> data);
    public Collection<AttributeWrapper[]> importData(IExcelCellReader cellReader);
    public void setupExport(ExportWrapper exportWrapper);
    public void setupImport(ImportWrapper importWrapper);
}
