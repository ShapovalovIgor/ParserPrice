package ru.shapovalov.UI;

import ru.shapovalov.GetData.Goods;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static ru.shapovalov.SearchChange.SearchChange.secondGoods;

public class SetColorTable extends DefaultTableCellRenderer
{
    private static final Color evenColor = new Color(240, 240, 255);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected)
        {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        else
            {
            setForeground(table.getForeground());
            setBackground((row % 2 == 0) ? evenColor : table.getBackground());
        }
        if ((table.getValueAt(row, 2)).equals(null) && !(table.getValueAt(row, 3)).equals(null))
        {
            setBackground(Color.BLUE);
            setForeground(Color.WHITE);
        }
        else if(((double) table.getValueAt(row, 2)) > ((double)table.getValueAt(row, 3)) && (column == 2 || column == 3))
        {
            setBackground(Color.GREEN);
            setForeground(Color.BLACK);
        }
        else if (((double) table.getValueAt(row, 2)) < ((double)table.getValueAt(row, 3)) && (column == 2 || column == 3))
        {
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        }
        for (Map.Entry<Integer, Goods> gSecond : secondGoods.entrySet()) {
            Goods goodsTemp = gSecond.getValue();
            int type = goodsTemp.getType();

//            table.getValueAt(row, )
            if (!(column == 2 || column == 3) && 1 == (int)table.getValueAt(row, 8) && column == 1) {
                setBackground(Color.BLUE);
                setForeground(Color.WHITE);
            } else if (!(column == 2 || column == 3) && 2 == (int)table.getValueAt(row, 8) && column == 1) {
                setBackground(Color.YELLOW);
                setForeground(Color.BLACK);
            }else if (!(column == 2 || column == 3) && 3 == (int)table.getValueAt(row, 8) && column == 1) {
                setBackground(Color.ORANGE);
                setForeground(Color.BLACK);
            }
        }
        setHorizontalAlignment((value instanceof Number) ? RIGHT : LEFT);
        return this;
    }
}
