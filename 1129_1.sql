SELECT * FROM yhdb.books;

-- 문자열을 합치는 함수 concat() 
-- author_fname 과 author_lname 컬럼의 문자열을 
-- 하나로 합해서, full_name 으로 보여주고 싶다. 
select concat(author_fname , ' ' , author_lname) as full_name  
from books;

-- concat_ws() 함수를 사용하는 방법 
-- 첫번째 파라미터가, 문자열을 붙일때 사용할 문자열이다.
select concat_ws(' ', author_fname, author_lname)  as full_name
from books;

-- 이름 두개를 붙여서, full_name 을 만들고, 
-- 책 제목과 출간년도도 같이 보여달라.
select concat(author_fname, ' ', author_lname) as full_name , title, released_year
from books;

-- 문자열의 일부분만 가져오는 함수 substring()

-- 책 제목을, 첫글자부터 10번째 글자까지만 가져오시오.
-- 서브스트링 함수의 시작위치는 1부터!!!
select substring( title , 1, 10 ) as title
from books;

-- 제목을, 맨 뒤에서 5번째 글자부터, 끝까지 나오도록 
-- 데이터를 가져오시오.
select substring(title , -5)
from books;

-- 제목을, 앞에서 3번째 글자부터, 끝까지 나오도록
-- 데이터를 가져오시오.
select substring(title , 3)
from books;

-- 문자열의 내용을 바꾸는 함수 replace() 
-- 책 제목에, The 가 있으면, Hello 로 바꾸고 싶다.
select replace( title , 'The' , 'Hello')
from books;

-- 문자열의 내용을 바꾸는 함수 replace() 
-- 책 제목에, The 가 있으면, 제거하고 싶다.
select replace( title , 'The' , '')
from books;

-- 문자열의 순서를, 역순으로 바꿔주는 함수 reverse()
-- 작가 author_lname 을 역순으로 가져오시오. 
select reverse( author_lname ) as author
from books;

-- 문자열의 갯수를 구하는 함수 char_length()
-- 책 제목의 글자 갯수를 구하세요.
select char_length( title ) as length, title, pages
from books;

-- 대소문자 처리하는 함수 upper()  lower() 
-- author_fname은 대문자로, author_lname은 소문자로 가져오시오.

select upper(author_fname) as author_fname , lower(author_lname) as author_lname
from books;

-- 여러 함수의 조합 

-- 책 제목을, 맨 앞부터 10글자 까지만 가져오고, 뒤에 ... 을 붙인다.
select concat( substr(title, 1, 10) , '...' ) as title
from books;


select replace(title, ' ', '->') as title
from books;

select author_lname as forwards,  
		reverse(author_lname) as backwards
from books;



select  upper( concat( author_fname, ' ', author_lname) ) as 'full name in caps'
from books;


select  concat( title, ' was released in ', released_year ) as blurb
from books;


select title ,  char_length(title) as 'character count'
from books;


select concat( substr( title, 1, 10) , '...') as 'short title' , 
		concat( author_lname, ',' , author_fname) as 'author' , 
        concat( stock_quantity , ' in stock' ) as 'quantity'
from books;



select * 
from books;

-- 데이터를 중복제거해서 유니크 하게 가져오는 키워드(함수 아님!)
-- author_lname 은 카테고리컬 데이터다.(중복데이터가있음)
-- 이 컬럼의 데이터를 유니크 하게 가져오자.
select distinct  author_lname
from books;

-- full name 으로, 중복제거해서 유니크하게 이름 가져오자.
select distinct concat( author_fname, ' ', author_lname ) as author
from books;

-- 정렬 (오름차순정렬 / 내림차순정렬)
-- order by 키워드를 사용.
-- 이 키워드의 위치가 중요하다! => 항상 from 아래에 위치해야함.

-- author_lname 으로 정렬하시오.
select *
from books
order by author_lname;

select *
from books
order by author_lname asc ;

-- 내림차순 정렬
select *
from books
order by author_lname desc;

-- full name 으로 정렬하세요.
select id, title, author_fname, author_lname, released_year, stock_quantity, pages,  concat(author_fname, ' ', author_lname) as 'full name'
from books
order by `full name` asc;

-- 출판년도 내림차순으로 정렬하되, 
-- 책 제목, 출판년도, 페이지수를 가져오시오.
select title, released_year, pages
from books
order by released_year desc;


-- 테이블의 원래 컬럼을 모두 표시하고, 추가로 다른 컬럼을 
-- 표시하고 싶을때
select * , concat(author_fname,' ',author_lname) as full_name
from books;

-- author_lname 으로 오름차순 정렬하고, 
-- lname이 같으면, author_fname으로 내림차순 정렬하시오.
select *
from books
order by author_lname asc, author_fname desc;

-- 데이터를 끊어서 가져오는 방법 
-- limit 키워드! (오프셋과 갯수)

-- books 테이블의 데이터를 5개만 가져오시오.
select *
from books
limit 0, 5;

-- 그리고 나서 위의 5개 이후의데이터를 또 5개만 가져오시오.
select *
from books
limit 5, 5;

select *
from books
limit 10, 5;

select *
from books
limit 15, 5;

-- 출판년도를 내림차순으로 정렬한후,
-- 처음부터 7개의 데이터를 가져오시오. 
select *
from books
order by released_year desc
limit 0, 7;

-- 문자열 안에,  원하는 문자열이 들어있는지 검색
-- like 키워드 

-- 책 제목에 the 가 들어있는 데이터를 가져오시오
select *
from books
where title like '%the%';

-- 책 제목이 the 로 시작하는 책을 찾으시오. 
select *
from books
where title like 'the%';

-- 책 제목이 the 로 끝나는 책을 찾으시오. 
select *
from books
where title like '%the';

-- stock_quantity 의 숫자가, 2자리수인 데이터를 찾으시오.
select *
from books
where stock_quantity like '__';

-- 1. 
select *
from books
where title like '%stories%';

-- 2. 
select title, pages
from books
order by pages desc
limit 0, 1;

-- 3.
select concat( title, ' - ', released_year ) as summary
from books
order by released_year desc
limit 0, 3;

-- 4.
select title, author_lname
from books
where author_lname like '% %' ;

select title, released_year, stock_quantity
from books
order by stock_quantity
limit 0, 3;


select title, author_lname
from books
order by author_lname asc, title asc;

select upper(concat('my favorite author is ', author_fname, author_lname, '!'))  as yell
from books
order by author_lname;

-- 데이터의 갯수를 세는 함수 count() 

-- books 테이블의 전체 데이터는 몇개???
select count(*)
from books;

-- author_lname 컬럼의 전체 데이터갯수는 몇개?? 
select count( author_lname )
from books;

-- author_lname은 중복데이터가 있다. 
-- 따라서, 중복데이터 제거한 유니크한 데이터의 갯수는 몇개??
select count( distinct author_lname )
from books;

-- 책 제목에 the 가 들어있는 책은, 몇권입니까?
select count(*)
from books
where title like '%the%';

-- 카테고리컬 데이터의 경우
-- ~ 별로 묶어서 처리할 수 있다. => group by 키워드!!

-- author_lname 별로, 몇권의 책을 썼는지 권수를 보여주세요.

select author_lname, count(author_lname) as book_count
from books
group by author_lname ;

-- 년도별로 각각 몇권의 책이 출간되었는지
-- 년도와 책의갯수를 보여주세요.

select released_year,  count(released_year) as cnt
from books
group by released_year
order by cnt desc;

-- 최대값 구하는 함수  max()
-- 페이지수가 가장 많은 책은, 몇페이지 입니까?
select max(pages)  
from books;


-- 최소값 구하는 함수 min()
-- 출판년도가 가장 빠른 책은 몇년도 인가??
select min( released_year )
from books;

-- 페이지의 최소값과 최대값을 함께 보여주시오. 
select min(pages) as min , max(pages) as max
from books;

-- 페이지수가 가장 긴 책의,
-- 제목과 페이지수를 보여주세요. 
-- 방법 2 :  Sub Query 서브 쿼리 
select max(pages)
from books;
-- 634 

select *
from books
where pages = 634;

select title, pages
from books
where pages = ( select max(pages) from books ) ;


-- 방법 1
select title, pages
from books
order by pages desc
limit 0,1;

-- stock_quantity 가 가장 적은 책의 
-- 책 제목, 작가 fname, 페이지수를 보여주세요.
select min(stock_quantity)
from books;
-- 12 

select title, author_fname, pages
from books
where stock_quantity = (select min(stock_quantity) from books);

-- 각 작가의 full_name 별로,
-- 해당 작가의 최초 책 발간한 년도는 언제인지
-- 작가의 full_name과 발간년도를 보여주세요. 
select concat(author_fname, ' ',author_lname) as full_name, min(released_year)
from books
group by full_name;

-- 각 작가별(풀 네임별) 자신이 쓴 책중에서 가장 긴 책의 
-- 페이지수를 보여주세요. (작가 풀네임, 페이지수)


































































