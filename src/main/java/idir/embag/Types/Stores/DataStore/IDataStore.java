package idir.embag.Types.Stores.DataStore;

import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IDataStore extends IStore{
    public static final int STOCK_DELEGATE = 0;
    public static final int INVENTORY_DELEGATE = 1;
    public static final int FAMILY_DELEGATE = 2;
    public static final int HISTORY_DELEGATE = 3;
    public static final int WORKER_DELEGATE = 4;
    public static final int SESSION_DELEGATE = 5;
    public static final int SESSION_WORKER_DELEGATE = 6;
    public static final int SESSION_GROUP_DELEGATE = 7;
    public static final int DESIGNATION_DELEGATE = 8;
    public static final int PERMISSION_DELEGATE = 9;
    public static final int USER_DELEGATE = 10;
    public static final int GROUP_PERMISSION_DELEGATE = 11;


    public static final int DELEGATES_COUNT = 12;

    public void add(StoreEvent event);
    public void remove(StoreEvent event);
    public void update(StoreEvent event);
    public void search(StoreEvent event);
    public void load(StoreEvent event);
    public void importCollection(StoreEvent event);
}
