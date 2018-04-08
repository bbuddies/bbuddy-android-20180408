package com.odde.bbuddy.budget;

import android.app.Activity;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {
    private final Activity activity;

    @Inject
    public EditableBudget(Activity activity){

        this.activity = activity;
    }

    public void add(){
        activity.finish();
    }
}
