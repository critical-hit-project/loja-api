package com.critical_hit.loja.service;

import com.critical_hit.lib.dto.jogo.JogoResponse;
import com.critical_hit.lib.dto.loja.CarrinhoResponse;
import com.critical_hit.lib.dto.loja.CompraRequest;
import com.critical_hit.lib.enums.StatusCarrinho;
import com.critical_hit.loja.converter.DtoConverter;
import com.critical_hit.loja.model.Carrinho;
import com.critical_hit.loja.model.ItemCarrinho;
import com.critical_hit.loja.repository.CarrinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository repository;
    private final JogoService jogoService; //Busca jogos
    private final UsuarioService usuarioService; //Busca usuários
    private final DtoConverter converter;

    public CarrinhoResponse comprar(CompraRequest compraRequest) {
        Carrinho carrinho = repository.save(criarCarrinhoInicial(compraRequest.usuarioId()));

        try {
            List<ItemCarrinho> itens = validateAndBuildItems(compraRequest.jogoIds(), carrinho);

            BigDecimal total = itens.stream()
                    .map(ItemCarrinho::getPreco)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            carrinho.setJogos(itens);
            carrinho.setValorTotal(total);

            usuarioService.efetivarCompra(compraRequest.usuarioId(), compraRequest.jogoIds(), total);

            carrinho.setStatus(StatusCarrinho.FINALIZADO);
            return converter.convertToDTO(repository.save(carrinho));

        } catch (Exception e) {
            carrinho.setStatus(StatusCarrinho.CANCELADO);
            repository.save(carrinho);
            throw new RuntimeException("Erro ao processar compra: " + e.getMessage());
        }
    }

    //Metodo auxiliar da validação de jogos
    private List<ItemCarrinho> validateAndBuildItems(List<Long> jogoIds, Carrinho carrinho) {
        return jogoIds.stream().map(jogoId -> {
            JogoResponse jogoDTO = jogoService.getJogoById(jogoId);

            if (jogoDTO == null || !jogoDTO.disponivel()) {
                throw new RuntimeException("Jogo com ID " + jogoId + " é inválido ou indisponível");
            }

            ItemCarrinho item = new ItemCarrinho();
            item.setJogoId(jogoId);
            item.setPreco(jogoDTO.preco());
            item.setCarrinho(carrinho);
            return item;
        }).toList();
    }

    public CarrinhoResponse buscarPorId(Integer id) {
        return repository.findById(id)
                .map(converter::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o ID: " + id));
    }

    public List<CarrinhoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public List<CarrinhoResponse> buscarPorUsuario(Long usuarioId) {
        return repository.findAllByUsuarioId(usuarioId)
                .stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public List<CarrinhoResponse> buscarPorStatus(StatusCarrinho status) {
        return repository.findAllByStatus(status)
                .stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public List<CarrinhoResponse> buscarPorData(LocalDate data) {
        return repository.findAllByDataCriacao(data)
                .stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public List<CarrinhoResponse> buscarPorUsuarioEData(Long usuarioId, LocalDate data) {
        return repository.findAllByUsuarioIdAndDataCriacao(usuarioId, data)
                .stream()
                .map(converter::convertToDTO)
                .toList();
    }

    //Metodo auxiliar da criação de carrinho
    private Carrinho criarCarrinhoInicial(Long usuarioId) {
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuarioId(usuarioId);
        carrinho.setStatus(StatusCarrinho.ABERTO);
        carrinho.setDataCriacao(LocalDate.now());
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinho.setJogos(new ArrayList<>());
        return carrinho;
    }
    
}
