package ru.shapovalov.UI;

import javax.swing.table.AbstractTableModel;

public class PriceTableModel extends AbstractTableModel {

    String[] columnNames;
    public static Object[][] dataPrice;


    public PriceTableModel() {

        columnNames = new String[]{"ИД товара", "Название", "Старая цена руб", "Новая цена руб", "Продавец",
                "Продаж", "+ отзывов", "- отзывов", "Тип"};
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return dataPrice.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return dataPrice[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    //запрещаем редактирование таблицы
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        dataPrice[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
