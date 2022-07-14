package idir.embag.Application.Controllers.Stock;

import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public interface IStockHelper {

    void update(IProduct product);

    void remove(int id);

    void add();

    void refresh();

    void search();

    void notifyEvent(StoreEvent event);

    void notifySelected();
    
}
