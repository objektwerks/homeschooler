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
* Course (id, gradeid, name, started, completed)
* Assignment (id, courseid, task, assigned, completed, score)

Relational Model
----------------
* Student 1 ---> * Grade 1 ---> * Course 1 ---> * Assignement

Panes
-----
* top west pane - students
* top east pane - grades
* bottom west pane - courses
* bottom east pane - assignments

Charts
------
1. courses - bar chart ( x = name, y = score, c = name )
2. assignments - line chart ( x = completed, y = score )

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
2. unzip ~/universal/homeschool-${version}.zip
3. set executable permissions for ~/universal/homeschool-${version}/bin/homeschool

Execute
-------
1. execute ~/universal/homeschool-${version}/bin/homeschool

License
-------
>Copyright (c) 2020 Mike Funk

>Published under GPL.v3