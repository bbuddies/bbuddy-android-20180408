package com.odde.bbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.odde.bbuddy.account.Account;

public class EditAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Account account = (Account) getIntent().getSerializableExtra("account");

        EditText nameField = (EditText) findViewById(R.id.name);
        nameField.setText(account.getName());
        EditText balanceBroughtForwardField = (EditText) findViewById(R.id.balanceBroughtForward);
        balanceBroughtForwardField.setText(String.valueOf(account.getBalanceBroughtForward()));

        Button updateButton = (Button) findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra("tabPosition", 1);
                startActivity(intent);
            }
        });
    }
}
