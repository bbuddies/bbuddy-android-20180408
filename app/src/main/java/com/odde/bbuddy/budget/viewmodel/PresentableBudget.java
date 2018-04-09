package com.odde.bbuddy.budget.viewmodel;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

public class PresentableBudget implements ItemPresentationModel<Budget> {

    private String month;
    private int amount;

    @Override
    public void updateData(Budget budget, ItemContext itemContext) {
        this.month = budget.getMonth();
        this.amount = budget.getAmount();
    }

    public String getDisplayOfBudget() {
        return month + " " + amount;
    }
}
