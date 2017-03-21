package ru.shapovalov.UI;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

class TableWithURL extends JTable
{

    public TableWithURL(TableModel model)
    {

        super(model);

        this.addMouseListener(new MouseAdapter()
        {

            public void mouseClicked(MouseEvent e)
            {

                if (e.getClickCount() == 2)
                {
                    Object valueInTable = getValueAt(rowAtPoint(e.getPoint()), 0);
                    String stringValue = valueInTable.toString();
                        try
                        {
                            Desktop.getDesktop().browse(URI.create("http://plati.ru/itm//" + stringValue));
                        }
                        catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                }
            }
        }
        );
    }
}