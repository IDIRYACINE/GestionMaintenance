package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Others.FamilyCode;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Infrastructure.Database.Metadata.EFamilyCodeAttributes;

public class FamilyCodeRepository {
    
    public Collection<IProduct> resultSetToProduct(ResultSet source){
        Collection<IProduct> result = new ArrayList<IProduct>();
        try {
            while (source.next()) {
                result.add(new FamilyCode( source.getString(EFamilyCodeAttributes.FamilyName.toString()),
                    source.getInt(EFamilyCodeAttributes.FamilyCode.toString())
                  ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
