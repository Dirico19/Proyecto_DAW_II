
drop database if exists bd_biblioteca;
create database bd_biblioteca;

use bd_biblioteca;

create table rol(
 id_rol int not null auto_increment,
 des_rol varchar(45),
 primary key(id_rol)
);

create table usuario(
 id_usu int not null auto_increment,
 nom_usu varchar(45) not null unique,
 cont_usu varchar(255) not null,
 id_rol int not null,
 estado tinyint(1) default(1),
 primary key(id_usu),
 foreign key(id_rol) references rol(id_rol)
);

create table empleado(
 id_emp int not null auto_increment,
 nom_emp varchar(45),
 ape_emp varchar(45),
 cel_emp varchar(12),
 fec_contrato date,
 cargo_emp varchar(45),
 suel_emp decimal(10,2),
 cor_emp varchar(100),
 id_usu int,
 primary key(id_emp),
 foreign key(id_usu) references usuario(id_usu)
);

create table socio(
 id_soc int not null auto_increment,
 nom_soc varchar(45),
 ape_soc varchar(45),
 dni_soc char(8) unique,
 fec_nac date,
 fec_reg datetime,
 cel_soc varchar(12),
 cor_soc varchar(100),
 deuda decimal(10,2) default(0),
 id_usu int,
 primary key(id_soc),
 foreign key(id_usu) references usuario(id_usu)
);

create table categoria(
 id_cat int not null auto_increment,
 des_cat varchar(45),
 primary key(id_cat)
);

create table libro(
 id_lib int not null auto_increment,
 titulo_lib varchar(100),
 autor_lib varchar(100),
 año_lib int,
 id_cat int not null,
 stk_lib int check(stk_lib>=0),
 foto varchar(255) default(''),
 primary key(id_lib),
 foreign key(id_cat) references categoria(id_cat)
);

create table prestamo(
 id_pres int not null auto_increment,
 id_soc int,
 id_lib int,
 fec_pres date,
 fec_limit date,
 fec_devol date,
 estado varchar(45) check(estado in('Pendiente','Devuelto')),
 mora varchar(2) default('No') check(mora in('Si','No')),
 primary key(id_pres),
 foreign key(id_soc) references socio(id_soc),
 foreign key(id_lib) references libro(id_lib)
);

create table pago(
 id_pago int not null auto_increment,
 id_pres int,
 fec_pago date,
 monto decimal(10,2),
 primary key(id_pago),
 foreign key(id_pres) references prestamo(id_pres)
);

insert rol(des_rol) values('Administrador'),('Mantenimiento'),('Socio');

insert usuario(id_rol,nom_usu,cont_usu) values
(1,'jlujan','$2a$10$/QtmftcIx84tk9pNhDVKgeHwfYiDxAqyofMxlMWe2m0hvqKyJMKO6'),(2,'jfernandez','$2a$10$iJKU73Foo778Mz9qD61f0.p6uMa45hGa8BdDsIdpRra5u4ib9eD4a'),
(2,'mantonio','$2a$10$OhdRe37AfubObQQxHxfh8u5ZSD1sXeIWurhtdmKjl2mU6WRnA6Mjm'),(3,'croque','$2a$10$ypIBt1ZM2D4v8Yx8E/UTaO6PYwKvoeceHinlLwTtY.bnCUDqkgHiu'),
(3,'rcastro','$2a$10$1B6iKw7C05fl9qKo2eT2WONmYUv3TSHu7nL0OzMhUUHNpM.I/98Iy'),(3,'jcastillo','$2a$10$5q03Yf3NqxKp5zf3xtTRZu7UhSRBUN2cyW7cI.O9rkiRH9ebNmiEq');

insert empleado(nom_emp,ape_emp,cel_emp,fec_contrato,cargo_emp,suel_emp,cor_emp,id_usu) values
('Jesús','Luján','999123040','2015-03-21','Gerente',8000,'jlujan@gmail.com',1),
('José','Fernandez','998877665','2020-05-12','Bibliotecario',3000,'jfernandez@gmail.com',2),
('Marco','Antonio','987456756','2021-05-15','Asistente',1500,'mantonio@gmail.com',3);

insert socio(nom_soc,ape_soc,dni_soc,fec_nac,fec_reg,cel_soc,cor_soc,id_usu) values
('César','Roque','01239034','2000-12-10','2022-05-12','909123798','croque@gmail.com',4),
('Ramón','Castro','77890126','1990-10-22','2022-05-13','995567843','rcastro@gmail.com',5),
('Juan','Castillo','71903285','2002-05-09','2022-05-15','912069870','jcastillo@gmail.com',6);

insert categoria(des_cat) values('Sátira'),('Realismo'),('Fantasía'),('Ficción'),('Épico'),('Comedia');

insert libro(titulo_lib,autor_lib,año_lib,id_cat,stk_lib) values
('Don Quijote de la Mancha','Miguel de Cervantes',1605,1,20),
('La casa de los espíritus','Isabel Allende',1982,2,15),
('La metamorfosis','Franz Kafka',1915,3,3),
('La Odisea','Homero',1906,5,21),
('La Regenta','Leopoldo Alas',1884,4,13),
('Madame Bovary','Gustave Flaubert',1856,1,18),
('La Colmena','Camilo José Cela',1950,3,12),
('Los viajes de Gulliver','Jonathan Swift',1726,4,9);

insert prestamo(id_soc,id_lib,fec_pres,fec_limit,fec_devol,estado,mora) values
(2,3,'2023-03-22',date_add('2023-03-22',interval 20 day),null,'Pendiente','No'),
(3,1,'2023-03-22',date_add('2023-03-22',interval 20 day),'2023-05-30','Devuelto','Si'),
(1,1,'2023-03-25',date_add('2023-03-25',interval 20 day),'2023-05-01','Devuelto','No'),
(2,4,'2023-04-11',date_add('2023-04-11',interval 20 day),null,'Pendiente','No'),
(1,5,'2023-04-12',date_add('2023-04-12',interval 20 day),'2023-05-21','Devuelto','No');

insert pago(id_pres,fec_pago,monto) values
(3,'2023-05-03',5),(5,'2023-05-22',5);

select * from rol;
select * from usuario;
select * from empleado;
select * from socio;
select * from categoria;
select * from libro;
select * from prestamo;
select * from pago;
