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
  public List<StudentDetail> handleStudentTransaction(StudentDetail studentDetail, boolean isRetrieveDetails) {
    if (isRetrieveDetails) {
      // 受講生情報とコース情報をリポジトリから取得
      List<Student> students = repository.search();
      List<StudentsCourses> courses = new ArrayList<>();

      // 各受講生に関連するコース情報を取得
      for (Student student : students) {
        List<StudentsCourses>  findStudentCourses= repository.findStudentCourses((student.getId()));
        courses.addAll( findStudentCourses);
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

//  @Transactional
////指定された Student 情報を更新するメソッド（更新成功なら true）
//  public boolean updateStudent(Student student) {
//    return repository.updateStudent(student) > 0;
//  }
//  //更新したいID をもとに特定の Student 情報を取得するメソッド
//  public StudentDetail findStudent(String id) {
//    Student student =  repository.findStudent(id);
//    List<StudentsCourses> studentsCourses = repository.findStudentCourses(student.getId());
//    StudentDetail studentDetail = new StudentDetail();
//    studentDetail.setStudent(student);
//    studentDetail.setStudentsCourses(studentsCourses);
//    return studentDetail;
//  }

  // コース情報を更新するメソッド
  public int updateCourse(StudentsCourses studentsCourses) {
    return repository.updateCourse(studentsCourses); // コース情報の更新処理を呼び出し
  }
}


