package idir.embag.Types.Stores.DataConverterStore;

import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IDataConverterStore extends IStore {
    public static final int STOCK_DELEGATE = 0;
    public static final int INVENTORY_DELEGATE = 1;
    public static final int FAMILY_DELEGATE = 2;
    public static final int HISTORY_DELEGATE = 3;
    public static final int WORKER_DELEGATE = 4;
    public static final int SESSION_DELEGATE = 5;

    public static final int DELEGATES_COUNT = 7;

    public void exportData(StoreEvent event);
    public void importData(StoreEvent event);

}
    

