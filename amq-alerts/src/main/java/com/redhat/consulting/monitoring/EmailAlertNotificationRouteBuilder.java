package com.redhat.consulting.monitoring;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmailAlertNotificationRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// @formatter:off
		from("direct:emailBrokerAlertNotification")
			.routeId("email.alert.notification")
			
			.choice()
									
			// alert Notifications 
			.when(header("alertExists").contains("true"))
				.log("Broker Alert Exists")
				.to("velocity:BrokerAlertTemplate.vm")
				.to("smtp://{{smtp.host}}:{{smtp.port}}?username={{smtp.username}}&password={{smtp.password}}&from={{smtp.fromEmail}}&contentType={{smtp.contentType}}")
				
			.otherwise()
				.log("Evaluation Complete")
			
			.end();
		// @formatter:on

	}

}
