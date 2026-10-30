CREATE TABLE carrinho (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    valor_total NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_criacao DATE NOT NULL
);

CREATE TABLE item_carrinho (
    id BIGSERIAL PRIMARY KEY,
    carrinho_id BIGINT NOT NULL,
    jogo_id BIGINT NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    CONSTRAINT fk_item_carrinho_carrinho 
        FOREIGN KEY (carrinho_id) 
        REFERENCES carrinho(id) 
        ON DELETE CASCADE
);