package idir.embag.Application.Controllers.Login;

import java.util.HashMap;
import java.util.Map;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.LoginWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;

public class LoginController {


    public void login(String username, String password) {
        Map<EServerKeys,Object> data = new HashMap<>();

        LoginWrapper apiWrapper = new LoginWrapper(username,password);
        data.put(EServerKeys.ApiWrapper, apiWrapper);

        ServicesProvider.getInstance().getRemoteServer().dispatchApiCall(data);
    }

    
    
}
