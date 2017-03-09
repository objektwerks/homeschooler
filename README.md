Homeschool
----------
>Homeschool app.

Object Model
------------
* Student (id, name, email, born)
* Grade (id, studentId, grade, started, completed)
* School (id, name, website?)
* Category (name)
* Course (id, schoolId, name, category, website?)
* Assignment (id, studentId, gradeId, courseId, description, assigned, completed, score)

Relational Model
----------------
* Student 1 ---> * Grade
* School 1 ---> * Course 1 ---> 1 Category
* Assignement 1 ---> 1 Student | Grade | Course

Commands
--------
* Change[T]

Events
------
* Changed[T]

Queries
-------
1. List[T]
2. Calculate Score By Student, Grade and Course across Assignments

Test
----
1. sbt clean test

Run
---
1. sbt clean test run