package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("rawtypes")
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication  {

	public static void main(String[] args) {
		ApplicationContext ctx= SpringApplication.run(DemoApplication.class, args);
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
