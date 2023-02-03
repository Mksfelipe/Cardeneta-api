insert into account (id, amount, created, updated, last_data_payment, days_without_paying) values (1, 0, now(), now(), '2022-01-01 09:51:49.311', 0);
insert into account (id, amount, created, updated, last_data_payment, days_without_paying) values (2, 0, now(), now(), '2023-01-02 09:51:49.311', 0);
insert into account (id, amount, created, updated, last_data_payment, days_without_paying) values (3, 0, now(), now(), '2022-10-20 09:51:49.311', 0);
insert into account (id, amount, created, updated, last_data_payment, days_without_paying) values (4, 0, now(), now(), '2022-08-02 09:51:49.311', 0);
insert into account (id, amount, created, updated, last_data_payment, days_without_paying) values (5, 0, now(), now(), '2023-01-25 09:51:49.311', 0);



insert into client(id, cpf, first_name, last_name, "password", "enable", account_id) values(1, '07782169348', 'FELIPE', 'SOUSA', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC', 'Y', 1);

insert into client(id, cpf, first_name, last_name, "password", "enable", account_id) values(2, '27104873651', 'ISAAC', 'FERNANDO', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC', 'Y', 2);

insert into client(id, cpf, first_name, last_name, "password", "enable", account_id) values(3, '92502023505', 'CAIO', 'LEANDRO', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC', 'Y', 3);

insert into client(id, cpf, first_name, last_name, "password", "enable", account_id) values(4, '76622118515', 'ISABEL', 'ESTER', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC', 'Y', 4);

insert into client(id, cpf, first_name, last_name, "password", "enable", account_id) values(5, '74607690457', 'ISADORA', 'ISABEL', '$2a$12$yr2vdqAMBVayCp64I7QVce7XMVSMzG7yy0APvCgXSaWzTczRiPbIC', 'Y', 5);

insert into "role" (id, "name") values (1, 'ADMIN');
insert into "role" (id, "name") values (2, 'USER');

insert into client_roles (client_id, role_id) values (1,1);
insert into client_roles (client_id, role_id) values (2,2);
insert into client_roles (client_id, role_id) values (3,2);
