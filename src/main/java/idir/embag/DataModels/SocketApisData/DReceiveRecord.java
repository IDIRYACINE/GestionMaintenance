package idir.embag.DataModels.SocketApisData;

import java.sql.Timestamp;

public class DReceiveRecord {
    public int articleId;
    public String articleName;
    public Timestamp recordDate;
    public String stockPrice;
    public String stockQuantity;
    public String recordQuantity;
    public String quantityShift;
    public String priceShift;
    public int groupId;
    public String workerName;
    public Timestamp sessionId;
    public int productDesignation;
}
