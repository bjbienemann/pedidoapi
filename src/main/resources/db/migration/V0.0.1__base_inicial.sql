CREATE TABLE pedido (
    numero_controle BIGINT NOT NULL,
    data_cadastro DATE,
    nome_produto VARCHAR(100) NOT NULL,
    quantidade_produto DECIMAL,
    valor_produto DECIMAL NOT NULL,
    valor_total DECIMAL NOT NULL,
    codigo_cliente BIGINT NOT NULL,
    PRIMARY KEY (numero_controle)
);