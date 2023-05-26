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
    private String baseUrl = "https://apis.data.go.kr/B551182/MadmDtlInfoService2/";
    private String totalCnt = "0";
    private String errCode = "-1";
    private String errMsg = "null";
    private String address = "null";
    private String clCdNm = "null";
    private String estbDd = "null";
    private String telNum = "null";
    private String yadmNm = "null";
    private String[] deptlist;
    public int getDepts(String ykiho){
        String deptUrl = baseUrl+"getDgsbjtInfo2";
        try {
            int currentPage = 0;
            int countPerPage = 100;
            do{
                currentPage++;
                URL url = new URL(deptUrl+"?serviceKey=" + ServiceKey+"&ykiho="+ykiho + "&pageNo=" + currentPage + "&numOfRows=" + countPerPage+"&_type=xml");
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
                    deptlist = new String[Integer.parseInt(totalCnt)];
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
                            deptlist[currRow]=getTagValue("ykiho", eElement);
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

    public int searchHospDetail(String ykiho){
        String telUrl = baseUrl+"getEqpInfo2";
        try {
            URL url = new URL(telUrl+"?serviceKey=" + ServiceKey + "&pageNo=" + 1 + "&numOfRows=" + 1);
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
                        address=getTagValue("addr", eElement);
                        clCdNm=getTagValue("clCdNm", eElement);
                        estbDd=getTagValue("estbDd", eElement);
                        telNum=getTagValue("telno", eElement);
                        yadmNm=getTagValue("yadmNm", eElement);
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public String[] getDeptlist() {
        return deptlist;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getAddress() {
        return address;
    }

    public String getClCdNm() {
        return clCdNm;
    }

    public int getErrCode() {
        return Integer.parseInt(errCode);
    }

    public int getEstbDd() {
        return Integer.parseInt(estbDd);
    }

    public String getTelNum() {
        return telNum;
    }

    public int getTotalCnt() {
        return Integer.parseInt(totalCnt);
    }

    public String getYadmNm() {
        return yadmNm;
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
