package ru.job4j.rest.server.connection;

import ru.job4j.rest.sapData.DataSet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ModulesCollector {

    public ModulesCollector() {
    }

    public DataSet[] getModules() {
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
