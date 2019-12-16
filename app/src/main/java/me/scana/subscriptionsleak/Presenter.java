package me.scana.subscriptionsleak;

import android.support.annotation.NonNull;

public interface Presenter {
    void setView(@NonNull MovieSuggestionView view);

    void present();

    void onViewTapped();

    void destroy();
}
