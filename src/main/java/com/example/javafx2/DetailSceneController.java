package com.example.javafx2;

import Objects.HospitalDetail;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import network.ReadData;
import network.SendData;
import org.locationtech.proj4j.datum.Grid;

import java.net.URI;
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
        subjectGrid.setVgap(5);
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

        if(!detail.getNoTrmtHoli().equals("null"))
            HolTrmtLabel.setText(detail.getNoTrmtHoli());

        if(!detail.getNoTrmtSun().equals("null"))
            SunTrmtLabel.setText(detail.getNoTrmtSun());

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
        if(!operationGrid.getChildren().isEmpty())
            OperationPane.setContent(operationGrid);

        if(!detail.getHospUrl().equals("null")) {
            HomepageLabel.setText(detail.getHospUrl());
            HomepageLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() > 1) {
                        try {
                            open(HomepageLabel.getText());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void open(String url) throws Exception{
        URI u = new URI(url);
        java.awt.Desktop.getDesktop().browse(u);
    }
}
