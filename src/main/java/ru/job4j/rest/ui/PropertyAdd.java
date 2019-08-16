package ru.job4j.rest.ui;

import ru.job4j.rest.properties.Setup;
import ru.job4j.rest.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// класс настройки прочих параметров
class PropertyAdd extends JFrame {
    private JButton loadBtn = new JButton("добавить");
    private JButton deleteBtn = new JButton("удалить");
    private JButton closeBtn = new JButton("назад");
    private JTextField keyText = new JTextField("", 10);
    private JTextField valueText = new JTextField("", 10);
    private JLabel keyLable = new JLabel("ключ:");
    private JLabel valueLable = new JLabel("значение:");
    private Setup setup;
    private Server server;

    // конструктор
    PropertyAdd(Server server, Setup setup) {
        super("Прочие настройки параметров");
        setSize(200, 100);
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
        container.setLayout(new GridLayout(4, 2, 2, 2));

        container.add(keyLable);
        container.add(keyText);

        container.add(valueLable);
        container.add(valueText);

        container.add(loadBtn);
        container.add(deleteBtn);
        container.add(closeBtn);
    }

    // активация событий при нажитии на конпки
    private void buttonsActions() {
        actCloseButton();
        actDeleteBtn();
        actLoadBtn();
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

    // событие для кнопки удаления
    private void actDeleteBtn() {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isExist(keyText.getText())) {
                    if (JOptionPane.showConfirmDialog(PropertyAdd.this,
                            "Хотите удалить параметр " + keyText.getText() + "?", "Прочие параметры",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        setup.removeProperty(keyText.getText());
                    }
                }

            }
        });
    }

    // событие для кнопки добавления
    private void actLoadBtn() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isExist(keyText.getText())) {

                    if (!keyText.getText().equals("") && !valueText.getText().equals("")) {
                        setup.setProperty(keyText.getText(), valueText.getText());
                        if (keyText.getText().equals("lifeTime")) {
                            server.setLifeTime();
                        }
                        String message = "Добавлен новый параметр " + keyText.getText();
                        JOptionPane.showMessageDialog(null, message, "Прочие настройки параметров",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        String message = "Заполнены не все поля параметра";
                        JOptionPane.showMessageDialog(null, message, "Прочие настройки параметров",
                                JOptionPane.PLAIN_MESSAGE);
                    }

                } else {
                    if (!keyText.getText().equals("") && !valueText.getText().equals("")) {
                        if (JOptionPane.showConfirmDialog(PropertyAdd.this,
                                "Параметр с ключом " + keyText.getText() + " уже существует, хотите перезаписать ?",
                                "Прочие параметры",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            setup.setProperty(keyText.getText(), valueText.getText());
                            if (keyText.getText().equals("lifeTime")) {
                                server.setLifeTime();
                            }
                            String message = "Параметр " + keyText.getText() + " изменен";
                            JOptionPane.showMessageDialog(null, message, "Прочие настройки параметров",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        String message = "Заполнены не все поля параметра";
                        JOptionPane.showMessageDialog(null, message, "Прочие настройки параметров",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
    }

    // проверка на наличие системы в списке
    private boolean isExist(String enteredName) {
        for (String key : setup.getPropertiesNames()) {
            String tempKey = key.substring(2);
            if (tempKey.equals(enteredName)) {
                return true;
            }
        }
        return false;
    }
}
