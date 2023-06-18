package Objects;

public class HospitalDetail {
    private String yadmNm= "null";
    private String clcdNm = "null";
    private String yAddress = "null";
    private String[] depts = {"null"};

    public String getClcdNm() {
        return clcdNm;
    }

    public String getYadmNm() {
        return yadmNm;
    }

    public String getyAddress() {
        return yAddress;
    }

    public String[] getDepts() {
        return depts;
    }

    public void setyAddress(String yAddress) {
        this.yAddress = yAddress;
    }

    public void setClcdNm(String clcdNm) {
        this.clcdNm = clcdNm;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public void setDepts(String[] depts) {
        this.depts = depts;
    }
}
