package ru.job4j.rest.server;

import ru.job4j.rest.sessions.Session;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private LinkedHashMap<String, Session> sessionList = new LinkedHashMap<>();
    private final static AtomicInteger ID = new AtomicInteger(0);

    public Server() {
    }

    public LinkedHashMap<String, Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(LinkedHashMap<String, Session> sessionList) {
        this.sessionList = sessionList;
    }

    public Session getSession(String systemAddress, String login, String password, int id) {
        return sessionList.get(systemAddress + login + password + id);
    }

    public void setSession(String systemAddress, String login, String password, int id, Session session) {
        sessionList.put(systemAddress + login + password + "~" + id, session);
    }

    public int idSetter(int id) {
        for (String key : sessionList.keySet()) {
            for (int i = 0; i < key.length(); i++) {
                int idTemp = 0;
                if (key.charAt(i) == '~') {
                    idTemp = Integer.parseInt(key.substring(i+1));
                }
                if (id <= idTemp) {
                    id = idTemp + 1;
                }
            }
        }
        return id;
    }
}
