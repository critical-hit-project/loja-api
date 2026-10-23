package com.critical_hit.loja.service;

import com.critical_hit.lib.dto.jogo.JogoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JogoService {

    @Value("${services.jogo.url:http://localhost:8081}")
    private String jogoApiURL;

    public JogoResponse getJogoById(Long id) {
        try {
            return WebClient.builder().baseUrl(jogoApiURL).build()
                    .get()
                    .uri("/jogos/{id}", id)
                    .retrieve()
                    .bodyToMono(JogoResponse.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}