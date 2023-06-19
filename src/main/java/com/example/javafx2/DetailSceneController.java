package com.example.javafx2;

import Objects.HospitalDetail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private AnchorPane SubjectPane;
    @FXML
    private ScrollPane OperationPane;
    @FXML
    private Label SunTrmtLabel;
    @FXML
    private Label HolTrmtLabel;

    private HospitalDetail detail;
    private final int URL_ROW = 4;
    private final int OPERATION_ROW = 5;
    private final int SUN_TRMT_ROW = 6;
    private final int HOL_TRMT_ROW = 7;

    public void initData(HospitalDetail detail) {
        this.detail = detail;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HospitalNameLabel.setText(detail.getYadmNm());
        AddressLabel.setText(detail.getYAddress());
        KindLabel.setText(detail.getClcdNm());
        TelLabel.setText(detail.getTelNo());
        for (String dept : detail.getDepts()) {
            Label subjectLabel = new Label(dept);
            SubjectPane.getChildren().add(subjectLabel);
        }

        if(detail.getNoTrmtHoli().equals("null"))
            DetailGridPane.getRowConstraints().remove(HOL_TRMT_ROW);
        else HolTrmtLabel.setText(detail.getNoTrmtHoli());

        if(detail.getNoTrmtSun().equals("null"))
            DetailGridPane.getRowConstraints().remove(SUN_TRMT_ROW);
        else SunTrmtLabel.setText(detail.getNoTrmtSun());

        AnchorPane operationAnchor = new AnchorPane();
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
                operationAnchor.getChildren().add(new Label(OperationTime[i]));
                if(!detail.getLunchWeek().equals("null") && (i < 5))
                    operationAnchor.getChildren().add(new Label(detail.getLunchWeek() + " 점심시간"));
                else if(!detail.getLunchSat().equals("null") && (i == 5))
                    operationAnchor.getChildren().add(new Label(detail.getLunchSat() + " 점심시간"));
            }
        }
        if(operationAnchor.getChildren().isEmpty())
            DetailGridPane.getRowConstraints().remove(OPERATION_ROW);
        else OperationPane.setContent(operationAnchor);

        if(detail.getHospUrl().equals("null"))
            DetailGridPane.getRowConstraints().remove(URL_ROW);
        else {
            HomepageLabel.setText(detail.getHospUrl());
        }
    }

    private Stage getHostWindow(Hyperlink hyperlink) {
        return (Stage) hyperlink.getScene().getWindow();
    }
}
