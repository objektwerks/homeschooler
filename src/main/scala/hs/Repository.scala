package hs

import java.time.LocalDate

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.*
import scala.language.postfixOps
import scala.util.control.NonFatal

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

final class Repository(config: DatabaseConfig[JdbcProfile],
                       val profile: JdbcProfile, 
                       awaitDuration: Duration = 1 second):
  import profile.api.*

  private val schema = students.schema ++ grades.schema ++ courses.schema ++ assignments.schema

  private val db = config.db
  
  def ifAbsentInstall(): Repository =
    try
      await( students.list() ).length
      this
    catch
      case NonFatal(_) =>
        createSchema()
        this

  def await[T](action: DBIO[T]): T = Await.result(db.run(action), awaitDuration)

  def exec[T](action: DBIO[T]): Future[T] = db.run(action)

  def close() = db.close()

  def createSchema() = await(DBIO.seq(schema.create))

  def dropSchema() = await(DBIO.seq(schema.drop))

  class Students(tag: Tag) extends Table[Student](tag, "students"):
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def born = column[LocalDate]("born")
    def * = (id.?, name, born).mapTo[Student]

  object students extends TableQuery(new Students(_)):
    val compiledList = Compiled {
      sortBy(_.born.asc)
    }
    def save(student: Student) = (this returning this.map(_.id)).insertOrUpdate(student)
    def list() = compiledList.result

  class Grades(tag: Tag) extends Table[Grade](tag, "grades"):
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def studentid = column[Int]("student_id")
    def year = column[String]("grade")
    def started = column[LocalDate]("started")
    def completed = column[LocalDate]("completed")
    def studentFk = foreignKey("student_fk", studentid, TableQuery[Students])(_.id)
    def * = (id.?, studentid, year, started, completed).mapTo[Grade]

  object grades extends TableQuery(new Grades(_)):
    val compiledList = Compiled { ( studentid: Rep[Int] ) =>
      filter(_.studentid === studentid).sortBy(_.started.asc) 
    }
    def save(grade: Grade) = (this returning this.map(_.id)).insertOrUpdate(grade)
    def list(studentid: Int) = compiledList(studentid).result

  class Courses(tag: Tag) extends Table[Course](tag, "courses"):
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.Unique)
    def started = column[LocalDate]("started")
    def completed = column[LocalDate]("completed")
    def gradeFk = foreignKey("grade_fk", gradeid, TableQuery[Grades])(_.id)
    def gradeid = column[Int]("grade_id")
    def * = (id.?, gradeid, name, started, completed).mapTo[Course]

  object courses extends TableQuery(new Courses(_)):
    val compiledList = Compiled { ( gradeid: Rep[Int] ) =>
      filter(_.gradeid === gradeid).sortBy(_.started.asc) 
    }
    def save(course: Course) = (this returning this.map(_.id)).insertOrUpdate(course)
    def list(gradeid: Int) = compiledList(gradeid).result

  class Assignments(tag: Tag) extends Table[Assignment](tag, "assignments"):
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def courseid = column[Int]("course_id")
    def task = column[String]("task")
    def assigned = column[LocalDate]("assigned")
    def completed = column[LocalDate]("completed")
    def score = column[Double]("score")
    def courseFK = foreignKey("course_fk", courseid, TableQuery[Courses])(_.id)
    def * = (id.?, courseid, task, assigned, completed, score).mapTo[Assignment]

  object assignments extends TableQuery(new Assignments(_)):
    val compiledList = Compiled { ( courseid: Rep[Int] ) => filter(_.courseid === courseid).sortBy(_.assigned.asc) }
    val compiledScore = Compiled { ( courseid: Rep[Int] ) => filter(_.courseid === courseid).map(_.score).avg }
    def save(assignment: Assignment) = (this returning this.map(_.id)).insertOrUpdate(assignment)
    def list(courseid: Int) = compiledList(courseid).result
    def score(courseid: Int) = compiledScore(courseid).result