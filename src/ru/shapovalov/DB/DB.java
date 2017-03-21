package ru.shapovalov.DB;
import ru.shapovalov.GetData.Goods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.shapovalov.SearchChange.SearchChange.firstGoods;
import static ru.shapovalov.UI.AllTableModel.data;


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
                                        "discount text," +
                                        "gift text," +
                                        "id_seller integer," +
                                        "name_seller text," +
                                        "rating text," +
                                        "cnt_sell integer," +
                                        "cnt_return integer," +
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
        for (int i = 0; i<data.length;i++) {
            statmt.execute("INSERT INTO goods ('id_goods', 'name_goods', 'price_old', 'price_new'," +
                    "'discount', 'gift', 'id_seller', 'name_seller', 'rating', 'cnt_sell', " +
                    "'cnt_return', 'cnt_goodresponses', 'cnt_badresponses', 'type') " +
                    "VALUES ('" + data[i][0] + "', '" + data[i][1] + "', '" + data[i][2] + "', '" + data[i][3] + "', " +
                    "'" + data[i][4] + "', '" + data[i][5] + "', '" + data[i][6] + "', '" + data[i][7] + "', " +
                    "'" + data[i][8] + "', '" + data[i][9] + "', '" + data[i][10] + "', '" + data[i][11] + "', '" + data[i][12] + "', '" + data[i][13] + "');");
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

        data = new Object[rowCount][rowCount];
        while(resSet.next())
        {
            int id_goods = resSet.getInt("id_goods");
            String nameGoods = resSet.getString("name_goods");
            double priceOld = resSet.getDouble("price_old");
            double priceNew = resSet.getDouble("price_new");
            String discount = resSet.getString("discount");
            String gift = resSet.getString("gift");
            int idSeller =  resSet.getInt("id_seller");
            String nameSeller = resSet.getString("name_seller");
            String rating = resSet.getString("rating");
            int cntSell = resSet.getInt("cnt_sell");
            int cntReturn = resSet.getInt("cnt_return");
            int cntGoodresponses = resSet.getInt("cnt_goodresponses");
            int cntBadresponses = resSet.getInt("cnt_badresponses");
            int type = resSet.getInt("type");


            data[i][0] = id_goods;
            data[i][1] = nameGoods;
            data[i][2] = priceOld;
            data[i][3] = priceNew;
            data[i][4] = cntSell;
            data[i][5] = cntReturn;
            data[i][6] = cntGoodresponses;
            data[i][7] = cntBadresponses;
            data[i][8] = type;
            firstGoods.clear();
            firstGoods.put(id_goods, new Goods(id_goods, nameGoods, priceOld, priceNew,
                    cntSell, cntReturn, cntGoodresponses, cntBadresponses, type));
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