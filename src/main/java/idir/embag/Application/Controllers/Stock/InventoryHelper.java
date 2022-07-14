package idir.embag.Application.Controllers.Stock;

import java.util.Comparator;

import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class InventoryHelper implements IStockHelper{
    
    private MFXTableView<IProduct> tableStock;

    

    public InventoryHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
    }

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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void search() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add:
                break;
            case Remove:
                break;  
            case Update:
                break;
            case Search:
                break;          
              default:
                   break;
           }
        
    }

    private void setColumns(){
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(IProduct::getArticleName));
        MFXTableColumn<IProduct> codebarColumn = new MFXTableColumn<>(Names.Codebar, true, Comparator.comparing(IProduct::getArticleCode));

        MFXTableColumn<IProduct> familyColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getFamilyCode));
        MFXTableColumn<IProduct> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(IProduct::getStockPrice));

		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleName));

        codebarColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleCode));
		priceColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getStockPrice));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getFamilyCode));

        tableStock.getTableColumns().setAll(idColumn,codebarColumn,nameColumn,familyColumn,priceColumn);
    }

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
    }
    
}
