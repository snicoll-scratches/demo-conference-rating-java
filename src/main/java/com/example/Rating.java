package com.example;

import java.time.Instant;

public class Rating {

	private final String conference;
	private final int rating;
	private Instant instant;

	public Rating(String conference, int rating) {
		this.conference = conference;
		this.rating = rating;
	}

	public String getConference() {
		return this.conference;
	}

	public int getRating() {
		return this.rating;
	}

	void setInstant(Instant instant) {
		this.instant = instant;
	}

	public Instant getInstant() {
		return this.instant;
	}

	@Override
	public String toString() {
		return "Rating{" + "conference='" + conference + '\'' +
				", rating=" + rating +
				'}';
	}

}
