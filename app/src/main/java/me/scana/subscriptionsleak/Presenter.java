package me.scana.subscriptionsleak;

import android.support.annotation.NonNull;

interface Presenter {
    void setView(@NonNull MovieSuggestionView view);

    void onViewTapped();

    void destroy();
}
