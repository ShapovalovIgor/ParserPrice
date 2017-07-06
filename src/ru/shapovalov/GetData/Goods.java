package ru.shapovalov.GetData;

public class Goods {

    private int id_goods;
    private String name_goods;
    private double priceOld;
    private double priceNew;
    private String nameCustomer;
    private int cnt_sell;
    private int cnt_goodresponses;
    private int cnt_badresponses;
    private int type;

    public Goods(int id_goods, String name_goods, double priceOld, double priceNew, String nameCustomer, int cnt_sell, int cnt_goodresponses, int cnt_badresponses, int type) {
        this.id_goods = id_goods;
        this.name_goods = name_goods;
        this.priceOld = priceOld;
        this.priceNew = priceNew;
        this.nameCustomer = nameCustomer;
        this.cnt_sell = cnt_sell;
        this.cnt_goodresponses = cnt_goodresponses;
        this.cnt_badresponses = cnt_badresponses;
        this.type = type;
    }

    public void setId_goods(int id_goods) {
        this.id_goods = id_goods;
    }

    public void setName_goods(String name_goods) {
        this.name_goods = name_goods;
    }

    public void setPriceOld(double priceOld) {
        this.priceOld = priceOld;
    }

    public void setPriceNew(double priceNew) {
        this.priceNew = priceNew;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public void setCnt_sell(int cnt_sell) {
        this.cnt_sell = cnt_sell;
    }

    public void setCnt_goodresponses(int cnt_goodresponses) {
        this.cnt_goodresponses = cnt_goodresponses;
    }

    public void setCnt_badresponses(int cnt_badresponses) {
        this.cnt_badresponses = cnt_badresponses;
    }

    public void setType(int type) {this.type = type;}


    public int getId_goods() {
        return id_goods;
    }

    public String getName_goods() {
        return name_goods;
    }

    public double getPriceOld() {
        return priceOld;
    }

    public double getPriceNew() {
        return priceNew;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public int getCnt_sell() {
        return cnt_sell;
    }

    public int getCnt_goodresponses() {
        return cnt_goodresponses;
    }

    public int getCnt_badresponses() {
        return cnt_badresponses;
    }

    public int getType() {return type;}
    public Goods() {

    }
}
