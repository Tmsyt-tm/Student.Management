package raisentech.Student.Management;

import java.util.List;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  //全件取得機能
  @Select("SELECT * FROM students ")
  List<Student> search();

  @Select("SELECT * FROM student_courses")
  List<StudentsCourses> searchStudentCourses();

  // 30代の学生を取得するクエリ
  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<Student> searchInTheir30sStudents();

  //Javaコースのコース情報だけを取得するクエリ
  @Select("SELECT * FROM student_courses WHERE course_name = 'Java Programming'")
  List<StudentsCourses> searchJavaCourseStudents();

  // 受講生情報をデータベースに登録
  @Insert("INSERT INTO students (name) VALUES (#{name})")
  void insertStudent(Student student);  // 引数としてStudentオブジェクトを受け取る

}





