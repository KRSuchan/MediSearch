package com.example.javafx2;

import Objects.Address;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import network.NetworkVO;
import network.ReadData;
import network.SendData;

import java.io.ObjectInputStream;
import java.net.Socket;

public class AddressSceneController {
    private String resultAddress;

    @FXML
    private TextField SearchTextField;

    @FXML
    private TextFlow ResultList;

    @FXML
    private Button SearchBtn;

    Socket cliSocket = null;
    private ObjectInputStream ois;
    private int totalAddressCnt = 0;
    public static final int SLEEPTIME= NetworkVO.SLEEPTIME;

    public void initialize(String input, Socket cliSocket, ObjectInputStream ois) throws InterruptedException {
        this.ois = ois;
        this.cliSocket = cliSocket;
        makeAddressBtns(input, cliSocket, ois);
    }
    private void makeAddressBtns(String input, Socket cliSocket, ObjectInputStream ois) throws InterruptedException{
        if (!input.equals("") && !input.contains("/") && !input.contains("=")) {
            try {
                resultAddress = new String();
                SearchTextField.setText(input);
                SendData sd = new SendData(cliSocket);
                input = "01/=/" + input;
                sd.run(input);
                ReadData rd = new ReadData(cliSocket, ois);
                rd.start();
                Thread.sleep(SLEEPTIME);
                System.out.println("under the rd.start in AddressScene initialize");
                Address[] addresses = (Address[]) rd.getData();
                this.totalAddressCnt = addresses.length;
//            System.out.println("주소 개수"+addrTotalCnt);
                System.out.println(totalAddressCnt + " addresses Founded.");
                ResultList.getChildren().clear();
                for (int i = 0; i < totalAddressCnt; i++) {
                    addItemInResultList(addresses[i].getRoadAddress(), addresses[i].getJibunAddress());
                }
            } catch (ClassCastException e) {
                System.out.println("ClassCastException in initialize, AddressScene");
                e.printStackTrace();
            } catch (RuntimeException e) {
                System.out.println("RuntimeException in initialize, AddressScene");
                e.printStackTrace();
            }
        }else {
            System.out.println("null");
        }
    }

    @FXML
    protected void onSearchBtnClick() throws InterruptedException {

        String input = SearchTextField.getText();
        // function call by using input String and get Address info
        makeAddressBtns(input, cliSocket, ois);
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
