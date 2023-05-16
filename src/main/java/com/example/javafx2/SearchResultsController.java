package com.example.javafx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class SearchResultsController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void makeBtnClicked(ActionEvent event) {
        Label newLabelN = new Label("마이구미 병원");
        newLabelN.setPrefWidth(466.0);
        newLabelN.setPrefHeight(50.0);
        newLabelN.setAlignment(Pos.CENTER);
        newLabelN.setFont(new Font(14.0));

        Label newLabelD = new Label("경북 구미시 마이대로 61");
        newLabelD.setStyle("-fx-background-color: #cfd3d8;"); // set the background color to white
        newLabelD.setPrefWidth(466.0);
        newLabelD.setPrefHeight(50.0);
        newLabelD.setAlignment(Pos.CENTER_LEFT);
        newLabelD.setFont(new Font(12.0));
        newLabelD.setPadding(new Insets(0, 0, 0, 20)); // set left padding to 20

        TextFlow textFlow = (TextFlow) scrollPane.getContent().lookup("#textFlow");
        textFlow.getChildren().addAll(newLabelN, newLabelD);
    }
}