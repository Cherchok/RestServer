package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// класс настройки SAP систем
class SystemsAdd extends JFrame {
    private JButton loadBtn = new JButton("добавить");
    private JButton deleteBtn = new JButton("удалить");
    private JButton closeBtn = new JButton("назад");
    private JTextField progNameText = new JTextField("", 10);
    private JTextField ipText = new JTextField("", 10);
    private JTextField portText = new JTextField("", 10);
    private JTextField uriText = new JTextField("", 10);
    private JLabel progLable = new JLabel("имя системы:");
    private JLabel ipLable = new JLabel("ip:");
    private JLabel portLable = new JLabel("port:");
    private JLabel uriLable = new JLabel("uri:");
    private Setup setup;
    private Server server;

    // конструктор
    SystemsAdd(Server server, Setup setup) {
        super("Настройка систем SAP");
        setSize(200, 150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.server = server;
        this.setup = setup;
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

    // активация событий при нажитии на конпки
    private void buttonsActions() {
        actLoadBtn();
        actDeleteBtn();
        actCloseButton();
    }

    // событие для кнопки добавления
    private void actLoadBtn() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isExist(progNameText.getText())) {
                    if (!progNameText.getText().equals("") && !ipText.getText().equals("") &&
                            !portText.getText().equals("") && !uriText.getText().equals("")) {
                        setup.setSAPsystem(progNameText.getText(), ipText.getText(), portText.getText(), uriText.getText());
                        String message = "Система " + progNameText.getText() + " добавлена";
                        JOptionPane.showMessageDialog(null, message, "Настройка систем SAP",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        String message = "Не все параметры указаны";
                        JOptionPane.showMessageDialog(null, message, "Настройка систем SAP",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    if (JOptionPane.showConfirmDialog(SystemsAdd.this,
                            "Хотите перезаписать систему " + progNameText.getText() + " ?", "Настройка систем SAP",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (!progNameText.getText().equals("") && !ipText.getText().equals("") &&
                                !portText.getText().equals("") && !uriText.getText().equals("")) {
                            setup.removeSystem(progNameText.getText());
                            setup.setSAPsystem(progNameText.getText(), ipText.getText(), portText.getText(), uriText.getText());
                            String message = "Система " + progNameText.getText() + " перезаписана";
                            JOptionPane.showMessageDialog(null, message, "Настройка систем SAP",
                                    JOptionPane.PLAIN_MESSAGE);
                        } else {
                            String message = "Не все параметры указаны";
                            JOptionPane.showMessageDialog(null, message, "Настройка систем SAP",
                                    JOptionPane.PLAIN_MESSAGE);
                        }

                    }
                }
            }
        });
    }

    // событие для кнопки удаления
    private void actDeleteBtn() {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isExist(progNameText.getText())) {
                    if (JOptionPane.showConfirmDialog(SystemsAdd.this,
                            "Хотите удалить систему " + progNameText.getText() + " ?", "Настройка систем SAP",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        setup.removeSystem(progNameText.getText());
                    }
                } else {
                    String message = "Системы с именем " + progNameText.getText() + " нет";
                    JOptionPane.showMessageDialog(null, message, "Настройка систем SAP",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }

    // событие для кнопки возврата
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

    // проверка на наличие системы в списке
    private boolean isExist(String enteredName) {
        for (String name : setup.getPropertiesNames()) {
            if (setup.getProperties().get(name).equals(enteredName)) {
                return true;
            }
        }
        return false;
    }

}
