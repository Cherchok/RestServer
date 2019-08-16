package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PropertyAdd extends JFrame {
    private JButton loadBtn = new JButton("load");
    private JButton deleteBtn = new JButton("delete");
    private JButton closeBtn = new JButton("back");
    private JTextField keyText = new JTextField("", 10);
    private JTextField valueText = new JTextField("", 10);
    private JLabel keyLable = new JLabel("name:");
    private JLabel valueLable = new JLabel("value:");
    private Setup setup;
    private Server server;

    PropertyAdd(Server server, Setup setup) {
        super("Property");
        this.server = server;
        this.setup = setup;
        this.setBounds(100, 100, 200, 100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonsActions();
        fillFrame();
    }

    // заполнение окна атрибутами
    private void fillFrame() {
        // своего рода таблица панелей, состаящая из 7 строк и 2 столбцов
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 2, 2, 2));

        container.add(keyLable);
        container.add(keyText);

        container.add(valueLable);
        container.add(valueText);

        container.add(loadBtn);
        container.add(deleteBtn);
        container.add(closeBtn);
    }

    //
    private void buttonsActions() {
        actCloseButton();
        actDeleteBtn();
        actLoadBtn();
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

    private void actDeleteBtn() {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setup.removeProperty(keyText.getText());
            }
        });
    }

    private void actLoadBtn() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setup.setProperty(keyText.getText(), valueText.getText());
                server.setLifeTime();
            }
        });
    }
}
