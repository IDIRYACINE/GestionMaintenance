package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Session.Session;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;

public class SessionRepository {

    public Collection<SessionRecord> resultSetToRecord(ResultSet source){
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

    public Collection<SessionGroup> resultSetToGroup(ResultSet source) {
        Collection<SessionGroup> result = new ArrayList<>();
        try {
            while (source.next()) {
                result.add(new SessionGroup(
                source.getInt(MDatabase.SessionGroupsAttributes.Id),
                source.getString(MDatabase.SessionGroupsAttributes.Name),
                source.getInt(MDatabase.SessionGroupsAttributes.SessionId)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Collection<SessionWorker> resultSetToWorker(ResultSet source) {
        Collection<SessionWorker> result = new ArrayList<>();
        try {
            while (source.next()) {
                result.add(new SessionWorker(
                source.getInt(MDatabase.SessionWorkersAttributes.Id),
                source.getString(MDatabase.WorkersAttributes.Name),
                source.getString(MDatabase.SessionWorkersAttributes.Password),
                source.getString(MDatabase.SessionGroupsAttributes.Name),
                source.getInt(MDatabase.SessionGroupsAttributes.Id)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Collection<Session> resultSetToSession(ResultSet source) {
        Collection<Session> result = new ArrayList<>();
        try {
            while (source.next()) {
                result.add(new Session(
                source.getInt(MDatabase.SessionsAttributes.SessionId),
                source.getString(MDatabase.SessionsAttributes.StartDate),
                source.getString(MDatabase.SessionsAttributes.EndDate),
                source.getDouble(MDatabase.SessionsAttributes.PriceShiftValue),
                source.getDouble(MDatabase.SessionsAttributes.QuantityShiftValue)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
