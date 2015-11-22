package root.com.jiranimmicrocredit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import root.com.jiranimmicrocredit.BackgroudThread.BackGroundThread;
import root.com.jiranimmicrocredit.utils.Constants;
import root.com.jiranimmicrocredit.utils.Functions;
import root.com.jiranimmicrocredit.utils.NetworkUtils;

/**
 * Created by Origicheck on 25/10/2015.
 */
public class SignupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText inputfName,inputlName,inputsName,inputphone,inputEmail,inputcPassword,inputPassword,inputidnumber;
    private TextInputLayout inputLayoutfName,inputLayoutsName,inputLayoutlName,inputLayoutcPassword,inputLayoutphone, inputLayoutEmail,inputLayoutidnumber, inputLayoutPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutfName = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        inputLayoutsName = (TextInputLayout) findViewById(R.id.input_layout_secondname);
        inputLayoutlName = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        inputLayoutphone = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutcPassword = (TextInputLayout) findViewById(R.id.input_layout_cpassword);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutidnumber = (TextInputLayout) findViewById(R.id.input_layout_idnumber);

        inputfName = (EditText) findViewById(R.id.editfirstname);
        inputidnumber=(EditText) findViewById(R.id.editidbumber);
        inputphone = (EditText) findViewById(R.id.editmobile);
        inputsName = (EditText) findViewById(R.id.editsecondname);
        //inputName = (EditText) findViewById(R.id.editfirstname);
        inputEmail = (EditText) findViewById(R.id.editemail);
        inputlName = (EditText) findViewById(R.id.editlastname);
        inputcPassword = (EditText) findViewById(R.id.edicpassword);
        inputPassword = (EditText) findViewById(R.id.edipassword);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputfName.addTextChangedListener(new MyTextWatcher(inputfName));
        inputlName.addTextChangedListener(new MyTextWatcher(inputlName));
        inputsName.addTextChangedListener(new MyTextWatcher(inputsName));
        inputphone.addTextChangedListener(new MyTextWatcher(inputphone));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputcPassword.addTextChangedListener(new MyTextWatcher(inputcPassword));


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        String fname=inputfName.getText().toString();
        String sname=inputsName.getText().toString();
        String lname=inputlName.getText().toString();
        String phone=inputphone.getText().toString();
        String email=inputEmail.getText().toString();
        String cpass=inputcPassword.getText().toString();
        String pass=inputPassword.getText().toString();
        String idnumber=inputidnumber.getText().toString();

        if(cpass.equalsIgnoreCase(pass)){


            TelephonyManager tmanager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String serialnumber=tmanager.getSimSerialNumber();
            String phonenumber=tmanager.getLine1Number();

            if(phonenumber.equalsIgnoreCase(phone)) {

                if (NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("Wifi Enabled") || NetworkUtils.getConnectivityStatusString(this).equalsIgnoreCase("mobile data Enabled") || Constants.ConnectionStatus.equalsIgnoreCase("Wifi Enabled") || Constants.ConnectionStatus.equalsIgnoreCase("mobile data Enabled")) {


                    new BackGroundThread(this, "finishing up", 1).execute(new Functions().profile(0, fname, lname, sname, phone, email, idnumber, pass, serialnumber, "none"));

                } else {
                    Toast.makeText(this, Constants.CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Use your phone number", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Password not Matching", Toast.LENGTH_SHORT).show();
        }
       // Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();


    }

    private boolean validateName() {
        if (inputfName.getText().toString().trim().isEmpty()) {
            inputLayoutfName.setError(getString(R.string.err_msg_name));
            requestFocus(inputfName);
            return false;
        } else {
            inputLayoutfName.setErrorEnabled(false);
        }


        if (inputidnumber.getText().toString().trim().isEmpty()) {
            inputLayoutidnumber.setError(getString(R.string.err_msg_name));
            requestFocus(inputidnumber);
            return false;
        } else {
            inputLayoutidnumber.setErrorEnabled(false);
        }



        if (inputsName.getText().toString().trim().isEmpty()) {
            inputLayoutsName.setError(getString(R.string.err_msg_name));
            requestFocus(inputsName);
            return false;
        }  else {
                inputLayoutsName.setErrorEnabled(false);
            }

        if (inputlName.getText().toString().trim().isEmpty()) {
            inputLayoutlName.setError(getString(R.string.err_msg_name));
            requestFocus(inputlName);
            return false;
        }else {
            inputLayoutlName.setErrorEnabled(false);
        }

        if (inputphone.getText().toString().trim().isEmpty()) {
            inputLayoutphone.setError(getString(R.string.err_msg_name));
            requestFocus(inputphone);
            return false;
        }else {
            inputLayoutphone.setErrorEnabled(false);
        }


        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputcPassword.getText().toString().trim().isEmpty()) {
            inputLayoutcPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputcPassword);
            return false;
        } else {
            inputLayoutcPassword.setErrorEnabled(false);
        }

        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.editfirstname:
                    validateName();
                break;
                case R.id.editsecondname:
                    validateName();
                    break;
                case R.id.editidbumber:
                    validateName();
                    break;
                case R.id.editlastname:
                    validateName();
                    break;
                case R.id.editmobile:
                    validateName();
                    break;
                case R.id.editemail:
                    validateEmail();
                    break;
                case R.id.edipassword:
                    validatePassword();
                    break;
                case R.id.edicpassword:
                    validatePassword();
                    break;
            }
        }
    }
}

