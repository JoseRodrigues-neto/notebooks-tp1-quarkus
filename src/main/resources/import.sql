-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

INSERT INTO fabricante (nome,paisOrigem) VALUES ('Dell','EUA');
INSERT INTO fabricante (nome,paisOrigem) VALUES ('Lenovo','CHINA'), ('Apple','EUA');

 
INSERT INTO categoria (nome, descricao) 
VALUES ('Laptops', 'Computadores portáteis projetados para serem transportados com facilidade. Incluem modelos variados com diferentes tamanhos de tela, capacidades de processamento e recursos.');

INSERT INTO categoria (nome,descricao)
 VALUES ('Ultrabooks','ultrafinos e leves, projetados para oferecer alto desempenho e longa duração de bateria.');
 
 INSERT INTO categoria (nome, descricao) 
 VALUES ('Gamers', 'Equipamentos de alta performance, com componentes poderosos para rodar os jogos mais exigentes do mercado.');
 


INSERT INTO Especificacao (processador, memoriaRam, armazenamento, tela, bateria, peso)
VALUES ('Intel Core i7', '16GB', '512GB SSD', '13.3 polegadas', '5000mAh', 1.2),
       ('AMD Ryzen 9', '32GB', '1TB SSD', '14 polegadas', '6000mAh', 1.3),
        ('teste', '16GB', '1TB SSD', '15.6 inches', '8 horas', 2.5);


 INSERT INTO Notebook (modelo, preco, garantia, fabricante_id, especificacao_id,cor,categoria_id)
 VALUES ('Ideapad I1',2500,2,2,1,'PRETO',1);   

 
INSERT INTO Usuario (perfil,email, nome,senha, username ) 
VALUES ('USER','joaosilva@email.com', 'João da Silva','59Af5ITwXq76BnsGob+0ARmVPdsW5g0Jd8UWs6n1OxhcXHgKZ/ogprO5ZLz/iJokPTBUAmNGN5Rkt+guiOj+1Q==',
'j123');

INSERT INTO Usuario (perfil,email, nome,senha, username ) 
VALUES ('ADM','joseneto@email.com', 'jose da Silva','59Af5ITwXq76BnsGob+0ARmVPdsW5g0Jd8UWs6n1OxhcXHgKZ/ogprO5ZLz/iJokPTBUAmNGN5Rkt+guiOj+1Q==',
'n123');

INSERT INTO UsuarioBasico (nome, email,perfil)
VALUES ('teste','teste@example.com','USER_BASIC'); 
 

  INSERT INTO cliente (data_nascimento, usuario, cpf, endereco, telefone)
VALUES ('2000-01-01',1,'12345678900','Rua dos Exemplos, 123' ,'(11) 98765-4321');

INSERT INTO funcionario (matricula, cargo, usuario)
values ('1595', 'gerente',2);

INSERT into Lote (dataentrada, quantidade,notebook_id)
values ('2024-01-01', 20, 1);


 