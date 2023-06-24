insert into users (id,first_name,last_name,image,is_block,gender,date_of_birth,phone_number)
values(1,'Admin','Admin','img',false,'MALE',now(),'+996700102030'),
      (2,'Askar','Salymbekov','img',false,'MALE',now(),'+996700102031'),
      (3,'Omurbek','Babanov','img',false,'MALE',now(),'+996700102032'),
      (4,'Askar','Akaev','img',true,'MALE',now(),'+996700102033');

insert  into user_info (id,nick_name,email,password,role,created_at,modified_at,user_id)
values (1,'admin123','admin@gmail.com','$2a$12$VA0btDbN.xJpCbxvcOzZRO87UgU6h7LdKtNQtsYsQCrtct6IAFPb2','ADMIN',now(),now(),1),         --> Admin123
       (2,'AsSal','askar@gmail.com','$2a$12$//nmEd9v2lDUmSqBTwF9o.SlPXdUzzI.r2/SG39AN1JhicYw3Zat.','JOURNALIST',now(),now(),2),       --> Askar123
       (3,'omur','omur@gmail.com','$2a$12$ZvJxAtBCnBw9bn/l4rCTOORkN1dA0SmM2YK5WjG2f9ltzY.XhjY2C','READER',now(), now(),3),            --> Omur123
       (4,'AskAka','akaev@gmail.com','$2a$12$X3QtS7z5.aTAYmHP76YsdOL1BU0Y4qxpoytZK9APzsrYeCDJJjYeW','READER',now(),now(),4);          --> Akaev123

insert into categories(id, name)
values (1,'SPORT'),
       (2,'POLICY'),
       (3,'THE SCIENCE'),
       (4,'FASHION');

insert into news (id,name,image,description,text,create_date,user_id)
values (1,'SpaceX','img','Elon Musk','text',now(),2),
       (2,'Micro Service','img','Amazon prime','text',now(),2),
       (3,'ChatGPT 4','img','Sam Altman','text',now(),2),
       (4,'Google','img','Bard','text',now(),2);

insert into comments(id,text,create_date,updated_date,news_id,user_id)
values (1,'Wow',now(),now(),2,3),
       (2,'Real',now(),now(),2,2),
       (3,'Super',now(),now(),2,3),
       (4,'Lol',now(),now(),2,4);

insert into news_categories (categories_id, news_id)
values (1,1),
       (1,4),
       (2,3),
       (3,1);


insert into favorites(id,create_date,news_id,user_id)
values (1,now(),1,3),
       (2,now(),4,3),
       (3,now(),3,4),
       (4,now(),4,4);

