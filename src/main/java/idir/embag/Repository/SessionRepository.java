package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Session.Session;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionGroupAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionWorkerAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;

public class SessionRepository {

    public Collection<SessionRecord> resultSetToRecord(ResultSet source){
        Collection<SessionRecord> result = new ArrayList<SessionRecord>();
        try {
            while (source.next()) {
                result.add(new SessionRecord(
                    source.getInt(ESessionRecordAttributes.InventoryId.toString()),
                    source.getString(EStockAttributes.ArticleName.toString()),
                    source.getString(ESessionRecordAttributes.RecordDate.toString()),
                    source.getString(ESessionRecordAttributes.StockPrice.toString()),
                    source.getString(ESessionRecordAttributes.StockQuantity.toString()),
                    source.getString(ESessionRecordAttributes.RecordQuantity.toString()),
                    source.getString(ESessionRecordAttributes.QuantityShift.toString()),
                    source.getString(ESessionRecordAttributes.PriceShift.toString()),
                    source.getString(ESessionRecordAttributes.RecordId.toString()), 
                    source.getString(EWorkerAttributes.WorkerName.toString())));
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
                source.getInt(ESessionGroupAttributes.GroupId.toString()),
                source.getString(ESessionGroupAttributes.GroupName.toString()),
                source.getTimestamp(ESessionGroupAttributes.SessionId.toString())));
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
                source.getInt(ESessionWorkerAttributes.WorkerId.toString()),
                source.getString(EWorkerAttributes.WorkerName.toString()),
                source.getString(ESessionWorkerAttributes.Username.toString()),
                source.getString(ESessionWorkerAttributes.Password.toString()),
                source.getString(ESessionGroupAttributes.GroupName.toString()),
                source.getInt(ESessionGroupAttributes.GroupId.toString())
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
                source.getTimestamp(ESessionAttributes.SessionId.toString()),
                source.getBoolean(ESessionAttributes.Active.toString()),
                source.getString(ESessionAttributes.StartDate.toString()),
                source.getString(ESessionAttributes.EndDate.toString()),
                source.getDouble(ESessionAttributes.PriceShiftValue.toString()),
                source.getDouble(ESessionAttributes.QuantityShiftValue.toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
