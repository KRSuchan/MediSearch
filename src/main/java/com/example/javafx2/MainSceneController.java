package com.example.javafx2;

import Objects.Hospital;
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
import network.NetworkVO;
import network.ReadData;
import network.SendData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static network.NetworkVO.SLEEPTIME;

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

    private String searchedAddress = "";
    private boolean isSearched = false;
    private boolean isCancel = false;

    private List<CheckBox> checkBoxList;
    public static final String HOST = NetworkVO.HOST;
    public static final int PORT = NetworkVO.PORT;
    private Socket cliSocket = null;
    private ObjectInputStream ois = null;

    public void initialize() {
        try {
            cliSocket = new Socket(HOST, PORT);
            System.out.println("Connection successful");
            System.out.println("ois is null ? in MainScene initialize() : " + ois);
            if (ois == null) {
                InputStream is = cliSocket.getInputStream();
                ois = new ObjectInputStream(is);
            }
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
                grid.add(new Text("- " + subjectList[0] + " -"), columnIdx, rowIdx++);
                for (int i = 1; i < subjectList.length; i++) {
                    CheckBox checkBox = new CheckBox(subjectList[i]);

                    checkBoxList.add(checkBox);
                    if (subjectList[i].length() > 8) {
                        grid.add(checkBox, columnIdx++, rowIdx, 2, 1);
                        columnIdx++;
                    } else {
                        grid.add(checkBox, columnIdx++, rowIdx);
                    }
                    if (columnIdx > 3) {
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
            searchedAddress = "";

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
        }catch (IOException e){
            System.out.println("IOException in initialize(), MainSceneController");
            System.err.println(e);
        }
    }

    private void onSizeSelectAction(MenuItem item) {
        SizeSelect.setText(item.getText());
    }

    @FXML
    protected void onAddressSearchAction(ActionEvent event) {
        String selected = ((Node)event.getSource()).getId();
        System.out.println(selected);
        TextField currentTextField = AddInput;
        isCancel = false;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddressScene.fxml"));
            Parent root = fxmlLoader.load();
            AddressSceneController controller = fxmlLoader.getController();
            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Address");
            stage.setScene(scene);
            stage.setOnCloseRequest(event1 -> {
                isCancel = true;
            });
            controller.initialize(currentTextField.getText(), cliSocket, ois);
            stage.showAndWait();
            if(isCancel) {
                currentTextField.setText(searchedAddress);
                return;
            }
            searchedAddress = controller.getResultAddress().replace("도로명 : ", "");
            currentTextField.setText(searchedAddress);
            if(!searchedAddress.equals(""))
                isSearched = true;
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSearchBtnClick(ActionEvent event) throws InterruptedException {
        int mediCodeLen = 0;
        String distance = String.valueOf((int)Math.round(Double.parseDouble(DistanceLabel.getText()) * 1000));
        String mediCode = "";
        String kindCode = StoreData.convertToSizeCode(SizeSelect.getText()) + "/=/";
        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                mediCode += StoreData.convertToMediCode(checkBox.getText()) + "/=/";
                mediCodeLen++;
            }
        }

        String totalInfo;
        if(kindCode.equals("/=/")) {
            if(mediCodeLen != 0)
                totalInfo = "02/=/" + AddInput.getText() + "/=/" + mediCodeLen + "/=/" + mediCode + distance;
            else
                totalInfo = "05/=/" + AddInput.getText() + "/=/" + distance;
        } else {
            if (mediCodeLen != 0)
                totalInfo = "03/=/" + AddInput.getText() + "/=/" + mediCodeLen + "/=/" + mediCode + kindCode + distance;
            else
                totalInfo = "04/=/" + AddInput.getText() + "/=/" + kindCode + distance;
        }

        System.out.println("Send Data in onSearchBtnClick, MainSceneController : "+totalInfo);

        // TODO : 서버 전송 및 수신(+병원 객체 생성) 위치 (전송 STRING : totalInfo)
//
//        SendData sd = new SendData(cliSocket);
//        sd.run(totalInfo);
//
//        ReadData rd = new ReadData(cliSocket, ois);
//        rd.start();
//        Thread.sleep(SLEEPTIME+3000);
//        System.out.println("under the rd.start in MainSceneController onSearchBtnClick");
//        Hospital[] hospitals = (Hospital[]) rd.getData();
//        int totalHospitalsCnt = hospitals.length;
//        System.out.println(totalHospitalsCnt + " hospitals are Founded.");
        // 서버 전송 및 수신(+객체 생성) 종료

        Hospital tmp1 = new Hospital();
        tmp1.setYkiho("yKiho1");
        tmp1.setYadmNm("임시병원1");
        tmp1.setClcdNm("대학병원");
        tmp1.setYAddress("구미시");
        Hospital tmp2 = new Hospital();
        tmp2.setYkiho("yKiho2");
        tmp2.setYadmNm("임시병원2");
        tmp2.setClcdNm("대학병원");
        tmp2.setYAddress("구미시");
        tmp2.setDistance(9.154832);
        Hospital[] hospitals = {tmp1, tmp2};
        System.out.println(totalInfo);

        if(!isSearched) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("검색 오류");
            alert.setHeaderText("주소 검색 미시행");
            alert.setContentText("주소를 검색하십시오");
            alert.showAndWait();
            return;
        } else if(AddInput.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("검색 오류");
            alert.setHeaderText("주소 미입력");
            alert.setContentText("주소를 입력하십시오");
            alert.showAndWait();
            return;
        } else if(!AddInput.getText().equals(searchedAddress)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("검색 오류");
            alert.setHeaderText("현재 주소 항목 변경 감지");
            alert.setContentText("현재 주소를 검색한 후 변경하지 마십시오");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ResultScene.fxml"));
            Parent root = fxmlLoader.load();
            ResultSceneController controller = fxmlLoader.getController();
            controller.initData(hospitals);
            Scene scene = new Scene(root, 576, 509);
            Stage stage = new Stage();
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {}
    }
}