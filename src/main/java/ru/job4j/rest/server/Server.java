package ru.job4j.rest.server;

import ru.job4j.rest.properties.IDoperator;
import ru.job4j.rest.properties.Setup;
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
    // время жизни сессии
    private int lifeTime;
    // настрройки  сервера
    private Setup setup = new Setup();

    // конструктор
    public Server() {
        for (String name : setup.getPropertiesNames()) {
            System.out.println(name + ": " + setup.getProperties().get(name));
        }
        setLifeTime();
        setCurrTime();
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
        IDoperator idOperator = new IDoperator();
        return idOperator.getNumberFromString(id, sessionList.keySet());
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
                System.out.println("life check: " + (lifeTime / 2));
                killSession();
            }
        }, 1000 * 60 * lifeTime / 2, 1000 * 60 * lifeTime / 2);
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
                System.out.println("lifeTime sessionKill: " + lifeTime);
                if (sessionActivityTime > 60 * lifeTime) {
                    kill(sessionList, key);
                }
            }
            System.out.println("Колличество Сессий на сервере = " + sessionList.size());
        }
    }

    // список нстроек
    public Setup getSetup() {
        return setup;
    }

    // время бездействия сессии, по истечении которго удалется из списка
    public void setLifeTime() {
        this.lifeTime = Integer.parseInt(setup.getProperty("0.lifeTime"));
        sessionLifeCheck();
    }
}
