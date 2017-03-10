create table "schools" ("id" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,"name" VARCHAR NOT NULL UNIQUE,"website" VARCHAR)
create table "courses" ("id" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,"school_id" INTEGER NOT NULL,"name" VARCHAR NOT NULL,"website" VARCHAR)
create table "students" ("id" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,"name" VARCHAR NOT NULL,"born" DATE NOT NULL)
create table "grades" ("id" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,"student_id" INTEGER NOT NULL,"grade" INTEGER NOT NULL,"started" DATE NOT NULL,"completed" DATE NOT NULL)
create table "assignments" ("id" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,"grade_id" INTEGER NOT NULL,"course_id" INTEGER NOT NULL,"task" VARCHAR NOT NULL,"assigned" TIMESTAMP NOT NULL,"completed" TIMESTAMP,"score" DOUBLE NOT NULL)
alter table "courses" add constraint "school_fk" foreign key("school_id") references "schools"("id") on update NO ACTION on delete NO ACTION
alter table "grades" add constraint "student_fk" foreign key("student_id") references "students"("id") on update NO ACTION on delete NO ACTION
alter table "assignments" add constraint "course_assignment_fk" foreign key("course_id") references "courses"("id") on update NO ACTION on delete NO ACTION
alter table "assignments" add constraint "grade_assignment_fk" foreign key("grade_id") references "grades"("id") on update NO ACTION on delete NO ACTION
alter table "assignments" drop constraint "course_assignment_fk"
alter table "assignments" drop constraint "grade_assignment_fk"
alter table "grades" drop constraint "student_fk"
alter table "courses" drop constraint "school_fk"