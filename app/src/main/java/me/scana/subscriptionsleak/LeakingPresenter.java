package me.scana.subscriptionsleak;

import androidx.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.scana.subscriptionsleak.di.NonConfigurationScope;


@NonConfigurationScope
public class LeakingPresenter implements Presenter {

    private final RecommendMovieUseCase recommendMovieUseCase;

    private Disposable disposable = Disposables.empty();
    private MovieSuggestionView view;
    private boolean didUserTapTitle;

    public LeakingPresenter(RecommendMovieUseCase recommendMovieUseCase) {
        this.recommendMovieUseCase = recommendMovieUseCase;
    }

    @Override
    public void setView(@NonNull MovieSuggestionView view) {
        this.view = view;
    }

    @Override
    public void present() {
        showRecommendedMovieTitle(view);
    }

    private void showRecommendedMovieTitle(final MovieSuggestionView view) {
        view.showProgress();
        disposable = recommendMovieUseCase.recommendRandomMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String movieTitle) {
                        view.hideProgress();
                        view.showTitle(movieTitle);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        view.hideProgress();
                        view.showLoadingError();
                    }
                });
    }

    @Override
    public void onViewTapped() {
        didUserTapTitle = true;
    }

    @Override
    public void destroy() {
        disposable.dispose();
        view = null;
    }
}
