create table cars
(
    id    serial primary key,
    brand varchar(20) not null,
    model varchar(20) not null,
    price integer     not null
);

create table persons
(
    id               serial primary key,
    name             varchar(20) not null,
    age              integer     not null,
    hasDriverLicense boolean     not null,
    car_id           integer references cars (id)
);

insert into cars(brand, model, price)
values ('BMW', 'X5', '5000000'),
       ('TOYOTA', 'COROLLA', '100000'),
       ('MAZDA', 'BONGO', '600000');

insert into persons(name, age, hasDriverLicense, car_id)
values ('Ivanov Ivan', '25', true, 1),
       ('Semenov Semen', '31', true, 2),
       ('Pavlov Pavel', '42', true, 3),
       ('Petrov Petr', '35', true, 2);