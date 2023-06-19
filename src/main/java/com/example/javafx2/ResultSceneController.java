package com.example.javafx2;

import Objects.Hospital;
import Objects.HospitalDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import network.ReadData;
import network.SendData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResultSceneController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private Label resultLabel;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn kindCol;
    @FXML
    private TableColumn distanceCol;

    private ArrayList<String> ykihoArray;

    private Socket cliSocket = null;
    private ObjectInputStream ois = null;
    ObservableList data = FXCollections.observableArrayList();


    public void initData(Hospital[] hospitals, Socket cliSocket, ObjectInputStream ois) {
        this.cliSocket = cliSocket;
        this.ois = ois;
        for (Hospital hospital : hospitals) {
            ykihoArray.add(hospital.getYkiho());
        }
        data.addAll(hospitals);
        resultLabel.setText(data.size() + "개의 병원이 검색되었습니다");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ykihoArray = new ArrayList<String>();
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() > 1) {
                    String totalInfo = "06/=/" + ykihoArray.get(tableView.getSelectionModel().getSelectedIndex());
                    System.out.println(totalInfo);
                    // 서버 전송 및 수신(+병원상세 객체 생성)
                    SendData sd = new SendData(cliSocket);
                    sd.run(totalInfo);

                    ReadData rd = new ReadData(cliSocket, ois);
                    rd.run();
                    System.out.println("under the rd.start in ResultSceneController onDetailBtnClick");
                    HospitalDetail detail = (HospitalDetail) rd.getData();
                    System.out.println("under the hospitalDetail new Object");
                    // 서버 전송 및 수신(+객체 생성) 종료

                    onTableItemDoubleClick(detail);
                }
            }
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("yadmNm"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("yAddress"));
        kindCol.setCellValueFactory(new PropertyValueFactory<>("clcdNm"));
        distanceCol.setCellValueFactory(new PropertyValueFactory<>("distanceStr"));

        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%555
        // 실행 안될 시 제거할 부분
        nameCol.setCellFactory(column -> {
            return new TableCell<Hospital, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setAlignment(Pos.CENTER);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        addressCol.setCellFactory(column -> {
            return new TableCell<Hospital, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setAlignment(Pos.CENTER);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        kindCol.setCellFactory(column -> {
            return new TableCell<Hospital, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setAlignment(Pos.CENTER);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        distanceCol.setCellFactory(column -> {
            return new TableCell<Hospital, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setAlignment(Pos.CENTER_LEFT);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER_LEFT);
                    }
                }
            };
        });
        // 이 주석 사이에 있는 코드 전부 삭제
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

        tableView.setItems(data);
    }

    void onTableItemDoubleClick(HospitalDetail detail) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailScene.fxml"));
            Parent root = fxmlLoader.load();
            DetailSceneController controller = fxmlLoader.getController();
            controller.initData(detail);
            Scene scene = new Scene(root, 627, 547);
            Stage stage = new Stage();
            stage.setTitle("Details");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
