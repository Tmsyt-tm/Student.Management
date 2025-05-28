package raisentech.Student.Management.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisentech.Student.Management.StudentRepository;
import raisentech.Student.Management.controller.converter.StudentConverter;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;
import raisentech.Student.Management.domain.StudentDetail;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Getter
  private final List<StudentDetail> studentList = new ArrayList<>();

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生情報を登録もしくは検索を行います。
   * 検索の処理{@code isRetrieveDetails} が trueの場合 全受講生情報とそれに紐づくコース情報を取得し、converterでStudentdetailへ変換し返します。
   * 登録の処理{@code isRetrieveDetails} が false の場合 StudentDetailを元に受講生情報とコース情報を登録します。
   *
   * @param studentDetail  登録処理時に使用される受講生およびコース情報を含むオブジェクト。検索処理時には未使用ですが、null にしないでください。
   * @param isRetrieveDetails true の場合は情報を取得し返します、false の場合は登録処理を実行します。
   * @return {@code isRetrieveDetails} が true の場合、受講生詳細情報を返します。false の場合は null を返します。
   */
  @Transactional
  public List<StudentDetail> handleStudentTransaction(StudentDetail studentDetail,
      boolean isRetrieveDetails) {
    if (isRetrieveDetails) {
      // 受講生情報とコース情報をリポジトリから取得
      List<Student> students = repository.search();
      List<StudentsCourses> courses = new ArrayList<>();

      for (Student student : students) {
        List<StudentsCourses> findStudentCourses = repository.findStudentCourses((student.getId()));
        courses.addAll(findStudentCourses);
      }

      // 変換処理を行い返す
      return converter.convertStudentDetails(students, courses);
    } else {
      // 登録処理の場合
      Student student = studentDetail.getStudent();

      // ① Student 情報の登録
      repository.insertStudent(student);

      // ② 登録後、生成された ID を利用するために、必要ならば student の ID を取得（DB 側で自動生成される場合）
      // ここで student に ID がセットされる前提で、以下の処理を実施

      // ③ コース情報の登録
      for (StudentsCourses course : studentDetail.getStudentsCourses()) {
        // 登録された student の ID をセットする
        course.setStudentId((student.getId()));
        repository.registerStudentsCourses(course);
      }
    }
    return null;
  }

  @Transactional
  //更新したいID をもとに特定の Student 情報を取得するメソッド
  public StudentDetail findStudent(int id) {
    Student student = repository.findStudent(id);
    List<StudentsCourses> studentsCourses = repository.findStudentCourses(student.getId());

    return new StudentDetail(student, studentsCourses);
  }

  @Transactional

  //指定された Student 情報を更新するメソッド（更新成功なら true）
  public boolean updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    List<StudentsCourses> courses = studentDetail.getStudentsCourses();

    // コース情報の更新（新規追加の場合 studentId をセット）
    for (StudentsCourses course : courses) {
      if (course.getId() == 0) {  // 新しいコース（まだ登録されていない）
        course.setStudentId(student.getId());  // studentId をセット
      }
      updateCourse(course);  // コース情報の更新 or 挿入
    }
    // 受講生本体の更新
    return repository.updateStudent(student) > 0;
  }

  // コース情報を更新するメソッド
  public int updateCourse(StudentsCourses studentsCourses) {
    int result = repository.updateCourse(studentsCourses);
    System.out.println("更新結果：" + result);
    return result;
  }
}