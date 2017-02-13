package me.scana.subscriptionsleak.di;

import me.scana.subscriptionsleak.MovieSuggestionViewImpl;
import me.scana.subscriptionsleak.Presenter;

public class NonConfigurationComponent {

    public static final String NAME = "nonConfigurationComponent";

    private final Presenter presenter;

    public NonConfigurationComponent(MovieSuggestionModule movieSuggestionModule) {
        this.presenter = movieSuggestionModule.providePresenter();
    }

    public void inject(MovieSuggestionViewImpl movieSuggestionView) {
        movieSuggestionView.setPresenter(presenter);
    }
}
