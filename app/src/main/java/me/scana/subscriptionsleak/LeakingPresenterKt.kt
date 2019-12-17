package me.scana.subscriptionsleak

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import me.scana.subscriptionsleak.di.NonConfigurationScope

@NonConfigurationScope
class LeakingPresenterKt(private val recommendMovieUseCase: RecommendMovieUseCase) : Presenter {

    private var disposable = Disposables.empty()
    private var view: MovieSuggestionView? = null
    private var didUserTapTitle = false

    override fun setView(view: MovieSuggestionView) {
        this.view = view
    }

    override fun present() {
        view?.let { showRecommendedMovieTitle(it) }
    }

    private fun showRecommendedMovieTitle(view: MovieSuggestionView) {
        view.showProgress()
        disposable = recommendMovieUseCase.recommendRandomMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieTitle ->
                    view.hideProgress()
                    view.showTitle(movieTitle)
                }) {
                    view.hideProgress()
                    view.showLoadingError()
                }
    }

    override fun onViewTapped() {
        didUserTapTitle = true
    }

    override fun destroy() {
        disposable.dispose()
        view = null
    }

}
