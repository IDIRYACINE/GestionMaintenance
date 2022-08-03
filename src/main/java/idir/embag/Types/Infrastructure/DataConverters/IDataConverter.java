package idir.embag.Types.Infrastructure.DataConverters;

import java.util.Collection;

import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public interface IDataConverter {
    public <T> void exportData(IExcelCellWriter<T> cellWriter , Collection<T> data);
    public void importData();
    public void setupExport(ExportWrapper exportWrapper);
    public void setupImport(ImportWrapper importWrapper);
}
