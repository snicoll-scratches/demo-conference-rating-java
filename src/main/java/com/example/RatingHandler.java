package com.example;

import java.time.Duration;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class RatingHandler {

	private final RatingGenerator generator;

	public RatingHandler(RatingGenerator generator) {
		this.generator = generator;
	}

	public Mono<ServerResponse> fetchRatings(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(this.generator.fetchRatingStream(Duration.ofMillis(200)), Rating.class);
	}

}
