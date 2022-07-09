package idir.embag.Utility.Server;

public interface IServer {

    public void connectToWebSocket();
    public void login();
    public void logout();
    public void subscribeToEvent();
    public void unsubscribeFromEvent();
    public void backupDatabase();
    public void restoreDatabase();

}
