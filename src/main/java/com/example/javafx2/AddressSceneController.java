package com.example.javafx2;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AddressSceneController {
    private String resultAddress;

    @FXML
    private TextField SearchTextField;

    @FXML
    private TextFlow ResultList;

    @FXML
    private Button SearchBtn;

    public void initialize(String input){
        resultAddress = new String();
        SearchTextField.setText(input);
    }

    @FXML
    protected void onSearchBtnClick() {
        String input = SearchTextField.getText();
        // function call by using input String and get Address info
        addItemInResultList("도로명 1", "지번 1");
        addItemInResultList("도로명 2", "지번 2");
        addItemInResultList("도로명 3", "지번 3");
        addItemInResultList("도로명 4", "지번 4");
    }

    void addItemInResultList(String roadName, String lotName) {
        Button button = new Button();
        button.setPrefHeight(76);
        button.setPrefWidth(567);
        TextFlow item = new TextFlow();
        item.setPrefHeight(87);
        item.setPrefWidth(531);
        Label road = new Label("도로명 : " + roadName);
        road.setPrefHeight(37);
        road.setPrefWidth(531);
        road.setId("road");
        Label lot = new Label("지번 : " + lotName);
        lot.setPrefWidth(47);
        lot.setPrefWidth(531);
        item.getChildren().addAll(road, lot);
        button.setGraphic(item);
        button.setOnAction(e -> {
            Label tmp = (Label) ((Node)e.getSource()).lookup("#road");
            resultAddress = tmp.getText();
            Stage stage = (Stage) ResultList.getScene().getWindow();
            stage.close();
        });
        ResultList.getChildren().add(button);
    }

    public String getResultAddress() {
        return resultAddress;
    }
}
