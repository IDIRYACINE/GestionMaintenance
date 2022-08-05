package idir.embag.Types.Stores.DataConverterStore;

import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IDataConverterStore extends IStore {
    public static final int EXCEL_DELEGATE = 0;
    public static final int DELEGATES_COUNT = 1;

    public void exportData(StoreEvent event);
    public void importData(StoreEvent event);

}
    

