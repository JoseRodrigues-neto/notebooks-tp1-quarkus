package br.unitins.tp1.notebooks.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends Error {
    
    private  record FieldErro(String fieldName, String massage) {};

    private List<FieldErro> errors =null;

    public ValidationError(String code, String massage){
        super(code, massage);

    }


    public  List<FieldErro>  getErros(){ return errors; }
    

    public void addFieldError (String fieldName, String massage)
    {
        if(errors == null)
        errors = new ArrayList<FieldErro>();
        errors.add(new FieldErro(fieldName, massage));
    }
}
