package idir.embag.Application.Workers;

import java.util.Comparator;

import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Application.Workers.IWorkersController;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class WorkersController implements IWorkersController {
      
    private MFXTableView<Worker> tableWorkers;

    public WorkersController(MFXTableView<Worker> tableWorkers) {
        this.tableWorkers = tableWorkers;
    }

    @Override
    public void notifyActive() {
        setColumns();
        
    }

    private void setColumns(){
        MFXTableColumn<Worker> idColumn = new MFXTableColumn<>(Names.WorkerId, true, Comparator.comparing(Worker::getId));
		MFXTableColumn<Worker> nameColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(Worker::getName));
        MFXTableColumn<Worker> emailColumn = new MFXTableColumn<>(Names.WorkerEmail, true, Comparator.comparing(Worker::getEmail));
        MFXTableColumn<Worker> phoneColumn = new MFXTableColumn<>(Names.WorkerPhone, true, Comparator.comparing(Worker::getPhone));

        tableWorkers.getTableColumns().setAll(idColumn,nameColumn,emailColumn,phoneColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addWorker() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateWorker() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void archiveWorker() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addWorkerToSession() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void searchWorkers() {
        // TODO Auto-generated method stub
        
    }
    
}
