package com.example.javafx2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultSceneController implements Initializable {

    @FXML

    private TableView<ResultData> tableView;

    @FXML
    private Label resultLabel;
    @FXML

    private TableColumn<ResultData, String> nameCol;

    @FXML

    private TableColumn<ResultData, String> addressCol;
    @FXML

    private TableColumn<ResultData, String> phoneNumCol;
    @FXML
    private TableColumn<ResultData, Void> buttonCol;

    ObservableList<ResultData> data = FXCollections.observableArrayList();
    //new ResultData("성모병원","대천로103번길","010-420-132"),
    //            new ResultData("부민병원2","대천로103번길2","010-420-2"),new ResultData("백병원3","대천로103번길3","010-420-2"


    public void initData(List<ResultData> list){
        data.addAll(list);
        resultLabel.setText(data.size()+"개의 병원이 검색되었습니다.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        nameCol.setCellValueFactory(cellData -> cellData.getValue().getName());

        addressCol.setCellValueFactory(cellData -> cellData.getValue().getAddress());

        phoneNumCol.setCellValueFactory(cellData -> cellData.getValue().getPhoneNum());

        buttonCol.setCellFactory(param -> new TableCell<>()
        {
            private final Button button = new Button("상세보기");
            {
                button.setOnAction(event -> {
                    // 버튼 동작 구현
                    ResultData resultData = getTableView().getItems().get(getIndex());
                    System.out.println("Clicked on " + resultData.getName() + " " + resultData.getPhoneNum());
                    //
                    try{FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Detailed.fxml"));
                        Parent root = fxmlLoader.load();
                        DetailedSceneController controller = fxmlLoader.getController();
                        controller.initData(resultData);

                        Scene scene = new Scene(root, 576, 509);
                        Stage stage = new Stage();
                        stage.setTitle("Detailed");
                        stage.setScene(scene);
                        stage.show();}
                    catch(IOException e){

                    }


                });
            }
           @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }


        });
        tableView.setItems(data);
    }



}
