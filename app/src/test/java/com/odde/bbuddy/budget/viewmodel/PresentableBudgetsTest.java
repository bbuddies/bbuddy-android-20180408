package com.odde.bbuddy.budget.viewmodel;

import android.support.annotation.NonNull;

import com.odde.bbuddy.budget.api.BudgetApi;
import com.odde.bbuddy.common.functional.Consumer;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class PresentableBudgetsTest {

    BudgetApi stubBudgetApi = mock(BudgetApi.class);

    @Test
    public void no_budgets_should_be_0() {
        givenExistBudgets();

        assertThat(calculate("2018-01-05", "2018-01-14")).isEqualTo(0);
    }

    @Test
    public void one_budget_but_no_overlap_with_start_date_and_end_date() {
        givenExistBudgets(budget("2018-02", 280));

        assertThat(calculate("2018-01-05", "2018-01-14")).isEqualTo(0);
    }

    @Test
    public void one_budget_and_start_date_and_end_date_is_the_same_date_in_it() {
        givenExistBudgets(budget("2018-02", 280));

        assertThat(calculate("2018-02-05", "2018-02-05")).isEqualTo(10);
    }

    @Test
    public void one_budget_and_start_date_and_end_date_both_in_it() {
        givenExistBudgets(budget("2018-02", 280));

        assertThat(calculate("2018-02-05", "2018-02-15")).isEqualTo(110);
    }

    @Test
    public void one_budget_and_start_date_in_it_but_end_date_not_in_it() {
        givenExistBudgets(budget("2018-02", 280));

        assertThat(calculate("2018-02-05", "2018-03-05")).isEqualTo(240);
    }

    @Test
    public void one_budget_and_start_date_not_in_it_but_end_date_in_it() {
        givenExistBudgets(budget("2018-02", 280));

        assertThat(calculate("2018-01-05", "2018-02-10")).isEqualTo(100);
    }

    @Test
    public void one_budget_and_start_date_before_it_and_end_date_after_it() {
        givenExistBudgets(budget("2018-02", 280));

        assertThat(calculate("2018-01-05", "2018-03-10")).isEqualTo(280);
    }

    @Test
    public void two_budget_and_start_date_and_end_date_in_it() {
        givenExistBudgets(
                budget("2018-02", 280),
                budget("2018-03", 310));

        assertThat(calculate("2018-02-05", "2018-03-10")).isEqualTo(340);
    }

    @Test
    public void lack_of_some_budget() {
        givenExistBudgets(
                budget("2018-02", 2800),
                budget("2018-04", 300));

        assertThat(calculate("2018-02-05", "2018-04-10")).isEqualTo(2500);
    }

    @Test
    public void different_year_but_same_month() {
        givenExistBudgets(
                budget("2018-02", 280),
                budget("2018-04", 300));

        assertThat(calculate("2017-02-05", "2017-04-10")).isEqualTo(0);
    }

    @Test
    public void different_year_but_between_start_and_end() {
        givenExistBudgets(budget("2018-04", 300));

        assertThat(calculate("2017-03-05", "2017-05-10")).isEqualTo(0);
    }

    @Test
    public void start_is_after_end() {
        givenExistBudgets(budget("2018-04", 300));

        assertThat(calculate("2018-04-15", "2018-04-10")).isEqualTo(0);
    }

    @NonNull
    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

    @NonNull
    private PresentableBudgets presentableBudgets() {
        return new PresentableBudgets(stubBudgetApi, null, null);
    }

    private int calculate(String startDate, String endDate) {
        PresentableBudgets presentableBudgets = presentableBudgets();
        presentableBudgets.setStartDate(startDate);
        presentableBudgets.setEndDate(endDate);
        return presentableBudgets.calculate();
    }

    private void givenExistBudgets(final Budget... budgets) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                consumer.accept(Arrays.asList(budgets));
                return null;
            }
        }).when(stubBudgetApi).processAllBudgets(any(Consumer.class));
    }

}