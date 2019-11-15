package com.spring.redis.receivers;

import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	private CountDownLatch latch;

	@Autowired
	public Receiver(CountDownLatch latch) {
		this.latch = latch;
	}

	public void receiveMessage(String message) {
		try {
			JSONArray jsonArray = new JSONArray(message);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String userId = obj.getString("user_id");
				String blogId = obj.getString("blog_id");
				JSONArray emails = new JSONArray(obj.getString("emails"));
				LOGGER.info("Send batch emails to all emails listed as: " + emails);
				for (int j = 0; j < emails.length(); j++) {
					LOGGER.info("Send single email to user based on any other criterial : " + emails.getString(j));
				}

				LOGGER.info("Poster ID: " + userId);
				LOGGER.info("Blog ID: " + blogId);
				LOGGER.info("Recepients: " + obj.getString("emails"));
			}

			latch.countDown();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}