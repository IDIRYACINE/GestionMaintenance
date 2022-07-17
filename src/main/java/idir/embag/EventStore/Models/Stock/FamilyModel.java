package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Stores.DataStore.IDataDelegate;

@SuppressWarnings("unchecked")
public class FamilyModel implements IDataDelegate{

    private IProductQuery productQuery;

    public FamilyModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterFamilyCode((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(Map<EEventDataKeys,Object> data) {

       try {
        productQuery.UnregisterFamilyCode((int) data.get(EEventDataKeys.ArticleId));
        ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
    }

    @Override
    public void update(Map<EEventDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);

        try {
            productQuery.UpdateFamilyCode((int)data.get(EEventDataKeys.ArticleId), wrappers);
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        List<Object> result = null;
        /*  TODO: implement this
        try {
            result = productQuery.SearchFamilyCode((SearchWrapper) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        
        return result;
    }
    
}
