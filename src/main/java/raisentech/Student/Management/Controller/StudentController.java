package raisentech.Student.Management.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisentech.Student.Management.Controller.converter.StudentConverter;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;
import raisentech.Student.Management.domain.StudentDetail;
import raisentech.Student.Management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;

  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

    return convertStudentDetails(students, studentsCourses);
  }

  private List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourses> covertStudentCourses = studentsCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentsCourses(covertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  @GetMapping("/studentCourseList")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();

  }

  // 30代の生徒情報を取得
  @GetMapping("/students/in-their-30s")
  public List<Student> getStudentsInTheir30s() {
    return service.searchintheir30sStudents();
  }

  // Javaコースの生徒情報を取得
  @GetMapping("/students/java-course")
  public List<StudentsCourses> getJavaCourseStudents() {
    return service.searchJavaCourseStudents();
  }
}
