package raisentech.Student.Management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private  StudentRepository repository;

	private  String name ="Enami Kouzi";
	private  String age ="37";
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public  String  getStudentInfo()  {
		Student student = repository.searchByName("TanakaSatosi");
		return student.getName() + " " + student.getAge() +"歳";
	}

	@PostMapping("/studentInfo")
	public  void  setStudentInfo(String name,String age){
		this.name = name;
		this.age = age;
	}
}


