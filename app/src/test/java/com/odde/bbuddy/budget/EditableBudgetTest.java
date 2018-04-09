package com.odde.bbuddy.budget;

import android.app.Activity;

import com.odde.bbuddy.budget.api.BudgetApi;
import com.odde.bbuddy.budget.viewmodel.Budget;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    BudgetApi mockBudgetApi = mock(BudgetApi.class);
    //    BudgetNavigation mockAccountsNavigation = mock(BudgetNavigation.class);
    EditableBudget editableBudget = new EditableBudget(mockBudgetApi, mock(Activity.class));

    @Test
    public void add_should_correctly_add_budget() {

        editableBudget.setMonth("2018-04");
        editableBudget.setAmount("1000");
        editableBudget.add();

        ArgumentCaptor<Budget> captor = forClass(Budget.class);
        verify(mockBudgetApi).addBudget(captor.capture(), any(Runnable.class));
        assertThat(captor.getValue().getMonth()).isEqualTo("2018-04");
        assertThat(captor.getValue().getAmount()).isEqualTo(1000);

    }

}