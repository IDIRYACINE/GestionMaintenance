package idir.embag.Types.Stores.DataConverterStore;

import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IDataConverterStore extends IStore {
    public static final int EXCEL_DELEGATE = 0;
    public static final int REPORT_DELEGATE = 1;
    public static final int DELEGATES_COUNT = 2;

    public void exportData(StoreEvent event);
    public void importData(StoreEvent event);

}
    

