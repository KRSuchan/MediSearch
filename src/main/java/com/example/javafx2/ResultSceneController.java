package com.example.javafx2;

import Objects.Hospital;
import Objects.HospitalDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResultSceneController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private Label resultLabel;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn kindCol;
    @FXML
    private TableColumn distanceCol;

    private ArrayList<String> ykihoArray;

    ObservableList data = FXCollections.observableArrayList();


    public void initData(Hospital[] hospitals) {
        for (Hospital hospital : hospitals) {
            ykihoArray.add(hospital.getYkiho());
        }
        data.addAll(hospitals);
        resultLabel.setText(data.size() + "개의 병원이 검색되었습니다");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ykihoArray = new ArrayList<String>();
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() > 1) {
                    String totalInfo = "06/=/" + ykihoArray.get(tableView.getSelectionModel().getSelectedIndex());
                    System.out.println(totalInfo);
                    HospitalDetail detail = new HospitalDetail();
                    onTableItemDoubleClick(detail);
                }
            }
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("yadmNm"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("yAddress"));
        kindCol.setCellValueFactory(new PropertyValueFactory<>("clcdNm"));
        distanceCol.setCellValueFactory(new PropertyValueFactory<>("distanceStr"));

        tableView.setItems(data);
    }

    void onTableItemDoubleClick(HospitalDetail detail) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailScene.fxml"));
            Parent root = fxmlLoader.load();
            DetailSceneController controller = fxmlLoader.getController();
            controller.initData(detail);
            Scene scene = new Scene(root, 576, 509);
            Stage stage = new Stage();
            stage.setTitle("Details");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
