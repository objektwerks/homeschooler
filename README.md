Homeschool
----------
>Open source homeschool app using ScalaFX and H2.

Scala 3 Bug
-----------
>Made a PR on slick to document this https://github.com/slick/slick/pull/2760
>(workaround is quite easy, you can just define def tupled = (apply _).tupled
>in the companion object of the case class and it will also compile for all
>Scala versions).

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
1. sbt run

Package
-------
1. sbt clean test universal:packageBin
2. verify ./target/universal/homeschool-${version}.zip

Install
-------
1. unzip ./target/universal/homeschool-${version}.zip
2. copy unzipped homeschool-${version} directory to ${homeschool.directory}
3. set executable permissions for ${homeschool.directory}/homeschool-${version}/bin/homeschool

Execute
-------
1. execute ${homeschool-directory}/homeschool-${version}/bin/homeschool

License
-------
>Copyright (c) [2018 - 2023] [Objektwerks]

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
