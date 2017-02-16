package me.scana.subscriptionsleak.di;

import me.scana.subscriptionsleak.LeakingPresenter;
import me.scana.subscriptionsleak.Presenter;
import me.scana.subscriptionsleak.RecommendMovieUseCase;

public class MovieSuggestionModule {

    public Presenter providePresenter() {
        return new LeakingPresenter(new RecommendMovieUseCase());
    }

}
