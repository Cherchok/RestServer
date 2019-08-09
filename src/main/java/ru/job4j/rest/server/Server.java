package ru.job4j.rest.server;

import ru.job4j.rest.life.LifeCycle;
import ru.job4j.rest.sessions.Session;

import java.util.LinkedHashMap;

@SuppressWarnings("unused")
public class Server implements LifeCycle {
    // список сессий
    private LinkedHashMap<String, Session> sessionList = new LinkedHashMap<>();

    public Server() {
    }

    // получение списка сессий
    public LinkedHashMap<String, Session> getSessionList() {
        return sessionList;
    }

    // объявление саиска сессий
    public void setSessionList(LinkedHashMap<String, Session> sessionList) {
        this.sessionList = sessionList;
    }

    // получение сессии из списка
    public Session getSession(String systemAddress, String login, String password, int id) {
        return sessionList.get(systemAddress + login + password + id);
    }

    // занесение списка в сессию
    public void setSession(String systemAddress, String login, String password, int id, Session session) {
        sessionList.put(systemAddress + login + password + "~" + id, session);
    }

    // присвоение номера клиенту
    public int idSetter(int id) {
        int freeNum = 0;
        for (String key : sessionList.keySet()) {
            for (int i = 0; i < key.length(); i++) {
                int idTemp = 0;
                if (key.charAt(i) == '~') {
                    idTemp = Integer.parseInt(key.substring(i + 1));
                    if (freeNum != 0 || idTemp > i) {
                        freeNum = i;
                    }
                }
                if (id <= idTemp) {
                    if (freeNum != 0) {
                        id = freeNum;

                    } else id = idTemp + 1;

                }
            }
        }
        return id;
    }

    // завершение сессии
    @Override
    public void kill(LinkedHashMap<String, ?> object, String name) {
        object.remove(name);
        System.gc();
    }
}
