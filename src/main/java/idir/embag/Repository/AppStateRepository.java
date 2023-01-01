package idir.embag.Repository;

import java.sql.ResultSet;

import idir.embag.DataModels.AppState.AppStateWrapper;

public class AppStateRepository {
   
    public AppStateWrapper resultSetToAppStateWrapper(ResultSet resultSet) {
        try {
            int userCurrId = resultSet.getInt("userCurrId");
            int workerCurrId = resultSet.getInt("workerCurrId");
            int sessionGroupCurrId = resultSet.getInt("sessionGroupCurrId");
            return new AppStateWrapper(userCurrId, workerCurrId, sessionGroupCurrId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
