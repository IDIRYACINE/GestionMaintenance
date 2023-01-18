package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;

import idir.embag.DataModels.Users.Designation;
import idir.embag.Types.Infrastructure.Database.Metadata.EAffectationAttributes;

public class DesignationsRepository {

    public ArrayList<Designation> resultSetToDesignation(ResultSet designationsSource) {
        ArrayList<Designation> result = new ArrayList<Designation>();
        try {

            while (designationsSource.next()) {
                result.add(
                        new Designation(
                                designationsSource.getInt(EAffectationAttributes.AffectationId.toString()),
                                designationsSource.getNString(EAffectationAttributes.DesignationName.toString())));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    
}
