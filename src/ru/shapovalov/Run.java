package ru.shapovalov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.shapovalov.GetData.ParserStrings;
import ru.shapovalov.SearchChange.SearchChange;
import ru.shapovalov.UI.Window;

import java.awt.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static ru.shapovalov.SearchChange.SearchChange.newPrice;
import static ru.shapovalov.SearchChange.SearchChange.goodsMap;
import static ru.shapovalov.UI.Window.APPLICATION_NAME;
import static ru.shapovalov.UI.Window.ICON_STR;
import static ru.shapovalov.UI.Window.trayIcon;


public class Run extends Application {
    static Window window;
    public static boolean startCollection = true;
    public static ParserStrings parserStrings;

    public static void main(String[] args) throws Exception {
        parserStrings = new ParserStrings();
        parserStrings.parserGoods();
        SearchChange.searchDuplicateGame(goodsMap);

        newPrice(goodsMap);

        window = new Window();
        window.start();
        System.out.println("111");
        launch(args);//run javafx
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
        Parent root = FXMLLoader.load(getClass().getResource("UIJAVAFX/views/main.fxml"));
        primaryStage.setTitle("Parser price");
        primaryStage.getIcons().add(new Image(getResource(ICON_STR).toExternalForm()));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private URL getResource(String name) {
        return getClass().getResource(name);
    }
}
