-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

INSERT INTO fabricante (nome,paisOrigem) VALUES ('Dell','EUA');
INSERT INTO fabricante (nome) VALUES ('Lenovo','CHINA');

 
INSERT INTO categoria (nome, descricao) 
VALUES ('Laptops', 'Computadores portáteis projetados para serem transportados com facilidade. Incluem modelos variados com diferentes tamanhos de tela, capacidades de processamento e recursos.');
INSERT INTO categoria (nome,descricao) VALUES ('Ultrabooks''ultrafinos e leves, projetados para oferecer alto desempenho e longa duração de bateria.');
 
 
