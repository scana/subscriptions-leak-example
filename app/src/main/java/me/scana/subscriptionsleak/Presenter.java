package me.scana.subscriptionsleak;

import android.support.annotation.NonNull;

import me.scana.subscriptionsleak.di.NonConfigurationScope;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;


@NonConfigurationScope
public class Presenter {

    private final RecommendMovieUseCase recommendMovieUseCase;

    private Subscription subscription = Subscriptions.empty();
    private MovieSuggestionView view;
    private boolean didUserTapTitle;

    public Presenter(RecommendMovieUseCase recommendMovieUseCase) {
        this.recommendMovieUseCase = recommendMovieUseCase;
    }

    public void setView(@NonNull MovieSuggestionView view) {
        this.view = view;
        showRecommendedMovieTitle(this.view);
    }

    private void showRecommendedMovieTitle(final MovieSuggestionView view) {
        view.showProgress();
        subscription = recommendMovieUseCase.recommendRandomMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String movieTitle) {
                        view.hideProgress();
                        view.showTitle(movieTitle);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.hideProgress();
                        view.showLoadingError();
                    }
                });
    }

    public void onViewTapped() {
        didUserTapTitle = true;
    }

    public void destroy() {
        subscription.unsubscribe();
        view = null;
    }

}
