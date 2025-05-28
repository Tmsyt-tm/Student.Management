package raisentech.Student.Management.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisentech.Student.Management.controller.converter.StudentConverter;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;
import raisentech.Student.Management.domain.StudentDetail;
import raisentech.Student.Management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service, StudentConverter studentConverter) {

    this.service = service;
  }

  /**
   *受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   * @return 受講生一覧（全件）
   */
  // 統一メソッドで受講生情報とコース情報の一覧を取得
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<StudentDetail> studentDetails = service.handleStudentTransaction(null, true);

    return studentDetails;
  }


  /**
   * 新規受講生登録です。
   * registerStudent.htmlで入力した情報をstudentsテーブルへ登録します。
   * IDは自動生成されます。
   * @param model
   * @return
   */
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    Student student = new Student(); // 空の学生オブジェクトを用意
    StudentsCourses course = new StudentsCourses(); // 空のコースオブジェクトを用意
    List<StudentsCourses> courses = Arrays.asList(course); // リストに包む

    StudentDetail studentDetail = new StudentDetail(student, courses);

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @param model
   * @return 受講生
   */
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
  public ResponseEntity registerStudent(@RequestBody StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.ok( "registerStudent"); // バリデーションエラー時にフォームを再表示
    }
    if (studentDetail.getStudent() == null) {
      System.out.println("Error: studentDetail.getStudent() is null!");
      return ResponseEntity.ok("registerStudent"); // エラーが発生した場合の対応
    }

    // StudentDetail を登録
    service.handleStudentTransaction(studentDetail, false);

    return ResponseEntity.ok("新規登録完了"); // 登録後、学生一覧ページにリダイレクト
  }


  @PostMapping("/students/update")
  public ResponseEntity<String> updateStudentForm(@RequestBody StudentDetail studentDetail) {
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
      return ResponseEntity.ok("更新処理に成功しました");
    } else {
      // 更新に失敗した場合のレスポンスを返す
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("学生情報の更新に失敗しました");
    }
  }
}