package ru.shapovalov.UI;

import javax.swing.table.AbstractTableModel;

public class AllTableModel extends AbstractTableModel {

    String[] columnNames;
    public static Object[][] data;


    public AllTableModel() {

        columnNames = new String[]{"ИД товара", "Название", "Старая цена руб", "Новая цена руб",
                "Продаж", "+ отзывов", "- отзывов", "Тип"};
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    //запрещаем редактирование таблицы
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
