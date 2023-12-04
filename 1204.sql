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
update schedule
set isCompleted = 1
where id = 41 and userId = 1;

-- 완료 여부를 해제하는 SQL (완료 안했다)
update schedule
set isCompleted = 0
where id = 41 and userId = 1;

select *
from schedule
where userId = 1;

-- 위의 둘을, 한번에 처리하는 SQL 
update schedule 
set isCompleted =  if(isCompleted = 0, 1, 0)
where id = 41 and userId = 1;


-- 영화 리뷰 서비스 
use movie_db;

select * from movie;

-- 회원가입 SQL 
insert into user
(email, password, nickname, gender)
values
('aaa@naver.com', '1234', '홍길동' , 1);

select * from user;

-- 리뷰 작성 SQL  
select * from review;

insert into review
(movieId, userId, rating, content)
values
(3, 1, 4, '재밌어요');

-- 영화 상세정보 화면에 필요한 SQL  
-- 가정 : 영화 아이디가 2인 영화의 상세 정보. 
select m.* , avg(r.rating) as rating_avg , count(r.rating) review_count
from movie m
left join review r
on m.id = r.movieId
where m.id = 2;

select * from user;

-- 특정 영화에 대한 리뷰 가져오는 SQL


-- 내정보 화면에서,  내가 작성한 리뷰 리스트 가져오는 SQL 


-- 특정 영화를 선택하면, 즐겨찾기에 저장하는 SQL 
-- 가정 : 영화아이디 1번을 즐겨찾기 하거나 해제하는 것 


-- 위에서 즐겨찾기 한 영화들의 리스트를 볼수있는, 
-- 내즐겨찾기 화면에 필요한 SQL 문 작성. 


-- 메인화면처럼 나오도록 하는 SQL 문 작성. 


















