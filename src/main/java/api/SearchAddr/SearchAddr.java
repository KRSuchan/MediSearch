package api.SearchAddr;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

//  주소검색API 기본 주소 : https://www.juso.go.kr/addrlink/addrLinkApi.do?
    /*  주소검색API 파라미터
        Parameter   /   Type        /   필수여부    /   내용
        confmkey    /   String      /   Y   /   U01TX0FVVEgyMDIzMDQyOTEyNDg0NTExMzczMzU=
        currentPage /   Number      /   Y   /   1(default) 현재 페이지 번호 !! 기본 1 사용 !!
        countPerPage/   Number      /   Y   /   10(default) 페이지당 출력 결과 row 수 !! 3 사용하되 첫 row만 출력 !!
        keyword     /   String      /   Y   /   주소 검색어
        resultType  /   String      /   N   /   xml(default) | json : !!xml 사용!!
        ================ 상단까지 사용 ================
        hstryYn     /   String      /   N   /   N(default) 변동된 주소정보 포함 여부
        firstSort   /   String      /   N   /   none(default) 정확도 정렬
        addInfoYn   /   String      /   N   /   N(default) 출력결과에 추가항목 추가 여부

        주소검색API 리턴 데이터
        admCd    /   String  /   행정구역코드
        rnMgtSn  /   String  /   도로명코드
        udrtYn   /   String  /   지하여부
        buldMnnm /   Number  /   건물본번
        buldSlno /   Number  /   건물부번
     */

public class SearchAddr {
    private String confmkey = "U01TX0FVVEgyMDIzMDQyOTEyNDg0NTExMzczMzU=";
    private String baseUrl = "https://www.juso.go.kr/addrlink/addrLinkApi.do";
    private String totalCnt = "0";
    private String errCode = "-1";
    private String errMsg = "null";
    private ArrayList<String[]> addressLists  = new ArrayList<>();
    /*
    * addressLists[n번째리스트][의 0:행정구역코드 1: 도로명코드 2: 지하여부 3: 건물본번 4: 건물부번 5: 도로명주소 6: 지번주소]
    * */
    public int searchAddress(String keyword){
        try {
            int currentPage = 0;
            int countPerPage = 100;
            if(validCheckIsEmpty(keyword)==1){
                errCode = "unValid Character";
                errMsg = "특수문자 포함 혹은 빈 문자";
                System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                return -1;
            }
            String mappedWord = charMapper(keyword);
            do{
                currentPage++;
                URL url = new URL(baseUrl+"?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+mappedWord+"&confmKey="+confmkey);
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
                        totalCnt=getTagValue("totalCount", eElement);
                        errCode=getTagValue("errorCode", eElement);
                        errMsg=getTagValue("errorMessage", eElement);
                    }
                }
                if (!errCode.equals("0")){
                    System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                    return -1;
                }else if(totalCnt.equals("0")){
                    System.out.println("검색결과 없음");
                    return 1;
                }else{
                    for (int temp = 0; temp < nList2.getLength(); temp++) {
                        String[] addrData = new String[7];
                        Node nNode = nList2.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            addrData[0]=getTagValue("admCd", eElement); // 행정구역코드
                            addrData[1]=getTagValue("rnMgtSn", eElement); // 도로명코드
                            addrData[2]=getTagValue("udrtYn", eElement); // 지하여부
                            addrData[3]=getTagValue("buldMnnm", eElement); // 건물본번
                            addrData[4]=getTagValue("buldSlno", eElement); // 건물부번
                            addrData[5]=getTagValue("roadAddr", eElement); // 도로명주소
                            addrData[6]=getTagValue("jibunAddr", eElement); // 지번주소
                        }
                        addressLists.add(addrData);
                    }
                }
            }while((Integer.parseInt(totalCnt)/countPerPage+1 != currentPage)&&(currentPage<90));
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public ArrayList<String[]> getResultLists(){
        return addressLists;
    }
    public int getTotalCount(){
        return Integer.parseInt(totalCnt);
    }
    public String getErrorCode(){
        return errCode;
    }
    public String getErrorMessage(){
        return errMsg;
    }
    private String charMapper(String word){
        StringTokenizer st = new StringTokenizer(word," ");
        String newWord ="";
        while(st.hasMoreTokens()){
            newWord+=st.nextToken()+"%20";
        }
        return newWord;
    }
    public static int validCheckIsEmpty(String str) {
        int cnt = 0;

        if (str == null || "".equals(str)) {
            cnt = 1;
        } else {
            // 특수문자 있을시
            if (validCheckSpecialLetters(str) > 0) {
                cnt = 1;
            } else {
                cnt = 0;
            }
        }
        return cnt;
    }

    // 특수문자 체크(없으면 0)
    public static int validCheckSpecialLetters(String str) {
        int cnt = 0;
        if ((!str.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝| ]*"))) {
            cnt = 99;
        }
        return cnt;
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

