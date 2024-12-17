create table universities (
                              id INT not null primary key,
                              name VARCHAR not null,
                              primary key (id)
);

create table students (
                         id INT not null,
                         fio VARCHAR not null,
                         email VARCHAR not null,
                         phone_number VARCHAR not null,
                         university_id INT not null,
                         primary key (id),
                         foreign key (university_id) references universities(id)
);
