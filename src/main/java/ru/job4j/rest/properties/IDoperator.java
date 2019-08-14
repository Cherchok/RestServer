package ru.job4j.rest.properties;

import java.util.Set;

public class IDoperator {


    // выборка номера
    public int getNumberFromString(int id, Set<String> keyList) {
        int freeNum = 0;
        for (String key : keyList) {
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
}
