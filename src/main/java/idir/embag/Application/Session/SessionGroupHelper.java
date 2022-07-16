package idir.embag.Application.Session;

import java.util.Comparator;

import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.Types.Application.Session.ISessionManagerHelper;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionGroupHelper implements ISessionManagerHelper{
    
    private MFXTableView<SessionGroup> tableSessionGroups;

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
        MFXTableColumn<SessionGroup> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionGroup::getId));
		MFXTableColumn<SessionGroup> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionGroup::getName));
       
        tableSessionGroups.getTableColumns().setAll(idColumn,nameColumn);
        
    }

    
}