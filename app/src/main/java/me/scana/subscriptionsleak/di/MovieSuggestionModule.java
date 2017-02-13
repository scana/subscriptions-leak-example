package me.scana.subscriptionsleak.di;

import me.scana.subscriptionsleak.Presenter;
import me.scana.subscriptionsleak.RecommendMovieUseCase;

public class MovieSuggestionModule {

    public Presenter providePresenter() {
        return new Presenter(new RecommendMovieUseCase());
    }

}
