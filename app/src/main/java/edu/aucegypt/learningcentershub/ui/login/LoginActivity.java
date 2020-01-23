package edu.aucegypt.learningcentershub.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.aucegypt.learningcentershub.MyAccount;
import edu.aucegypt.learningcentershub.R;
import edu.aucegypt.learningcentershub.ui.login.LoginViewModel;
import edu.aucegypt.learningcentershub.ui.login.LoginViewModelFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.gotocreateAccount);
        textView.setOnClickListener(this);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        Button exitButton = findViewById(R.id.exit_login);
        exitButton.setOnClickListener(this);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonString;
                JSONObject json = new JSONObject();
                try {
                    json.put("Email", usernameEditText.getText());
                    json.put("Password", passwordEditText.getText().toString());
                    jsonString = json.toString();
                    String url = "http://10.40.47.60:3000/myroute/login";

                    OkHttpClient client = new OkHttpClient();
                    final MediaType JSON = MediaType.get("application/json; charset=utf-8");

                    final RequestBody body = RequestBody.create(jsonString, JSON);
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()){
                                final String myResponse = response.body().string();
                                JSONObject myResponseReader;
                                    if (myResponse != "") {
                                        try {
                                            myResponseReader = new JSONObject(myResponse);
                                            Boolean status = myResponseReader.getBoolean("status");
                                            String message = myResponseReader.getString("message");
                                                if (status == true){
                                                    Intent mIntent = new Intent(LoginActivity.this, MyAccount.class);
                                                    startActivity(mIntent);
                                                }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                            }

                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.gotocreateAccount) {
            Intent i = new Intent("edu.aucegypt.learningcentershub.CREATE_ACCOUNT");
            startActivity(i);
        }
        if (view.getId()==R.id.exit_login){
            Intent i = new Intent("edu.aucegypt.learningcentershub.MAIN_ACTIVITY");
            startActivity(i);
        }
    }
}
