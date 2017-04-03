Homeschool
----------
>Open source ScalaFX homeschool app.

License
-------
GPL.V3 ( See ./GPL.V3 )

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
1. sbt clean test universal:packageBin

Install
-------
1. copy universal directory to target directory (i.e., home directory ~ )
2. unzip ~/universal/homeschool-0.1.zip
3. set executable permissions for ~/universal/homeschool-0.1/bin/homeschool

Execute
-------
1. execute ~/universal/homeschool-0.1/bin/homeschool