package ru.job4j.rest.controller;


import ru.job4j.rest.sapData.DataSet;
import ru.job4j.rest.server.Server;
import ru.job4j.rest.server.connection.SystemsCollector;
import ru.job4j.rest.sessions.Session;
import ru.job4j.rest.ui.SapJavaSettings;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Path("/wmap")
public class Controller extends HttpServlet {
    private final static AtomicInteger ID = new AtomicInteger(0);
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final static Map<Integer, DataSet> RESP = new ConcurrentHashMap<>();
    private static Server server;
    private static boolean isInitial;
    private static Timer startServerTimer = new Timer();
    private static SapJavaSettings[] starter = {new SapJavaSettings()};
    private String userIP;

    // запуск сервера через Tomcat
    @GET
    @Path("")
    @Produces("application/json;charset=utf-8")
    public String start() {
        startServer();
        return "Server activated";
    }

    // запуск сервера
    private static void startServer() {

        starter[0].setVisible(true);
        if (server == null) {
            startServerTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    server = starter[0].getServer();
                    if (server != null) {
                        starter[0] = new SapJavaSettings(server, server.getSetup());
                        isInitial = true;
                        startServerTimer.cancel();
                    }
                }
            }, 0, 10);
        } else {
            isInitial = true;
        }
    }

    // метод, который при запуске клиентского приложения, определяет доступные системы и их адреса
    @GET
    @Path("/connection")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get() {
        SystemsCollector systemsCollector;
        if (isInitial) {
            systemsCollector = new SystemsCollector(server.getSetup());
            return systemsCollector.getSystems();
        }
        return null;
    }

    // метод для авторизации
    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{language}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("language") String language,
                         @Context HttpServletRequest request) {
        Session session;
        int id = 0;
        id = server.idSetter(id);
        // получаем адресс клиента (на будущее)
        userIP = request.getRemoteAddr();
        session = new Session(systemAddress, login, password, id);
        session.setDataSet(" ", " ", language, " ", " ", " ", " ");
        return session.getDataSet(" ", " ", language, " ", " ", " ", " ");
    }

    // методы к которым обращается клиент через get запросы с параметрами
    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}/{group}/{fieldNames}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order, @PathParam("group") String group,
                         @PathParam("fieldNames") String fieldNames) {

        String tempParam = table;
        table = tempParam.replaceAll("~~~", "");
        tempParam = fieldsQuan;
        fieldsQuan = tempParam.replaceAll("~~~", "");
        tempParam = where;
        where = tempParam.replaceAll("~~~", "");
        tempParam = order;
        order = tempParam.replaceAll("~~~", "");
        tempParam = group;
        group = tempParam.replaceAll("~~~", "");
        tempParam = fieldNames;
        fieldNames = tempParam.replaceAll("~~~", "");
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + "~" + id)) {
            int ids;
            if (isSameUser(systemAddress + login + password)) {
                ids = server.idSetter(Integer.parseInt(id));
            } else ids = 0;
            session = new Session(systemAddress, login, password, ids);
            id = String.valueOf(ids);
        } else {
            session = server.getSessionList().get(systemAddress + login + password + "~" + id);
        }
        session.setDataSet(table, fieldsQuan, language, where, order, group, fieldNames);
        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, fieldsQuan,
                language, where, order, group, fieldNames);
    }

    // методы для создания, добавления, редактирования и удаления результатов
    @GET
    @Path("/create")
    @Produces("applications/json")
    public DataSet create(DataSet mapa) {
        RESP.put(ID.incrementAndGet(), mapa);
        return mapa;
    }

    @POST
    @Path("/multiCreate")
    @Produces("applications/json")
    public DataSet[] create(DataSet[] maps) {
        for (DataSet mapa : maps) {
            this.create(mapa);
        }
        return maps;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        RESP.remove(id);
        return Response.status(200).build();
    }

    // метод удаления сессии при выходе из приложения на устройстве
    @DELETE
    @Path("{systemAddress: .*}/{login}/{password}/{id}")
    @Produces("applications/json")
    public Response delete(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                           @PathParam("password") String password, @PathParam("id") String id) {
        String key = systemAddress + login + password + "~" + id;
        server.kill(server.getSessionList(), key);
        return Response.status(200).build();
    }

    // метод удаления dataSet из памяти при нажатии return на просмотре таблиц на устройстве
    @DELETE
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}/{group}/{fieldNames}")
    @Produces("applications/json")
    public Response delete(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                           @PathParam("password") String password, @PathParam("id") String id,
                           @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                           @PathParam("language") String language, @PathParam("where") String where,
                           @PathParam("order") String order, @PathParam("group") String group,
                           @PathParam("fieldNames") String fieldNames) {
        String tempParam = table;
        table = tempParam.replaceAll("~~~", "");
        tempParam = fieldsQuan;
        fieldsQuan = tempParam.replaceAll("~~~", "");
        tempParam = where;
        where = tempParam.replaceAll("~~~", "");
        tempParam = order;
        order = tempParam.replaceAll("~~~", "");
        tempParam = group;
        group = tempParam.replaceAll("~~~", "");
        tempParam = fieldNames;
        fieldNames = tempParam.replaceAll("~~~", "");
        String keyServer = systemAddress + login + password + "~" + id;
        String keySession = table + fieldsQuan + language + where + order + group + fieldNames + "~" + id;
        server.getSessionList().get(keyServer).kill(server.getSessionList().get(keyServer).getDataList(), keySession);
        return Response.status(200).build();
    }

    // проверка на пользователя
    private boolean isSameUser(String userKey) {
        for (String key : server.getSessionList().keySet()) {
            if (key.contains(userKey)) {
                return true;
            }
        }
        return false;
    }
}
