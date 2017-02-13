package me.scana.subscriptionsleak;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class RecommendMovieUseCase {

    private static final List<String> COOL_MOVIES = Arrays.asList("Fight Club", "The Departed", "Hot Fuzz");
    private final Random random = new SecureRandom();

    public Observable<String> recommendRandomMovie() {
        return Observable.just(getRandomMovie()).delay(3, TimeUnit.SECONDS);
    }

    private String getRandomMovie() {
        return COOL_MOVIES.get(random.nextInt(COOL_MOVIES.size()));
    }

}
