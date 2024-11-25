package br.unitins.tp1.notebooks.validation;

public class Error {
    
    private  String  code;
    private String massage;


    
    public Error(String code, String massage) {
        this.code = code;
        this.massage = massage;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMassage() {
        return massage;
    }
    public void setMassage(String massage) {
        this.massage = massage;
    }

}
