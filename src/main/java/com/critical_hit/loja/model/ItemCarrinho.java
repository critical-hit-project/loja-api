package com.critical_hit.loja.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Long jogoId;

    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;
}
