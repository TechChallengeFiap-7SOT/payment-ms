package br.com.fiap.soat7.grupo18.lanchonete.payment.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.fiap.soat7.grupo18.lanchonete.payment.exception.RequiredDataException;

@ControllerAdvice
public class GlobalExceptionHandlerConfig {


    @ExceptionHandler({RequiredDataException.class})
    @ResponseBody
    public ResponseEntity<String> handleDomainException(RuntimeException re){
        re.printStackTrace();
        return ResponseEntity.badRequest().body(re.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleNotFoundException(Exception e){
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
