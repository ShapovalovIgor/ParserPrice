package ru.shapovalov.UIJAVAFX.controller;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import ru.shapovalov.GetData.Goods;

import java.util.Map;

import static ru.shapovalov.SearchChange.SearchChange.firstGoods;

public class Controller {

    private ObservableList<Goods> usersData = FXCollections.observableArrayList();

    @FXML
    private GridPane gridPane;

    @FXML
    private TableView<Goods> tableUsers;

    @FXML
    private TableColumn<Goods, Integer> id_goods;

    @FXML
    private TableColumn<Goods, String> name_goods;

    @FXML
    private TableColumn<Goods, Double> priceOld;

    @FXML
    private TableColumn<Goods, Double> priceNew;

    @FXML
    private TableColumn<Goods, Integer> cnt_sell;

    @FXML
    private TableColumn<Goods, Integer> cnt_return;

    @FXML
    private TableColumn<Goods, Integer> cnt_goodresponses;

    @FXML
    private TableColumn<Goods, Integer> cnt_badresponses;

    @FXML
    private TableColumn<Goods, Integer> type;

    @FXML
    private Label count_goods;

    // инициализируем форму данными
    @FXML
    private void initialize() {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        id_goods.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("id_goods"));
        name_goods.setCellValueFactory(new PropertyValueFactory<Goods, String>("name_goods"));
        priceOld.setCellValueFactory(new PropertyValueFactory<Goods, Double>("priceOld"));
        priceNew.setCellValueFactory(new PropertyValueFactory<Goods, Double>("priceNew"));
        cnt_sell.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("cnt_sell"));
        cnt_return.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("cnt_return"));
        cnt_goodresponses.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("cnt_goodresponses"));
        cnt_badresponses.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("cnt_badresponses"));
        type.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("type"));

        // заполняем таблицу данными
        tableUsers.setItems(usersData);
       // tableUsers.setRowFactory();
        count_goods.setText(String.valueOf(usersData.size()));
        gridPane.setHgrow(tableUsers, Priority.ALWAYS);
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() {
        for (Map.Entry<Integer, Goods> gFirst : firstGoods.entrySet()) {
            usersData.add(gFirst.getValue());
        }

        count_goods.getText();
    }

}
