package idir.embag.EventStore.Models.Workers;

import idir.embag.DataModels.Metadata.EWorker;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.EventStore.Models.Generics.ISearchable;

public interface IWorkersManager extends ISearchable<EWorker,Worker>{

   public void addWorker(Worker worker);
   public void removeWorker(Worker worker);
   public void updateWorker(EWorker[] updatedFields,String []updatedValues);

}
