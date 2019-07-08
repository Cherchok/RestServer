package ru.job4j.rest.controller;


import ru.job4j.rest.sapData.DataSet;
import ru.job4j.rest.server.Server;
import ru.job4j.rest.sessions.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/wmap")
public class Controller {
    private final static AtomicInteger ID = new AtomicInteger(0);
    private final static Map<Integer, DataSet> RESP = new ConcurrentHashMap<>();
    private static Server server;

    // метод для запуска сервера и авторизации
    @GET
    @Path("{clientUrl: .*}/{login}/{password}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password) {
        server = new Server();
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password);
        session.setDataSet(" ", " ", " ", " ", " ", " ", " ");
        server.setSession(clientUrl, login, password,0, session);
        return server.getSessionList().get(clientUrl + login + password + 0).getDataSet(" ", " ",
                " ", " ", " ", " ", " ");
    }

    // методы к которым обращается клиент через get запросы с параметрами
    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, "5", "R", "", "", "", "");
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, "5",
                "R", "", "", "", "");
    }

    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, fieldsQuan, "R", "", "", "", "");
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, fieldsQuan,
                "R", "", "", "", "");
    }

    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, fieldsQuan, language, "", "", "", "");
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, fieldsQuan,
                language, "", "", "", "");
    }

    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, fieldsQuan, language, where, "", "", "");
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, fieldsQuan,
                language, where, "", "", "");
    }

    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, fieldsQuan, language, where, order, "", "");
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, fieldsQuan,
                language, where, order, "", "");
    }

    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}/{group}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order, @PathParam("group") String group) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, fieldsQuan, language, where, order, group, "");
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, fieldsQuan,
                language, where, order, group, "");
    }

    @GET
    @Path("{clientUrl: .*}/{login}/{password}/{id}/{table}/{fieldsQuan}/{language}/{where}/{order}/{group}/{fieldNames}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password, @PathParam("id") String id,
                         @PathParam("table") String table, @PathParam("fieldsQuan") String fieldsQuan,
                         @PathParam("language") String language, @PathParam("where") String where,
                         @PathParam("order") String order, @PathParam("group") String group,
                         @PathParam("fieldNames") String fieldNames) {
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password + id)) {
            session = new Session(login,password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, fieldsQuan, language, where, order, group, fieldNames);
        server.setSession(clientUrl, login, password, Integer.parseInt(id), session);
        return server.getSessionList().get(clientUrl + login + password + id).getDataSet(table, fieldsQuan,
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
