package idir.embag.Application.Controllers.Exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Generics.EExportSessionKeys;
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

@SuppressWarnings("unchecked")
public class Exporter implements IEventSubscriber {

    private boolean isExporting = false;

    private boolean isImporting = false;

    private ExportWrapper exportWrapper;

    private ImportWrapper importWrapper;

    private String exportHistoryFilePath = new File("").getAbsolutePath() + "/Data/ExportHistory.yaml";

    private Map<EExportSessionKeys, Object> sessionProgress;

    private Runnable doneCallback;

    public Exporter() {
        loadLastSessionProgress();
    }

    private void exportData(Map<EEventsDataKeys, Object> data) {
        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);

        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, exportWrapper.getTargetTable(),
                EStoreEventAction.Export, data);
        storeCenter.dispatch(event);

    }

    private void importData() {
        Map<EEventsDataKeys, Object> data = new HashMap<EEventsDataKeys, Object>();
        data.put(EEventsDataKeys.Subscriber, this);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);
        wrappersData.put(EWrappers.ImportWrapper, importWrapper);

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, importWrapper.getTargetTable(),
                EStoreEventAction.Import, data);
        storeCenter.dispatch(event);
    }

    private void exportDataFromDatabase(EStoreEvents exportedTable) {
        Map<EEventsDataKeys, Object> data = new HashMap<EEventsDataKeys, Object>();
        data.put(EEventsDataKeys.Subscriber, this);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);
        wrappersData.put(EWrappers.LoadWrapper, exportWrapper.getLoadWrapper());

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataStore, exportedTable, EStoreEventAction.Load,
                data);
        storeCenter.dispatch(event);

    }

    public void startExport(EStoreEvents exportedTable, ExportWrapper exportWrapper) {
        this.exportWrapper = exportWrapper;
        isExporting = true;
        isImporting = false;
        exportDataFromDatabase(exportedTable);
    }

    public void startImport(EStoreEvents importedTable, ImportWrapper importWrapper) {
        this.importWrapper = importWrapper;
        isImporting = true;
        isExporting = false;
        importData();
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        System.out.println("Exporter: " + event.getAction());


        EOperationStatus status = (EOperationStatus) event.getData().get(EEventsDataKeys.OperationStatus);
        System.out.println("Exporter: " + status);
        switch (status) {
            case HasData:
                startExportOrImport(event.getData());
                break;
            case Completed:
                onNoData();
                break;
            case Stop:
                onNoData();
                break;
            case NoData:
                onNoData();
                break;
            default:
                exportData(event.getData());
                break;
        }
    }

    // private void nextDataSet(){
    // if(isExporting){
    // exportWrapper.nextRowPatch();
    // startExport(exportWrapper.getTargetTable(), exportWrapper);
    // }
    // else if(isImporting){
    // importWrapper.nextRowPatch();
    // importData();
    // }
    // }

    private void startExportOrImport(Map<EEventsDataKeys, Object> data) {
        if (isExporting) {
            exportData(data);
        } else if (isImporting) {
            importData();
        }
    }

    private void loadLastSessionProgress() {
        try {
            File source = new File(exportHistoryFilePath);
            InputStream input = new FileInputStream(source);
            Yaml yaml = new Yaml();
            sessionProgress = (Map<EExportSessionKeys, Object>) yaml.load(input);

        } catch (Exception e) {
            sessionProgress = new HashMap<>();
        }
    }

    private void saveSessionProgress() {
        try {

            Map<EExportSessionKeys, Object> data = new HashMap<>();
            if (exportWrapper != null)
                data.put(EExportSessionKeys.ExportSession, exportWrapper.getMap());
            if (importWrapper != null)
                data.put(EExportSessionKeys.ImportSession, importWrapper.getMap());

            File source = new File(exportHistoryFilePath);

            if (!source.exists())
                source.createNewFile();

            FileWriter writer = new FileWriter(source);

            Yaml yaml = new Yaml();
            yaml.dump(data, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onNoData() {
        if (isExporting || isImporting) {
            saveSessionProgress();
            isImporting = false;
            isExporting = false;
        }
        doneCallback.run();
    }

    public void unsubscribe() {
        StoreCenter.getInstance().unsubscribeFromEvents(EStores.DataConverterStore, null, this);
    }

    public void cancel() {
        saveSessionProgress();
    }

    public void loadSessionExportSession() {
        Map<EExportSessionKeys, Object> rawExportWrapper = (Map<EExportSessionKeys, Object>) sessionProgress
                .get(EExportSessionKeys.ExportSession);
        exportWrapper = new ExportWrapper(rawExportWrapper);
    }

    public void loadSessionImportSession() {
        Map<EExportSessionKeys, Object> rawImportWrapper = (Map<EExportSessionKeys, Object>) sessionProgress
                .get(EExportSessionKeys.ImportSession);
        importWrapper = new ImportWrapper(rawImportWrapper);
    }

    public void setDoneCallback(Runnable callback) {
        doneCallback = callback;

    }

}
