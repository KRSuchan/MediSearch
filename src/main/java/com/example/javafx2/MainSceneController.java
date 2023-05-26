package com.example.javafx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController {

    @FXML
    private Label SimpleMediLabel;

    @FXML
    private MenuButton SimpleMediSelect;

    @FXML
    private Label AdvMediLabel;

    @FXML
    private MenuButton AdvMediSelect;

    @FXML
    private Spinner<Integer> AdvResultSpin;

    @FXML
    private MenuButton SimpleDaySelect;

    @FXML
    private MenuButton AdvDaySelect;

    @FXML
    private MenuButton AdvSizeSelect;

    @FXML
    private RadioButton AdvDayRadioBtn;

    @FXML
    private RadioButton AdvNowRadioBtn;

    @FXML
    private ToggleGroup Visit;

    private boolean isSimpleMediMode = true; // variable to keep track of current mode
    private boolean isAdvMediMode = true;

    public void initialize() {
        SimpleMediSelect.getItems().setAll(
                new MenuItem("소아과"),
                new MenuItem("이비인후과"),
                new MenuItem("치과")
        );

        AdvMediSelect.getItems().setAll(
                new MenuItem("소아과"),
                new MenuItem("이비인후과"),
                new MenuItem("치과")
        );

        // Set initial values for the AdvResultSpin
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        AdvResultSpin.setValueFactory(valueFactory);

        // Set event listener for menuItems of SimpleMediSelect
        for (MenuItem item : SimpleMediSelect.getItems()) {
            item.setOnAction(event -> onSimpleMediSelectAction(item));
        }

        // Set event listener for menuItems of SimpleDaySelect
        for (MenuItem item : SimpleDaySelect.getItems()) {
            item.setOnAction(event -> onSimpleDaySelectAction(item));
        }

        // Set event listener for menuItems of AdvMediSelect
        for (MenuItem item : AdvMediSelect.getItems()) {
            item.setOnAction(event -> onAdvMediSelectAction(item));
        }

        // Set event listener for menuItems of AdvDaySelect
        for (MenuItem item : AdvDaySelect.getItems()) {
            item.setOnAction(event -> onAdvDaySelectAction(item));
        }

        // Set event listener for menuItems of AdvSizeSelect
        for (MenuItem item : AdvSizeSelect.getItems()) {
            item.setOnAction(event -> onAdvSizeSelectAction(item));
        }

        // Set event listener for ToggleGroup "Visit"
        Visit.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == AdvDayRadioBtn) {
                AdvDaySelect.setDisable(false); // Enable AdvDaySelect
            } else if (newValue == AdvNowRadioBtn) {
                AdvDaySelect.setDisable(true); // Disable AdvDaySelect
            }
        });
    }

    private void onSimpleMediSelectAction(MenuItem item) {
        SimpleMediSelect.setText(item.getText());
    }

    private void onSimpleDaySelectAction(MenuItem item) {
        SimpleDaySelect.setText(item.getText());
    }

    private void onAdvMediSelectAction(MenuItem item) {
        AdvMediSelect.setText(item.getText());
    }

    private void onAdvDaySelectAction(MenuItem item) {
        AdvDaySelect.setText(item.getText());
    }

    private void onAdvSizeSelectAction(MenuItem item) {
        AdvSizeSelect.setText(item.getText());
    }

    @FXML
    void onSimpleMediToggleBtnClick() {
        isSimpleMediMode = !isSimpleMediMode; // toggle the mode

        if (isSimpleMediMode) {
            SimpleMediLabel.setText("진료 과목 설정");
            SimpleMediSelect.setText("진료 과목을 선택하시오.");
            SimpleMediSelect.getItems().setAll(
                    new MenuItem("소아과"),
                    new MenuItem("이비인후과"),
                    new MenuItem("치과")
            );

        } else {
            SimpleMediLabel.setText("증상 설정");
            SimpleMediSelect.setText("증상을 선택하시오.");
            SimpleMediSelect.getItems().setAll(
                    new MenuItem("두통"),
                    new MenuItem("치통"),
                    new MenuItem("복통")
            );
        }

        // Set event listener for menuItems of SimpleMediSelect
        for (MenuItem item : SimpleMediSelect.getItems()) {
            item.setOnAction(event -> onSimpleMediSelectAction(item));
        }
    }

    @FXML
    void onAdvMediToggleBtnClick() {
        isAdvMediMode = !isAdvMediMode; // toggle the mode

        if (isAdvMediMode) {
            AdvMediLabel.setText("진료 과목 설정");
            AdvMediSelect.setText("진료 과목을 선택하시오.");
            AdvMediSelect.getItems().setAll(
                    new MenuItem("소아과"),
                    new MenuItem("이비인후과"),
                    new MenuItem("치과")
            );
        } else {
            AdvMediLabel.setText("증상 설정");
            AdvMediSelect.setText("증상을 선택하시오.");
            AdvMediSelect.getItems().setAll(
                    new MenuItem("두통"),
                    new MenuItem("치통"),
                    new MenuItem("복통")
            );
        }

        // Set event listener for menuItems of AdvMediSelect
        for (MenuItem item : AdvMediSelect.getItems()) {
            item.setOnAction(event -> onAdvMediSelectAction(item));
        }
    }

    @FXML
    private void onAdvResultSpinAction() {
        int value = AdvResultSpin.getValue();
        System.out.println("AdvResultSpin value changed to: " + value);
    }

    @FXML
    protected void onSimpleSearchBtnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(""));
            Scene scene = new Scene(fxmlLoader.load(), 576, 509);
            Stage stage = new Stage();
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {}
    }

    @FXML
    protected void onAdvSearchBtnClick(ActionEvent event) {
        try {

            List<ResultData> resultList =new ArrayList<>();
            resultList.add(new ResultData("성모병원","대천로103번길","010-420-132"));
            resultList.add(new ResultData("성모원2","대천로1","010-4223"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ResultScene.fxml"));
            Parent root = fxmlLoader.load();
            ResultSceneController controller = fxmlLoader.getController();
            controller.initData(resultList);
            Scene scene = new Scene(root, 576, 509);
            Stage stage = new Stage();
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {}
    }


}