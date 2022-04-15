create table students (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR NOT NULL,
  born DATE NOT NULL
);
create table grades (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  student_id INTEGER NOT NULL,
  grade VARCHAR NOT NULL,
  started DATE NOT NULL,
  completed DATE NOT NULL,
  foreign key(student_id) references students(id)
);
create table courses (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  grade_id INTEGER NOT NULL,
  name VARCHAR NOT NULL UNIQUE,
  started DATE NOT NULL,
  completed DATE NOT NULL,
  foreign key(grade_id) references grades(id)
);
create table assignments (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  course_id INTEGER NOT NULL,
  task VARCHAR NOT NULL,
  assigned DATE NOT NULL,
  completed DATE NOT NULL,
  score DOUBLE NOT NULL,
  foreign key(course_id) references courses(id)
);