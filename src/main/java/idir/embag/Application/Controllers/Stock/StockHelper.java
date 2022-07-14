package idir.embag.Application.Controllers.Stock;

import idir.embag.DataModels.Metadata.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import javafx.scene.Node;

public class StockHelper implements IStockHelper{

    @Override
    public void update(IProduct product) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(int id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void add() {

        Node dialogContent =  buildAddDialog();

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);

        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().dispatch(action);
        
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void search() {
        // TODO Auto-generated method stub
        
    }

    private Node buildAddDialog(){
        ManagerDialog<EProductAttributes> dialog = new ManagerDialog<>();

        EProductAttributes attributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog.getView();

    }
    
}
