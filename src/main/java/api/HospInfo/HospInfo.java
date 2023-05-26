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
    private String ServiceKey = "rh9XsZ6XI7IOep5CsaMU1uoUTQsQ04giqb3Z2Wyg8bJNA3EIcD5ex3BhvvS7Ph2y8j%2ByYasz9%2FOaRZcqoj07pA%3D%3D";
    private String baseUrl ="https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList";
    public ArrayList<double[]> getXYPos(int pageNo, int numOfRows, double xPos, double yPos, double radius){
        ArrayList<double[]> XYPoss = new ArrayList<double[]>();
        try {
            URL url = new URL(baseUrl+"?serviceKey=" + ServiceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&sidoCd=110000&sgguCd=110019&emdongNm=%EC%8B%A0%EB%82%B4%EB%8F%99&yadmNm=%EC%84%9C%EC%9A%B8%EC%9D%98%EB%A3%8C%EC%9B%90&zipCd=2010&clCd=11&dgsbjtCd=01&xPos=" + xPos + "&yPos=" + yPos + "&radius=" + radius);
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("header");

            NodeList nList2 = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList1.getLength(); temp++) {
                Node nNode = nList1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("resultCode : " + getTagValue("resultCode", eElement));
                    System.out.println("resultMsg : " + getTagValue("resultMsg", eElement));
                }
            }

            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    double[] xyPos = new double[2];
                    xyPos[0] = Double.parseDouble(getTagValue("XPos", eElement));
                    xyPos[1] = Double.parseDouble(getTagValue("YPos", eElement));
                    XYPoss.add(xyPos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return XYPoss;
    }
    public void getAll(int pageNo, int numOfRows, double xPos, double yPos, double radius){
        try {
            URL url = new URL(baseUrl+"?serviceKey=" + ServiceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&xPos=" + xPos + "&yPos=" + yPos + "&radius=" + radius);
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("header");

            NodeList nList2 = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList1.getLength(); temp++) {
                Node nNode = nList1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("resultCode : " + getTagValue("resultCode", eElement));
                    System.out.println("resultMsg : " + getTagValue("resultMsg", eElement));
                }
            }
            System.out.println("----------------------------------");

            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("ykiho : "+getTagValue("ykiho", eElement));
                    System.out.println("yadmNm : "+getTagValue("yadmNm", eElement));
                    System.out.println("Address : "+getTagValue("addr", eElement));
                    System.out.println("distance : "+getTagValue("distance", eElement));
                    System.out.println("XPos : "+getTagValue("XPos", eElement));
                    System.out.println("YPos : "+getTagValue("YPos", eElement));
                    System.out.println("----------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getTagValue(String tag, Element eElement) {
        NodeList nList =eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nList.item(0);
        if(nValue==null)
            return null;
        return nValue.getNodeValue();
    }
}
