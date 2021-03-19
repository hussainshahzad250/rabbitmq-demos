package com.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.rabbitmq.config.MessagingConfig;
import com.rabbitmq.dto.Order;
import com.rabbitmq.dto.OrderStatus;

@Component
public class User {

	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

	@RabbitListener(queues = MessagingConfig.QUEUE)
	public void consumeMessageFromQueue(OrderStatus orderStatus) {
		LOGGER.info("Message recieved from queue [my_queue]: " + orderStatus);
		Order order = orderStatus.getOrder();
		String url = "http://localhost:8087/order";

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			protected boolean hasError(HttpStatus statusCode) {
				return false;
			}
		});

		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Order> httpEntity = new HttpEntity<>(order, httpHeader);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		LOGGER.info("Message published to queue [my_queue1]:  " + response.getBody());
	}
}
