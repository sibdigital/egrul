create extension if not exists "ltree";

create table if not exists okved
(
    id uuid not null
        constraint okved_pk
            primary key,
    class_code     varchar(2),
    subclass_code  varchar(4),
    group_code     varchar(5),
    subgroup_code  varchar(7),
    kind_code      varchar(8),
    type_code      smallint,
    path           ltree,
    status         smallint,
    kind_name      text,
    description    text,
    ts_kind_name   tsvector,
    ts_description tsvector
);

create index if not exists kind_code_idx on okved (kind_code);

create index if not exists ts_kind_name_gin_idx on okved using gin(ts_kind_name);

create index if not exists ts_description_gin_idx on okved using gin(ts_description);

create table if not exists reg_egrul
(
    id uuid not null
      constraint reg_egrul_pk
          primary key,
    load_date timestamp,
    inn varchar(20),
    data jsonb,
    file_path varchar(255)
);

create table reg_egrip
(
    id uuid not null
        constraint reg_egrip_pk
            primary key,
    load_date timestamp,
    inn varchar(20),
    data jsonb,
    file_path varchar(255)
);

create table reg_egrul_okved
(
    id_egrul uuid
        constraint reg_egrul_okved_reg_egrul_id_fk
            references reg_egrul,
    id_okved uuid
        constraint okved_reg_egrul_id_fk
            references okved
);

create table reg_egrip_okved
(
    id_egrip uuid
        constraint reg_egril_okved_reg_egril_id_fk
            references reg_egrip,
    id_okved uuid
        constraint okved_reg_egrip_id_fk
            references okved
)
