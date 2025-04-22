CREATE TYPE forma_pagamento AS ENUM ('pix', 'credito','debito','dinheiro');

DROP TABLE atracao_cliente;
DROP TABLE ingresso;
DROP TABLE bilheteria;
DROP TABLE atracao;


CREATE TABLE IF NOT EXISTS bilheteria (
	id serial primary key,
	preco decimal(5,2),
	horario_fechamento time not null default '15:00:00',
	funcionamento date not null default current_date,
	quantidade_disponivel int NOT NULL CHECK (quantidade_disponivel >= 0)
);


CREATE TABLE IF NOT EXISTS ingresso (
	id serial primary key,
	id_cliente int NOT NULL, 
	id_bilheteria int NOT NULL,
	data_venda date not null default current_date,
	pagamento forma_pagamento NOT NULL,
	foreign key (id_cliente) references cliente(id),
	foreign key (id_bilheteria) references bilheteria(id)
);


CREATE TABLE IF NOT EXISTS atracao (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	descricao TEXT,
	horario_inicio TIME NOT NULL,
	horario_fim TIME NOT NULL,
	capacidade INT NOT NULL CHECK (capacidade > 0)
);

CREATE TABLE IF NOT EXISTS atracao_cliente (
	id SERIAL PRIMARY KEY,
	id_atracao INT NOT NULL,
	id_ingresso INT NOT NULL UNIQUE,
	horario_uso TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (id_atracao) REFERENCES atracao(id),
	FOREIGN KEY (id_ingresso) REFERENCES ingresso(id)
);


-- GATILHOS (Não é obrigatório)

CREATE OR REPLACE FUNCTION decrementacao_bilhete()
RETURNS TRIGGER AS $$
BEGIN
	IF (SELECT quantidade_disponivel FROM bilheteria WHERE id = NEW.id_bilheteria) <= 0
		THEN RAISE EXCEPTION 'Não há ingressos disponíveis!';
	END IF;
	UPDATE bilheteria  
	SET quantidade_disponivel = quantidade_disponivel - 1
	WHERE id = NEW.id_bilheteria;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_decrementar_bilhete
BEFORE INSERT ON ingresso
FOR EACH ROW
EXECUTE FUNCTION decrementacao_bilhete();



