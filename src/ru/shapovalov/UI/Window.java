package ru.shapovalov.UI;

import ru.shapovalov.DB.DB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.shapovalov.Run.parserStrings;
import static ru.shapovalov.SearchChange.SearchChange.searchPrice;
import static ru.shapovalov.UI.PriceTableModel.dataPrice;

public class Window extends Thread {
    public static final String APPLICATION_NAME = "Parser price";
    public static final String ICON_STR = "/image/ico.png";
    public static final String ICON_REFRESH = "/image/refresh.png";
    public static final String ICON_SAVE = "/image/save.png";
    public static TrayIcon trayIcon;
    public static ImageIcon icon;
    public static PriceTableModel priceTableModel;
    public static JTextField filterText;
    public static JTextField filterSel;
    public static TableWithURL tableWithURL;
    public static TableRowSorter<PriceTableModel> sorter;
    private static JFrame frame;
    public static JLabel numberOfLines;

    @Override
    public void run() {
        try {
            icon = new ImageIcon(Window.class.getResource(ICON_STR));
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
        frame.setIconImage(icon.getImage());
        createTable(frame);
        createButton(frame);
        numberOfLines = new JLabel("Записей " + dataPrice.length);
        frame.add(numberOfLines, BorderLayout.SOUTH);

        frame.setPreferredSize(new Dimension(1100, 600));
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
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        trayMenu.add(itemExit);
        trayMenu.add(itemUpdate);

        trayIcon = new TrayIcon(icon.getImage(), APPLICATION_NAME, trayMenu);
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

        SetColorPriceTable setColorPriceTable = new SetColorPriceTable();

        priceTableModel = new PriceTableModel();
        tableWithURL = new TableWithURL(priceTableModel);
        for (TableColumn column : Collections.list(tableWithURL.getColumnModel().getColumns())) {
            column.setCellRenderer(setColorPriceTable);
        }
        tableWithURL.getColumnModel().getColumn(0).setMinWidth(65);
        tableWithURL.getColumnModel().getColumn(0).setMaxWidth(65);
        tableWithURL.getColumnModel().getColumn(1).setMinWidth(450);
        tableWithURL.getColumnModel().getColumn(1).setMaxWidth(450);
        tableWithURL.getColumnModel().getColumn(2).setMinWidth(105);
        tableWithURL.getColumnModel().getColumn(2).setMaxWidth(105);
        tableWithURL.getColumnModel().getColumn(3).setMinWidth(105);
        tableWithURL.getColumnModel().getColumn(3).setMaxWidth(105);
        tableWithURL.getColumnModel().getColumn(4).setMinWidth(120);
        tableWithURL.getColumnModel().getColumn(4).setMaxWidth(120);
        tableWithURL.getColumnModel().getColumn(5).setMinWidth(65);
        tableWithURL.getColumnModel().getColumn(5).setMaxWidth(65);
        tableWithURL.getColumnModel().getColumn(6).setMinWidth(65);
        tableWithURL.getColumnModel().getColumn(6).setMaxWidth(65);
        tableWithURL.getColumnModel().getColumn(7).setMinWidth(65);
        tableWithURL.getColumnModel().getColumn(7).setMaxWidth(65);
        tableWithURL.getColumnModel().getColumn(8).setMinWidth(30);
        tableWithURL.getColumnModel().getColumn(8).setMaxWidth(30);
        System.out.println("Url=" + tableWithURL.getPreferredSize());
        tableWithURL.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
//        frame.add(tableWithURL.getTableHeader());
        frame.getContentPane().add(tableWithURL);
        frame.add(new JScrollPane(tableWithURL));
        frame.validate();
        sorter = new TableRowSorter<>(priceTableModel);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        tableWithURL.setRowSorter(sorter);
    }

    public static void createButton(final JFrame frame) throws IOException {


        JPanel jPanel = new JPanel();
        JLabel l1 = new JLabel("Название:", SwingConstants.TRAILING);
        JLabel nameSel = new JLabel("Имя продовца:", SwingConstants.TRAILING);

        l1.setHorizontalAlignment(JLabel.LEFT);
        nameSel.setHorizontalAlignment(JLabel.LEFT);
        jPanel.add(l1);
        filterText = new JTextField(20);
        filterSel = new JTextField(20);

        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        filterSel.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        l1.setLabelFor(filterText);
        nameSel.setLabelFor(filterSel);
        jPanel.add(filterText);
        jPanel.add(nameSel);
        jPanel.add(filterSel);

        JButton save = new JButton("Сохранить в базу таблицу", new ImageIcon(Window.class.getResource(ICON_SAVE)));
        save.setHorizontalAlignment(AbstractButton.CENTER);
        jPanel.add(save);

        JButton get = new JButton("Выгрузить из базы таблицу", new ImageIcon(Window.class.getResource(ICON_REFRESH)));
        get.setHorizontalAlignment(AbstractButton.CENTER);
        jPanel.add(get);

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
                Object[][] oldData = dataPrice;
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
                System.out.println(oldData.length + "---" + dataPrice.length);
                try {
                    parserStrings.parserGoods();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (oldData.length == dataPrice.length) {
                    for (int i = 0; i < oldData.length; i++) {
                        if (!(oldData[i][2].equals(dataPrice[i][2]) && oldData[i][3].equals(dataPrice[i][3]))) {
                            try {
                                searchPrice();
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {
                    try {
                        searchPrice();
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                trayIcon.displayMessage(APPLICATION_NAME, "Данные из базы записанны в таблицу.", TrayIcon.MessageType.INFO);
            }
        });

        frame.add(jPanel, BorderLayout.NORTH);
    }

    private static void newFilter() {
        RowFilter<PriceTableModel, Object> rf = null;
        //If current expression doesn'tableWithURL parse, don'tableWithURL update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 1).regexFilter(filterSel.getText(), 4);

        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

}