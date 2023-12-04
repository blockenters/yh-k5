select * from user;
select * from schedule;
select * from follow;

-- 회원가입 SQL 작성 
insert into user 
( email, password, nickname )
values
( 'aaa@naver.com', '1234', '홍길동');

select * from user;

-- 로그인 SQL 작성 
select * 
from user
where email = 'aaa@naver.com' and password = '1234';


select * from user;

-- 일정을 등록하는 SQL 
insert into schedule 
(content, date, userId) 
values
('크리스마스 파티', '2023-12-24 18:00', 101);

select * from schedule;

-- 내일정 가져오는 SQL 
-- 내 아이디가 1이다 라고 가정하고 작성.
select id, content, date, isCompleted, createdAt
from schedule
where userId = 1;

-- 친구맺는 SQL
-- 나는 홍길동이다. 유저아이디 1번을 친구등록하겠다. 
select * from follow;

insert into follow
(followerId, followeeId)
values
(101, 1);

-- 나는 홍길동이다. 이미 친구인 유저아이디 1번을 친구해제하겠다.
delete from follow 
where followerId = 101 and followeeId = 1;

-- 친구들의 일정을 가져오되, 
-- 현재 시간 기준으로, 현재시간보다 큰 일정들만 가져온다.

-- 가정 : 내아이디는 1이다. 
select *
from follow f
join schedule s
on f.followeeId = s.userId
join user u 
on f.followeeId = u.id 
where f.followerId = 1 and s.date < now() ;

-- 완료 여부를 체크하는 SQL (완료했다)
 
-- 완료 여부를 해제하는 SQL (완료 안했다)

-- 위의 둘을, 한번에 처리하는 SQL 














