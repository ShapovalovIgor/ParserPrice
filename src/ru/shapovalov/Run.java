package ru.shapovalov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SceneBuilder;
import javafx.stage.Stage;
import ru.shapovalov.GetData.ParserStrings;
import ru.shapovalov.SearchChange.SearchChange;
import ru.shapovalov.UI.Window;

import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static ru.shapovalov.SearchChange.SearchChange.goodsMap;
import static ru.shapovalov.UI.Window.APPLICATION_NAME;
import static ru.shapovalov.UI.Window.trayIcon;


public class Run extends Application {
    static Window window;
    public static boolean startCollection = true;
    public static ParserStrings parserStrings;

    public static void main(String[] args) throws Exception {
//        parserStrings = new ParserStrings();
//        parserStrings.parserGoods();
//        SearchChange.searchDuplicateGame(goodsMap);
//
//        newPrice(goodsMap);

//        window = new Window();
//        window.start();
        System.out.println("111");
        launch(args);
        System.out.println("111");

        startCollection = false;
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            trayIcon.displayMessage(APPLICATION_NAME, "Получено " + goodsMap.size() + " позиций.",
                    TrayIcon.MessageType.INFO);
            TimeUnit.MINUTES.sleep(0);
            parserStrings.parserGoods();
            SearchChange.searchPrice();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In SearchDemo#start()");
        Object tabsParamObj;
        Object languageParamObj;
        Object countryParamObj;
        String fxmlFile = "UIJAVAFX/search_demo.fxml";
        Locale locale = Locale.getDefault();

        Map<String, String> namedParams = getParameters().getNamed();
        if (namedParams != null) {
            tabsParamObj = namedParams.get("tabs");
            if (tabsParamObj != null) {
                System.out.println("param tabs:" + tabsParamObj);
                if (((String)tabsParamObj).equalsIgnoreCase("true")) {
                    fxmlFile = "UIJAVAFX/search_demo_w_tabs.fxml";
                }
            }
            else {
                System.out.println("param tabs not found");
            }

            languageParamObj = namedParams.get("language");
            if (languageParamObj != null) {
                System.out.println("param language:" + languageParamObj);
            }
            else {
                System.out.println("param language not found");
            }

            countryParamObj = namedParams.get("country");
            if (countryParamObj != null) {
                System.out.println("param country:" + countryParamObj);
            }
            else {
                System.out.println("param country not found");
            }

            if ((languageParamObj != null) &&
                    ((String)languageParamObj).trim().length() > 0) {
                if ((countryParamObj != null) &&
                        ((String)countryParamObj).trim().length() > 0) {
                    locale = new Locale(((String)languageParamObj).trim(),
                            ((String)countryParamObj).trim());
                }
                else {
                    locale = new Locale(((String)languageParamObj).trim());
                }
            }
        }
        else {
            System.out.println("No params found for SearchDemo");
        }
        System.out.println("Locale set to:" + locale);

        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile),
                ResourceBundle.getBundle("ru/shapovalov/UIJAVAFX/search_demo", locale));
        System.out.println("Locale set to:" + locale);

        primaryStage.setTitle("Search Demo");
        primaryStage.setWidth(650);
        primaryStage.setHeight(500);

        primaryStage.setScene(
                SceneBuilder.create()
                        .root(root)
                        .stylesheets("demos/search/myStyles.css")
                        .build()
        );
        primaryStage.show();
    }

    private URL getResource(String name) {

        return getClass().getResource(name);
    }
}
