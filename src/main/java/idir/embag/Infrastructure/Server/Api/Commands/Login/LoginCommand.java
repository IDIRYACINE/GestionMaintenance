package idir.embag.Infrastructure.Server.Api.Commands.Login;

import java.net.URI;
import java.net.URISyntaxException;

import idir.embag.Infrastructure.Server.Server;
import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.LoginWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.LoginRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.LoginResponse;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders;
import idir.embag.Types.Api.EHeaders.Headers;
import idir.embag.Types.Api.IApi;

public class LoginCommand extends SimpleServiceCommand<LoginWrapper, LoginEvent> {
    final ServerConfigurations serverConfigurations;

    public LoginCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    @Override
    public void execute(LoginEvent event) {
        IApi loginApi = new LoginRequest(event.getData());

        loginApi.addHeader(Headers.access_token, serverConfigurations.authToken);

        Runnable initSocket = new Runnable() {
            @Override
            public void run() {
                try {
                    URI url = new URI("ws://" + serverConfigurations.serverPath + ":" + serverConfigurations.port);
                    Server server = Server.getInstance();
                    server.webSocketClient = new WebSocketImpl(url);
                    server.webSocketClient.addHeader(EHeaders.valueOf(Headers.access_token),
                            serverConfigurations.authToken);
                    server.webSocketClient.connectBlocking();

                } catch (URISyntaxException | InterruptedException e) {
                    System.out.println("Invalid server path");
                    e.printStackTrace();
                }
            }
        };

        LoginResponse loginHandler = new LoginResponse(initSocket);
        loginApi.setResponseHandler(loginHandler);

        loginApi.execute();

    }

    @Override
    public int getEventId() {
        return LoginEvent.eventId;
    }

}
