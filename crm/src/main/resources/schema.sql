drop table if exists customer CASCADE;
create table customer (ID VARCHAR(36) not null, CREATED_DATE timestamp not null, LAST_UPDATED_DATE timestamp not null, DATE_OF_BIRTH date not null, EMAIL varchar(255), FIRST_NAME varchar(255), GENDER varchar(255) not null, LAST_NAME varchar(255), primary key (ID));
alter table customer add constraint UKdwk6cx0afu8bs9o4t536v1j5v unique (email);