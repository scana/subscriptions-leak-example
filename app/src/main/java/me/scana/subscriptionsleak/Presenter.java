package me.scana.subscriptionsleak;

import androidx.annotation.NonNull;

public interface Presenter {
    void setView(@NonNull MovieSuggestionView view);

    void present();

    void onViewTapped();

    void destroy();
}
