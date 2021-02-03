package com.laxmena.a478_editcontactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import java.util.concurrent.atomic.AtomicReference;
/**
 * Creaetd by Lakshmanan Meiyappan on 02/02/2021
 */
public class EditName extends AppCompatActivity {
    private final String TAG = "EditNameActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        EditText fullNameText = findViewById(R.id.editTextFullName);

        // Create a Listener to handle Done option from Soft Keyboard
        fullNameText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                String fullName = fullNameText.getText().toString().trim(); //Trim excess whitespace
                // Check if the full name has only alphabets and spaces
                boolean result = validateFullName(fullName);
                submitForm(result, fullName);
            }
            return true;
        });
    }

    private void submitForm(boolean result, String fullName) {
        // Send the values - fullName to the parent class
        Intent i = new Intent();
        i.putExtra(Const.FULL_NAME, fullName);
        if(result) {
            setResult(RESULT_OK, i);
        } else {
            setResult(RESULT_CANCELED, i);
        }
        finish();
    }

    private boolean validateFullName(String fullName) {
        // Full Name should adhere to following condition
        // 1. Has only Alphabets and spaces
        // 2. Must contain a First Name and a Last Name
        //
        // Returns true if FullName satisfies the above conditions
        // Returns false if it doesn't satisfy or has null/empty strings.
        String regex = "([a-zA-Z ])+";
        if(fullName == null |
                fullName.equals("") |
                !fullName.contains(" ") |
                !fullName.matches(regex))
            return false;
        return true;
    }

}