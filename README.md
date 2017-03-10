Homeschool
----------
>Homeschool app.

Object Model
------------
* School (id, name, website?)
* Course (id, schoolid, name, website?)
* Student (id, name, born)
* Grade (id, studentid, grade, started, completed)
* Assignment (id, gradeid, courseid, task, assigned, completed, score)

Relational Model
----------------
* School 1 ---> * Course
* Student 1 ---> * Grade 1 ---> * Assignement 1 ---> 1 Course

Commands
--------
* Change[T]

Events
------
* Changed[T]

Queries
-------
1. List[T]
2. Calculate Course Assignments Score

Test
----
1. sbt clean test

Run
---
1. sbt clean test run