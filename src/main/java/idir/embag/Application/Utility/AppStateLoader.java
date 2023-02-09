package idir.embag.Application.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.yaml.snakeyaml.Yaml;

import idir.embag.Application.State.AppState;
import idir.embag.Application.State.ConfigState;
import idir.embag.DataModels.AppState.AppStateWrapper;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Repository.AppStateRepository;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.MetaData.EAutoIncrementedAliases;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;

public class AppStateLoader {
    private static AppStateRepository appStateRepository = new AppStateRepository();


    public static void loadAppState( ) {

        try {
            ConfigState configState = loadConfigState();

            String query = formulateAppStateQuery(configState.databaseName);

            ResultSet result = ServicesProvider.getInstance().getDatabaseInitialiser().executeQuery(query);

            AppStateWrapper wrapper = appStateRepository.resultSetToAppStateWrapper(result);

            AppState.getInstance().setAppState(wrapper,configState);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String formulateAppStateQuery(String databasename) {
        String query = "SELECT u.AUTO_INCREMENT AS '" + EAutoIncrementedAliases.userCurrId + "',"
                + " sg.AUTO_INCREMENT AS '" + EAutoIncrementedAliases.sessionGroupCurrId + "',"
                + " w.AUTO_INCREMENT AS '" + EAutoIncrementedAliases.workerCurrId + "'"
                + " FROM information_schema.TABLES u"
                + " LEFT JOIN information_schema.TABLES sg ON sg.TABLE_SCHEMA = '" + databasename+ "'"
                + " AND sg.TABLE_NAME ='" + ETables.SessionsGroups + "'"
                + " LEFT JOIN information_schema.TABLES w ON w.TABLE_SCHEMA = '" + databasename+ "'"
                + " AND w.TABLE_NAME ='" + ETables.Workers + "'"
                + " WHERE u.TABLE_SCHEMA = '" + databasename+ "'"
                + " AND u.TABLE_NAME ='" + ETables.Users + "'";

        return query;
    }

    public static ConfigState loadConfigState() {
        String configFilePath = new File("").getAbsolutePath() + "/config.yaml";
        try {
            File source = new File(configFilePath);
            InputStream input = new FileInputStream(source);
            Yaml yaml = new Yaml();
            Map<String,Object> config = yaml.load(input);

            return appStateRepository.jsonToConfigState(config);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            String message = "Config file not found or corrupted,generate default config file ?";
            displayPopupMessage(message,AppStateLoader::generateDefaultConfigFile);
            return ConfigState.defaultState();
        }
    }



    private static void displayPopupMessage(String message, Consumer<Map<EEventsDataKeys, Object>> callback) {
        ConfirmationDialog dialogContent = new ConfirmationDialog();

        dialogContent.setMessage(message);

        dialogContent.setOnConfirm(callback);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.loadFxml();

        StoreCenter.getInstance().dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent,
                EStoreEventAction.Dialog, data);

    }

    private static void generateDefaultConfigFile(Map<EEventsDataKeys, Object> extra){
        try {
            String configFilePath = new File("").getAbsolutePath() + "/config.yaml";

            Map<String, Object> data = new HashMap<>();
            data.put(ConfigState.Keys.databaseName.name(), "test");
            data.put(ConfigState.Keys.databaseUser.name(), "idir");
            data.put(ConfigState.Keys.databaseHost.name(), "127.0.0.1");
            data.put(ConfigState.Keys.databasePort.name(), 3306);
            data.put(ConfigState.Keys.databasePassword.name(), "idir");
            data.put(ConfigState.Keys.serverApiVersion.name(), 0);
            data.put(ConfigState.Keys.serverAuthToken.name(), "embag343adminvcs");
            data.put(ConfigState.Keys.serverHost.name(), "127.0.0.1");
            data.put(ConfigState.Keys.serverPort.name(), 3050);


            File source = new File(configFilePath);

            if (!source.exists())
                source.createNewFile();

            FileWriter writer = new FileWriter(source);

            Yaml yaml = new Yaml();
            yaml.dump(data, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}