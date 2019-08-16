package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartServer extends JFrame {
    private JButton settingsBtn = new JButton("settings");
    private JButton startBtn = new JButton("start");
    private JButton exitBtn = new JButton("exit");
    private Server server;
    private Setup setup;

    public StartServer(Server server, Setup setup) {
        super("Server");
        this.server = server;
        this.setup = setup;
        this.setBounds(100, 100, 250, 100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonsActions();
        fillFrame();
    }

    private void fillFrame() {
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(1, 3, 2, 2));
        container.add(settingsBtn);
        container.add(startBtn);
        container.add(exitBtn);
    }

    private void buttonsActions() {
        actSettingsBtn();
        actStartBtn();
        actExitBtn();
    }

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

    private void actStartBtn() {
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO

            }
        });
    }

    private void actExitBtn() {
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
    }

}
