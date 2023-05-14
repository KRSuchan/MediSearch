package api.HospitalAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HospitalData {
    public ArrayList<double[]> getXYPos(int pageNo, int numOfRows, double xPos, double yPos, double radius){
        String ServiceKey = "rh9XsZ6XI7IOep5CsaMU1uoUTQsQ04giqb3Z2Wyg8bJNA3EIcD5ex3BhvvS7Ph2y8j%2ByYasz9%2FOaRZcqoj07pA%3D%3D";
        // pageNo = 1, numOfRows = 10, xPos = 127.09854004628151, yPos = 37.6132113197367, radius = 3000
        ArrayList<double[]> XYPoss = new ArrayList<double[]>();
        try {
//          https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?serviceKey=rh9XsZ6XI7IOep5CsaMU1uoUTQsQ04giqb3Z2Wyg8bJNA3EIcD5ex3BhvvS7Ph2y8j%2ByYasz9%2FOaRZcqoj07pA%3D%3D&pageNo=1&numOfRows=10&xPos=127.09854004628151&yPos=37.6132113197367&radius=3000
            URL url = new URL("https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?serviceKey=" + ServiceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&sidoCd=110000&sgguCd=110019&emdongNm=%EC%8B%A0%EB%82%B4%EB%8F%99&yadmNm=%EC%84%9C%EC%9A%B8%EC%9D%98%EB%A3%8C%EC%9B%90&zipCd=2010&clCd=11&dgsbjtCd=01&xPos=" + xPos + "&yPos=" + yPos + "&radius=" + radius);
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

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
    private static String getTagValue(String tag, Element eElement) {
        NodeList nList =eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nList.item(0);
        if(nValue==null)
            return null;
        return nValue.getNodeValue();
    }
}
