package raisentech.Student.Management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/studentList")
  public List<Student> getstudentList() {
    return repository.search();
  }

  @GetMapping("/studentcourseList")
  public List<student_coruses> getstudentcourseList() {
    return repository.searchcourse();
///新規リポジトリへのプッシュ
  }
}
