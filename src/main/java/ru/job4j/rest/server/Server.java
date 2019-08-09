package ru.job4j.rest.server;

import ru.job4j.rest.sessions.Session;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Server {
    // список сессий
    private Map<String, Session> sessionList = new ConcurrentHashMap<>();
    // текущее время
    private long currTime;

    // конструктор
    public Server() {
        setCurrTime();
        sessionLifeCheck();
    }


    // получение списка сессий
    public Map<String, Session> getSessionList() {
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

    // удаление данных из списка
    public void kill(Map<String, ?> object, String name) {
        object.remove(name);
        System.gc();
    }

    // проверка сессий на активность
    private void sessionLifeCheck() {
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Проверка активности...");
                killSession();
            }
        }, 1000 * 30, 1000 * 30);
    }

    // текущее время обновлястя каждую секунду
    private void setCurrTime() {
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                currTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            }
        }, 1000, 1000);
    }

    // удаление сессии
    private void killSession() {
        if (sessionList != null) {
            for (String key : sessionList.keySet()) {
                long sessionActivityTime = currTime - sessionList.get(key).getLifeTime();
                System.out.println(key + ": -> " + sessionActivityTime + " sec");
                if (sessionActivityTime > 60 * 2) {
                    kill(sessionList, key);
                }
            }
            System.out.println("Колличество Сессий на сервере = " + sessionList.size());
        }
    }
}
