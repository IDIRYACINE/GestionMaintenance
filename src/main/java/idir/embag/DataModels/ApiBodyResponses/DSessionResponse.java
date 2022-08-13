package idir.embag.DataModels.ApiBodyResponses;

import java.util.List;
import idir.embag.DataModels.Session.SessionRecord;

public class DSessionResponse {
    public int sessionId;
    public List<SessionRecord> records;
}
