package idir.embag.DataModels.SocketApisData;

import java.sql.Timestamp;

public class DSubmitRecord {
    public int barcode;
    public int workerId;
    public String groupId;
    public String workerName;
    public Timestamp scannedDate;
    public Timestamp requestTimestamp;
}
