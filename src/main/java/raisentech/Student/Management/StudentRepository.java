package raisentech.Student.Management;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

   //検索機能 一行取得
   @Select("SELECT * FROM student WHERE name = #{name}")
   Student searchByName(String name);

  //登録機能
   @Insert("INSERT student values(#{name},#{age})")
   void  registerStudent(String name, int age);

   //更新機能
   @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
   void updateStudent(String name , int age);

  //削除機能
   @Delete("DELETE FROM student WHERE name = #{name}")
   void  deleteStudent(String name);

   //データベース全取得機能
   @Select("SELECT * FROM student")
   List<Student> findAll();
}



