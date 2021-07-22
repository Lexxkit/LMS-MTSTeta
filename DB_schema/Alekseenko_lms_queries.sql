-- Выбрать все курсы
select *
from courses
where is_deleted = false;

-- Выбрать все курсы по автору, длительности > недели и категории = 1, не удаленные,
-- отсортированные по рейтингу в порядке убывания
select *
from courses
where author = 'author'
	and duration_weeks > 1
	and category_id = 1
	and is_deleted = false
order by avg_rating desc;

-- Выбрать курс с идентификатором 1
select *
from courses
where course_id = 1;

-- Выбрать все курсы с названием 'course 1'(полнотекстовый поиск), категорией = 1 и не удаленные
select *
from courses
where title = 'course 1'
	and category_id = 1
	and is_deleted = false;

-- Выбрать все курсы с названием содержащим 'our'(вхождение), категорией = 1 и не удаленные
select *
from courses
where title like '%our%'
	and category_id = 1
	and is_deleted = false;

-- Выбрать все курсы пользователя с ID = 1 и не удаленные
select c.*
from courses_users cu
	inner join courses c
on cu.course_id = c.course_id
where cu.user_id = 1
	and c.is_deleted = false;
	
-- Выбрать все не удаленные модули
select *
from modules
where is_deleted = false;

-- Выбрать все не удаленные темы
select *
from themes
where is_deleted = false;

-- Выбрать все не удаленные модули для курса с ID = 1
select *
from modules
where course_id = 1
	and is_deleted = false;
	
-- Выбрать все не удаленные темы для модуля с ID = 1
select t.*
from modules_themes mt
	inner join themes t
on mt.theme_id = t.theme_id
where mt.module_id = 1
	and t.is_deleted = false;
	
-- Рассчитать средний рейтинг для курса с ID = 1
select coalesce(avg(grade), 0)
from courses_ratings
where course_id = 1;

