package raisentech.Student.Management.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  // 受講生一覧を取得（StudentDetail 型で返す）
  public List<StudentDetail> getStudentDetails() {
    // まず Student と StudentCourses のリストを取得
    List<Student> students = repository.search();
    List<StudentsCourses> courses = repository.searchStudentCourses();
    // StudentConverter を使って StudentDetail 型に変換
    return converter.convertStudentDetails(students, courses);
  }

  // Studentを登録するメソッド（データベース保存）
  public void registerStudentToDb(Student student) {
    repository.insertStudent(student);  // StudentRepository経由でデータベースに保存
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


}
