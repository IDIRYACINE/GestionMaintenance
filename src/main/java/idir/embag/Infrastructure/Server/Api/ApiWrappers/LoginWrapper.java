package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class LoginWrapper extends IApiWrapper{

    String password;

    String username;

    public LoginWrapper(String username,String password) {
        this.password = password;
        this.username = username;
        api = EApi.loginAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
