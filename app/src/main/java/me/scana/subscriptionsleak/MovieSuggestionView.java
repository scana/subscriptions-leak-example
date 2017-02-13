package me.scana.subscriptionsleak;

public interface MovieSuggestionView {
    void showTitle(String movieTitle);
    void showLoadingError();
    void showProgress();
    void hideProgress();
}
