package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
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
    public void add(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
        try {
            productQuery.RegisterFamilyCode(result.values());
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
       try {
        productQuery.UnregisterFamilyCode((int) result.get(EEventDataKeys.Id).getValue());
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
    }

    @Override
    public void update(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;

        try {
            productQuery.UpdateFamilyCode((int) result.get(EEventDataKeys.Id).getValue(), result.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Object data) {
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
