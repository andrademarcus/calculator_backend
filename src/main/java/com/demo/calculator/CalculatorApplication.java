package com.demo.calculator;

import com.demo.calculator.persistence.model.UserRecord;
import com.demo.calculator.response.UserRecordDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();

		TypeMap<UserRecord, UserRecordDTO> propertyMapper = modelMapper.createTypeMap(UserRecord.class, UserRecordDTO.class);
		propertyMapper.addMappings(
				mapper -> {
					mapper.map(src -> src.getDate(), UserRecordDTO::setDateInserted);
					mapper.map(src -> src.getOperation().getType(), UserRecordDTO::setOperationType);
				}
		);

		return modelMapper;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/labels");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setAlwaysUseMessageFormat(true);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

}
