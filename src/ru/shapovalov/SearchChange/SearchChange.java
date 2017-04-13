package ru.shapovalov.SearchChange;

import ru.shapovalov.GetData.Goods;
import ru.shapovalov.UI.AllTableModel;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static ru.shapovalov.UI.AllTableModel.data;
import static ru.shapovalov.UI.Window.*;

public class SearchChange {
    public static Map<Integer, Goods> firstGoods = new HashMap<>();
    public static Map<Integer, Goods> secondGoods = new HashMap<>();

    public static void searchPrice() throws MalformedURLException {
        if (!firstGoods.containsValue(secondGoods)) {
            for (Map.Entry<Integer, Goods> gFirst : firstGoods.entrySet()) {
                Goods firstValue = gFirst.getValue();
                for (Map.Entry<Integer, Goods> gSecond : secondGoods.entrySet()) {
                    Goods secondValue = gSecond.getValue();
                    if (firstValue.getId_goods() == secondValue.getId_goods()) {

                        if (Double.valueOf(firstValue.getPriceOld()) == null) {
                            trayIcon.displayMessage(APPLICATION_NAME, "Добавлена новая позиция " + secondValue.getPriceOld() +
                                    "руб ID=" + secondValue.getId_goods() + secondValue.getName_goods() + " http://plati.ru/itm//" + firstValue.getId_goods(), TrayIcon.MessageType.INFO);
                            secondValue.setType(3);
                            secondGoods.put(gSecond.getKey(), secondValue);
                            System.out.println("Добавлена новая позиция " + secondValue.getPriceOld());
                        } else if ((firstValue.getPriceOld() > secondValue.getPriceNew())) {
                            trayIcon.displayMessage(APPLICATION_NAME, "Цена снижена с " + firstValue.getPriceOld() + "руб до " + secondValue.getPriceNew() +
                                    " ID=" + secondValue.getId_goods() + secondValue.getName_goods() + " http://plati.ru/itm//" + firstValue.getId_goods(), TrayIcon.MessageType.INFO);
                            secondValue.setType(1);
                            secondGoods.put(gSecond.getKey(), secondValue);
                            System.out.println("Цена снижена с " + firstValue.getPriceOld() + "руб до " + secondValue.getPriceNew());
                        } else if ((firstValue.getPriceOld() < secondValue.getPriceNew())) {
                            trayIcon.displayMessage(APPLICATION_NAME, "Цена повышена с " + firstValue.getPriceOld() + "руб до " + secondValue.getPriceNew() +
                                    " ID=" + secondValue.getId_goods() + secondValue.getName_goods() + " http://plati.ru/itm//" + firstValue.getId_goods(), TrayIcon.MessageType.INFO);
                            secondValue.setType(2);
                            secondGoods.put(gSecond.getKey(), secondValue);
                            System.out.println("Цена повышена с " + firstValue.getPriceOld() + "руб до " + secondValue.getPriceNew());
                        }
                    }
                }
            }
        }

        SearchChange.searchDuplicateGame(secondGoods);
        newPrice(secondGoods);
    }

    public static void newPrice(Map<Integer, Goods> goodsMap) {

        data = new Object[goodsMap.size()][9];
        int i = 0;
        for (Map.Entry<Integer, Goods> gSecond : goodsMap.entrySet()) {
            data[i][0] = gSecond.getValue().getId_goods();
            data[i][1] = gSecond.getValue().getName_goods();
            data[i][2] = gSecond.getValue().getPriceOld();
            data[i][3] = gSecond.getValue().getPriceNew();
            data[i][4] = gSecond.getValue().getCnt_sell();
            data[i][5] = gSecond.getValue().getCnt_return();
            data[i][6] = gSecond.getValue().getCnt_goodresponses();
            data[i][7] = gSecond.getValue().getCnt_badresponses();
            data[i][8] = gSecond.getValue().getType();
            i++;
        }
//        alltableModel.fireTableDataChanged();
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

                    if (platiRuGameName.contains(platiRuGameNameExternal) ||
                            platiRuGameNameExternal.contains(platiRuGameName)) {
                        fillTypeList.add(secondValue);
                    }
                }
            }

            if (fillTypeList.size() >= 3) {
                sort(fillTypeList);
                Goods goodsMax = fillTypeList.get((fillTypeList.size() -1));
                goodsMax.setType(3);
                Goods goodsMin = fillTypeList.get(0);
                goodsMin.setType(1);
                int idGoodsMax = goodsMax.getId_goods();
                int idGoodsMin = goodsMin.getId_goods();


                goodsMap.put(idGoodsMax, goodsMax);
                goodsMap.put(idGoodsMin, goodsMin);
                System.out.println(fillTypeList);
                System.out.println(idGoodsMax + "----" + idGoodsMin);
                fillTypeList.remove((fillTypeList.size() -1));
                fillTypeList.remove(0);

                for (Goods goods : fillTypeList) {
                    goods.setType(2);
                    goodsMap.put(goods.getId_goods(), goods);
                }

            } else if (fillTypeList.size() == 2) {
                sort(fillTypeList);
                Goods goodsMax = fillTypeList.get((fillTypeList.size() -1));
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
            public int compare(Goods o1, Goods o2) {
                Double o1Val = new Double(o1.getCnt_goodresponses());
                Double o2Val = new Double(o2.getCnt_goodresponses());
                return o1Val.compareTo(o2Val);
            }
        });
    }
}
