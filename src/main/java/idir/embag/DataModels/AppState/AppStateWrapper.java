package idir.embag.DataModels.AppState;

public class AppStateWrapper {
    public final int userCurrId;
    public final int workerCurrId;
    public final int sessionGroupCurrId;

    public AppStateWrapper(int userCurrId, int workerCurrId, int sessionGroupCurrId) {
        this.userCurrId = userCurrId;
        this.workerCurrId = workerCurrId;
        this.sessionGroupCurrId = sessionGroupCurrId;
    }
}
