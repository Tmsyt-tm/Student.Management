package raisentech.Student.Management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentCourses;
import raisentech.Student.Management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();

  }

  // 30代の生徒情報を取得
  @GetMapping("/students/in-their-30s")
  public List<Student> getStudentsInTheir30s() {
    return service.searchintheir30sStudents();
  }

  // Javaコースの生徒情報を取得
  @GetMapping("/students/java-course")
  public List<StudentCourses> getJavaCourseStudents() {
    return service.searchJavaCourseStudents();
  }
}
