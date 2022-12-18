package idir.embag.EventStore.Stores.NavigationStore;

import java.util.ArrayList;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class NavigationStore implements IStore {

    public static final int PanelCount = 5;
    public static final int SettingsPanelId = 0;
    public static final int SessionPanelId = 1;
    public static final int HistoryPanelId = 2;
    public static final int WorkersPanelId = 3;
    public static final int StockPanelId = 4;

    private INavigationController navigationController;

    private ArrayList<IEventSubscriber> subscribers;

    public NavigationStore(INavigationController navigationController) {
        this.navigationController = navigationController;
        subscribers = new ArrayList<>();
    }

    @Override
    public void dispatch(StoreEvent event) {
        Map<EEventsDataKeys, Object> data = event.getData();

        switch (event.getAction()) {
            case Navigation:
                navigationController.navigateToPanel(
                        DataBundler.retrieveNestedValue(data, EEventsDataKeys.NavigationKeys, ENavigationKeys.PanelId));
                break;
            case Subscribe:
                subscribe(event);
                break;
            case Unsubscribe:
                unsubscribe(event);
                break;
            case Dialog:
                navigationController.displayPopup(DataBundler.retrieveNestedValue(data, EEventsDataKeys.NavigationKeys,
                        ENavigationKeys.DialogContent));
                break;
            default:
                break;
        }
    }

    @Override
    public void notifySubscriber(StoreEvent event) {
        for (IEventSubscriber subscriber : subscribers) {
            subscriber.notifyEvent(event);
        }
    }

    @Override
    public void broadcast(StoreEvent event) {
        for (IEventSubscriber subscriber : subscribers) {
            subscriber.notifyEvent(event);
        }
    }

    @Override
    public void subscribe(StoreEvent event) {
        subscribers.add((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber));
    }

    @Override
    public void unsubscribe(StoreEvent event) {
        subscribers.remove((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber));
    }

}
