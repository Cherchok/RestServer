package ru.job4j.rest.server.connection;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.sapData.DataSet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SystemsCollector {
    private StringBuilder addressBuilder = new StringBuilder();
    private String name = "";
    private LinkedList<String> addressList = new LinkedList<>();
    private LinkedHashMap<String, LinkedList<String>> systAddresses = new LinkedHashMap<>();
    private LinkedList<String> setupKeyList = new LinkedList<>();
    private int id;
    private Setup setup;

    // конструктор поумолчанию
    public SystemsCollector(Setup setup) {
        this.setup = setup;
    }

    // номер позиции в setup
    private int getNumber(String key) {
        int number = 0;
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == '.') {
                number = Integer.parseInt(String.valueOf(key.substring(0, i)));
            }
        }
        return number;
    }

    // составляем список ключей доступных систем
    private void setSetupKeyList() {
        // все списки систем начинаются с 1 , с 0 начинаются другие параметры
        id = 1;
        for (int i = 0; i < setup.getPropertiesNames().size(); i++) {
            for (String key : setup.getPropertiesNames()) {
                int num = getNumber(key);
                if (num == id) {
                    setupKeyList.add(key);
                }
            }
            id++;
        }
    }

    // заполняем список систем с ключами
    private void setSystAddresses() {
        setSetupKeyList();
        id = 1;
        int lastIter = setupKeyList.size() - 1;
        Collections.sort(setupKeyList);
        for (String key : setupKeyList) {

            int num = Integer.parseInt(String.valueOf(key.charAt(0)));
            String address;
            if (num == id) {
                if (!key.contains("progName")) {
                    addressBuilder.append(setup.getProperty(key));
                    if (lastIter == 0) {
                        address = addressBuilder.toString();
                        addressList.add(address);
                        systAddresses.put(name, addressList);
                    }
                } else {
                    name = setup.getProperty(key);
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
                    addressBuilder.append(setup.getProperty(key));
                } else {
                    name = setup.getProperty(key);
                }
            }
            lastIter--;
        }
    }

    // заполняем системы в класс DataSet для передачи Rest клиенту
    public DataSet[] getSystems() {
        setSystAddresses();
        DataSet[] modules = new DataSet[systAddresses.size()];
        id = 0;

        for (String systName : systAddresses.keySet()) {

            DataSet system = new DataSet();
            system.setName(systName);
            system.setValues(systAddresses.get(systName));
            modules[id] = system;
            id++;

        }
        return modules;
    }
}
