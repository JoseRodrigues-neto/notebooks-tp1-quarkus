package br.unitins.tp1.notebooks.validation;

public class ValidationException extends RuntimeException{
    
    private String fildName;

    public ValidationException(String filedName, String massage)
{
    super(massage);
    this.fildName = filedName;
}

    public String getFiledName() {
        return fildName;
    } 

}
