package ru.shapovalov.UI;

import javax.swing.table.AbstractTableModel;

public class SaleTableModel extends AbstractTableModel {

    String[] columnNames;
    public static Object[][] dataSale;


    public SaleTableModel() {

        columnNames = new String[]{"Название", "Цена руб", "Продавец", "Тип"};
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return dataSale.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return dataSale[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    //запрещаем редактирование таблицы
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        dataSale[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
