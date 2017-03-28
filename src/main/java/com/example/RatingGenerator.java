package com.example;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import org.springframework.stereotype.Component;

@Component
public class RatingGenerator {

	private final List<ConferenceDescriptor> conferences = Arrays.asList(
			new ConferenceDescriptor("Modern Component Design with Spring Framework 4.3", 5, 5),
			new ConferenceDescriptor("From Zero to Hero with Spring Boot"),
			new ConferenceDescriptor("Reactive Spring", 3, 5),
			new ConferenceDescriptor("What's new in Angular", 0, 1)
	);

	public Flux<Rating> fetchRatingStream(Duration period) {
		return Flux.generate(() -> 0,
				(BiFunction<Integer, SynchronousSink<Rating>, Integer>) (index, sink) -> {
					Rating rating = generateRandomRating(conferences.get(index));
					sink.next(rating);
					return ++index % conferences.size();
				})
				.zipWith(Flux.interval(period)).map(Tuple2::getT1)
				.map(rating -> {
					rating.setInstant(Instant.now()); return rating;
				})
				.share()
				.log();
	}

	private Rating generateRandomRating(ConferenceDescriptor descriptor) {
		int rating = descriptor.minRating + (int) (Math.random() *
				((descriptor.maxRating - descriptor.minRating) + 1));
		return new Rating(descriptor.title, rating);
	}


	private static class ConferenceDescriptor {

		private final String title;

		private final int minRating;

		private final int maxRating;

		ConferenceDescriptor(String title, int minRating, int maxRating) {
			this.title = title;
			this.minRating = minRating;
			this.maxRating = maxRating;
		}

		ConferenceDescriptor(String title) {
			this(title, 0, 5);
		}

	}

}
