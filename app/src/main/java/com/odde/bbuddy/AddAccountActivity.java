package com.odde.bbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.odde.bbuddy.account.viewmodel.Account;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Account presentableAccount = new Account(this);
        ViewBinder viewBinder = createViewBinder();
        View rootView = viewBinder.inflateAndBind(R.layout.activity_add_account, presentableAccount);
        setContentView(rootView);
    }

    private ViewBinder createViewBinder() {
        BinderFactory reusableBinderFactory = new BinderFactoryBuilder().build();
        return reusableBinderFactory.createViewBinder(this);
    }

}
