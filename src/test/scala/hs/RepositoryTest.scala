package hs

import java.time.LocalDate

import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

class RepositoryTest extends FunSuite with BeforeAndAfterAll with Matchers {
  val config = DatabaseConfig.forConfig[JdbcProfile]("test", ConfigFactory.load("test.conf"))
  val repository = new Repository(config, H2Profile)
  import repository._

  override protected def beforeAll(): Unit = {
    schema.createStatements foreach println
    schema.dropStatements foreach println
    createSchema()
  }

  override protected def afterAll(): Unit = {
    dropSchema()
    closeRepository()
  }

  test("repository") {
    val giftedSchoolId = await(schools.save(School(name = "gifted"))).get
    val commonSchoolId = await(schools.save(School(name = "common"))).get

    val mathCourseId = await(courses.save(Course(schoolId = giftedSchoolId, name = "basic math"))).get
    val scienceCourseId = await(courses.save(Course(schoolId = commonSchoolId, name = "basic science"))).get

    val barneyStudentId = await(students.save(Student(name = "barney", born = LocalDate.now.minusYears(7)))).get
    val fredStudentId = await(students.save(Student(name = "fred", born = LocalDate.now.minusYears(7)))).get

    val barneyGradeId = await(grades.save(Grade(studentId = barneyStudentId, grade = 1))).get
    val fredGradeId = await(grades.save(Grade(studentId = fredStudentId, grade = 1))).get

    await(assignments.save(Assignment(gradeId = barneyGradeId, courseId = mathCourseId, task = "add numbers", score = 100.00)))
    await(assignments.save(Assignment(gradeId = fredGradeId, courseId = scienceCourseId, task = "study atoms", score = 60.00)))

    await(schools.list()).length shouldBe 2

    await(courses.list(giftedSchoolId)).length shouldBe 1
    await(courses.list(commonSchoolId)).length shouldBe 1

    await(students.list()).length shouldBe 2

    await(grades.list(barneyStudentId)).length shouldBe 1
    await(grades.list(fredStudentId)).length shouldBe 1

    await(assignments.list(barneyGradeId, mathCourseId)).length shouldBe 1
    await(assignments.list(fredGradeId, scienceCourseId)).length shouldBe 1

    await(assignments.calculateScore(barneyGradeId, mathCourseId)).get shouldBe 100.0
    await(assignments.calculateScore(fredGradeId, scienceCourseId)).get shouldBe 60.0
  }
}