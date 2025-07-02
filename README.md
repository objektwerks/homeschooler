Homeschool
----------
>Homeschool app using ScalaFx, Slick, H2, Ox and Scala 3.

Install
-------
1. Select [Homeschool](https://www.jdeploy.com/~homeschool)
2. Select a platform to download a compressed app installer.
3. Decompress app installer.
4. Install app by double-clicking app installer.
5. Select app icon to launch app.
>This install has been tested on macOS.

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

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Run
---
1. sbt run

Assembly
--------
1. sbt clean test assembly copyAssemblyJar

Execute
-------
1. java -jar .assembly/homeschool-9.0.0.jar ( or double-click executable jar )

Deploy
------
1. edit build.sbt ( jarVersion + version )
2. edit package.json ( version + jdeploy / jar )
3. edit app.conf ( about > alert > contentText )
4. sbt clean test assembly copyAssemblyJar
5. perform github release ( from https://github.com/objektwerks/homeschool )
6. npm login
7. jdeploy publish ( to https://www.jdeploy.com/~homeschool )
8. check email for npm message
>See [jDeploy Docs](https://www.jdeploy.com/docs/manual/#_getting_started) for details.

jDeploy Install
---------------
1. Setup npm account at npmjs.com
2. Install node, which installs npm, which bundles npx.
3. Install jdeploy via npm - *npm install -g jdeploy*
4. Add icon.png ( 256x256 or 512x512 ) to project root and resources/image.
5. Edit jDeploy *package.json* as required.
6. Add *jdeploy* and *jdeploy-bundle* to .gitignore
>See [jDeploy Docs](https://www.jdeploy.com/docs/manual/#_getting_started) for details.

jDeploy Issues
--------------
1. ***jDeploy publish*** fails due to npm *2fa* one-time password error.
    1. See: [Github Solution](https://github.com/shannah/jdeploy/issues/74)
2. ***macOS app icon*** not rendered correctly in Dock and Launchpad.
    1. Ensure app icon ( ./icon.png + ./src/main/resources/image/icon.png ) is at least 256x256. 512x512 is recommended.
    2. See objektwerks.ui.App stage.icons, Taskbar and Toolkit code for details.

NPM Versioning
--------------
>The ```build.sbt``` **must** contain a ```semver 3-digit``` **version** number. See: [Npmjs Semver](https://docs.npmjs.com/about-semantic-versioning)

NPM Registry
------------
>Homeschool is deployed to: https://www.npmjs.com/package/homeschool

License
-------
>Copyright (c) [2018 - 2025] [Objektwerks]

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.