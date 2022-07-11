package idir.embag.Application.Models.Workers;

import idir.embag.Application.Models.Generics.ISearchable;
import idir.embag.DataModels.Workers.EWorker;
import idir.embag.DataModels.Workers.Worker;

public interface IWorkersManager extends ISearchable<EWorker,Worker>{

   public void addWorker(Worker worker);
   public void removeWorker(Worker worker);
   public void updateWorker(EWorker[] updatedFields,String []updatedValues);

}
