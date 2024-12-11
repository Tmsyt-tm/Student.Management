package raisentech.Student.Management;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

@Getter
@Setter
public class Student {

  private String id;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String region;
  private String phone_number;
  private String age;
  private String gender;
}
