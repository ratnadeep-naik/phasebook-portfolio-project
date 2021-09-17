package com.portfolio.phasebook.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;


@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
				.setAllowedOrigins("http://localhost:4200")
				.setAllowedOrigins("http://phasebookfrontend.s3-website.us-east-2.amazonaws.com")
				.setAllowedOrigins("http://phasebook-frontend.s3-website.us-east-2.amazonaws.com")
				.withSockJS();
	}


	@Override
	    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//	        messages.anyMessage().authenticated();

//	        messages
//	                .nullDestMatcher().authenticated()
//	                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
//	                .simpDestMatchers("/app/**").hasRole("USER")
//	                .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
//	                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
//	                .anyMessage().denyAll();

//		messages
////            .nullDestMatcher().authenticated()
//				.nullDestMatcher().permitAll()
//				.simpSubscribeDestMatchers("/topic/greetings/*").permitAll()
//				.simpDestMatchers("/app/**").permitAll()
////				.simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
////				.simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
//				.anyMessage().denyAll();


		messages
				.nullDestMatcher().authenticated()
				.simpSubscribeDestMatchers("/user/queue/errors").permitAll()
				.simpDestMatchers("/app/**").permitAll()
				.simpSubscribeDestMatchers("/news/**", "/topic/**").permitAll()
				.anyMessage().denyAll();



	}


	    @Override
	    protected boolean sameOriginDisabled() {
	        return true;
	    }

//	@Override
//	public boolean configureMessageConverters(List<MessageConverter> arg0) {
//		// TODO Auto-generated method stub
//		return true;
//	}


}
