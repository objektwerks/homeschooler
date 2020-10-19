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
> Copyright (c) [2020] [Mike Funk]

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
