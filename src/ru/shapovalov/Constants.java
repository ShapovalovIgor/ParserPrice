package ru.shapovalov;

public class Constants {

    private static final String GUID = "A47CB89A7A8545199A389B0E7276D760";
    private static final int GAME_CATOLOG_ID = 7682;
    public static final String GOODS_1 = "<digiseller.request>" +
            "<guid_agent>" + GUID + "</guid_agent>" +
            "<id_section>";
    public static final String GOODS_2 = "</id_section>" +
              "<encoding>windows-1251</encoding>\n" +
//            "<encoding>utf-8</encoding>" +
            "<page>";

    public static final String GOODS_3 = "</page>" +
            "<rows>500</rows>" +
            "<currency>RUR</currency>" +
            "<order>price</order>" +
            "</digiseller.request>";


    public static final String CATOLOG_ID_AND_COUNT_PAGE = "<digiseller.request>" +
            "<guid_agent>" + GUID + "</guid_agent>" +
            "<id_catalog>" + GAME_CATOLOG_ID + "</id_catalog>" +
            "<lang>ru-RU</lang>" +
            "<encoding>utf-8</encoding>" +
            "</digiseller.request>";

    public static final String SECTIONS_URL = "http://www.plati.com/xml/sections.asp";
    public static final String GOODS_URL = "http://www.plati.com/xml/goods.asp";
    public static final String SECTIONS_URL_RU = "http://www.plati.com/xml/sections.asp";
    public static final String GOODS_URL_RU = "http://www.plati.com/xml/goods.asp";
    public static final String STEAM_GAME_LIST_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v2";
}
