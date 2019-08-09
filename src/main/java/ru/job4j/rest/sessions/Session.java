package ru.job4j.rest.sessions;

import ru.job4j.rest.life.LifeCycle;
import ru.job4j.rest.sapData.DataSet;
import ru.job4j.rest.sapData.SapMap;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Session implements LifeCycle {
    //список данных полученных от SAP
    private LinkedHashMap<String, DataSet[]> dataList = new LinkedHashMap<>();
    // логин пользователя
    private String login;
    // пароль пользователя
    private String password;
    // адресс системы SAP
    private String systemAddress;
    // id клиента
    private int id;
    // время создания сессии или обращения в ней
    private long lifeTime;

    // конструктор поумолчанию

    public Session() {
        setTimeActivityToSeconds();
    }

    // конструктор с параметрами
    public Session(String systemAddress, String login, String password, int id) {
        setTimeActivityToSeconds();
        this.login = login;
        this.password = password;
        this.systemAddress = systemAddress;
        this.id = id;
    }

    // получение списка данных SAP
    public LinkedHashMap<String, DataSet[]> getDataList() {
        setTimeActivityToSeconds();
        return dataList;
    }

    // объявление нового списка данных SAP
    public void setDataList(LinkedHashMap<String, DataSet[]> dataList) {
        setTimeActivityToSeconds();
        this.dataList = dataList;
    }

    // получение данных SAP из списка
    public DataSet[] getDataSet(String table, String fieldsQuan, String language, String where, String order,
                                String group, String fieldNames) {
        setTimeActivityToSeconds();
        return dataList.get(table + fieldsQuan + language + where + order + group + fieldNames + "~" + id);
    }

    // занесение новых данных SAP в список
    public void setDataSet(String table, String fieldsQuan, String language, String where, String order,
                           String group, String fieldNames) {
        setTimeActivityToSeconds();
        LinkedHashMap<String, LinkedList<String>> lm;
        LinkedList<String> columnLeng;
        LinkedList<String> fieldName;
        LinkedList<String> dataType;
        LinkedList<String> repText;
        LinkedList<String> domName;
        LinkedList<String> outputLen;
        LinkedList<String> decimals;
        LinkedList<String> clientNumber = new LinkedList<>();

        if (!dataList.containsKey(table + fieldsQuan + language + where + order + group + fieldNames + "~" + id)) {
            SapMap sm = new SapMap(table, fieldsQuan, language, where, order, group, fieldNames);
            sm.dataFill(systemAddress, login, password);
            lm = sm.getDataMap();
            for (String name : lm.keySet()) {
                if (name.equals("code")) {
                    if (lm.get(name).get(0).equals("QR")) {
                        DataSet[] maps = new DataSet[lm.keySet().size()];
                        maps[0] = new DataSet(name, lm.get(name));
                        dataList.put(table + fieldsQuan + language + where + order + group + fieldNames + "~" + id, maps);
                        dataList.get(table + fieldsQuan + language + where + order + group + fieldNames + "~" + id);
                        return;
                    }
                }
            }
            columnLeng = sm.getColumnLeng();
            fieldName = sm.getFieldName();
            dataType = sm.getDataType();
            repText = sm.getRepText();
            domName = sm.getDomName();
            outputLen = sm.getOutputLen();
            decimals = sm.getDecimals();
            clientNumber.add(String.valueOf(id));
            DataSet[] maps = new DataSet[lm.keySet().size() + 8];
            int i = 0;
            for (String name : lm.keySet()) {
                maps[i] = new DataSet(name, lm.get(name));
                i++;
            }
            maps[i] = new DataSet("columnLeng", columnLeng);
            i++;
            maps[i] = new DataSet("fieldName", fieldName);
            i++;
            maps[i] = new DataSet("dataType", dataType);
            i++;
            maps[i] = new DataSet("repText", repText);
            i++;
            maps[i] = new DataSet("domName", domName);
            i++;
            maps[i] = new DataSet("outputLen", outputLen);
            i++;
            maps[i] = new DataSet("decimals", decimals);
            i++;
            maps[i] = new DataSet("clientNumber", clientNumber);
            dataList.put(table + fieldsQuan + language + where + order + group + fieldNames + "~" + id, maps);
        }
    }

    // удаление данных SAP из списка
    @Override
    public void kill(LinkedHashMap<String, ?> object, String name) {
        object.remove(name);
        System.gc();
    }

    // получение времени последней активности сессии
    public long getLifeTime() {
        return lifeTime;
    }

    // установка времени последней активноси сессии
    private void setTimeActivityToSeconds() {
        long milliseconds = System.currentTimeMillis();
        lifeTime = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
    }

}
