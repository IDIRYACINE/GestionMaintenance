package idir.embag.EventStore.Models.Stock;

import idir.embag.DataModels.Products.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Models.Generics.ISearchable;

public interface IStockModel extends ISearchable<EProductAttributes,IProduct>{
    

    public void addProduct(IProduct product);

    public void removeProduct(IProduct product);

    public void updateProduct(IProduct oldProduct , String[] fields , String[] values);
    

}
