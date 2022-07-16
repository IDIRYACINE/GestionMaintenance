package idir.embag.Application.Session;

import java.util.Comparator;

import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Application.Session.ISessionHelper;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionController {
    
    private MFXTableView<SessionRecord> tableRecord;
    private ISessionHelper sessionHelper;

    public SessionController(MFXTableView<SessionRecord> tableRecord ) {
        this.tableRecord = tableRecord;
    }
    
    public void refresh() {
        sessionHelper.refresh();
    }

    public void manageSessionGroups() {
      sessionHelper.manageSessionGroups();
    }
    
    public void notifyEvent(StoreEvent event) {
        sessionHelper.notifyEvent(event);
    }
    
    public void notifyActive() {
        setColumns();
    }

    public void closeSession() {
        sessionHelper.closeSession();
        
    }

    private void setColumns(){
        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionRecord::getArticleId));
		MFXTableColumn<SessionRecord> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getArticleName));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> priceShiftColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getPriceShift));
        MFXTableColumn<SessionRecord> totalPriceShiftColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getTotalPriceShift));
		MFXTableColumn<SessionRecord> workerIdColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getWorkerId));
        MFXTableColumn<SessionRecord> groupIdColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getGroupId));
		MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getDate));
		MFXTableColumn<SessionRecord> inventoryQuantiyColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getQuantityInventory));
        MFXTableColumn<SessionRecord> stockQuantiyColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getQuantityStock));


        tableRecord.getTableColumns().setAll(idColumn,nameColumn,groupIdColumn,workerIdColumn,dateColumn,
                inventoryQuantiyColumn,stockQuantiyColumn,priceColumn,priceShiftColumn,totalPriceShiftColumn);
        
    }

}