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
    val barneyStudentId = await(students.save(Student(name = "barney", email = "barney@em.com", born = LocalDate.now.minusYears(7)))).get
    val fredStudentId = await(students.save(Student(name = "fred", email = "fred@em.com", born = LocalDate.now.minusYears(7)))).get

    val barneyGradeId = await(grades.save(Grade(studentId = barneyStudentId, grade = 1))).get
    val fredGradeId = await(grades.save(Grade(studentId = fredStudentId, grade = 1))).get

    val giftedSchoolId = await(schools.save(School(name = "gifted"))).get
    val idiotSchoolId = await(schools.save(School(name = "idiot"))).get

    val mathCategory = Category("math")
    val scienceCategory = Category("science")
    await(categories.add(mathCategory))
    await(categories.add(scienceCategory))

    val basicMathCourseId = await(courses.save(Course(schoolId = giftedSchoolId, category = mathCategory.name, name = "basic math"))).get
    val basicScienceCourseId = await(courses.save(Course(schoolId = idiotSchoolId, category = scienceCategory.name, name = "basic science"))).get

    await(assignments.save(Assignment(studentId = barneyStudentId, gradeId = barneyGradeId, courseId = basicMathCourseId, description = "addition", score = 100.00)))
    await(assignments.save(Assignment(studentId = fredStudentId, gradeId = fredGradeId, courseId = basicScienceCourseId, description = "atoms", score = 60.00)))

    await(students.list()).length shouldBe 2

    await(grades.list(barneyStudentId)).length shouldBe 1
    await(grades.list(fredStudentId)).length shouldBe 1

    await(schools.list()).length shouldBe 2

    await(categories.list()).length shouldBe 2

    await(courses.list(giftedSchoolId)).length shouldBe 1
    await(courses.list(idiotSchoolId)).length shouldBe 1

    await(assignments.list(barneyStudentId, barneyGradeId, basicMathCourseId)).length shouldBe 1
    await(assignments.list(fredStudentId, fredGradeId, basicScienceCourseId)).length shouldBe 1

    await(assignments.calculateScore(barneyStudentId, barneyGradeId, basicMathCourseId)).get shouldBe 100.0
    await(assignments.calculateScore(fredStudentId, fredGradeId, basicScienceCourseId)).get shouldBe 60.0
  }
}