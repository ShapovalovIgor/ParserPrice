/*
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
 */

package ru.shapovalov.UIJAVAFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SceneBuilder;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Search demo application.
 */
public class SearchDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In SearchDemo#start()");
        Object tabsParamObj;
        Object languageParamObj;
        Object countryParamObj;
        String fxmlFile = "search_demo.fxml";
        Locale locale = Locale.getDefault();
        Map<String, String> namedParams = getParameters().getNamed();
        if (namedParams != null) {
          tabsParamObj = namedParams.get("tabs");
          if (tabsParamObj != null) {
            System.out.println("param tabs:" + tabsParamObj);
            if (((String)tabsParamObj).equalsIgnoreCase("true")) {
              fxmlFile = "search_demo_w_tabs.fxml";
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
        
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile),
          ResourceBundle.getBundle("demos/search/search_demo", locale));
        System.out.println("Locale set to:" + locale);
        
        primaryStage.setTitle("Search Demo");
        primaryStage.setWidth(650);
        primaryStage.setHeight(500);

        primaryStage.setScene(
          SceneBuilder.create()
            .root(root)
            .stylesheets("ru/shapovalov/UIJAVAFX/myStyles.css")
            .build()
        );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
