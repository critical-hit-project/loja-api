package com.critical_hit.loja.repository;

import com.critical_hit.lib.enums.StatusCarrinho;
import com.critical_hit.loja.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    List<Carrinho> findAllByStatus(StatusCarrinho status);

    List<Carrinho> findAllByDataCriacao(LocalDate dataCriacao);

    List<Carrinho> findAllByUsuarioId(Long usuarioId);

    List<Carrinho> findAllByUsuarioIdAndDataCriacao(Long usuarioId, LocalDate dataCriacao);
}
