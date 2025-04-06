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

  // StudentDetail型に変換するメソッドを追加
  public StudentDetail convertToStudentDetail(Student student) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.getStudent().setName(student.getName());
    studentDetail.getStudent().setFurigana(student.getFurigana());
    studentDetail.getStudent().setNickname(student.getNickname());
    studentDetail.getStudent().setEmail(student.getEmail());
    studentDetail.getStudent().setRegion(student.getRegion());
    studentDetail.getStudent().setPhone_number(student.getPhone_number());
    studentDetail.getStudent().setAge(student.getAge());
    studentDetail.getStudent().setGender(student.getGender());
    studentDetail.getStudent().setCourses(student.getCourses());

    // 名前の設定
    // 他のプロパティを設定する場合
    // studentDetail.setAge(student.getAge());
    return studentDetail;
  }

  }