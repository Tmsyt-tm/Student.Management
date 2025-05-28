package raisentech.Student.Management.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;

@Getter
@Setter
@NoArgsConstructor// Lombok を使って、引数なしのコンストラクタ（デフォルトコンストラクタ）を自動生成するアノテーション
@AllArgsConstructor//全フィールドを引数に取るコンストラクタを自動生成するアノテーション
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;

  }


