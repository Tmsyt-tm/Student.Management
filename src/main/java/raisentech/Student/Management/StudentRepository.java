package raisentech.Student.Management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  //全件取得機能
  @Select("SELECT * FROM students ")
  List<Student> search();

  @Select("SELECT * FROM student_courses")
  List<student_coruses> searchcourse();

}





