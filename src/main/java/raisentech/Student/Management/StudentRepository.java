package raisentech.Student.Management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentCourses;

@Mapper
public interface StudentRepository {

  //全件取得機能
  @Select("SELECT * FROM students ")
  List<Student> search();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  // 30代の学生を取得するクエリ
  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<Student> searchintheir30sStudents();

  //Javaコースのコース情報だけを取得するクエリ
  @Select("SELECT * FROM student_courses WHERE course_name = 'Java Programming'")
  List<StudentCourses> searchJavaCourseStudents();
}





