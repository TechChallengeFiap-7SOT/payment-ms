package br.com.fiap.soat7.grupo18.lanchonete.payment.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class CallbackService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger log = Logger.getLogger("br.com.fiap.soat7.grupo18.lanchonete.payment.service.CallbackService");

    @Async
    public void invokeCallback(String url){
        try{
            restTemplate.getForObject(url, String.class);
            log.info(String.format("%s was invoked", url));
        }catch(Exception e){
            log.log(Level.WARNING, String.format("Error when invoking '%s': %s", url, e.getMessage()));
        }

    }

}
