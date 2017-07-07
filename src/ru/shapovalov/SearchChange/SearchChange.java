package ru.shapovalov.SearchChange;

import ru.shapovalov.GetData.Goods;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;

import static ru.shapovalov.UI.PriceTableModel.dataPrice;
import static ru.shapovalov.UI.Window.*;

public class SearchChange {
    public static List<Integer> oldIdList = new ArrayList<>();
    public static Map<Integer, Goods> goodsMap = new HashMap<>();

    public static void searchPrice() throws MalformedURLException, InterruptedException {
        for (Map.Entry<Integer, Goods> good : goodsMap.entrySet()) {
            Goods goodValue = good.getValue();
            Integer key = good.getKey();
            if (oldIdList.contains(key)) {

                if ((goodValue.getPriceOld() > goodValue.getPriceNew())) {
                    trayIcon.displayMessage(APPLICATION_NAME, "Цена снижена с " + goodValue.getPriceOld() + "руб до " + goodValue.getPriceNew() +
                            " ID=" + goodValue.getId_goods() + goodValue.getName_goods() + " http://plati.ru/itm//" + goodValue.getId_goods(), TrayIcon.MessageType.INFO);
                    System.out.println("Цена снижена с " + goodValue.getPriceOld() + "руб до " + goodValue.getPriceNew());
                } else if ((goodValue.getPriceOld() < goodValue.getPriceNew())) {
                    trayIcon.displayMessage(APPLICATION_NAME, "Цена повышена с " + goodValue.getPriceOld() + "руб до " + goodValue.getPriceNew() +
                            " ID=" + goodValue.getId_goods() + goodValue.getName_goods() + " http://plati.ru/itm//" + goodValue.getId_goods(), TrayIcon.MessageType.INFO);
                    System.out.println("Цена повышена с " + goodValue.getPriceOld() + "руб до " + goodValue.getPriceNew());
                }
            } else {
//                trayIcon.displayMessage(APPLICATION_NAME, "Добавлена новая позиция " + goodValue.getPriceOld() +
//                        "руб ID=" + goodValue.getId_goods() + goodValue.getName_goods() + " http://plati.ru/itm//" + goodValue.getId_goods(), TrayIcon.MessageType.INFO);
                goodValue.setType(4);
                goodsMap.put(key, goodValue);
                System.out.println("Добавлена новая позиция " + goodValue.getPriceOld());
                oldIdList.add(key);
//                TimeUnit.SECONDS.sleep(1);
                numberOfLines.setText("Записей " + goodsMap.size());
            }

        }

        SearchChange.searchDuplicateGame(goodsMap);
        newPrice(goodsMap);
        priceTableModel.fireTableDataChanged();

    }

    public static void newPrice(Map<Integer, Goods> goodsMap) {

        dataPrice = new Object[goodsMap.size()][9];
        int i = 0;
        for (Map.Entry<Integer, Goods> gSecond : goodsMap.entrySet()) {
            dataPrice[i][0] = gSecond.getValue().getId_goods();
            dataPrice[i][1] = gSecond.getValue().getName_goods();
            dataPrice[i][2] = gSecond.getValue().getPriceOld();
            dataPrice[i][3] = gSecond.getValue().getPriceNew();
            dataPrice[i][4] = gSecond.getValue().getNameCustomer();
            dataPrice[i][5] = gSecond.getValue().getCnt_sell();
            dataPrice[i][6] = gSecond.getValue().getCnt_goodresponses();
            dataPrice[i][7] = gSecond.getValue().getCnt_badresponses();
            dataPrice[i][8] = gSecond.getValue().getType();
            i++;
        }
    }

    public static void searchDuplicateGame(Map<Integer, Goods> goodsMap) {
        List<Goods> fillTypeList = new ArrayList<Goods>();


        for (Map.Entry<Integer, Goods> gSecondExternal : goodsMap.entrySet()) {
            Goods secondValueExternal = gSecondExternal.getValue();
            String platiRuGameNameExternal = secondValueExternal.getName_goods();
            for (Map.Entry<Integer, Goods> gSecond : goodsMap.entrySet()) {
                {
                    Goods secondValue = gSecond.getValue();
                    String platiRuGameName = secondValue.getName_goods();

                    if (platiRuGameName.toLowerCase().contains(platiRuGameNameExternal.toLowerCase()) ||
                            platiRuGameNameExternal.toLowerCase().contains(platiRuGameName.toLowerCase())) {
                        fillTypeList.add(secondValue);
                    }
                }
            }

            if (fillTypeList.size() >= 3) {
                sort(fillTypeList);
                Goods goodsMax = fillTypeList.get((fillTypeList.size() - 1));
                goodsMax.setType(3);
                Goods goodsMin = fillTypeList.get(0);
                goodsMin.setType(1);
                int idGoodsMax = goodsMax.getId_goods();
                int idGoodsMin = goodsMin.getId_goods();


                goodsMap.put(idGoodsMax, goodsMax);
                goodsMap.put(idGoodsMin, goodsMin);
                System.out.println(fillTypeList);
                System.out.println(idGoodsMax + "----" + idGoodsMin);
                fillTypeList.remove((fillTypeList.size() - 1));
                fillTypeList.remove(0);

                for (Goods goods : fillTypeList) {
                    goods.setType(2);
                    goodsMap.put(goods.getId_goods(), goods);
                }

            } else if (fillTypeList.size() == 2) {
                sort(fillTypeList);
                Goods goodsMax = fillTypeList.get((fillTypeList.size() - 1));
                goodsMax.setType(3);
                Goods goodsMin = fillTypeList.get(0);
                goodsMin.setType(1);
                goodsMap.put(goodsMax.getId_goods(), goodsMax);
                goodsMap.put(goodsMin.getId_goods(), goodsMin);
            }
            fillTypeList.clear();
        }
    }

    public static void sort(List<Goods> values) {
        Collections.sort(values, new Comparator<Goods>() {
            @Override
            public int compare(Goods c1, Goods c2) {
                return Double.compare(c1.getPriceNew(), c2.getPriceNew());
            }
        });
    }
}
