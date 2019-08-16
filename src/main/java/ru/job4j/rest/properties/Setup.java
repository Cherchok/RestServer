package ru.job4j.rest.properties;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class Setup {
    private MyPropertiesHolder propertiesHolder;

    // очистка файла свойств
    public void clearProperties() {
        removeProperty("");
    }

    // удаление свойства
    @SuppressWarnings("WeakerAccess")
    public void removeProperty(String key) {
        boolean isRemoved = false;
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String name : getPropertiesNames()) {
            if (name.contains(key)) {
                propertiesHolder.getProperties().remove(name);
                isRemoved = true;
            }
        }
        if (!isRemoved) {
            System.out.println("Нет свойства с именем " + key);
        }
        try {
            propertiesHolder.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // удаление системы SAP
    public void removeSystem(String progName) {
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int number = systemNumber(progName);
        if (number != 0) {
            propertiesHolder.getProperties().remove(number + ".progName");
            propertiesHolder.getProperties().remove(number + ".ip");
            propertiesHolder.getProperties().remove(number + ".port");
            propertiesHolder.getProperties().remove(number + ".uri");
        } else System.out.println("Система с именем " + progName + " не существует.");
        try {
            propertiesHolder.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // определение номера выбранной системы
    private int systemNumber(String progName) {
        int number = 0;
        for (String name : getPropertiesNames()) {
            if (getProperties().get(name).contains(progName)) {
                for (int i = 0; i < name.length(); i++) {
                    if (name.charAt(i) == '.') {
                        number = Integer.parseInt(String.valueOf(name.substring(0, i)));
                    }
                }
            }
        }
        return number;
    }

    // получение записи по ключу
    public String getProperty(String key) {
        String value = "";
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (propertiesHolder != null) {
                value = propertiesHolder.getProperty(key);

                try {
                    propertiesHolder.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return value;
    }

    // список имен
    public Set<String> getPropertiesNames() {
        Set<String> namesList = new LinkedHashSet<>();
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (propertiesHolder != null) {
                for (Object name : propertiesHolder.getProperties().keySet()) {
                    namesList.add(name.toString());
                }


                try {
                    propertiesHolder.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        return namesList;
    }

    // список свойств
    public LinkedHashMap<String, String> getProperties() {
        LinkedHashMap<String, String> properties = new LinkedHashMap<>();
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (propertiesHolder != null) {
                for (Object name : propertiesHolder.getProperties().keySet()) {
                    properties.put(name.toString(), propertiesHolder.getProperties().getProperty(name.toString()));
                }

                try {
                    propertiesHolder.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        return properties;

    }

    // добавление свойства
    public void setProperty(String key, String value) {
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertiesHolder.getProperties().put("0." + key, value);

        try {
            propertiesHolder.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // присвоение следующего номера для системы
    private int getNextSystemNumber(@SuppressWarnings("SameParameterValue") int number) {
        for (String name : getPropertiesNames()) {
            for (int i = 0; i < name.length(); i++) {
                int tempNum;
                if (name.charAt(i) == '.') {
                    tempNum = Integer.parseInt(String.valueOf(name.substring(0, i)));
                    if (number <= tempNum) {
                        number += 1;
                    }
                }
            }
        }
        IDoperator idOp = new IDoperator();
        return idOp.getNumberFromString(number, getPropertiesNames());
    }

    // добавление новой системы SAP в свойства
    public void setSAPsystem(String progName, String ip, String port, String uri) {
        int number = getNextSystemNumber(1);
        boolean isExist = false;
        try {
            propertiesHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object name : propertiesHolder.getProperties().keySet()) {
            if (propertiesHolder.getProperties().getProperty(name.toString()).equals(progName)) {
                isExist = true;
            }
        }
        if (!isExist) {
            propertiesHolder.getProperties().put(number + ".progName", progName);
            propertiesHolder.getProperties().put(number + ".ip", ip);
            propertiesHolder.getProperties().put(number + ".port", port);
            propertiesHolder.getProperties().put(number + ".uri", uri);
        } else {
            String message = "Система с именем " + progName + " уже существует";
//            String message = "System " + progName + " already exist!!!";
            JOptionPane.showMessageDialog(null, message, "Settings List",
                    JOptionPane.PLAIN_MESSAGE);
        }


        try {
            propertiesHolder.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
