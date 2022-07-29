package idir.embag.Types.Repository;

import java.sql.ResultSet;
import java.util.Collection;

import idir.embag.DataModels.Products.IProduct;

public interface ProductRepository {
    Collection<IProduct> resultSetToProduct(ResultSet source);
}
