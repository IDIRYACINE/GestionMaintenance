package idir.embag.DataModels.ApiBodyResponses;

import java.sql.Timestamp;
import java.util.List;


import idir.embag.DataModels.Session.SessionRecord;

public class DSessionResponse {
    public Timestamp sessionId;
    public List<SessionRecord> records;
}
