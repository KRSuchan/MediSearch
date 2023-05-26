package api.HospInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HospInfo {
    private final String ServiceKey = "rh9XsZ6XI7IOep5CsaMU1uoUTQsQ04giqb3Z2Wyg8bJNA3EIcD5ex3BhvvS7Ph2y8j%2ByYasz9%2FOaRZcqoj07pA%3D%3D";
    private final String baseUrl ="https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList";
    private String totalCnt = "0";
    private String errCode = "-1";
    private String errMsg = "null";
    private String[][] hospInfoList;
    public int searchHosp(double xPos, double yPos, double radius){
        try {
            int currentPage = 0;
            int countPerPage = 100;
            do{
                currentPage++;
                URL url = new URL(baseUrl+"?serviceKey=" + ServiceKey + "&pageNo=" + currentPage + "&numOfRows=" + countPerPage + "&xPos=" + xPos + "&yPos=" + yPos + "&radius=" + radius);
                InputStream stream = url.openStream();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(stream);

                doc.getDocumentElement().normalize();

                NodeList nList1 = doc.getElementsByTagName("header");

                NodeList nList2 = doc.getElementsByTagName("item");

                NodeList nList3 = doc.getElementsByTagName("body");

                for (int temp = 0; temp < nList3.getLength(); temp++) {
                    Node nNode = nList3.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        totalCnt = getTagValue("totalCount", eElement);
                    }
                }
                if(currentPage==1){
                    hospInfoList = new String[Integer.parseInt(totalCnt)][7];
                }
                for (int temp = 0; temp < nList1.getLength(); temp++) {
                    Node nNode = nList1.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        errCode = getTagValue("resultCode", eElement);
                        errMsg = getTagValue("resultMsg", eElement);
                    }
                }
                if (!errCode.equals("0")){
                    System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                    return -1;
                }else if(totalCnt.equals("0")){
                    System.out.println("검색결과 없음");
                    return 1;
                }else {
                    for (int temp = 0; temp < nList2.getLength(); temp++) {
                        Node nNode = nList2.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            int currRow = temp+(currentPage-1)*countPerPage;
                            hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
                            hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
                            hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
                            hospInfoList[currRow][3]=getTagValue("addr", eElement);
                            hospInfoList[currRow][4]=getTagValue("distance", eElement);
                            hospInfoList[currRow][5]=getTagValue("telno", eElement);
                            hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);
                        }
                    }
                }
            }while((Integer.parseInt(totalCnt)/countPerPage+1 != currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    public int searchHosp(double xPos, double yPos, double radius, int dgsbjtCd){
        try {
            int currentPage = 0;
            int countPerPage = 100;
            do{
                currentPage++;
                URL url = new URL(baseUrl+"?serviceKey=" + ServiceKey + "&pageNo=" + currentPage + "&numOfRows=" + countPerPage + "&xPos=" + xPos + "&yPos=" + yPos + "&radius=" + radius + "&dgsbjtCd" + dgsbjtCd);
                InputStream stream = url.openStream();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(stream);

                doc.getDocumentElement().normalize();

                NodeList nList1 = doc.getElementsByTagName("header");

                NodeList nList2 = doc.getElementsByTagName("item");

                NodeList nList3 = doc.getElementsByTagName("body");

                for (int temp = 0; temp < nList3.getLength(); temp++) {
                    Node nNode = nList3.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        totalCnt = getTagValue("totalCount", eElement);
                    }
                }
                if(currentPage==1){
                    hospInfoList = new String[Integer.parseInt(totalCnt)][7];
                }
                for (int temp = 0; temp < nList1.getLength(); temp++) {
                    Node nNode = nList1.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        errCode = getTagValue("resultCode", eElement);
                        errMsg = getTagValue("resultMsg", eElement);
                    }
                }
                if (!errCode.equals("0")){
                    System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                    return -1;
                }else if(totalCnt.equals("0")){
                    System.out.println("검색결과 없음");
                    return 1;
                }else {
                    for (int temp = 0; temp < nList2.getLength(); temp++) {
                        Node nNode = nList2.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            int currRow = temp+(currentPage-1)*countPerPage;
                            hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
                            hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
                            hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
                            hospInfoList[currRow][3]=getTagValue("addr", eElement);
                            hospInfoList[currRow][4]=getTagValue("distance", eElement);
                            hospInfoList[currRow][5]=getTagValue("telno", eElement);
                            hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);
                        }
                    }
                }
            }while((Integer.parseInt(totalCnt)/countPerPage+1 != currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public int getErrCode() {
        return Integer.parseInt(errCode);
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getTotalCnt() {
        return Integer.parseInt(totalCnt);
    }
/*
    hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
    hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
    hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
    hospInfoList[currRow][3]=getTagValue("addr", eElement);
    hospInfoList[currRow][4]=getTagValue("distance", eElement);
    hospInfoList[currRow][5]=getTagValue("telno", eElement);
    hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);  */
    public String getYadmNm(int numOfRow){
        return hospInfoList[numOfRow][0];
    }
    public String getClCdNm(int numOfRow){
        return hospInfoList[numOfRow][0];
    }
    public String getAddr(int numOfRow){
        return hospInfoList[numOfRow][0];
    }
    public double getDistance(int numOfRow){
        return Double.parseDouble(hospInfoList[numOfRow][0]);
    }
    public String getTelno(int numOfRow){
        return hospInfoList[numOfRow][0];
    }
    public String getHospUrl(int numOfRow){
        return hospInfoList[numOfRow][0];
    }

    private static String getTagValue(String tag, Element eElement) {
        try{
            NodeList nList =eElement.getElementsByTagName(tag).item(0).getChildNodes();
            Node nValue = (Node) nList.item(0);
            if(nValue==null)
                return "";
            return nValue.getNodeValue();
        }catch (NullPointerException e){
            return "";
        }
    }
}
