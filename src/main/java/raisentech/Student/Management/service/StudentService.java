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

@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  // 登録された受講生のリストを取得
  @Getter
  private final List<StudentDetail> studentList = new ArrayList<>();

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  @Transactional
  // トランザクションの統一メソッド
  public List<StudentDetail> handleStudentTransaction(StudentDetail studentDetail, boolean isRetrieveDetails) {
    if (isRetrieveDetails) {
      // 受講生一覧を取得する処理はそのまま
      List<Student> students = repository.search();
      List<StudentsCourses> courses = repository.searchStudentCourses();
      // ここでリストを返す処理
      return converter.convertStudentDetails(students, courses);
    } else {
      // 登録処理の場合
      // まず、StudentDetail から Student を取得
      Student student = studentDetail.getStudent();

      // ① Student 情報の登録
      repository.insertStudent(student);

      // ② 登録後、生成された ID を利用するために、必要ならば student の ID を取得（DB 側で自動生成される場合）
      // ここで student に ID がセットされる前提で、以下の処理を実施

      // ③ コース情報の登録
      for (StudentsCourses course : studentDetail.getStudentsCourses()) {
        // 登録された student の ID をセットする
        course.setStudentId(student.getId());
        repository.registerStudentsCourses(course);
      }
    }
    return null;
  }

  // 生徒情報の全件取得
  public List<Student> searchStudentList() {
    return repository.search();
  }

  // コース情報の全件取得
  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentCourses();
  }

  // 30代の生徒情報取得
  public List<Student> searchInTheir30sStudents() {
    return repository.searchInTheir30sStudents();
  }

  // コース情報の中からJavaのみ取得
  public List<StudentsCourses> searchJavaCourseStudents() {
    return repository.searchJavaCourseStudents();
  }

  public boolean updateStudent(Student student) {
    return repository.updateStudent(student) > 0;
  }
}
