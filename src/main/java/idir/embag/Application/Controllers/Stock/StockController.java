package idir.embag.Application.Controllers.Stock;

import idir.embag.DataModels.Metadata.EStockTypes;
import idir.embag.Types.Application.Stock.IStockHelper;
import javafx.scene.Node;

public class StockController {

    private IStockHelper selectedStockHelper , stockHelper,inventoryHelper,familyCodesHelper;
    private IStockHelper affectationHelper;

    public StockController(IStockHelper stockHelper, IStockHelper inventoryHelper, IStockHelper familyCodesHelper,IStockHelper designationHelper) {
        this.stockHelper = stockHelper;
        this.inventoryHelper = inventoryHelper;
        this.familyCodesHelper = familyCodesHelper;
        this.affectationHelper = designationHelper;
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
            case Affectation:
                selectedStockHelper = affectationHelper;
                selectedStockHelper.notifySelected();
                break;    
        }
        
    }

    public void add(){
        selectedStockHelper.add();
    }

    public void remove(){
        selectedStockHelper.remove();
    }

    public void search(){
        selectedStockHelper.search();
    }

    public void refresh(){
        selectedStockHelper.refresh();
    }

    public void update(){
        selectedStockHelper.update();
    }

    public Node getTableView() {
        return selectedStockHelper.getView();
    }

}

