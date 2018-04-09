package com.odde.bbuddy.budget;

import android.app.Activity;

import com.odde.bbuddy.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class BudgetNavigation {

    private final Activity activity;

    @Inject
    public BudgetNavigation(Activity activity) {
        this.activity = activity;
    }

    public void navigate() {
        activity.finish();
    }
}
