package ru.shapovalov.DB;
import ru.shapovalov.GetData.Goods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import static ru.shapovalov.SearchChange.SearchChange.goodsMap;
import static ru.shapovalov.UI.PriceTableModel.dataPrice;


public class DB {
    public static Connection conn;

    public DB() {
    }

    public static Statement statmt;
    public static ResultSet resSet;
    public static String createGoods =  "create table goods (" +
                                        "id_goods integer," +
                                        "name_goods text," +
                                        "price_old real," +
                                        "price_new real," +
                                        "price_customer real," +
                                        "cnt_sell integer," +
                                        "cnt_goodresponses integer," +
                                        "cnt_badresponses integer," +
                                        "type integer)";

    public void conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:goods.s3db");

        System.out.println("К базе подключён!");
    }

    public void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("drop  table if exists goods");
        statmt.execute(createGoods);
    }

    public void writeDB() throws SQLException
    {
        for (int i = 0; i< dataPrice.length; i++) {
            statmt.execute("INSERT INTO goods ('id_goods', 'name_goods', 'price_old', 'price_new'," +
                    "'cnt_sell', 'cnt_goodresponses', 'cnt_badresponses', 'type') " +
                    "VALUES ('" + dataPrice[i][0] + "', '" + dataPrice[i][1] + "', '" + dataPrice[i][2] + "', '" + dataPrice[i][3] + "', " +
                    "'" + dataPrice[i][4] + "', '" + dataPrice[i][5] + "', '" + dataPrice[i][6] + "', '" + dataPrice[i][7] + "', '" + dataPrice[i][8] + "' " +
                    "'');");
        }

        System.out.println("База заполнена");
    }

    public void readDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT COUNT(*) row FROM goods");
        resSet.next();
        int rowCount = resSet.getInt("row");

        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM goods");
        int i =0;

        dataPrice = new Object[rowCount][rowCount];
        while(resSet.next())
        {
            int id_goods = resSet.getInt("id_goods");
            String nameGoods = resSet.getString("name_goods");
            double priceOld = resSet.getDouble("price_old");
            double priceNew = resSet.getDouble("price_new");
            double priceCustomer = resSet.getDouble("price_customer");
            int cntSell = resSet.getInt("cnt_sell");
            int cntGoodresponses = resSet.getInt("cnt_goodresponses");
            int cntBadresponses = resSet.getInt("cnt_badresponses");
            int type = resSet.getInt("type");


            dataPrice[i][0] = id_goods;
            dataPrice[i][1] = nameGoods;
            dataPrice[i][2] = priceOld;
            dataPrice[i][3] = priceNew;
            dataPrice[i][4] = priceCustomer;
            dataPrice[i][5] = cntSell;
            dataPrice[i][6] = cntGoodresponses;
            dataPrice[i][7] = cntBadresponses;
            dataPrice[i][8] = type;
            goodsMap.clear();
            goodsMap.put(id_goods, new Goods(id_goods, nameGoods, priceOld, priceNew, priceCustomer,
                    cntSell, cntGoodresponses, cntBadresponses, type));
            i++;
        }
        resSet.close();
    }

    public void сloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
    }
}