package com.spring.redis.controllers;

import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

	@Autowired
	ApplicationContext ctx;
	
	 @Value("${redis.topic.name:chat}")
	private String topicName;

	private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

	@GetMapping("/simulate-post-blog")
	public void postBlog() {
		try {
			//when user post the blog and it is saved to database, then an email is sent to all followers of the blog publisher
			//posting blog omitted for brevity 
			//We queue messages and list of users who should receive the message
			StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
			CountDownLatch latch = ctx.getBean(CountDownLatch.class);

			JSONArray json = new JSONArray();
			try {
				LOGGER.info("Sending message...");
				JSONObject obj = new JSONObject();
				obj.put("blog_id", "1");
				obj.put("user_id", "1");
				
				JSONArray emails = new JSONArray();
				emails.put("user1@gmail.com");
				emails.put("user2@gmail.com");
				emails.put("user3@gmail.com");
				obj.put("emails", emails);

				json.put(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			template.convertAndSend(topicName, json.toString());

			latch.await();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
