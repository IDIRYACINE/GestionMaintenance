package idir.embag.Repository;

import java.sql.ResultSet;

import idir.embag.DataModels.AppState.AppStateWrapper;
import idir.embag.Types.MetaData.EAutoIncrementedAliases;

public class AppStateRepository {
   
    public AppStateWrapper resultSetToAppStateWrapper(ResultSet resultSet) {
        try {
            resultSet.next();
            int userCurrId = resultSet.getInt(EAutoIncrementedAliases.userCurrId.name());
            int workerCurrId = resultSet.getInt(EAutoIncrementedAliases.workerCurrId.name());
            int sessionGroupCurrId = resultSet.getInt(EAutoIncrementedAliases.sessionGroupCurrId.name());
            return new AppStateWrapper(userCurrId, workerCurrId, sessionGroupCurrId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
