//внесение правил в хогвартс


alter table student
add constraint age_constraint check (age > 15);

alter table student
alter column age set default 20


alter table student
alter column name set not null

alter table student
add unique (name)

alter table faculty
add unique (name,color)