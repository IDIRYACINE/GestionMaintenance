package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;

public class WorkerRepository {
    
    public Collection<Worker> resultSetToProduct(ResultSet source){
        Collection<Worker> result = new ArrayList<Worker>();
        try {
            while (source.next()) {
                result.add(new Worker( source.getString(MDatabase.WorkersAttributes.Name),
                                        source.getInt(MDatabase.WorkersAttributes.Phone),
                                        source.getString(MDatabase.WorkersAttributes.Email),
                                        source.getInt(MDatabase.WorkersAttributes.WorkerId)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
