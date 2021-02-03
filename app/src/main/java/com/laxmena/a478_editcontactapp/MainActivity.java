package com.laxmena.a478_editcontactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Creaetd by Lakshmanan Meiyappan on 02/02/2021
 */

public class MainActivity extends AppCompatActivity {
    private int REQUEST_CODE = 1;
    private final String TAG = "MainActivity";
    private String userName = null;
    private int resultCode = RESULT_CANCELED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateWelcomeText(); // Sets Welcome text in the MainActivity

        // First Button - Opens new activity
        Button fillNameBtn = (Button)findViewById(R.id.editUsername);
        fillNameBtn.setOnClickListener((View v) -> {
            openEditNameActivity();
        });

        //Second Button - Tries to create a new contact
        Button createContactBtn = (Button)findViewById(R.id.createContact);
        createContactBtn.setOnClickListener((View v) -> {
            createNewContact();
        });
    }

    private void openEditNameActivity() {
        // Create a new Intent to the second activity
        Intent intent = new Intent(this, EditName.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void createNewContact() {
        // Create Intent to contact with the valid username
        if(resultCode == RESULT_OK & isValidUserName()) {
            Intent contactInt = new Intent(ContactsContract.Intents.Insert.ACTION);
            contactInt.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactInt.putExtra(ContactsContract.Intents.Insert.NAME, userName);
            startActivity(contactInt);

            resetValues();
        } else if(resultCode == RESULT_CANCELED){
            // Show error message as toast if no name is invalid
            if(isValidUserName())
                Toast.makeText(getApplicationContext(), Const.TOAST_INVALID_NAME + userName, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), Const.TOAST_FILL_NAME, Toast.LENGTH_SHORT).show();
        }
    }

    public void resetValues() {
        // This method is called when a new Contact is created, and the values need to be reset.
        userName = null;
        updateWelcomeText();
        resultCode = RESULT_CANCELED;
    }

    private boolean isValidUserName() {
        // Checks if the username is valid. Returns boolean.
        //
        // FullName is fetched from EditName Activity and assigned to username
        // only if it passes the validation in the EditName Activity.
        return !(userName==null);
    }

    private void updateWelcomeText() {
        // Updates Welcome Text in the MainActivity
        // Customized Welcome text is displayed if valid username is available.
        TextView welcomeText = (TextView)findViewById(R.id.welcomeText);
        if(isValidUserName()) {
            welcomeText.setText(Const.WELCOME_TEXT+userName);
        } else {
            welcomeText.setText(Const.DEFAULT_WELCOME_TEXT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) {
            resetValues();
            return;
        }
        if(requestCode == REQUEST_CODE) {
            userName = data.getStringExtra(Const.FULL_NAME);
            this.resultCode = resultCode;
            if(resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), Const.TOAST_INVALID_NAME + userName, Toast.LENGTH_SHORT).show();
            }
            updateWelcomeText(); //Update welcome text
        }
    }

}