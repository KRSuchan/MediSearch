package api.HospDetailInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;

public class HospDetail {

    private String ServiceKey = "rh9XsZ6XI7IOep5CsaMU1uoUTQsQ04giqb3Z2Wyg8bJNA3EIcD5ex3BhvvS7Ph2y8j%2ByYasz9%2FOaRZcqoj07pA%3D%3D";
    private String baseUrl = "https://apis.data.go.kr/B551182/MadmDtlInfoService2/getEqpInfo2";
    public void getTelNum(String ykiho, int pageNo, int numOfRows){
        try {
            URL url = new URL(baseUrl+"?serviceKey="+ServiceKey+"&ykiho="+ykiho+"&pageNo="+pageNo+"&numOfRows="+numOfRows+"&_type=xml");
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
                    System.out.println("yadmNm : "+getTagValue("yadmNm", eElement));
                    System.out.println("telno : "+getTagValue("telno", eElement));
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
