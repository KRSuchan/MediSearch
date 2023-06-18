package com.example.javafx2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController {
    @FXML
    private TitledPane MediSelectPane;

    @FXML
    private ScrollPane MediSelectScroll;

    @FXML
    private MenuButton SizeSelect;

    @FXML
    private TextField AddInput;

    @FXML
    private Slider DistanceSlider;

    @FXML
    private Label DistanceLabel;

    @FXML
    private Button SearchBtn;

    private String[][] subjectLists = {
            {"외과", "정형외과", "신경외과", "심장혈관흉부외과", "성형외과", "마취통증의학과", "재활의학과"},
            {"내과", "내과", "신경과", "병리과"},
            {"치과", "치과", "구강악안면외과", "치과보철과", "치과교정과", "소아치과", "치주과", "치과보존과", "구강내과", "영상치의학과", "구강병리과", "예방치과", "통합치의학과"},
            {"이비인후과", "이비인후과"},
            {"비뇨의학과", "비뇨의학과"},
            {"피부과", "피부과"},
            {"안과", "안과"},
            {"산부인과", "산부인과"},
            {"소아청소년과", "소아청소년과"},
            {"한의과", "한방내과", "한방부인과", "한방소아과", "한방신경정신과", "한방안·이비인후·피부과", "침구과", "한방재활의학과", "사상체질과", "한방응급"},
            {"정신과", "정신건강의학과"},
            {"가정의학과", "가정의학과"},
            {"기타", "영상의학과", "방사선종양학과", "진단검사의학과", "결핵과", "핵의학과", "응급의학과", "직업환경의학과", "예방의학과"}
    };

    private String[] sizes = {
            "상급종합병원", "종합병원", "병원", "요양병원", "정신병원", "의원", "치과병원",
            "치과의원", "조산원", "보건소", "보건지소", "보건진료소", "모자보건센타", "보건의료원",
            "약국", "한방종합병원", "한방병원", "한의원", "한약방"
    };

    private List<CheckBox> checkBoxList;

    public void initialize() {
        Label label = new Label("진료 과목 선택");
        MediSelectPane.setGraphic(label);
        MediSelectPane.setExpanded(false);
        int columnIdx = 0, rowIdx = 0;
        checkBoxList = new ArrayList<>();
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        for (String[] subjectList : subjectLists) {
            grid.add(new Text("- " + subjectList[0] + " -"),columnIdx, rowIdx++);
            for (int i = 1; i < subjectList.length; i++) {
                CheckBox checkBox = new CheckBox(subjectList[i]);

                checkBoxList.add(checkBox);
                if(subjectList[i].length() > 8) {
                    grid.add(checkBox, columnIdx++, rowIdx, 2 ,1);
                    columnIdx++;
                }
                else {
                    grid.add(checkBox, columnIdx++, rowIdx);
                }
                if(columnIdx > 3) {
                    columnIdx = 0;
                    rowIdx++;
                }
            }
            columnIdx = 0;
            grid.add(new Text(""), columnIdx, ++rowIdx);
            rowIdx++;
        }
        MediSelectScroll.setContent(grid);

        AddInput.setText("현재 주소를 입력하시오");
        AddInput.setPromptText("현재 주소를 입력하시오");

        for (String size : sizes) {
            MenuItem menuItem = new MenuItem(size);
            menuItem.setOnAction(event -> onSizeSelectAction(menuItem));
            SizeSelect.getItems().add(menuItem);
        }

        DistanceSlider.setMin(0);
        DistanceSlider.setMax(10);
        DistanceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            DistanceLabel.setText(String.format("%.1f", newValue)); // 값을 원하는 형식으로 포맷팅하여 출력
        });
    }

    private void onSizeSelectAction(MenuItem item) {
        SizeSelect.setText(item.getText());
    }

    @FXML
    protected void onAddressSearchAction(ActionEvent event) {
        String selected = ((Node)event.getSource()).getId();
        System.out.println(selected);
        TextField currentTextField = AddInput;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddressScene.fxml"));
            Parent root = fxmlLoader.load();
            AddressSceneController controller = fxmlLoader.getController();
            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Address");
            stage.setScene(scene);
            controller.initialize(currentTextField.getText());
            stage.showAndWait();
            currentTextField.setText(controller.getResultAddress().replace("도로명 : ", ""));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    protected void onSearchBtnClick(ActionEvent event) {
//        for (CheckBox checkBox : checkBoxList) {
//            if (checkBox.isSelected()) {
//                System.out.println(StoreData.convertToMediCode(checkBox.getText()));
//            }
//        }
//        System.out.println(StoreData.convertToSizeCode(SizeSelect.getText()));
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