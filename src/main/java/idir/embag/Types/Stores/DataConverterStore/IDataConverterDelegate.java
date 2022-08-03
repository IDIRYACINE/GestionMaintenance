package idir.embag.Types.Stores.DataConverterStore;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;

public interface IDataConverterDelegate {
    public void exportData(Map<EEventDataKeys,Object> data);
    public void importData(Map<EEventDataKeys,Object> data);
    public void setup(Map<EEventDataKeys, Object> data);

}
