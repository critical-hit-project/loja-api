package com.critical_hit.loja.converter;

import com.critical_hit.lib.dto.loja.CarrinhoResponse;
import com.critical_hit.lib.dto.loja.ItemCarrinhoResponse;
import com.critical_hit.loja.model.Carrinho;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {

    public CarrinhoResponse convertToDTO(Carrinho carrinho) {
        List<ItemCarrinhoResponse> itensDTO = carrinho.getJogos()
                .stream()
                .map(item -> new ItemCarrinhoResponse(item.getJogoId(), item.getPreco()))
                .collect(Collectors.toList());

        return new CarrinhoResponse(
                (long) carrinho.getId(),
                carrinho.getUsuarioId(),
                carrinho.getValorTotal(),
                carrinho.getStatus(),
                itensDTO
        );
    }
}
