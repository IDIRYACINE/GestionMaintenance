package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;
import okhttp3.HttpUrl.Builder;

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

    @Override
    public Builder getApiUrl() {
        Builder urlBuilder =  super.getApiUrl();
        urlBuilder.addQueryParameter("username", username);
        urlBuilder.addQueryParameter("password", password);
        return urlBuilder;
    }

    
}
