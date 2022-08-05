package idir.embag.Types.Stores.DataConverterStore;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventsDataKeys;

public interface IDataConverterDelegate {
    public static final int STOCK_EXPORTER = 0;
    public static final int INVENTORY_EXPORTER = 1;
    public static final int FAMILY_CODE_EXPORTER = 2;
    public static final int SESSION_RECORDS_EXPORTER = 3;
    public static final int SESSION_EXPORTER = 4;
    public static final int WORKERS_EXPORTER = 5;

    public static final int EXPOTERS_COUNT = 6;

    public void exportData(Map<EEventsDataKeys,Object> data);
    public void importData(Map<EEventsDataKeys,Object> data);
    public void setup(Map<EEventsDataKeys,Object> data);

   

}
