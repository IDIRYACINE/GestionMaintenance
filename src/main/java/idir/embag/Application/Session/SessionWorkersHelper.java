package idir.embag.Application.Session;

import java.util.Comparator;

import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Application.Session.ISessionManagerHelper;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionWorkersHelper implements ISessionManagerHelper {
        
    private MFXTableView<SessionWorker> tableSessionWorkers;

    @Override
    public void add() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notifyActive() {
        setColumns();
        
    }


    private void setColumns(){
        MFXTableColumn<SessionWorker> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionWorker::getWorkerId));
		MFXTableColumn<SessionWorker> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionWorker::getWorkerName));
        MFXTableColumn<SessionWorker> groupColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionWorker::getGroupName));
        MFXTableColumn<SessionWorker> passwordColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionWorker::getPassword));
	
        tableSessionWorkers.getTableColumns().setAll(idColumn,groupColumn,nameColumn,passwordColumn);
        
    }



    
}
