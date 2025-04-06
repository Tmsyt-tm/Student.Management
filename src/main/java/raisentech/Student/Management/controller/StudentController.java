package raisentech.Student.Management.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;
import raisentech.Student.Management.domain.StudentDetail;
import raisentech.Student.Management.service.StudentService;

@Controller
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    // 統一メソッドで受講生一覧を取得
    List<StudentDetail> studentDetails =  service.handleStudentTransaction(null, true);
    model.addAttribute("studentList", studentDetails);
    return "studentList";
  }

  @GetMapping("/studentCourseList")
  public List<StudentsCourses> getStudentsCourseList() {
    // コース情報の全件取得
    return service.searchStudentsCourseList();
  }

  // 30代の生徒情報を取得
  @GetMapping("/students/in-their-30s")
  public List<Student> getStudentsInTheir30s() {
    // 30代の生徒情報取得
    return service.searchInTheir30sStudents();
  }

  // Javaコースの生徒情報を取得
  @GetMapping("/students/java-course")
  public List<StudentsCourses> getJavaCourseStudents() {
    // Javaコースのみ取得
    return service.searchJavaCourseStudents();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses())); // コースをセット

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

    // StudentDetail 全体を登録するように変更する
    service.handleStudentTransaction(studentDetail, false);

    return "redirect:/studentList";
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Student student) {
    student.setId(id);
    boolean updated = service.updateStudent(student);  // 更新処理を呼び出し
    if (updated) {
      return ResponseEntity.ok("Student updated successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
    }
  }
}

