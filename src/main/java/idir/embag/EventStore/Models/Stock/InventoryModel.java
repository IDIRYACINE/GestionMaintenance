package idir.embag.EventStore.Models.Stock;

import java.util.List;

import idir.embag.DataModels.Products.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;

public class InventoryModel implements IStockModel{

    @Override
    public List<IProduct> search() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSearchParameters(EProductAttributes[] searchFields, String[] searchValues) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSearchResultFields(EProductAttributes[] resultFields) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addProduct(IProduct product) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeProduct(IProduct product) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateProduct(IProduct oldProduct, String[] fields, String[] values) {
        // TODO Auto-generated method stub
        
    }

    
}
