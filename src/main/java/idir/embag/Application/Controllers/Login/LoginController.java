package idir.embag.Application.Controllers.Login;

import java.util.HashMap;
import java.util.Map;

import idir.embag.Infrastructure.Server.Server;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.LoginWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.Infrastructure.Server.IServer;

public class LoginController {

    IServer server;

    public LoginController() {
        server = new Server("localhost:", "", 0);
    }

    public void login(String username, String password) {
        Map<EServerKeys,Object> data = new HashMap<>();

        LoginWrapper apiWrapper = new LoginWrapper(username,password);
        data.put(EServerKeys.ApiWrapper, apiWrapper);

        server.dispatchApiCall(data);
    }

    
    
}
