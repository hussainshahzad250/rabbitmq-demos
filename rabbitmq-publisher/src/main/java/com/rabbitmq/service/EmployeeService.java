package com.rabbitmq.service;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.config.MessagingConfig;
import com.rabbitmq.dto.Employee;

@Service
public class EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private RabbitTemplate template;

	public String constructObjectToXML(Employee employee) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(employee, sw);
			String xmlContent = sw.toString();
			LOGGER.info("[XML_CONTENT] =>" + xmlContent);
			return xmlContent;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String publishEmployee(Employee employee) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(employee, sw);
			String xmlContent = sw.toString();
			LOGGER.info("[XML_CONTENT] =>" + xmlContent);
			template.setExchange(MessagingConfig.TEST_EXCHANGE);
			template.convertAndSend(xmlContent);
			return "Published successfully";
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
