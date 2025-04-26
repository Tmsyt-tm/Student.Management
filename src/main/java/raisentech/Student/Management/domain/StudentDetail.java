package raisentech.Student.Management.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses ;

  // **コンストラクタ：新しいインスタンスが作られた際に最初に呼ばれる**

//  public StudentDetail() {
//    this.student = new Student(); // 初期化処理として Student オブジェクトを作成
//    this.studentsCourses = new ArrayList<>(); // リストの初期化
//  }
}

