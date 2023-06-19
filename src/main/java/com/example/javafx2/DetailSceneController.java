package com.example.javafx2;

import Objects.HospitalDetail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.locationtech.proj4j.datum.Grid;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DetailSceneController implements Initializable {

    @FXML
    private GridPane DetailGridPane;
    @FXML
    private Label HospitalNameLabel;
    @FXML
    private Label AddressLabel;
    @FXML
    private Label KindLabel;
    @FXML
    private Label TelLabel;
    @FXML
    private Hyperlink HomepageLabel;
    @FXML
    private ScrollPane SubjectPane;
    @FXML
    private ScrollPane OperationPane;
    @FXML
    private Label SunTrmtLabel;
    @FXML
    private Label HolTrmtLabel;
    private final int URL_ROW = 4;
    private final int OPERATION_ROW = 5;
    private final int SUN_TRMT_ROW = 6;
    private final int HOL_TRMT_ROW = 7;

    public void initData(HospitalDetail detail) {
        HospitalNameLabel.setText(detail.getYadmNm());
        AddressLabel.setText(detail.getYAddress());
        KindLabel.setText(detail.getClcdNm());
        TelLabel.setText(detail.getTelNo());

        GridPane subjectGrid = new GridPane();
        subjectGrid.setVgap(10);
        subjectGrid.setHgap(10);
        subjectGrid.setPadding(new Insets(5, 5, 5, 5));
        int row = 0, col = 0;
        for (String dept : detail.getDepts()) {
            Label subjectLabel = new Label(dept);
            subjectGrid.add(subjectLabel, col++, row);
            if(col > 4) {
                col = 0;
                row++;
            }
        }
        SubjectPane.setContent(subjectGrid);

        if(detail.getNoTrmtHoli().equals("null"))
            DetailGridPane.getRowConstraints().remove(HOL_TRMT_ROW);
        else HolTrmtLabel.setText(detail.getNoTrmtHoli());

        if(detail.getNoTrmtSun().equals("null"))
            DetailGridPane.getRowConstraints().remove(SUN_TRMT_ROW);
        else SunTrmtLabel.setText(detail.getNoTrmtSun());

        GridPane operationGrid = new GridPane();
        operationGrid.setVgap(2);
        operationGrid.setHgap(2);
        operationGrid.setPadding(new Insets(5, 5, 5, 5));
        row = 0; col = 0;
        String[] OperationTime = new String[7];
        Arrays.fill(OperationTime, "");
        if(!detail.getTrmtMonStart().equals("null")) {
            OperationTime[0] = "월 " + detail.getTrmtMonStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtMonEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        if(!detail.getTrmtTueStart().equals("null")) {
            OperationTime[1] = "화 " + detail.getTrmtTueStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtTueEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        if(!detail.getTrmtWedStart().equals("null")) {
            OperationTime[2] = "수 " + detail.getTrmtWedStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtWedEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        if(!detail.getTrmtThuStart().equals("null")) {
            OperationTime[3] = "목 " + detail.getTrmtThuStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtThuEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        if(!detail.getTrmtFriStart().equals("null")) {
            OperationTime[4] = "금 " + detail.getTrmtFriStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtFriEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        if(!detail.getTrmtSatStart().equals("null")) {
            OperationTime[5] = "토 " + detail.getTrmtSatStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtSatEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        if(!detail.getTrmtSunStart().equals("null")) {
            OperationTime[6] = "일 " + detail.getTrmtSunStart().replaceFirst("(\\d{2})(\\d{2})", "$1:$2") +
                    "-" + detail.getTrmtSunEnd().replaceFirst("(\\d{2})(\\d{2})", "$1:$2");
        }
        for (int i = 0; i < OperationTime.length; i++) {
            if (!OperationTime[i].isBlank()) {
                operationGrid.add(new Label(OperationTime[i]), col, row++);
                if(!detail.getLunchWeek().equals("null") && (i < 5))
                    operationGrid.add(new Label("\t" + detail.getLunchWeek() + " 점심시간"), col, row++);
                else if(!detail.getLunchSat().equals("null") && (i == 5))
                    operationGrid.add(new Label("\t" + detail.getLunchSat() + " 점심시간"), col, row++);
            }
        }
        if(operationGrid.getChildren().isEmpty()) {
            DetailGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == OPERATION_ROW);
            System.out.println("operationGrid is empty");
        }
        else OperationPane.setContent(operationGrid);

        if(detail.getHospUrl().equals("null")) {
            DetailGridPane.getRowConstraints().remove(URL_ROW);
            System.out.println("urlN: " + detail.getHospUrl());
        }
        else {
            HomepageLabel.setText(detail.getHospUrl());
            System.out.println("url: " + detail.getHospUrl());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
