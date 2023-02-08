package idir.embag.Application.Controllers.Login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.State.AppState;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.User;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.Server.Server;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.LoginWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.Login.LoginEvent;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class LoginController implements IEventSubscriber{



    public void login(String username, String password) {

        Map<EEventsDataKeys,Object> data = new HashMap<>();

        ArrayList<AttributeWrapper> wrappers = new ArrayList<>();
        
        wrappers.add(new AttributeWrapper(EUsersAttributes.UserName, username));
        wrappers.add(new AttributeWrapper(EUsersAttributes.Password, password));
        SearchWrapper searchWrapper = new SearchWrapper(wrappers);
        
        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.SearchWrapper, searchWrapper);
        data.put(EEventsDataKeys.Subscriber, this);

        StoreEvent event = new StoreEvent(EStoreEvents.UsersEvent, EStoreEventAction.Search, data);
        StoreDispatch disaptch = new StoreDispatch(EStores.DataStore, event);

        StoreCenter.getInstance().dispatch(disaptch);
        
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        Collection<User> users =  DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection);

        if(users.size() > 0){
            User user = users.iterator().next();
            AppState.getInstance().setCurrentUser(user);
            loginToRemoteServer();
        }
    }

    private void loginToRemoteServer(){
        LoginWrapper apiWrapper = new LoginWrapper("idir","idir");

        LoginEvent event = new LoginEvent("loginController",apiWrapper);
        Server.getInstance().dispatchEvent(event);
    }

    
}
