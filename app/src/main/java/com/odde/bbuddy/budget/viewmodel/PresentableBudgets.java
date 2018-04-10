package com.odde.bbuddy.budget.viewmodel;

import com.odde.bbuddy.account.view.EditDeleteAccountNavigation;
import com.odde.bbuddy.budget.api.BudgetApi;
import com.odde.bbuddy.common.functional.Consumer;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.joda.time.LocalDate;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

@PresentationModel
@ActivityScope
public class PresentableBudgets implements HasPresentationModelChangeSupport {
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String startDate;

    private String endDate;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    private String result;

    //    private final Lazy<PresentationModelChangeSupport> changeSupportLazyLoader;
    private final BudgetApi budgetApi;
    private final List<Budget> allBudgets = new ArrayList<>();
    private PresentationModelChangeSupport presentationModelChangeSupport
            = new PresentationModelChangeSupport(this);


    @Inject
    public PresentableBudgets(BudgetApi budgetApi, EditDeleteAccountNavigation editDeleteAccountNavigation, @Named("accounts") Lazy<PresentationModelChangeSupport> changeSupportLazyLoader) {
        this.budgetApi = budgetApi;
//        this.changeSupportLazyLoader = changeSupportLazyLoader;
        refresh();
    }
//
//    private PresentationModelChangeSupport changeSupport() {
//        return this.changeSupportLazyLoader.get();
//    }

    @ItemPresentationModel(value = PresentableBudget.class)
    public List<Budget> getBudgets() {
        return allBudgets;
    }

    public void updateAccount(ItemClickEvent event) {
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return presentationModelChangeSupport;
    }

    public void refresh() {
        budgetApi.processAllBudgets(new Consumer<List<Budget>>() {
            @Override
            public void accept(List<Budget> list) {
                allBudgets.clear();
                allBudgets.addAll(list);
                presentationModelChangeSupport.refreshPresentationModel();
            }
        });
    }

    public void calculateResult() {
        result = calculate() + "";
        presentationModelChangeSupport.refreshPresentationModel();
    }


    public int calculate() {
        return calculate(new Period(new LocalDate(this.startDate), new LocalDate(this.endDate)));
    }

    private int calculate(final Period period) {
        int summary = 0;

        for (Budget budget : allBudgets) {
            summary += budget.getOverlappingAmount(period);
        }

        return summary;
    }

}
