package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Others.FamilyCode;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;

public class FamilyCodeRepository {
    
    public Collection<IProduct> resultSetToProduct(ResultSet source){
        Collection<IProduct> result = new ArrayList<IProduct>();
        try {
            while (source.next()) {
                result.add(new FamilyCode( source.getString(MDatabase.FamilliesCodeAttributes.FamilyName),
                    source.getInt(MDatabase.FamilliesCodeAttributes.FamilyCode)
                  ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
