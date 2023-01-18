package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;

import idir.embag.DataModels.Users.Affectation;
import idir.embag.Types.Infrastructure.Database.Metadata.EAffectationAttributes;

public class AffectationsRepository {

    public ArrayList<Affectation> resultSetToAffectation(ResultSet affectationsSource) {
        ArrayList<Affectation> result = new ArrayList<Affectation>();
        try {

            while (affectationsSource.next()) {
                result.add(
                        new Affectation(
                                affectationsSource.getInt(EAffectationAttributes.AffectationId.toString()),
                                affectationsSource.getNString(EAffectationAttributes.AffectationName.toString())));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    
}
