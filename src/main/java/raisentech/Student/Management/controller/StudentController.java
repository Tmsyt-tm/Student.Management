package raisentech.Student.Management.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
  public StudentController(StudentService service, StudentConverter studentConverter) {

    this.service = service;
    this.converter = studentConverter;
  }

  // 統一メソッドで受講生情報とコース情報の一覧を取得
  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<StudentDetail> studentDetails = service.handleStudentTransaction(null, true);

    model.addAttribute("studentList", studentDetails);

    return "studentList";
  }

  //新規受講生の登録
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    Student student = new Student(); // 空の学生オブジェクトを用意
    StudentsCourses course = new StudentsCourses(); // 空のコースオブジェクトを用意
    List<StudentsCourses> courses = Arrays.asList(course); // リストに包む

    StudentDetail studentDetail = new StudentDetail(student, courses);

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  //受講生情報の更新
  @GetMapping("/students/edit/{id}")
  public String editStudent(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.findStudent(id); // IDに対応するStudentを取得
// もしコース情報が空だったら、空のコースを1個追加しておく
    if (studentDetail.getStudentsCourses() == null || studentDetail.getStudentsCourses()
        .isEmpty()) {
      StudentsCourses emptyCourse = new StudentsCourses();
      studentDetail.getStudentsCourses().add(emptyCourse);
    }
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }


  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent"; // バリデーションエラー時にフォームを再表示
    }
    if (studentDetail.getStudent() == null) {
      System.out.println("Error: studentDetail.getStudent() is null!");
      return "registerStudent"; // エラーが発生した場合の対応
    }

    // StudentDetail を登録
    service.handleStudentTransaction(studentDetail, false);

    return "redirect:/studentList"; // 登録後、学生一覧ページにリダイレクト
  }


  @PostMapping("/students/update")
  public String updateStudentForm(@ModelAttribute StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    List<StudentsCourses> courses = studentDetail.getStudentsCourses();

    // 学生情報の更新
    boolean updated = service.updateStudent(studentDetail);

    if (updated) {
      // コース情報が変更されている場合は、コース情報も更新
      for (StudentsCourses course : courses) {
        // コース情報が変更されているかチェック
        if (course.getId() != 0) {  // 0を特別な値として使用
          service.updateCourse(course);  // コース情報を更新
        }
      }
      return "redirect:/studentList"; // 一覧ページへリダイレクト
    } else {
      return "error"; // 更新失敗時にエラーページを表示
    }
  }
}

