insert into job_position (id,created_at,salary,title)
values
	(UUID(), CURRENT_TIMESTAMP(),1000000,"Accountant"),
	(UUID(), CURRENT_TIMESTAMP(),2000000,"Actress"),
	(UUID(), CURRENT_TIMESTAMP(),3000000,"Actor"),
	(UUID(), CURRENT_TIMESTAMP(),4000000,"Baker"),
	(UUID(), CURRENT_TIMESTAMP(),5000000,"Barber");

insert into last_education  (id,created_at, name)
values
	(UUID(), CURRENT_TIMESTAMP(),"1LastEducation"),
	(UUID(), CURRENT_TIMESTAMP(),"2LastEducation"),
	(UUID(), CURRENT_TIMESTAMP(),"3LastEducation"),
	(UUID(), CURRENT_TIMESTAMP(),"4LastEducation"),
	(UUID(), CURRENT_TIMESTAMP(),"5LastEducation");

