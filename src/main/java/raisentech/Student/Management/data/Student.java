package raisentech.Student.Management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String region;
  private String phone_number;
  private int age;
  private String gender;
  private String remarks;
  private Boolean isDeleted;
  private  String courses;
}
