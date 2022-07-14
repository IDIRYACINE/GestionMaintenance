package idir.embag.Application.Controllers.Stock;

import idir.embag.DataModels.Products.IProduct;

public interface IStockHelper {

    void update(IProduct product);

    void remove(int id);

    void add();

    void refresh();

    void search();
    
}
