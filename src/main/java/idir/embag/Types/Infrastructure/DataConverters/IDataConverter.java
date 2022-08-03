package idir.embag.Types.Infrastructure.DataConverters;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;

public interface IDataConverter {
    public void exportData(Map<EEventDataKeys,Object> data);
    public void importData();
    public void setupExport(ExportWrapper exportWrapper, IExcelCellWriter cellWriter);
    public void setupImport(ImportWrapper importWrapper, IExcelCellReader cellReader);
}
