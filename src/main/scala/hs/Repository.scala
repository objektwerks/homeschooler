package hs

import java.sql.Date
import java.time.LocalDate

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class Repository(val config: DatabaseConfig[JdbcProfile], val profile: JdbcProfile, val awaitDuration: Duration = 1 second) {
  import profile.api._

  implicit val dateMapper = MappedColumnType.base[LocalDate, Date](ld => Date.valueOf(ld),d => d.toLocalDate)
  val schema = students.schema ++ grades.schema ++ courses.schema ++ assignments.schema
  val db = config.db

  def await[T](action: DBIO[T]): T = Await.result(db.run(action), awaitDuration)
  def exec[T](action: DBIO[T]): Future[T] = db.run(action)

  def closeRepository() = db.close()

  def createSchema() = await(DBIO.seq(schema.create))
  def dropSchema() = await(DBIO.seq(schema.drop))

  case class Student(id: Int = 0, name: String, born: LocalDate)
  class Students(tag: Tag) extends Table[Student](tag, "students") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def born = column[LocalDate]("born")
    def * = (id, name, born) <> (Student.tupled, Student.unapply)
  }
  object students extends TableQuery(new Students(_)) {
    val compiledList = Compiled { sortBy(_.name.asc) }
    def save(student: Student) = (this returning this.map(_.id)).insertOrUpdate(student)
    def list() = compiledList.result
  }

  case class Grade(id: Int = 0, studentid: Int, grade: Int, started: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now.plusMonths(6))
  class Grades(tag: Tag) extends Table[Grade](tag, "grades") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def studentid = column[Int]("student_id")
    def grade = column[Int]("grade")
    def started = column[LocalDate]("started")
    def completed = column[LocalDate]("completed")
    def * = (id, studentid, grade, started, completed) <> (Grade.tupled, Grade.unapply)
    def studentFk = foreignKey("student_fk", studentid, TableQuery[Students])(_.id)
  }
  object grades extends TableQuery(new Grades(_)) {
    val compiledList = Compiled { studentid: Rep[Int] => filter(_.studentid === studentid).sortBy(_.grade.asc) }
    def save(grade: Grade) = (this returning this.map(_.id)).insertOrUpdate(grade)
    def list(studentid: Int) = compiledList(studentid).result
  }

  case class Course(id: Int = 0, gradeid: Int, name: String)
  class Courses(tag: Tag) extends Table[Course](tag, "courses") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def gradeid = column[Int]("grade_id")
    def name = column[String]("name")
    def * = (id, gradeid, name) <> (Course.tupled, Course.unapply)
    def gradeFk = foreignKey("grade_fk", gradeid, TableQuery[Grades])(_.id)
  }
  object courses extends TableQuery(new Courses(_)) {
    val compiledList = Compiled { gradeid: Rep[Int] => filter(_.gradeid === gradeid).sortBy(_.name.asc) }
    def save(course: Course) = (this returning this.map(_.id)).insertOrUpdate(course)
    def list(gradeid: Int) = compiledList(gradeid).result
  }

  case class Assignment(id: Int = 0, courseid: Int, task: String, assigned: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now, score: Double = 0.0)
  class Assignments(tag: Tag) extends Table[Assignment](tag, "assignments") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def courseid = column[Int]("course_id")
    def task = column[String]("task")
    def assigned = column[LocalDate]("assigned")
    def completed = column[LocalDate]("completed")
    def score = column[Double]("score")
    def * = (id, courseid, task, assigned, completed, score) <> (Assignment.tupled, Assignment.unapply)
    def courseFK = foreignKey("course_fk", courseid, TableQuery[Courses])(_.id)
  }
  object assignments extends TableQuery(new Assignments(_)) {
    val compiledList = Compiled { courseid: Rep[Int] => filter(_.courseid === courseid).sortBy(_.assigned.asc) }
    val compiledScore = Compiled { courseid: Rep[Int] => filter(_.courseid === courseid).map(_.score).sum }
    def save(assignment: Assignment) = (this returning this.map(_.id)).insertOrUpdate(assignment)
    def list(courseid: Int) = compiledList(courseid).result
    def score(courseid: Int) = compiledScore(courseid).result
  }
}