package ru.job4j.rest.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.util.Properties;

class MyPropertiesHolder {
    @SuppressWarnings("WeakerAccess")
    static int MODE_CREATE = 0;
    static int MODE_UPDATE = 1;
    private String filename;
    private Properties properties;

    MyPropertiesHolder(String filename, int mode) throws IOException {
        this.filename = filename;
        this.properties = new Properties();
        if (mode != MODE_CREATE) {
            FileInputStream inputStream = new FileInputStream(filename);
            properties.load(inputStream);
            inputStream.close();
        }
    }

    // возврат свойства по ключу
    String getProperty(String key) {
        return (String) properties.get(key);
    }

    // добавление или перезапись свойства
    @SuppressWarnings("unused")
    void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    // запись в хранилище свойств
    void commit() throws IOException {
//        FilePermission permission = new FilePermission(filename,"write");
        FileOutputStream outputStream = new FileOutputStream(filename);
        properties.store(outputStream, "");
        outputStream.close();
    }

    // список свойств
    Properties getProperties() {
        return properties;
    }


}
