package ru.job4j.rest.life;

import java.util.LinkedHashMap;

public interface LifeCycle {

    void kill(LinkedHashMap<String, ?> object, String name);

}
