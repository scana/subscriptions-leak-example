package me.scana.subscriptionsleak;

import androidx.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.scana.subscriptionsleak.di.NonConfigurationScope;


@NonConfigurationScope
public class NonLeakingPresenter implements Presenter {

    private final RecommendMovieUseCase recommendMovieUseCase;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieSuggestionView view;
    private boolean didUserTapTitle;

    public NonLeakingPresenter(RecommendMovieUseCase recommendMovieUseCase) {
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
        Disposable disposable = recommendMovieUseCase.recommendRandomMovie()
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
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewTapped() {
        didUserTapTitle = true;
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
        view = null;
    }

}

