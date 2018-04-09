package com.odde.bbuddy.budget;

import android.app.Activity;

import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.budget.api.BudgetApi;
import com.odde.bbuddy.budget.viewmodel.Budget;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {
    private final Activity mActivity;
    private final BudgetApi mBudgetApi;

    @Inject
    public EditableBudget(BudgetApi budgetApi, Activity activity) {
        mActivity = activity;
        mBudgetApi = budgetApi;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String month;

    private String amount;

    public void add() {
        mActivity.finish();
        Budget budget = new Budget();
        budget.setAmount(Integer.parseInt(amount));
        budget.setMonth(month);
        mBudgetApi.addBudget(budget,new Runnable(){

            @Override
            public void run() {

            }
        });
    }

}
