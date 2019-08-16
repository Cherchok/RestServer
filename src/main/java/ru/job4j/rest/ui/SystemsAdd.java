package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SystemsAdd extends JFrame {
    private JButton loadBtn = new JButton("load");
    private JButton deleteBtn = new JButton("delete");
    private JButton closeBtn = new JButton("back");
    private JTextField progNameText = new JTextField("", 10);
    private JTextField ipText = new JTextField("", 10);
    private JTextField portText = new JTextField("", 10);
    private JTextField uriText = new JTextField("", 10);
    private JLabel progLable = new JLabel("name:");
    private JLabel ipLable = new JLabel("ip:");
    private JLabel portLable = new JLabel("port:");
    private JLabel uriLable = new JLabel("uri:");
    private Setup setup;
    private Server server;


    SystemsAdd(Server server, Setup setup) {
        super("SAP Systems");
        this.server = server;
        this.setup = setup;
        this.setBounds(100, 100, 200, 150);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonsActions();
        fillFrame();

    }


    // заполнение окна атрибутами
    private void fillFrame() {
        // своего рода таблица панелей, состаящая из 7 строк и 2 столбцов
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6, 2, 2, 2));

        // наполнение 1 строки 1,2 столбцов
        container.add(progLable);
        container.add(progNameText);

        // наполнение 2 строки 1,2 столбцов
        container.add(ipLable);
        container.add(ipText);

        // наполнение 3 строки 1,2 столбцов
        container.add(portLable);
        container.add(portText);

        // наполнение 4 строки 1,2 столбцов
        container.add(uriLable);
        container.add(uriText);

        // наполнение 5 строки 1,2 столбцов
        container.add(loadBtn);
        container.add(deleteBtn);

        // наполнение 6 строки 1 столбца
        container.add(closeBtn);
    }

    //
    private void buttonsActions() {
        actLoadBtn();
        actDeleteBtn();
        actCloseButton();
    }

    private void actLoadBtn() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setup.setSAPsystem(progNameText.getText(), ipText.getText(), portText.getText(), uriText.getText());
            }
        });
    }

    private void actDeleteBtn() {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setup.removeSystem(progNameText.getText());
            }
        });
    }

    private void actCloseButton() {
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsSelector selector = new SettingsSelector(server, setup);
                selector.setVisible(true);
                dispose();
            }
        });
    }

}
