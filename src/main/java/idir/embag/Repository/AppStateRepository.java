package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.Map;

import idir.embag.Application.State.ConfigState;
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

    public ConfigState jsonToConfigState(Map<String, Object> config) {
      
        return new ConfigState(
            (String) config.get(ConfigState.Keys.databaseName.name()),
            (String) config.get(ConfigState.Keys.databaseUser.name()),
            (String) config.get(ConfigState.Keys.databasePassword.name()),
            (String) config.get(ConfigState.Keys.databaseHost.name()),
            (int) config.get(ConfigState.Keys.databasePort.name()),
            (String) config.get(ConfigState.Keys.serverHost.name()),
            (int) config.get(ConfigState.Keys.serverPort.name()),
            (String) config.get(ConfigState.Keys.serverAuthToken.name()),
            (int) config.get(ConfigState.Keys.serverApiVersion.name())
        );
    }
}
