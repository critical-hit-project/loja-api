package com.critical_hit.loja.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UsuarioService {

    @Value("${services.usuario.url:http://localhost:8082}")
    private String usuarioApiURL;

    public void efetivarCompra(Long usuarioId, List<Long> jogosIds, BigDecimal valorTotal) {
        record EfetivarCompraPayload(List<Long> jogosIds, BigDecimal valorTotal) {}

        WebClient.builder().baseUrl(usuarioApiURL).build()
                .post()
                .uri("/usuarios/{id}/compras/efetivar", usuarioId)
                .bodyValue(new EfetivarCompraPayload(jogosIds, valorTotal))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}