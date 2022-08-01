package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;

public class SessionRecordRepository {
    public Collection<SessionRecord> resultSetToProduct(ResultSet source){
        Collection<SessionRecord> result = new ArrayList<SessionRecord>();
        try {
            while (source.next()) {
                result.add(new SessionRecord(
                    source.getInt(MDatabase.SessionsRecordsAttributes.InventoryId),
                    source.getString(MDatabase.StockAttributes.ArticleName),
                    source.getString(MDatabase.SessionsRecordsAttributes.RecordDate),
                    source.getString(MDatabase.SessionsRecordsAttributes.StockPrice),
                    source.getString(MDatabase.SessionsRecordsAttributes.StockQuantity),
                    source.getString(MDatabase.SessionsRecordsAttributes.RecordQuantity),
                    source.getString(MDatabase.SessionsRecordsAttributes.QuantityShift),
                    source.getString(MDatabase.SessionsRecordsAttributes.PriceShift),
                    source.getString(MDatabase.SessionsRecordsAttributes.RecordId), 
                    source.getString(MDatabase.WorkersAttributes.Name)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
