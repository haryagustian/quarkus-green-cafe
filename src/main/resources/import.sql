insert into main.job_position (id,created_at,salary,title)
values
	('7045f236-70e8-4c14-83ae-88f650331dcd', pg_catalog.now(),5000000,'Chef'),
	('cab8c222-b260-4035-8655-a7a51acb8a0c', pg_catalog.now(),3500000,'Waitress'),
	('ae43ab73-eca8-47e0-8c5f-b7f60213aabb', pg_catalog.now(),4000000,'Cashier'),
	('06992ac0-a2c6-4c5e-a8ef-ccf0c4ba9f2f', pg_catalog.now(),7000000,'Manager'),
	('32e9e19d-7a94-49ec-9a23-7d95a881a281', pg_catalog.now(),3000000,'Janitor'),
	('6a87f488-3cdf-4c9a-833d-4a7da99ca47d', pg_catalog.now(),2500000,'Dishwasher'),
	('3319116f-f143-40aa-8334-34b44a45899b', pg_catalog.now(),4500000,'Barista');

insert into main.last_education (id,created_at, name)
values
	('36535870-e124-4a9e-bd95-0fa1d75b18b1', pg_catalog.now(),'SD'),
	('01db73fd-fe4c-4541-bc1e-6c265bc367c4', pg_catalog.now(),'SMP'),
	('a86dd814-b8bb-4955-b631-7fd56f6e0217', pg_catalog.now(),'SMA'),
	('38ec83df-c64b-4a85-bbae-204db2f33074', pg_catalog.now(),'D3'),
	('a6a3670e-970a-4021-88e6-635392857158', pg_catalog.now(),'S1/D4'),
	('af8a389d-2e25-4c06-90b8-10f6a79e1dcf', pg_catalog.now(),'S2'),
	('7501db94-7943-49cf-b1af-014471f2e7ec', pg_catalog.now(),'S3');


insert into main.payment_type (id,created_at, name, code)
values
	('a7b3356b-4422-4272-973a-06d45818459c', pg_catalog.now(),'Credit', 'CR'),
	('32fa6e7b-5a72-4168-9793-553ed945ba36', pg_catalog.now(),'Debit', 'DE'),
	('1124afed-fb0d-4108-9c7a-1b6cac52b5a8', pg_catalog.now(),'Chasless', 'CL'),
	('5d8f3071-2c01-41b8-aa23-875feb9da397', pg_catalog.now(),'Cash', 'CA');

--insert into employee (id,created_at,updated_at,dob,pob,full_name,email,gender,is_active,mobile_phone_number,job_position_id,last_education_id,created_by,updated_by)
--values
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','M',1,'012','Chef','SD',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP()),
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','F',1,'0123','Waitress','SMP',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP()),
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','M',1,'01245','Cashier','SMA',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP()),
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','F',0,'012456','Manager','D3',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP()),
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','M',1,'0124567','Janitor','S1/D4',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP()),
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','M',1,'01245678','Dishwasher','S2',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP()),
--	(UUID(), CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'FNA','M',1,'012456789','Baristha','S3',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());