package com.example.javafx2;

public class StoreData {
    public static String convertToMediCode(String text) {
        switch (text) {
            case "가정의학과": return "23";
            case "영상의학과": return "16";
            case "방사선종양학과": return "17";
            case "진단검사의학과": return "19";
            case "결핵과": return "20";
            case "핵의학과": return "22";
            case "응급의학과": return "24";
            case "직업환경의학과": return "25";
            case "예방의학과": return "26";
            case "내과": return "01";
            case "신경과": return "02";
            case "병리과": return "18";
            case "비뇨의학과": return "15";
            case "산부인과": return "10";
            case "소아청소년과": return "11";
            case "안과": return "12";
            case "외과": return "04";
            case "정형외과": return "05";
            case "신경외과": return "06";
            case "심장혈관흉부외과": return "07";
            case "성형외과": return "08";
            case "마취통증의학과": return "09";
            case "재활의학과": return "21";
            case "이비인후과": return "13";
            case "정신건강의학과": return "03";
            case "치과": return "49";
            case "구강악안면외과": return "50";
            case "치과보철과": return "51";
            case "치과교정과": return "52";
            case "소아치과": return "53";
            case "치주과": return "54";
            case "치과보존과": return "55";
            case "구강내과": return "56";
            case "영상치의학과": return "57";
            case "구강병리과": return "58";
            case "예방치과": return "59";
            case "통합치의학과": return "61";
            case "피부과": return "14";
            case "한방내과": return "80";
            case "한방부인과": return "81";
            case "한방소아과": return "82";
            case "한방안·이비인후·피부과": return "83";
            case "한방신경정신과": return "84";
            case "침구과": return "85";
            case "한방재활의학과": return "86";
            case "사상체질과": return "87";
            case "한방응급": return "88";
            default: return "";
        }
    }

    public static String convertToSizeCode(String text) {
        switch (text) {
            case "상급종합병원": return "01";
            case "종합병원": return "11";
            case "병원": return "21";
            case "요양병원": return "28";
            case "정신병원": return "29";
            case "의원": return "31";
            case "치과병원": return "41";
            case "치과의원": return "51";
            case "조산원": return "61";
            case "보건소": return "71";
            case "보건지소": return "72";
            case "보건진료소": return "73";
            case "모자보건센타": return "74";
            case "보건의료원": return "75";
            case "약국": return "81";
            case "한방종합병원": return "91";
            case "한방병원": return "92";
            case "한의원": return "93";
            case "한약방": return "94";
            default: return "";
        }
    }
}
