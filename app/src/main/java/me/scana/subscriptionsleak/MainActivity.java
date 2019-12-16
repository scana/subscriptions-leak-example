package me.scana.subscriptionsleak;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import me.scana.subscriptionsleak.di.MovieSuggestionModule;
import me.scana.subscriptionsleak.di.NonConfigurationComponent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup container;

    private NonConfigurationComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (ViewGroup) findViewById(R.id.movie_title_container);
        findViewById(R.id.button_recommend).setOnClickListener(this);
        findViewById(R.id.button_clear_recommendation).setOnClickListener(this);

        if(getLastCustomNonConfigurationInstance() == null) {
            component = new NonConfigurationComponent(new MovieSuggestionModule());
        } else {
            component = (NonConfigurationComponent) getLastCustomNonConfigurationInstance();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return component;
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if(NonConfigurationComponent.NAME.equals(name)) {
            return component;
        }
        return super.getSystemService(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_recommend:
                container.addView(new MovieSuggestionViewImpl(this));
                break;
            case R.id.button_clear_recommendation:
                container.removeAllViews();
                break;
        }
    }
}
