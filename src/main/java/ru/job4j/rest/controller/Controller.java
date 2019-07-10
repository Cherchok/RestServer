package ru.job4j.rest.controller;


import ru.job4j.rest.sapData.DataSet;
import ru.job4j.rest.server.Server;
import ru.job4j.rest.server.connection.ResourceManager;
import ru.job4j.rest.sessions.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/wmap")
public class Controller {
    private final static AtomicInteger ID = new AtomicInteger(0);
    private final static Map<Integer, DataSet> RESP = new ConcurrentHashMap<>();
    private static Server server;

    // метод, который при запуске клиентского приложения, определяет доступные системы и их адреса
    @GET
    @Path("/connection")
    @Produces("applications/json")
    public DataSet[] get() {
        StringBuilder addressBuilder = new StringBuilder();
        String address;
        String name = "";
        LinkedList<String> addressList = new LinkedList<>();
        LinkedHashMap<String, LinkedList<String>> systAddresses = new LinkedHashMap<>();
        LinkedList<String> setupKeyList = new LinkedList<>();
        ResourceManager resourceManager = new ResourceManager();
        int id = 1;

        for (int i = 0; i < resourceManager.getRb().keySet().size(); i++) {
            for (String key : resourceManager.getRb().keySet()) {
                int num = Integer.parseInt(String.valueOf(key.charAt(0)));
                if (num == id) {
                    setupKeyList.add(key);
                }
            }
            id++;
        }

        id = 1;
        int lastIter = setupKeyList.size() - 1;
        Collections.sort(setupKeyList);
        for (String key : setupKeyList) {
            int num = Integer.parseInt(String.valueOf(key.charAt(0)));
            if (num == id) {
                if (!key.contains("progName")) {
                    addressBuilder.append(resourceManager.getRb().getString(key));
                    if (lastIter == 0) {
                        address = addressBuilder.toString();
                        addressList.add(address);
                        systAddresses.put(name, addressList);
                    }
                } else {
                    name = resourceManager.getValue(key);
                    if (lastIter == 0) {
                        address = addressBuilder.toString();
                        addressList.add(address);
                        systAddresses.put(name, addressList);
                    }
                }

            } else {
                address = addressBuilder.toString();
                addressList.add(address);
                systAddresses.put(name, addressList);
                addressBuilder = new StringBuilder();
                addressList = new LinkedList<>();
                id++;
                if (!key.contains("progName")) {
                    addressBuilder.append(resourceManager.getRb().getString(key));
                } else {
                    name = resourceManager.getValue(key);
                }
            }
            lastIter--;
        }

        DataSet[] listOfSystems = new DataSet[systAddresses.size()];
        id = 0;

        for (String systName : systAddresses.keySet()) {
            DataSet system = new DataSet();
            system.setName(systName);
            system.setValues(systAddresses.get(systName));
            listOfSystems[id] = system;
            id++;
        }
        return listOfSystems;
    }

    // метод для запуска сервера и авторизации
    @GET
    @Path("{clientUrl: .*}/{login}/{password}")
    @Produces("applications/json")
    public DataSet[] get(@PathParam("clientUrl") String clientUrl, @PathParam("login") String login,
                         @PathParam("password") String password) {
        server = new Server();
        Session session;
        if (!server.getSessionList().containsKey(clientUrl + login + password)) {
            session = new Session(login, password);
        } else session = server.getSessionList().get(clientUrl + login + password);
        session.setDataSet(" ", " ", " ", " ", " ", " ", " ");
        server.setSession(clientUrl, login, password, 0, session);
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
            session = new Session(login, password);
        } else session = server.getSessionList().get(clientUrl + login + password + id);
        session.setDataSet(table, "100", "R", "", "", "", "");
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
            session = new Session(login, password);
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
            session = new Session(login, password);
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
            session = new Session(login, password);
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
            session = new Session(login, password);
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
            session = new Session(login, password);
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
            session = new Session(login, password);
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
