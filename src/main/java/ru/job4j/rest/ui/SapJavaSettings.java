package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// класс настройки параметров приложения
public class SapJavaSettings extends JFrame {
    private JButton settingsBtn = new JButton("Параметры");
    private JButton startBtn = new JButton("запуск");
    private JButton exitBtn = new JButton("выход");
    private Server server;
    private Setup setup;

    // конструктор для инициализированного сервера
    public SapJavaSettings(Server server, Setup setup) {
        super("Сервер");
        setSize(250, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.server = server;
        this.setup = setup;
        buttonsActions();
        fillFrame();
    }

    // конструктор без инициализации сервера
    public SapJavaSettings() {
        super("Сервер");
        setSize(250, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setup = new Setup();
        buttonsActions();
        fillFrame();
    }

    // наполнение интрефейса
    private void fillFrame() {
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(1, 3, 2, 2));
        container.add(settingsBtn);
        container.add(startBtn);
        container.add(exitBtn);
    }

    // активация событий при нажитии на конпки
    private void buttonsActions() {
        actSettingsBtn();
        actStartBtn();
        actExitBtn();
    }

    // событие для кнопки параметры
    private void actSettingsBtn() {
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsSelector selector = new SettingsSelector(server, setup);
                selector.setVisible(true);
                dispose();
            }
        });
    }

    // событие для кнопки запуска
    private void actStartBtn() {
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO

//                Client client = ClientBuilder.newClient();
//                WebTarget target = client.target("http://192.168.31.162:8080/rest/rest/wmap/start");
//                System.out.println(target.request(MediaType.APPLICATION_JSON).get(String.class));

                if (server == null) {
                    setServer();
                } else {
                    if (JOptionPane.showConfirmDialog(SapJavaSettings.this,
                            "Сервер уже запущен, хотите перезапустить?", "Сервер",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        setServer();
                    }
                }
            }
        });
    }

    // событие для кнопки выхода
    private void actExitBtn() {
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (JOptionPane.showConfirmDialog(SapJavaSettings.this,
                        "Хотите завершить работу сервера ?", "Сервер",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // возвращает сервер
    public Server getServer() {
        return server;
    }

    // инициализация сервера
    private void setServer() {
        this.server = new Server();
    }

}
