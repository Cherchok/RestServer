package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

class SettingsSelector extends JFrame {
    private JButton systemsBtn = new JButton("SAP systems");
    private JButton propertyBtn = new JButton("property");
    private JButton backBtn = new JButton("back");
    private JButton showSettingsBtn = new JButton("show");
    private Setup setup;
    private Server server;


    SettingsSelector(Server server, Setup setup) {
        super("Settings");
        this.server = server;
        this.setup = setup;
        this.setBounds(100, 100, 400, 100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonsActions();
        fillFrame();

    }

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
                JOptionPane.showMessageDialog(null, message, "Settings List",
                        JOptionPane.PLAIN_MESSAGE);

            }
        });
    }

    private void fillFrame() {
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(1, 1, 2, 2));
        container.add(systemsBtn);
        container.add(propertyBtn);
        container.add(backBtn);
        container.add(showSettingsBtn);
    }

    private void buttonsActions() {
        actSystemsBtn();
        actPropertyBtn();
        actBackBtn();
        actShowSettingsBtn();
    }

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

    private void actBackBtn() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartServer startServer = new StartServer(server, setup);
                startServer.setVisible(true);
                dispose();
            }
        });
    }


}
