package idir.embag.Application.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import idir.embag.Application.State.AppState;
import idir.embag.DataModels.AppState.AppStateWrapper;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Repository.AppStateRepository;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;

public class AppStateLoader {
    private static AppStateRepository appStateRepository = new AppStateRepository();
    private static String databasename = "test";

    public static void loadAppState() {

        try {

            String query = formulateAppStateQuery();

            ResultSet result = ServicesProvider.getInstance().getDatabaseInitialiser().executeQuery(query);

            AppStateWrapper wrapper = appStateRepository.resultSetToAppStateWrapper(result);

            AppState.getInstance().setAppState(wrapper);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String formulateAppStateQuery() {
        String query = "SELECT AUTO_INCREMENT FROM information_schema.TABLES"
                + " AS userCurrId "
                + "WHERE TABLE_SCHEMA = '" + databasename + "' AND TABLE_NAME ="
                + ETables.Users + "UNION "

                + "SELECT AUTO_INCREMENT FROM information_schema.TABLES"
                + " As workerCurrId "
                + "WHERE TABLE_SCHEMA = '" + databasename + "' AND TABLE_NAME ="
                + ETables.SessionsGroups + "UNION "

                + "SELECT AUTO_INCREMENT FROM information_schema.TABLES"
                + " As sessionGroupCurrId "
                + "WHERE TABLE_SCHEMA = '" + databasename + "' AND TABLE_NAME ="
                + ETables.Workers;

        return query;
    }
}
