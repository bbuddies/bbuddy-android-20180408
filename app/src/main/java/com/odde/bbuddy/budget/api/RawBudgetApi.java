package com.odde.bbuddy.budget.api;

import com.odde.bbuddy.budget.viewmodel.Budget;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RawBudgetApi {

    String BUDGET = "/budgets";
    String BUDGET_WITH_ID = BUDGET + "/{id}";

    @POST(BUDGET)
    Call<ResponseBody> addBudget(@Body Budget budget);

    @GET(BUDGET)
    Call<List<Budget>> getAllBudgets();
//
//    @PUT(ACCOUNTS_WITH_ID)
//    Call<ResponseBody> editAccount(@Path("id") int accountId, @Body Budget account);
//
//    @DELETE(ACCOUNTS_WITH_ID)
//    Call<ResponseBody> deleteAccount(@Path("id") int accountId);
}
