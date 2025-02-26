package raisentech.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisentech.Student.Management.controller.converter.StudentConverter;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;
import raisentech.Student.Management.domain.StudentDetail;
import raisentech.Student.Management.service.StudentService;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    // StudentDetail 型の受講生一覧を取得
    List<StudentDetail> studentDetails = service.getStudentDetails();
    model.addAttribute("studentList", studentDetails);
    return "studentList";
  }

  @GetMapping("/studentCourseList")
  public List<StudentsCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  // 30代の生徒情報を取得
  @GetMapping("/students/in-their-30s")
  public List<Student> getStudentsInTheir30s() {
    return service.searchInTheir30sStudents();
  }

  // Javaコースの生徒情報を取得
  @GetMapping("/students/java-course")
  public List<StudentsCourses> getJavaCourseStudents() {
    return service.searchJavaCourseStudents();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student()); // Student オブジェクトをセット

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent"; // バリデーションエラー時の処理
    }
    if (studentDetail.getStudent() == null) {
      System.out.println("Error: studentDetail.getStudent() is null!");
      return "registerStudent";
    }

    // StudentDetailをStudentに変換して、サービスを通じてデータベースに保存
    Student student = converter.convertToStudent(studentDetail);
    service.registerStudentToDb(student);

    return "redirect:/studentList";
  }

  }

