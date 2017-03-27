# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                            bigint not null,
  name                          varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  logined                       timestamp,
  created                       timestamp not null,
  updated                       timestamp not null,
  constraint pk_user primary key (id)
);
create sequence user_seq;


# --- !Downs

drop table if exists user;
drop sequence if exists user_seq;

