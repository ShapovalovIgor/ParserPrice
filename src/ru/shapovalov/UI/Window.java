package ru.shapovalov.UI;

import ru.shapovalov.DB.DB;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Collections;

import static ru.shapovalov.Run.parserStrings;
import static ru.shapovalov.SearchChange.SearchChange.searchPrice;
import static ru.shapovalov.UI.AllTableModel.data;

public class Window extends Thread {
    public static final String curDir = System.getProperty("user.dir");
    public static final String APPLICATION_NAME = "Parser price";
    public static final String ICON_STR = curDir + "/image/ico.png";
    public static final String ICON_REFRESH = curDir + "/image/refresh.png";
    public static final String ICON_SAVE = curDir + "/image/save.png";
    public static TrayIcon trayIcon;
    public static Image icon;
    public static AllTableModel alltableModel;
    public static boolean radioButton = false;
    public static TableWithURL t;
    public static Object[][] tempData;
    private static JFrame frame;

    @Override
    public void run() {
        try {
            icon = ImageIO.read(new File(ICON_STR));
            createGUI();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                createGUI();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void createGUI() throws IOException {
        frame = new JFrame(APPLICATION_NAME);
        frame.setIconImage(icon);
        createTable(frame);
        createButton(frame);
        JLabel numberOfLines = new JLabel("Записей " + data.length);
        frame.add(numberOfLines, BorderLayout.SOUTH);

        frame.setPreferredSize(new Dimension(1650, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setState(JFrame.ICONIFIED);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setExtendedState(JFrame.NORMAL);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setTrayIcon();
    }

    private static void setTrayIcon() throws IOException {
        if (!SystemTray.isSupported()) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem itemExit = new MenuItem("Выход");
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        MenuItem itemUpdate = new MenuItem("Обновить в не очереди");
        itemUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    parserStrings.parserGoods();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    searchPrice();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        trayMenu.add(itemExit);
        trayMenu.add(itemUpdate);

        trayIcon = new TrayIcon(icon, APPLICATION_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage(APPLICATION_NAME, "Приложение запущено",
                TrayIcon.MessageType.INFO);
    }

    public static void createTable(JFrame frame) {
        SetColorTable setColorTable = new SetColorTable();

        alltableModel = new AllTableModel();
        t = new TableWithURL(alltableModel);
        for (TableColumn column : Collections.list(t.getColumnModel().getColumns())) {
            column.setCellRenderer(setColorTable);
        }

        t.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        frame.getContentPane().add(t);
        frame.add(new JScrollPane(t));
        TableRowSorter<AllTableModel> sorter = new TableRowSorter<>(alltableModel);
        t.setRowSorter(sorter);
    }

    public static void createButton(final JFrame frame) throws IOException {
        JPanel jPanel = new JPanel();
        JButton save = new JButton("Сохранить в базу таблицу", new ImageIcon(ImageIO.read(new File(ICON_SAVE))));
        save.setVerticalTextPosition(AbstractButton.CENTER);
        jPanel.add(save);

        JButton get = new JButton("Выгрузить из базы таблицу", new ImageIcon(ImageIO.read(new File(ICON_REFRESH))));
        get.setVerticalTextPosition(AbstractButton.CENTER);
        jPanel.add(get);

        JButton ref = new JButton("Обновить принудительно", new ImageIcon(ImageIO.read(new File(ICON_REFRESH))));
        ref.setVerticalTextPosition(AbstractButton.CENTER);
        jPanel.add(ref);


        JRadioButton fullBase = new JRadioButton("Показать только записи с изменёной ценой и новые");
        fullBase.setVerticalTextPosition(AbstractButton.CENTER);
        jPanel.add(fullBase);

        save.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) {
                                       DB db = new DB();
                                       try {
                                           db.conn();
                                           db.createDB();
                                           db.writeDB();
                                           db.сloseDB();
                                       } catch (ClassNotFoundException e1) {
                                           e1.printStackTrace();
                                       } catch (SQLException e1) {
                                           e1.printStackTrace();
                                       }
                                       trayIcon.displayMessage(APPLICATION_NAME, "Данные сохранены в базу.", TrayIcon.MessageType.INFO);
                                   }
                               }
        );

        get.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] oldData = data;
                DB db = new DB();
                try {
                    db.conn();
                    db.readDB();
                    db.сloseDB();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                System.out.println(oldData.length + "---" + data.length);
                try {
                    parserStrings.parserGoods();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (oldData.length == data.length) {
                    for (int i = 0; i < oldData.length; i++) {
                        if (!(oldData[i][2].equals(data[i][2]) && oldData[i][3].equals(data[i][3]))) {
                            try {
                                searchPrice();
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {
                    try {
                        searchPrice();
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                }
                trayIcon.displayMessage(APPLICATION_NAME, "Данные из базы записанны в таблицу.", TrayIcon.MessageType.INFO);
            }
        });

        ref.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    parserStrings.parserGoods();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    searchPrice();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                trayIcon.displayMessage(APPLICATION_NAME, "Данные в таблице обнавлены.", TrayIcon.MessageType.INFO);
            }
        });

        fullBase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//
//            if(radioButton){
//                data = tempData;
//               update();
//                trayIcon.displayMessage(APPLICATION_NAME, "В таблице показанны все существующие позиции.", TrayIcon.MessageType.INFO);
//                radioButton = false;
//            }else {
//                tempData = new Object[data.length][data.length];
//                tempData = data;
//                for (int i = 0; i < data.length; i++) {
//                    System.out.println(tempData.length);
//
//                    if ((int)data[i][13] == 0) {
//                        data[i][0] = null;
//                        data[i][1] = null;
//                        data[i][2] = new Double(0);
//                        data[i][3] = new Double(0);
//                        data[i][4] = null;
//                        data[i][5] = null;
//                        data[i][6] = null;
//                        data[i][7] = null;
//                        data[i][8] = null;
//                        data[i][9] = null;
//                        data[i][10] = null;
//                        data[i][11] = null;
//                        data[i][12] = null;
//                        data[i][13] = 0;
//                    }
//                }
//                update();
//                trayIcon.displayMessage(APPLICATION_NAME, "В таблице показанны только новые позиции \nа так же позиции с изминёными ценами.", TrayIcon.MessageType.INFO);
//                radioButton = true;
//            }
            }

        });

        frame.add(jPanel, BorderLayout.NORTH);
    }
}