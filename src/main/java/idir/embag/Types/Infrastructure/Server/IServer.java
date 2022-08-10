package idir.embag.Types.Infrastructure.Server;

import java.util.Map;

public interface IServer {
    public void dispatchApiCall(Map<EServerKeys,Object> data);
}
