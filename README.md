Homeschool
----------
>Open source homeschool app.

Object Model
------------
* Student (id, name, born)
* Grade (id, studentid, grade, started, completed)
* Course (id, gradeid, name)
* Assignment (id, courseid, task, assigned, completed, score)

Relational Model
----------------
* Student 1 ---> * Grade 1 ---> * Course 1 ---> * Assignement

Test
----
1. sbt clean test

Run
---
1. sbt clean test run

Package
-------
1. sbt universal:packageBi