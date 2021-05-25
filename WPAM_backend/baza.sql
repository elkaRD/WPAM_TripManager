SET FOREIGN_KEY_CHECKS = 0;
drop table if exists tm_users;
drop table if exists tm_trips;
drop table if exists tm_questions;
drop table if exists tm_answers;
drop table if exists tm_answer_votes;
drop table if exists tm_places;
drop table if exists tm_users_trips;
drop table if exists tm_trip_dates;
drop table if exists tm_availability;
drop table if exists tm_place_votes;

drop table if exists tm_seq_users;
drop table if exists tm_seq_trips;
drop table if exists tm_seq_questions;
drop table if exists tm_seq_answers;
drop table if exists tm_seq_places;
SET FOREIGN_KEY_CHECKS = 1;

create table tm_users
(
    user_id int,
    device_id varchar(255),
    primary key(user_id)
);

create table tm_trips
(
    trip_id int, 
    host_id int,
    name varchar(255),
    code varchar(12),
    allow_adding_places bool, 
    allow_adding_questions bool,
    allow_inviting bool,
    accepted bool,
    accepted_place_id int,
    primary key(trip_id),
    foreign key(host_id)
        references tm_users(user_id)
);

create table tm_questions
(
    user_id int,
    question_date date,
    question varchar(255),
    question_id int,
    trip_id int,
    primary key(question_id),
    foreign key(trip_id)
        references tm_trips(trip_id),
    foreign key(user_id)
        references tm_users(user_id)
);

create table tm_answers
(
    question_id int,
    answer_id int,
    answer varchar(255),
    user_id int,
    trip_id int,
    primary key (answer_id),
    foreign key(question_id)
        references tm_questions(question_id),
    foreign key(user_id)
        references tm_users(user_id),
    foreign key(trip_id)
        references tm_trips(trip_id)
);

create table tm_answer_votes
(
    user_id int,
    question_id int,
    answer_id int,
    trip_id int,
    primary key(user_id, answer_id),
    foreign key(user_id)
        references tm_users(user_id),
    foreign key(question_id)
        references tm_questions(question_id),
    foreign key(answer_id)
        references tm_answers(answer_id),
    foreign key(trip_id)
        references tm_trips(trip_id)
);

create table tm_places
(
    place_id int,
    name varchar(255),
    trip_id int,
    user_id int,
    latitude varchar(32),
    longitude varchar(32),
    accepted bool,
    primary key (place_id),
    foreign key(trip_id)
        references tm_trips(trip_id),
    foreign key(user_id)
        references tm_users(user_id)
);

create table tm_users_trips
(
    trip_id int,
    user_id int,
    username varchar(32),
    primary key(trip_id, user_id),
    foreign key(trip_id)
        references tm_trips(trip_id),
    foreign key(user_id)
        references tm_users(user_id)
);

create table tm_trip_dates
(
    trip_id int,
    day varchar(8),
    primary key(trip_id, day),
    foreign key(trip_id)
        references tm_trips(trip_id)
);

create table tm_availability
(
    user_id int,
    trip_id int,
    day varchar(8),
    primary key(user_id, trip_id, day),
    foreign key(user_id)
        references tm_users(user_id),
    foreign key(trip_id)
        references tm_trips(trip_id)
);

create table tm_place_votes
(
    user_id int,
    place_id int,
    trip_id int,
    primary key(user_id, place_id),
    foreign key(user_id)
        references tm_users(user_id),
    foreign key(place_id)
        references tm_places(place_id),
    foreign key(trip_id)
        references tm_trips(trip_id)
);



alter table tm_trips
  add constraint foreign key (accepted_place_id) references tm_places (place_id);



create sequence tm_seq_users start with 1000 increment by 1;
create sequence tm_seq_trips start with 1000 increment by 1;
create sequence tm_seq_questions start with 1000 increment by 1;
create sequence tm_seq_answers start with 1000 increment by 1;
create sequence tm_seq_places start with 1000 increment by 1;




