package raisentech.Student.Management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisentech.Student.Management.data.Student;
import raisentech.Student.Management.data.StudentsCourses;
import raisentech.Student.Management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourses> covertStudentCourses = studentsCourses.stream()
          .filter(studentCourse -> student.getId() == studentCourse.getStudentId())
          .collect(Collectors.toList());

      studentDetail.setStudentsCourses(covertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  // StudentDetailをStudentに変換するメソッドを追加
  public Student convertToStudent(StudentDetail studentDetail) {
    Student student = new Student();
    student.setName(studentDetail.getStudent().getName());;
    // 必要に応じて他の属性も設定

    return student;
  }
}