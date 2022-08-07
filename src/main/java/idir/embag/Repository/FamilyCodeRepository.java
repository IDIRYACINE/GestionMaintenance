package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Products.FamilyCode;
import idir.embag.Types.Infrastructure.Database.Metadata.EFamilyCodeAttributes;

public class FamilyCodeRepository {
    
    public Collection<FamilyCode> resultSetToProduct(ResultSet source){
        Collection<FamilyCode> result = new ArrayList<FamilyCode>();
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
