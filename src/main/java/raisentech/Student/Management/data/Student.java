package raisentech.Student.Management.data;

import lombok.Getter;
import lombok.Setter;

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
  private String remarks;
  private boolean isDeleted;

}
