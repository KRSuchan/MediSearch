package api.MyPos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;

    //  시작 url : https://business.juso.go.kr/addrlink/addrCoordApi.do

    /*  입력 파라미터
        Parameter   /   Type        /   필수 여부    /   내용
        confmkey    /   String      /   Y   /   인증키
        admCd       /   String      /   Y   /   행정구역코드
        rnMgtSn     /   String      /   Y   /   도로명코드
        udrtYn      /   String      /   Y   /   지하여부 0:지상, 1:지하
        buldMnnm    /   Number      /   Y   /   건물본번
        buldSlno    /   Number      /   Y   /   건물부번
        resultType  /   String      /   N   /   xml(default) | json : !!xml 사용!!
    */

public class MyPos {
    private String confmKey = "U01TX0FVVEgyMDIzMDQyOTEyNDA0OTExMzczMzQ=";
    private String baseUrl = "https://business.juso.go.kr/addrlink/addrCoordApi.do";
    public void printMyXY(String admCd, String rnMgtSn, String udrtYn, int buldMnnm, int buldSlno){
        try {
            URL url = new URL(baseUrl+"?admCd="+admCd+"&rnMgtSn="+rnMgtSn+"&udrtYn="+udrtYn+"&buldMnnm="+buldMnnm+"&buldSlno="+buldSlno+"&confmKey="+confmKey);
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("common");

            NodeList nList2 = doc.getElementsByTagName("juso");

            for (int temp = 0; temp < nList1.getLength(); temp++) {
                Node nNode = nList1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("totalCount : " + getTagValue("totalCount", eElement));
                    System.out.println("errorCode : " + getTagValue("errorCode", eElement));
                    System.out.println("errorMessage : " + getTagValue("errorMessage", eElement));
                }
            }

            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("entX : "+getTagValue("entX", eElement));
                    System.out.println("entY : "+getTagValue("entY", eElement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public double[] getMyXY(String admCd, String rnMgtSn, String udrtYn, int buldMnnm, int buldSlno){
        try {
            URL url = new URL(baseUrl+"?admCd="+admCd+"&rnMgtSn="+rnMgtSn+"&udrtYn="+udrtYn+"&buldMnnm="+buldMnnm+"&buldSlno="+buldSlno+"&confmKey="+confmKey);
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            NodeList nList2 = doc.getElementsByTagName("juso");

            double[] xy = new double[2];

            for (int temp = 0; temp < nList2.getLength(); temp++) {
                Node nNode = nList2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    xy[0] = Double.parseDouble(getTagValue("entX", eElement));
                    xy[1] = Double.parseDouble(getTagValue("entY", eElement));
                }
            }
            return xy;
        } catch (Exception e) {
            double[] none = {-1, -1};
            e.printStackTrace();
            return none;
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
