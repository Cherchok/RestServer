package ru.job4j.rest.controller;


import ru.job4j.rest.sapData.DataSet;
import ru.job4j.rest.server.Server;
import ru.job4j.rest.server.connection.ModulesCollector;
import ru.job4j.rest.sessions.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/wmap")
public class Controller {
    private final static AtomicInteger ID = new AtomicInteger(0);
    private final static Map<Integer, DataSet> RESP = new ConcurrentHashMap<>();
    private static Server server;

    static {
        server = new Server();
    }

    // метод, который при запуске клиентского приложения, определяет доступные системы и их адреса
    @GET
    @Path("/connection")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get() {
        ModulesCollector modulesCollector = new ModulesCollector();
        return modulesCollector.getModules();
    }

    // метод для запуска сервера и авторизации
    @GET
    @Path("{systemAddress: .*}/{login}/{password}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password) {
        Session session;
        int id = 0;
        if (!server.getSessionList().containsKey(systemAddress + login + password)) {
            session = new Session(systemAddress, login, password);
            id = server.idSetter(id);
        } else {
            session = server.getSessionList().get(systemAddress + login + password);
        }
        session.setDataSet(" ", " ", " ", " ", " ", " ", " ");
        server.setSession(systemAddress, login, password, id, session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(" ", " ",
                " ", " ", " ", " ", " ");
    }

    // методы к которым обращается клиент через get запросы с параметрами
    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
        }
        session.setDataSet(table, "100", "R", "", "", "", "");

        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, "5",
                "R", "", "", "", "");
    }

    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
        }
        session.setDataSet(table, fieldsQuan, "R", "", "", "", "");
        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, fieldsQuan,
                "R", "", "", "", "");
    }

    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
        }
        session.setDataSet(table, fieldsQuan, language, "", "", "", "");
        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, fieldsQuan,
                language, "", "", "", "");
    }

    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
        }
        session.setDataSet(table, fieldsQuan, language, where, "", "", "");
        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, fieldsQuan,
                language, where, "", "", "");
    }

    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
        }
        session.setDataSet(table, fieldsQuan, language, where, order, "", "");
        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, fieldsQuan,
                language, where, order, "", "");
    }

    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}/{group}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order, @PathParam("group") String group) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
        }
        session.setDataSet(table, fieldsQuan, language, where, order, group, "");
        server.setSession(systemAddress, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(systemAddress + login + password + "~" + id).getDataSet(table, fieldsQuan,
                language, where, order, group, "");
    }



    @GET
    @Path("{systemAddress: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}/{group}/{fieldNames}")
    @Produces("applications/json;charset=utf-8")
    public DataSet[] get(@PathParam("systemAddress") String systemAddress, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order, @PathParam("group") String group,
                         @PathParam("fieldNames") String fieldNames) {
        Session session;
        if (!server.getSessionList().containsKey(systemAddress + login + password + id)) {
            session = new Session(systemAddress, login, password);
            id = String.valueOf(server.idSetter(Integer.parseInt(id)));
        } else {
            session = server.getSessionList().get(systemAddress + login + password + id);
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
}
