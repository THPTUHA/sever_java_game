package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("rawtypes")
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication  {

	public static void main(String[] args) throws IOException {
		// UserRespository userRespository=context.getBean(UserRespository.class);
		// for(User a:users)System.out.println(a);
		// userRespository.delete(new User(14,"Linh"));
		// User users=userRespository.getUser(14);
		// System.out.println(users);
		// System.out.println("Let's inspect the beans provided by Spring Boot:");

        // String[] beanNames = ctx.getBeanDefinitionNames();
        // Arrays.sort(beanNames);
        // for (String beanName : beanNames) {
        //     System.out.println(beanName);
        // }
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			"cloud_name", "nghiawebgamejava",
			"api_key", "175186513787978", 
			"api_secret", "syF76RQKDzTyopD22292VwMPgKk")); 
			SingletonManager manager = new SingletonManager();
			manager.setCloudinary(cloudinary);
			manager.init();
		// File  file = new File("C:\\Users\\Dell\\Downloads\\album\\a.jpg");
		// Map upload = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		// System.out.println(upload.get("url"));
	ApplicationContext ctx= SpringApplication.run(DemoApplication.class, args);
	}
	// @Autowired
    // UserRepository userRepository;
    // @Autowired
    // PasswordEncoder passwordEncoder;

    // @Override
    // public void run(String... args) throws Exception {
    //     // Khi chương trình chạy
    //     // Insert vào csdl một user.
    //     User user = new User("minhhieu@gmail.com",passwordEncoder.encode("123"));
    //     userRepository.save(user);
    //     System.out.println(user);
    // }
	// @Bean
	// 	public FilterRegistrationBean corsFilterRegistration() {
	// 		FilterRegistrationBean registrationBean =
	// 				new FilterRegistrationBean(new CORSFilter());
	// 		registrationBean.setName("CORS Filter");
	// 		registrationBean.addUrlPatterns("/*");
	// 		registrationBean.setOrder(1);
	// 		return registrationBean;
	// 	}

}
