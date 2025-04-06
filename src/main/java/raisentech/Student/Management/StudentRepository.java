package raisentech.Student.Management;

import java.util.List;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  @Insert("INSERT INTO students (name,furigana,nickname,email,region,phone_number,age,gender,courses) "
      +"VALUES (#{name}, #{furigana}, #{nickname}, #{email}, #{region},  #{phone_number}, #{age}, #{gender},#{courses})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);  // 引数としてStudentオブジェクトを受け取る

  //コース情報をデータベースへ登録
  @Insert("INSERT INTO student_courses(student_id, course_name,start_date, end_date) "
      +"VALUES(#{studentId},#{courseName},#{startDate},#{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void  registerStudentsCourses(StudentsCourses studentsCourses);

//受講生更新処理
  @Update("UPDATE students SET name = #{name}, email = #{email}, phone_number= #{phone_number} WHERE id = #{id}")
  int updateStudent(Student student);

}





