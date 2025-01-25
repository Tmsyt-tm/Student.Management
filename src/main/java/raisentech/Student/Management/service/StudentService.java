package raisentech.Student.Management.service;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisentech.Student.Management.StudentRepository;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentCourses;

@Service
public class StudentService {

  private static StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //生徒情報の全件取得
  public List<Student> searchStudentList() {
    return repository.search();
  }

  //コース情報の全件取得
  public List<StudentCourses> searchStudentsCourseList() {
    return repository.searchStudentCourses();
  }

  //30代の生徒情報取得
  public static List<Student> searchintheir30sStudents() {
    return repository.searchintheir30sStudents();
  }

  //コース情報の中からJavaのみ取得
  public static List<StudentCourses> searchJavaCourseStudents() {
    return repository.searchJavaCourseStudents();
  }
}
