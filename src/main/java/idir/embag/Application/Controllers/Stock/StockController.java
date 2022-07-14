package idir.embag.Application.Controllers.Stock;

import idir.embag.DataModels.Metadata.EStockTypes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public class StockController {

    private IStockHelper selectedStockHelper , stockHelper,inventoryHelper,familyCodesHelper;

    
    
    public StockController(IStockHelper stockHelper, IStockHelper inventoryHelper, IStockHelper familyCodesHelper) {
        this.stockHelper = stockHelper;
        this.inventoryHelper = inventoryHelper;
        this.familyCodesHelper = familyCodesHelper;
        selectedStockHelper = this.stockHelper;
    }

    public void selectStockHelper(EStockTypes stockType){
        switch (stockType) {
            case Stock:
                selectedStockHelper = stockHelper;
                selectedStockHelper.notifySelected();
                break;
            case Inventory:
                selectedStockHelper = inventoryHelper;
                selectedStockHelper.notifySelected();
                break;
            case FamilyCodes:
                selectedStockHelper = familyCodesHelper;
                selectedStockHelper.notifySelected();
                break;
        }
        
    }

    public void add(){
        selectedStockHelper.add();
    }

    public void remove(int id){
        selectedStockHelper.remove(id);
    }

    public void search(){
        selectedStockHelper.search();
    }

    public void refresh(){
        selectedStockHelper.refresh();
    }

    public void update(IProduct product) {
        selectedStockHelper.update(product);
    }

    public void notifyEvent(StoreEvent event) {
        selectedStockHelper.notifyEvent(event);
    }



}

