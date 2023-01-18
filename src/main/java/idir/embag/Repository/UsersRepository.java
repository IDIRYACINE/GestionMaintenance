package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Users.Affectation;
import idir.embag.DataModels.Users.User;
import idir.embag.Types.Infrastructure.Database.Metadata.EAffectationAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EAffectationPermissions;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;

public class UsersRepository {

    public Collection<User> resultSetToUsers(ResultSet userSource, ResultSet designationsSource) {
        Collection<User> result = new ArrayList<User>();
        ArrayList<Affectation> designations = new ArrayList<Affectation>();

        try {

            while (designationsSource.next()) {
                designations.add(
                        new Affectation(
                                designationsSource.getInt(EAffectationPermissions.AffectationId.toString()),
                                designationsSource.getNString(EAffectationAttributes.AffectationName.toString())));
            }

            while (userSource.next()) {

                result.add(new User(
                        userSource.getInt(EUsersAttributes.UserId.toString()),
                        userSource.getString(EUsersAttributes.UserName.toString()),
                        userSource.getString(EUsersAttributes.Password.toString()),

                        userSource.getBoolean(EUsersAttributes.Admin.toString()),
                        designations));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Collection<User> resultSetToUsers(ResultSet userSource) {
        Collection<User> result = new ArrayList<User>();

        try {
            while (userSource.next()) {

                result.add(new User(
                        userSource.getInt(EUsersAttributes.UserId.toString()),
                        userSource.getString(EUsersAttributes.UserName.toString()),
                        userSource.getString(EUsersAttributes.Password.toString()),

                        userSource.getBoolean(EUsersAttributes.Admin.toString()),
                        null));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
