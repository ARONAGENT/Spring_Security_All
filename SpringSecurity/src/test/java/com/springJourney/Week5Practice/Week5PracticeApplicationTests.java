package com.springJourney.Week5Practice;

import com.springJourney.Week5Practice.Entities.UserEntity;
import com.springJourney.Week5Practice.Services.JwtServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Week5PracticeApplicationTests {


	@Autowired
	private JwtServices jwtServices;
//	@Test
//	void contextLoads() {
//		UserEntity userEntity=new UserEntity(1L,"rohan123@gmail.com","Rohan@123");
//		String Token=jwtServices.generateJwtToken(userEntity);
//		Long id=jwtServices.getUserIdFromToken(Token);
//		System.out.println("Your token :"+Token);
//		System.out.println("token userid :"+id);
//	}

}
