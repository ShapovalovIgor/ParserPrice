package ru.shapovalov.GetData;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.shapovalov.Constants;
import ru.shapovalov.GetXML.GetData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.floor;
import static ru.shapovalov.Run.startCollection;
import static ru.shapovalov.SearchChange.SearchChange.firstGoods;
import static ru.shapovalov.SearchChange.SearchChange.secondGoods;
import static ru.shapovalov.UI.AllTableModel.data;


public class ParserStrings {
    public ParserStrings() {
    }

    public void parserGoods() throws Exception {
        GetData getData = new GetData();

        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            Map<Integer, Integer> sectionIdAndCountGoodsMap = getSectionIdAndCountGoods(db, getData, is);
            System.out.println(sectionIdAndCountGoodsMap.size());

            for (Map.Entry<Integer, Integer> sectionIdAndCountGoods : sectionIdAndCountGoodsMap.entrySet()) {
                int sectionId = sectionIdAndCountGoods.getKey();
                int countPages = sectionIdAndCountGoods.getValue();
                System.out.println(sectionId);
                for (int page = 1; page <= countPages; page++) {
                    is.setCharacterStream(new StringReader(getData.getNewData(
                            new URL(Constants.GOODS_URL),
                            Constants.GOODS_1 + sectionId + Constants.GOODS_2 + page + Constants.GOODS_3)));

                    Document doc = db.parse(is);

                    NodeList nodes = doc.getElementsByTagName("row");

                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);

                        NodeList id_goods = element.getElementsByTagName("id_goods");
                        Element line = (Element) id_goods.item(0);
                        int idGoodsInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList name_goods = element.getElementsByTagName("name_goods");
                        line = (Element) name_goods.item(0);
                        String[] nameGoodsStrArray = getCharacterDataFromElement(line).replace("&amp;", "").replace("quot;", "").split("[(,+,/,|,-]");
                        String nameGoodsStr = nameGoodsStrArray[0];

                        NodeList price = element.getElementsByTagName("price");
                        line = (Element) price.item(0);
                        double priceDoub = Double.valueOf(getCharacterDataFromElement(line).replace(',', '.'));

                        NodeList cnt_sell = element.getElementsByTagName("cnt_sell");
                        line = (Element) cnt_sell.item(0);
                        int cntSellInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList cnt_return = element.getElementsByTagName("cnt_return");
                        line = (Element) cnt_return.item(0);
                        int cntReturnInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList cnt_goodresponses = element.getElementsByTagName("cnt_goodresponses");
                        line = (Element) cnt_goodresponses.item(0);
                        int cntGoodresponsesInt = Integer.parseInt(getCharacterDataFromElement(line));

                        NodeList cnt_badresponses = element.getElementsByTagName("cnt_badresponses");
                        line = (Element) cnt_badresponses.item(0);
                        int cntBadresponsesInt = Integer.parseInt(getCharacterDataFromElement(line));

                        if (startCollection) {
                            firstGoods.put(idGoodsInt, new Goods(idGoodsInt, nameGoodsStr, priceDoub, priceDoub,
                                    cntSellInt, cntReturnInt, cntGoodresponsesInt, cntBadresponsesInt, 0));
                        } else {
                            if (firstGoods.get(idGoodsInt) != null) {
                                secondGoods.put(idGoodsInt, new Goods(idGoodsInt, nameGoodsStr, firstGoods.get(idGoodsInt).getPriceOld(), priceDoub,
                                        cntSellInt, cntReturnInt, cntGoodresponsesInt, cntBadresponsesInt, 0));
                            } else {
                                secondGoods.put(idGoodsInt, new Goods(idGoodsInt, nameGoodsStr, priceDoub, priceDoub,
                                        cntSellInt, cntReturnInt, cntGoodresponsesInt, cntBadresponsesInt, 0));
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("First size" + firstGoods.size());

        if (startCollection) {
            data = new Object[firstGoods.size()][9];
            int i = 0;
            for (Map.Entry<Integer, Goods> gFirst : firstGoods.entrySet()) {
                data[i][0] = gFirst.getValue().getId_goods();
                data[i][1] = gFirst.getValue().getName_goods();
                data[i][2] = gFirst.getValue().getPriceOld();
                data[i][3] = gFirst.getValue().getPriceNew();
                data[i][4] = gFirst.getValue().getCnt_sell();
                data[i][5] = gFirst.getValue().getCnt_return();
                data[i][6] = gFirst.getValue().getCnt_goodresponses();
                data[i][7] = gFirst.getValue().getCnt_badresponses();
                data[i][8] = gFirst.getValue().getType();
                i++;
            }
        }

    }

    public Map<Integer, Integer> getSectionIdAndCountGoods(DocumentBuilder db, GetData getData, InputSource is) throws IOException, SAXException {
        is.setCharacterStream(new StringReader(getData.getNewData(new URL(Constants.SECTIONS_URL), Constants.CATOLOG_ID_AND_COUNT_PAGE)));

        Document doc = db.parse(is);
        System.out.println(doc.toString());
        Map<Integer, Integer> sectionIdAndCountGoods = new HashMap<>();
        NodeList nodes = doc.getElementsByTagName("section");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element sectionIdElement = (Element) nodes.item(i);
            int sectionId = Integer.parseInt(sectionIdElement.getAttribute("id"));

            NodeList cntGoodsNodeList = sectionIdElement.getElementsByTagName("cnt_goods");
            Element cntGoodsElement = (Element) cntGoodsNodeList.item(0);
            double cntGoods = Integer.parseInt(getCharacterDataFromElement(cntGoodsElement));

            int pagesCount = (int) Math.floor((cntGoods / 500)) + 1;
            System.out.println("Section=" + sectionId + "Page count=" + pagesCount);
            sectionIdAndCountGoods.put(sectionId, pagesCount);
        }
        return sectionIdAndCountGoods;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }
}
