
use mysql;


-- SQL 문 작성 

-- 데이터베이스(스키마) 생성

-- 테이블 만들기 

-- 테이블에 데이터 넣기

use test;

insert into cats 
(name, age) 
values 
('Jetson', 7);

-- 테이블의 데이터를 가져오는 방법
select * 
from cats;

insert into cats
(age, name)
values
( 12 , '야옹이' );

-- 여러 데이터를 한꺼번에 저장하는 방법 
insert into cats 
( name, age )
values 
( '찰리' , 3 ) , ( 'Sadie'  , 8 ), ( 'Bear' , 4 )  ;


insert into cats
(name, age)
values
('hello hi everybody', 2);

select *
from cats;

-- Null : 데이터가 없음.  (Nan)

insert into cats
(name)
values
('키티');

insert into cats
(age)
values
(7);

-- 두 컬럼에 NN (Not Null) 을 설정한 경우.

insert into cats
(name)
values
('키티');

insert into cats
(age)
values
(7);


insert into cats
(name)
values
('키티' );

insert into cats
(name, age)
values
('Kitty', 5);


-- 데이터를 가져오는 방법 

use yhdb;

select *
from cats;

-- id , name 컬럼만 가져오기 
select id, name
from cats;

-- name, age, id 컬럼만 가져오시오
select name, age, id
from cats;

-- id, name 컬럼을 가져오되 cat name 이라는 컬럼으로 바꿔서 
-- 가져오기 
select id, name as 'cat name'
from cats;

select id, name 'cat name'
from cats;

select id, name cat_name, age cat_age
from cats;

select * 
from cats;

-- age 가 4인 데이터를 가져오시오. 
select * 
from cats
where age = 4 ;

-- age 가 8보다 큰 데이터를 가져오시오.
select *
from cats
where age > 8;

-- 데이터의 값을 변경 

select *
from cats;

-- breed 가 Tabby 인 데이터의 breed 를 Shorthair로 변경.
update cats
set breed = 'Shorthair'
where breed = 'Tabby';

-- name 이 Misty 인 고양이의 나이를 14로 변경. 
update cats
set age = 14
where name = 'Misty';

























