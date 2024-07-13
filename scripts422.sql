//создание таблиц для машин и владельцев
человек:
имя(VARCHAR(100)), возраст(INTEGER), наличие прав(BOOLEAN)

машина:
марка(VARCHAR(100)), модель(VARCHAR(100)), стоимость(NUMERIC)


create table cars
(
id SERIAL primary key not null,
brand VARCHAR(100) not null,
model VARCHAR(100) not null,
cost numeric not null
);




create table humans
(
id SERIAL primary key not null,
age INTEGER check (age > 0)  not null,
name VARCHAR(100) not null,
drivers_license boolean default false,
car_id INTEGER,
foreign key (car_id) references cars(id)
)