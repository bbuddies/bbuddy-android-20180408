package com.odde.bbuddy.budget;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.odde.bbuddy.budget.api.BudgetApi;
import com.odde.bbuddy.budget.viewmodel.Budget;
import com.odde.bbuddy.budget.viewmodel.PresentableBudgets;
import com.odde.bbuddy.common.functional.Consumer;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
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

    @Test
    public void should_correctly_calculate_budget_empty() {

        BudgetApi stubBudgetApi = mock(BudgetApi.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                Budget budget = new Budget();
                budget.setMonth("2018-02");
                budget.setAmount(2800);
                consumer.accept(Arrays.asList(budget));
                return null;
            }
        }).when(stubBudgetApi).processAllBudgets(any(Consumer.class));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgetApi, null, null);

        presentableBudgets.setStartDate("2018-01-05");
        presentableBudgets.setEndDate("2018-01-10");
        presentableBudgets.calculateResult();

        assertThat(presentableBudgets.getResult()).isEqualTo("0");

    }

    @Test
    public void should_correctly_calculate_budget_singlemonth() {

        BudgetApi stubBudgetApi = mock(BudgetApi.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                Budget budget = new Budget();
                budget.setMonth("2018-02");
                budget.setAmount(2800);
                consumer.accept(Arrays.asList(budget));
                return null;
            }
        }).when(stubBudgetApi).processAllBudgets(any(Consumer.class));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgetApi, null, null);

        presentableBudgets.setStartDate("2018-01-05");
        presentableBudgets.setEndDate("2018-03-10");
        presentableBudgets.calculateResult();

        assertThat(presentableBudgets.getResult()).isEqualTo("2800");

    }


    @Test
    public void should_correctly_calculate_budget_multimonth() {

        BudgetApi stubBudgetApi = prepareData(
                budget("2018-01", 3100),
                budget("2018-02", 2800),
                budget("2018-03", 3100));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgetApi, null, null);

        presentableBudgets.setStartDate("2018-01-01");
        presentableBudgets.setEndDate("2018-04-10");
        presentableBudgets.calculateResult();

        assertThat(presentableBudgets.getResult()).isEqualTo("9000");

    }

    @Test
    public void should_correctly_calculate_budget_dayofMonth() {

        BudgetApi stubBudgetApi = prepareData(
                budget("2018-01", 3100),
                budget("2018-02", 2800),
                budget("2018-03", 3100));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgetApi, null, null);

        presentableBudgets.setStartDate("2018-01-10");
        presentableBudgets.setEndDate("2018-03-16");
        presentableBudgets.calculateResult();

        assertThat(presentableBudgets.getResult()).isEqualTo("6600");

    }

    @Test
    public void should_correctly_calculate_budget_same_month() {

        BudgetApi stubBudgetApi = prepareData(
                budget("2018-01", 3100),
                budget("2018-02", 2800),
                budget("2018-03", 3100));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgetApi, null, null);

        presentableBudgets.setStartDate("2018-01-10");
        presentableBudgets.setEndDate("2018-01-15");
        presentableBudgets.calculateResult();

        assertThat(presentableBudgets.getResult()).isEqualTo("600");

    }

    @Test
    public void should_correctly_calculate_budget_same_month_notyear() {

        BudgetApi stubBudgetApi = prepareData(
                budget("2018-01", 3100),
                budget("2018-02", 2800),
                budget("2018-03", 3100));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgetApi, null, null);

        presentableBudgets.setStartDate("2017-01-10");
        presentableBudgets.setEndDate("2017-01-15");
        presentableBudgets.calculateResult();

        assertThat(presentableBudgets.getResult()).isEqualTo("0");

    }

    private BudgetApi prepareData(final Budget... budgets) {
        BudgetApi stubBudgetApi = mock(BudgetApi.class);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                consumer.accept(Arrays.asList(budgets));
                return null;
            }
        }).when(stubBudgetApi).processAllBudgets(any(Consumer.class));
        return stubBudgetApi;
    }

    @NonNull
    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }
}