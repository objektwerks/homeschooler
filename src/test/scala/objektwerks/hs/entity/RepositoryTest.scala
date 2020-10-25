package objektwerks.hs.entity

import java.time.LocalDate

import objektwerks.hs.repository._

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class RepositoryTest extends AnyFunSuite with BeforeAndAfterAll with Matchers {
  val repository = Repository("test.conf")

  override protected def beforeAll(): Unit = {
    repository.schema.createStatements foreach println
  }

  override protected def afterAll(): Unit = {
    repository.close()
  }

  test("repository") {
    import repository._

    val barneyStudentId = await(students.save(Student(name = "barney", born = LocalDate.now.minusYears(7)))).get
    val fredStudentId = await(students.save(Student(name = "fred", born = LocalDate.now.minusYears(7)))).get

    val barneyGradeId = await(grades.save(Grade(studentid = barneyStudentId, year = "1"))).get
    val fredGradeId = await(grades.save(Grade(studentid = fredStudentId, year = "1"))).get

    val mathCourseId = await(courses.save(Course(gradeid = barneyGradeId, name = "basic math"))).get
    val scienceCourseId = await(courses.save(Course(gradeid = fredGradeId, name = "basic science"))).get

    await(assignments.save(Assignment(courseid = mathCourseId, task = "ch 1", score = 100.00)))
    await(assignments.save(Assignment(courseid = scienceCourseId, task = "ch 1", score = 75.00)))

    await(students.list()).length shouldBe 2

    await(grades.list(barneyStudentId)).length shouldBe 1
    await(grades.list(fredStudentId)).length shouldBe 1

    await(courses.list(barneyGradeId)).length shouldBe 1
    await(courses.list(fredGradeId)).length shouldBe 1

    await(assignments.list(mathCourseId)).length shouldBe 1
    await(assignments.list(scienceCourseId)).length shouldBe 1

    await(assignments.score(mathCourseId)).get shouldBe 100.0
    await(assignments.score(scienceCourseId)).get shouldBe 75.0
  }
}