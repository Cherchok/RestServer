package ru.job4j.rest.sessions;

import ru.job4j.rest.sapData.DataSet;
import ru.job4j.rest.sapData.SapMap;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Session {
    private LinkedHashMap<String, DataSet[]> dataList = new LinkedHashMap<>();
    private String login;
    private String password;

    public Session() {
    }

    public Session(String login, String password){
        this.login = login;
        this.password = password;
    }

    public LinkedHashMap<String, DataSet[]> getDataList() {
        return dataList;
    }

    public void setDataList(LinkedHashMap<String, DataSet[]> dataList) {
        this.dataList = dataList;
    }

    public DataSet[] getDataSet(String table, String fieldsQuan, String language, String where, String order,
                                String group, String fieldNames) {

        return dataList.get(table + fieldsQuan + language + where + order + group + fieldNames);
    }

    public void setDataSet(String table, String fieldsQuan, String language, String where, String order,
                           String group, String fieldNames) {
        LinkedHashMap<String, LinkedList<String>> lm;
        LinkedList<String> columnLeng;
        LinkedList<String> fieldName;
        LinkedList<String> dataType;
        LinkedList<String> repText;
        LinkedList<String> domName;
        LinkedList<String> outputLen;
        LinkedList<String> decimals;

        if (!dataList.containsKey(table + fieldsQuan + language + where + order + group + fieldNames)) {
            SapMap sm = new SapMap(table, fieldsQuan, language, where, order, group, fieldNames);
            sm.dataFill(login,password);
            lm = sm.getDataMap();
            columnLeng = sm.getColumnLeng();
            fieldName = sm.getFieldName();
            dataType = sm.getDataType();
            repText = sm.getRepText();
            domName = sm.getDomName();
            outputLen = sm.getOutputLen();
            decimals = sm.getDecimals();
            DataSet[] maps = new DataSet[lm.keySet().size() + 7];
            int id = 0;
            for (String name : lm.keySet()) {
                maps[id] = new DataSet(name, lm.get(name));
                id++;
            }
            maps[id] = new DataSet("columnLeng", columnLeng);
            id++;
            maps[id] = new DataSet("fieldName", fieldName);
            id++;
            maps[id] = new DataSet("dataType", dataType);
            id++;
            maps[id] = new DataSet("repText", repText);
            id++;
            maps[id] = new DataSet("domName", domName);
            id++;
            maps[id] = new DataSet("outputLen", outputLen);
            id++;
            maps[id] = new DataSet("decimals", decimals);
            dataList.put(table + fieldsQuan + language + where + order + group + fieldNames, maps);
        }
    }
}