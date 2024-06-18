package com.demo.saga.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.thoughtworks.xstream.XStream;

@SpringBootApplication
public class ShippingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShippingServiceApplication.class, args);
	}

	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();
		xStream.allowTypesByWildcard(new String[] { "com.demo.saga.**" });
		return xStream;
	}
}