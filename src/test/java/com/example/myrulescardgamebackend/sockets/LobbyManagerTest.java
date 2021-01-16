package com.example.myrulescardgamebackend.sockets;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.games.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LobbyManagerTest {
    LobbyManager lobbyManager;

    @BeforeEach
    public void init() {
        lobbyManager = new LobbyManager();
    }

    @Test
    public void CreateLobbyTest() {
        SocketIOClient socket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(socket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();

        lobbyManager.createLobby(socket, hostName, testGame);

        assertThat(lobbyManager.getPlayers().size()).isEqualTo(1);
        assertThat(lobbyManager.getLobbys().size()).isEqualTo(1);

        assertThat(lobbyManager.getPlayers().containsKey(uuid)).isEqualTo(true);
        assertThat(lobbyManager.getLobbys().containsKey(lobbyManager.getLobbyBySocket(socket).getCode())).isEqualTo(true);
        assertThat(lobbyManager.getLobbyBySocket(socket).getHost()).isEqualTo(lobbyManager.getPlayerBySocket(socket));
        assertThat(lobbyManager.getLobbyBySocket(socket).getGame()).isEqualTo(testGame);
        assertThat(lobbyManager.getLobbyBySocket(socket).getPlayers().size()).isEqualTo(1);
        assertThat(lobbyManager.getLobbyBySocket(socket).getPlayers().get(0)).isEqualTo(lobbyManager.getPlayerBySocket(socket));
    }

    @Test
    public void CreateMultipleLobbyTest() {
        SocketIOClient socket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(socket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();

        SocketIOClient socket2 = Mockito.mock(SocketIOClient.class);
        UUID uuid2 = new UUID(0, 2);
        Mockito.when(socket2.getSessionId()).thenReturn(uuid2);
        String hostName2 = "host2";
        Game testGame2 = new Game();

        lobbyManager.createLobby(socket, hostName, testGame);
        lobbyManager.createLobby(socket2, hostName2, testGame2);

        assertThat(lobbyManager.getPlayers().size()).isEqualTo(2);
        assertThat(lobbyManager.getLobbys().size()).isEqualTo(2);

        assertThat(lobbyManager.getPlayers().containsKey(uuid)).isEqualTo(true);
        assertThat(lobbyManager.getLobbys().containsKey(lobbyManager.getLobbyBySocket(socket).getCode())).isEqualTo(true);
        assertThat(lobbyManager.getLobbyBySocket(socket).getHost()).isEqualTo(lobbyManager.getPlayerBySocket(socket));
        assertThat(lobbyManager.getLobbyBySocket(socket).getGame()).isEqualTo(testGame);
        assertThat(lobbyManager.getLobbyBySocket(socket).getPlayers().size()).isEqualTo(1);
        assertThat(lobbyManager.getLobbyBySocket(socket).getPlayers().get(0)).isEqualTo(lobbyManager.getPlayerBySocket(socket));

        assertThat(lobbyManager.getPlayers().containsKey(uuid2)).isEqualTo(true);
        assertThat(lobbyManager.getLobbys().containsKey(lobbyManager.getLobbyBySocket(socket2).getCode())).isEqualTo(true);
        assertThat(lobbyManager.getLobbyBySocket(socket2).getHost()).isEqualTo(lobbyManager.getPlayerBySocket(socket2));
        assertThat(lobbyManager.getLobbyBySocket(socket2).getGame()).isEqualTo(testGame2);
        assertThat(lobbyManager.getLobbyBySocket(socket2).getPlayers().size()).isEqualTo(1);
        assertThat(lobbyManager.getLobbyBySocket(socket2).getPlayers().get(0)).isEqualTo(lobbyManager.getPlayerBySocket(socket2));
    }

    @Test
    public void lobbyExistsWithWrongCodeTest() {
        String testCode = "test";

        boolean result = lobbyManager.lobbyExists(testCode);

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void lobbyExistsWithRightCodeTest() {
        SocketIOClient socket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(socket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();

        lobbyManager.createLobby(socket, hostName, testGame);
        String code = lobbyManager.getLobbyBySocket(socket).getCode();

        boolean result = lobbyManager.lobbyExists(code);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void lobbyExistsWithWrongCodeWithExistingLobbysTest() {
        SocketIOClient socket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(socket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();

        lobbyManager.createLobby(socket, hostName, testGame);
        String code = "tst"; //codes have a length of 5 so this should always result in false

        boolean result = lobbyManager.lobbyExists(code);

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void joinLobbyTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        String code = lobbyManager.getLobbyBySocket(hostSocket).getCode();

        SocketIOClient joinSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid2 = new UUID(0, 2);
        Mockito.when(joinSocket.getSessionId()).thenReturn(uuid2);
        String joinName = "joiner";

        lobbyManager.joinLobby(joinSocket, code, joinName);

        assertThat(lobbyManager.getPlayers().size()).isEqualTo(2);
        assertThat(lobbyManager.getLobbys().size()).isEqualTo(1);

        assertThat(lobbyManager.getPlayers().containsKey(uuid)).isEqualTo(true);
        assertThat(lobbyManager.getPlayers().containsKey(uuid2)).isEqualTo(true);
        assertThat(lobbyManager.getLobbyBySocket(joinSocket)).isEqualTo(lobbyManager.getLobbyBySocket(hostSocket));
    }

    @Test
    public void joinLobbyWithMultipleTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        String code = lobbyManager.getLobbyBySocket(hostSocket).getCode();

        SocketIOClient host2Socket = Mockito.mock(SocketIOClient.class);
        UUID uuid3 = new UUID(0, 3);
        Mockito.when(host2Socket.getSessionId()).thenReturn(uuid3);
        String host2Name = "host2";
        Game testGame2 = new Game();
        lobbyManager.createLobby(host2Socket, host2Name, testGame2);

        SocketIOClient joinSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid2 = new UUID(0, 2);
        Mockito.when(joinSocket.getSessionId()).thenReturn(uuid2);
        String joinName = "joiner";

        lobbyManager.joinLobby(joinSocket, code, joinName);

        assertThat(lobbyManager.getPlayers().size()).isEqualTo(3);
        assertThat(lobbyManager.getLobbys().size()).isEqualTo(2);

        assertThat(lobbyManager.getPlayers().containsKey(uuid)).isEqualTo(true);
        assertThat(lobbyManager.getPlayers().containsKey(uuid2)).isEqualTo(true);
        assertThat(lobbyManager.getPlayers().containsKey(uuid3)).isEqualTo(true);
        assertThat(lobbyManager.getLobbyBySocket(joinSocket)).isEqualTo(lobbyManager.getLobbyBySocket(hostSocket));
    }

    @Test
    public void removeLobbyTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        String code = lobbyManager.getLobbyBySocket(hostSocket).getCode();

        lobbyManager.removeLobby(code);

        assertThat(lobbyManager.getLobbys().containsKey(code)).isEqualTo(false);
    }

    @Test
    public void removeLobbyWithMultipleTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        String code = lobbyManager.getLobbyBySocket(hostSocket).getCode();

        SocketIOClient host2Socket = Mockito.mock(SocketIOClient.class);
        UUID uuid2 = new UUID(0, 1);
        Mockito.when(host2Socket.getSessionId()).thenReturn(uuid);
        String host2Name = "host";
        Game testGame2 = new Game();
        lobbyManager.createLobby(host2Socket, host2Name, testGame2);
        String code2 = lobbyManager.getLobbyBySocket(host2Socket).getCode();

        lobbyManager.removeLobby(code);

        assertThat(lobbyManager.getLobbys().containsKey(code)).isEqualTo(false);
        assertThat(lobbyManager.getLobbys().containsKey(code2)).isEqualTo(true);
    }

    @Test
    public void removePlayerFromLobbyTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        String code = lobbyManager.getLobbyBySocket(hostSocket).getCode();

        SocketIOClient joinSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid2 = new UUID(0, 2);
        Mockito.when(joinSocket.getSessionId()).thenReturn(uuid2);
        String joinName = "joiner";
        lobbyManager.joinLobby(joinSocket, code, joinName);

        lobbyManager.removePlayerBySocket(joinSocket);

        assertThat(lobbyManager.getLobbyBySocket(joinSocket)).isNotEqualTo(lobbyManager.getLobbyBySocket(hostSocket));
    }

    @Test
    public void nameExistsInLobbyTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        Lobby testLobby = lobbyManager.getLobbyBySocket(hostSocket);

        boolean result = lobbyManager.nameExistsInLobby("host", testLobby);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void nameExistsInLobbyWithNotExistingNameTest() {
        SocketIOClient hostSocket = Mockito.mock(SocketIOClient.class);
        UUID uuid = new UUID(0, 1);
        Mockito.when(hostSocket.getSessionId()).thenReturn(uuid);
        String hostName = "host";
        Game testGame = new Game();
        lobbyManager.createLobby(hostSocket, hostName, testGame);
        Lobby testLobby = lobbyManager.getLobbyBySocket(hostSocket);

        boolean result = lobbyManager.nameExistsInLobby("joiner", testLobby);

        assertThat(result).isEqualTo(false);
    }
}
