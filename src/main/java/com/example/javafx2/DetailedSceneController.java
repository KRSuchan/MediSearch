package com.example.javafx2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DetailedSceneController {

    @FXML
    TextArea reusltInfo;
    public void initData(ResultData resultData){
        reusltInfo.setText(resultData.getName()+" "+resultData.getAddress());
    }
}
