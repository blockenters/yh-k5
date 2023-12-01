use ig_clone;

-- 1. 가장 오래된 회원 5명을 찾으세요. 
select *
from users
order by created_at asc;

-- 회원가입을 가장 많이 하는 요일은??
select * , dayname(created_at) as day, 
		count(*) as cnt
from users
group by day
order by cnt desc;

-- 회원가입은 했지만, 사진은 한번도 올리지 않은,
-- 유령회원들의 데이터를 가져오시오. 
select u.*
from users u
left join photos p 
on u.id = p.user_id
where p.id is null ;

-- 가장 유명한 사진은 무엇인지 찾아서
-- 유저이름, image_url, 좋아요 수를 나타내시오.
select u.username, p.image_url, count(l.photo_id) as total
from likes l
join users u
on l.user_id = u.id
join photos p 
on l.photo_id = p.id
group by l.photo_id
order by total desc
limit 1;

-- 가장 많이 사용된 해쉬태그의 이름은 무엇이며,
-- 몇개인지 나타내시오. 
select t.tag_name , count(pt.tag_id) as cnt
from photo_tags pt
join tags t 
on pt.tag_id = t.id
group by tag_id
order by cnt desc
limit 1;

-- 특정 포스팅 : 포토스 테이블의 아이디 72번의
-- 작성자, 사진url, 좋아요 수를 가져오시오. 
select u.username, p.image_url , count(l.id) as like_cnt
from photos p 
join users u 
on p.user_id = u.id
left join likes l
on p.id = l.photo_id
where p.id = 72;


-- 포토아이디 72번의 해시태그 이름들을 가져오세요.
select t.id, t.tag_name
from photo_tags pt
join tags t
on pt.tag_id = t.id
where pt.photo_id = 72;

-- 포토아이디 72번의 댓글 리스트를 가져오시오.
-- 댓글작성자이름, 댓글내용, 댓글 작성 날짜 
-- 댓글은 최근순으로. 
select u.username, c.comment_text, c.created_at 
from comments c
join users u
on c.user_id = u.id
where c.photo_id = 72
order by c.created_at desc;

-- 유저 아이디 2인 사람이 
-- 몇개의 포스팅을 올렸는지 가져오시오.
select count(*) as photo_cnt
from photos
where user_id = 2;

-- 유저 아이디 2인 사람의
-- 팔로워 수와, 팔로윙 수를 가져오시오.
select count(*) as follower_cnt
from follows
where followee_id = 2;

select count(*) as following_cnt
from follows 
where follower_id = 2;

-- 유저아이디 2번이 팔로잉하는 사람들의
-- 포스팅 리스트 가져오기. 25개씩 가져오기. 
-- 작성자이름, 이미지주소, 좋아요수 
select u.username, p.image_url , count(l.id) as like_cnt
from follows f
join photos p
on f.followee_id = p.user_id
join users u 
on p.user_id = u.id
left join likes l 
on p.id = l.photo_id
where f.follower_id = 2
group by p.id
order by p.created_at desc
limit 0, 25;























