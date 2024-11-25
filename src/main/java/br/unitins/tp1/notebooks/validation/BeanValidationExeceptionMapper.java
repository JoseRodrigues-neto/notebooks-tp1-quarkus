package br.unitins.tp1.notebooks.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BeanValidationExeceptionMapper implements ExceptionMapper<ConstraintViolationException>{

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ValidationError error = new ValidationError("400", "Erro de validação");

        for (ConstraintViolation<?> viollation : exception.getConstraintViolations()) {
            String fullFieldName = viollation.getPropertyPath().toString();
            int index = fullFieldName.lastIndexOf(".");
            String filedName = fullFieldName.substring(index + 1);
            String massage = viollation.getMessage();
          
            error.addFieldError(filedName, massage);

        }

 
         return Response.status(400).entity(error).build();
    }
    
   

}
