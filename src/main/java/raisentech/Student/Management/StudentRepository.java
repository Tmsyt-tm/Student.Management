package raisentech.Student.Management;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

   @Select("SELECT * FROM student WHERE name = #{name}")
   Student searchByName(String name);

}
