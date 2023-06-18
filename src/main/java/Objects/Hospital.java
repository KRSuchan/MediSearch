package Objects;

public class Hospital {
    String ykiho = "null";
    String yadmNm = "null";
    String clcdNm = "null";
    String yAddress = "null";
    Double distance = 0.0;

    public String getYadmNm() {
        return yadmNm;
    }

    public Double getDistance() {
        return distance;
    }

    public String getYkiho() {
        return ykiho;
    }

    public String getClcdNm() {
        return clcdNm;
    }

    public String getyAddress() {
        return yAddress;
    }

    public void setClcdNm(String clcdNm) {
        this.clcdNm = clcdNm;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setYAddress(String yAddress) {
        this.yAddress = yAddress;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public void setYkiho(String ykiho) {
        this.ykiho = ykiho;
    }
}
