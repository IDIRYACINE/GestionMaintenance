package idir.embag.Application.Models.Stock;

import java.util.List;

import idir.embag.Application.Models.Generics.ISearchable;
import idir.embag.DataModels.Products.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;

public interface IStockModel extends ISearchable<EProductAttributes,IProduct>{
    

    public void addProduct(IProduct product);

    public void removeProduct(IProduct product);

    public void updateProduct(IProduct oldProduct , String[] fields , String[] values);
    

}
