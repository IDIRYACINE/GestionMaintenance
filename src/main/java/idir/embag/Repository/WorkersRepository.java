package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;

public class WorkersRepository {
    
    public Collection<Worker> resultSetToProduct(ResultSet source){
        Collection<Worker> result = new ArrayList<Worker>();
        try {
            while (source.next()) {
                result.add(new Worker( source.getString(EWorkerAttributes.WorkerName.toString()),
                                        source.getInt(EWorkerAttributes.WorkerPhone.toString()),
                                        source.getString(EWorkerAttributes.WorkerEmail.toString()),
                                        source.getInt(EWorkerAttributes.WorkerId.toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
