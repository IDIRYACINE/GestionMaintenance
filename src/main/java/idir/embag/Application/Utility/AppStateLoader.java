package idir.embag.Application.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import idir.embag.Application.State.AppState;
import idir.embag.DataModels.AppState.AppStateWrapper;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Repository.AppStateRepository;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.MetaData.EAutoIncrementedAliases;

public class AppStateLoader {
    private static AppStateRepository appStateRepository = new AppStateRepository();

    private static String databasename = "'test'";

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
        String query = "SELECT u.AUTO_INCREMENT AS '" + EAutoIncrementedAliases.userCurrId + "',"
                + " sg.AUTO_INCREMENT AS '" + EAutoIncrementedAliases.sessionGroupCurrId + "',"
                + " w.AUTO_INCREMENT AS '" + EAutoIncrementedAliases.workerCurrId + "'"
                + " FROM information_schema.TABLES u"
                + " LEFT JOIN information_schema.TABLES sg ON sg.TABLE_SCHEMA = " + databasename
                + " AND sg.TABLE_NAME ='" + ETables.SessionsGroups + "'"
                + " LEFT JOIN information_schema.TABLES w ON w.TABLE_SCHEMA = " + databasename
                + " AND w.TABLE_NAME ='" + ETables.Workers + "'"
                + " WHERE u.TABLE_SCHEMA = " + databasename
                + " AND u.TABLE_NAME ='" + ETables.Users + "'";

        return query;
    }
}
