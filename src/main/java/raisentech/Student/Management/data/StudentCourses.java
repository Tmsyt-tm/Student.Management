package raisentech.Student.Management.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

  private int id; // "id" カラムに対応
  private int studentId; // "student_id" カラムに対応
  private String courseName; // "course_name" カラムに対応
  private LocalDate startDate; // "start_date" カラムに対応
  private LocalDate endDate;
}
