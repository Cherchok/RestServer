package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// класс настройки
class SettingsSelector extends JFrame {
    private JButton systemsBtn = new JButton("Системы SAP");
    private JButton propertyBtn = new JButton("прочие настройки");
    private JButton backBtn = new JButton("назад");
    private JButton showSettingsBtn = new JButton("показать параметры");
    private Setup setup;
    private Server server;

    // конструктор
    SettingsSelector(Server server, Setup setup) {
        super("Настройки");
        setSize(600,100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.server = server;
        this.setup = setup;
        buttonsActions();
        fillFrame();
    }

    // наполнение интрефейса
    private void fillFrame() {
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(1, 1, 2, 2));
        container.add(systemsBtn);
        container.add(propertyBtn);
        container.add(backBtn);
        container.add(showSettingsBtn);
    }

    // активация событий при нажитии на конпки
    private void buttonsActions() {
        actSystemsBtn();
        actPropertyBtn();
        actBackBtn();
        actShowSettingsBtn();
    }

    // событие для кнопки параметры ситем SAP
    private void actSystemsBtn() {
        systemsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SystemsAdd systemsAdd = new SystemsAdd(server, setup);
                systemsAdd.setVisible(true);
                dispose();
            }
        });
    }

    // событие для кнопки парметры
    private void actPropertyBtn() {
        propertyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertyAdd propertyAdd = new PropertyAdd(server, setup);
                propertyAdd.setVisible(true);
                dispose();
            }
        });
    }

    // событие для кнопки
    private void actBackBtn() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SapJavaSettings sapJavaSettings = new SapJavaSettings(server, setup);
                sapJavaSettings.setVisible(true);
                dispose();
            }
        });
    }

    // событие для кнопки
    private void actShowSettingsBtn() {
        showSettingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> settingsList = new ArrayList<>(setup.getPropertiesNames());
                StringBuilder sb = new StringBuilder();
                Collections.sort(settingsList);
                for (String key : settingsList) {
                    sb.append(key).append(" : ").append(setup.getProperty(key)).append("\n");
                }
                String message = sb.toString();
                JOptionPane.showMessageDialog(null, message, "Список параметров",
                        JOptionPane.PLAIN_MESSAGE);

            }
        });
    }

}
