package br.com.fiap.soat7.grupo18.lanchonete.payment.test.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.soat7.grupo18.lanchonete.payment.handler.dto.PaymentRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentRestHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp(){

    }

    @Test
    public void successfullPaymentTest() throws Exception{
        PaymentRequestDto request = PaymentRequestDto.builder()
                                        .idPedido("abcdef-12345")
                                        .valor(10.5D)
                                        .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void unsuccessfullPaymentTest() throws Exception{
        PaymentRequestDto request = PaymentRequestDto.builder()
                                        .idPedido("abcdef-12345")
                                        .valor(0.0D)
                                        .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

   
}
