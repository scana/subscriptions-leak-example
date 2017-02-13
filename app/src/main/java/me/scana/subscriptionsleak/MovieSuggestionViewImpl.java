package me.scana.subscriptionsleak;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import me.scana.subscriptionsleak.di.NonConfigurationComponent;

public class MovieSuggestionViewImpl extends FrameLayout implements MovieSuggestionView {

    private ProgressBar progressBar;
    private TextView title;
    private Presenter presenter;

    public MovieSuggestionViewImpl(Context context) {
        super(context);
        initWidget(context);
    }

    private void initWidget(Context context) {
        View.inflate(context, R.layout.widget_movie_suggestion, this);
        progressBar = (ProgressBar) findViewById(R.id.widget_movie_suggestion_progress_bar);
        title = (TextView) findViewById(R.id.widget_movie_suggestion_title);
    }

    @Override
    public void showTitle(String movieTitle) {
        title.setText(movieTitle);
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NonConfigurationComponent injector = (NonConfigurationComponent) getContext().getSystemService(NonConfigurationComponent.NAME);
        injector.inject(this);
        presenter.setView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.destroy();
        super.onDetachedFromWindow();
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), R.string.error_loading_suggested_movie, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        title.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        title.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
