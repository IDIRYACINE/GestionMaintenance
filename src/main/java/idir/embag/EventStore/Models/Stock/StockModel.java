package idir.embag.EventStore.Models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Stores.DataStore.IDataDelegate;

@SuppressWarnings("unchecked")
public class StockModel implements IDataDelegate{

    IProductQuery productQuery;

    public StockModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterStockProduct((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(Map<EEventDataKeys,Object> data) {
        IProduct product = (IProduct)data.get(EEventDataKeys.ProductInstance);
        
        try {
            productQuery.UnregisterStockProduct(product.getArticleId());
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(Map<EEventDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);
        try {
            productQuery.UpdateStockProduct((int) data.get(EEventDataKeys.ArticleId),wrappers);
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
    }


    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        List<Object> result = null;
        
        return result;
    }

    @Override
    public void load(Map<EEventDataKeys,Object> data) {
        //TODO : change method to get a proper load wrapper
        LoadWrapper loadWrapper = new LoadWrapper(0,0);
        try{
            ResultSet result = productQuery.LoadStockProduct(loadWrapper);
            ((Consumer<ResultSet>) data.get(EEventDataKeys.OnSucessCallback)).accept(result);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}
