package me.scana.subscriptionsleak;

import android.support.annotation.NonNull;

import me.scana.subscriptionsleak.di.NonConfigurationScope;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


@NonConfigurationScope
public class NonLeakingPresenter implements Presenter {

    private final RecommendMovieUseCase recommendMovieUseCase;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private MovieSuggestionView view;
    private boolean didUserTapTitle;

    public NonLeakingPresenter(RecommendMovieUseCase recommendMovieUseCase) {
        this.recommendMovieUseCase = recommendMovieUseCase;
    }

    @Override
    public void setView(@NonNull MovieSuggestionView view) {
        this.view = view;
        showRecommendedMovieTitle(this.view);
    }

    private void showRecommendedMovieTitle(final MovieSuggestionView view) {
        view.showProgress();
        Subscription subscription = recommendMovieUseCase.recommendRandomMovie()
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
        compositeSubscription.add(subscription);
    }

    @Override
    public void onViewTapped() {
        didUserTapTitle = true;
    }

    @Override
    public void destroy() {
        compositeSubscription.clear();
        view = null;
    }

}

