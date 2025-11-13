public class Document {
    private String code;
    private int durationInMonths;

    public Document(String code, int durationInMonths){
        this.code = code;
        this.durationInMonths = durationInMonths;
    }

    public String getCode(){ return code; }
    public int getDurationInMonths() { return durationInMonths; }
}
