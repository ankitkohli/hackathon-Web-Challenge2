package com.hackathon.mobileapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.mobileapp.cacheManager.CacheManagerServiceImpl;
import com.hackathon.mobileapp.domain.Address;
import com.hackathon.mobileapp.domain.AppUser;


@RestController
public class StoreUserLocationController {
	
	//used for loading context file
	/*public static ClassPathXmlApplicationContext context;

	static {
		// Acquire Context
		context = new ClassPathXmlApplicationContext("SpringContext.xml");
	}
*/


	@RequestMapping(value="/storeUserInfo",method=RequestMethod.POST)
	public void storeUserInfo(@RequestBody AppUser appUser){
	//System.out.println(appUser.getFirstName());
	
		
	//CacheManagerServiceImpl cacheService = (CacheManagerServiceImpl) context.getBean("cacheService");
	// assume  this code stores data in the redis server.
	//cacheService.storeValue(appUser.getUIN(), appUser);
	 	
		System.out.println("User saved with UIN::" +appUser.getUIN());
	}
	
	
	@RequestMapping(value="/getUserInfo",method=RequestMethod.GET)
	public ResponseEntity<List<AppUser>> getUserInfo(){
		
		// this data will be fetched from redis
		List<AppUser> appUsers=	createAppUserData();
		return new ResponseEntity<List<AppUser>>(appUsers,HttpStatus.OK);
	}


	private List<AppUser> createAppUserData() {
		// TODO Auto-generated method stub
		AppUser appUser1 = new AppUser();
		appUser1.setFirstName("Ankit");
		appUser1.setLastName("kohli");
		appUser1.setUIN("12436839");
		Address userAddress1 =new Address();
		userAddress1.setCityName("Delhi");
		userAddress1.setLatitude("123E");
		userAddress1.setLongitude("74f");
		userAddress1.setPinCode("110027");
		appUser1.setUserAddress(userAddress1);
		
		AppUser appUser2 = new AppUser();
		appUser2.setFirstName("Aman ");
		appUser2.setLastName("Sharma");
		appUser2.setUIN("1243683942");
		Address userAddress2 =new Address();
		userAddress2.setCityName("Chandigarh");
		userAddress2.setLatitude("123E");
		userAddress2.setLongitude("74f");
		userAddress2.setPinCode("110027");
		appUser2.setUserAddress(userAddress2);
		
		AppUser appUser3 = new AppUser();
		appUser3.setFirstName("Amit");
		appUser3.setLastName("kumar");
		appUser3.setUIN("124368232339");
		Address userAddress3 =new Address();
		userAddress3.setCityName("gurgaon");
		userAddress3.setLatitude("123E");
		userAddress3.setLongitude("74f");
		userAddress3.setPinCode("110027");
		appUser3.setUserAddress(userAddress3);
		
		List<AppUser> appUsers =new ArrayList<AppUser>();
		appUsers.add(appUser1);
		appUsers.add(appUser2);
		appUsers.add(appUser3);
		return appUsers;
	}


	
}
