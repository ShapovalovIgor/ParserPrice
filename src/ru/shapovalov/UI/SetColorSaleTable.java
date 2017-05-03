package ru.shapovalov.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static ru.shapovalov.UI.Window.userName;

public class SetColorSaleTable extends DefaultTableCellRenderer {
    private static final Color evenColor = new Color(240, 240, 255);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground((row % 2 == 0) ? evenColor : table.getBackground());
        }

        if (1 == (int) table.getValueAt(row, 3) && column == 1) {
            setBackground(Color.BLUE);
            setForeground(Color.WHITE);
        } else if (2 == (int) table.getValueAt(row, 3) && column == 1) {
            setBackground(Color.YELLOW);
            setForeground(Color.BLACK);
        } else if (3 == (int) table.getValueAt(row, 3) && column == 1) {
            setBackground(Color.ORANGE);
            setForeground(Color.BLACK);
        } else if (4 == (int) table.getValueAt(row, 3) && column == 1) {
            setBackground(Color.GREEN);
            setForeground(Color.BLACK);
        }

        if (null != userName && userName.equals(table.getValueAt(row, 2)) && column == 2) {
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        }
        setHorizontalAlignment((value instanceof Number) ? RIGHT : LEFT);
        return this;
    }
}
