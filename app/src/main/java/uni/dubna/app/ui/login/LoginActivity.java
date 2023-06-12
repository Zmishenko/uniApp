package uni.dubna.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import uni.dubna.app.MainActivity;
import uni.dubna.app.R;
import uni.dubna.app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(getApplication()))
                .get(LoginViewModel.class);

        Bundle b = getIntent().getExtras();
        if (b != null && b.getBoolean(SHOULD_CLEAR_CACHED_USER_DATA, false)) {
            loginViewModel.clearUserData();
        } else {
            loginViewModel.checkCachedUserData();
        }
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final CheckBox rememberCheckBox = binding.cbRemember;

        loginViewModel.haveCachedUserData.observe(this, userData -> {

            loginViewModel.login(userData.getLogin(), userData.getPassword(), false);
        });
        loginViewModel.onSignedIn.observe(this, data -> {
            showLoadingProgress(false);

            Intent intent = new Intent(getApplication(), MainActivity.class)
                    .putExtra(MainActivity.USER_ARGUMENT, new Gson().toJson(data));
            startActivity(intent);
            finish();
        });

        loginViewModel.showErrorToast.observe(this, s -> {
            showLoadingProgress(false);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
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
        });

//        loginViewModel.getLoginResult().observe(this, loginResult -> {
//            if (loginResult == null) {
//                return;
//            }
//            loadingProgressBar.setVisibility(View.GONE);
//            if (loginResult.getError() != null) {
//                showLoginFailed(loginResult.getError());
//            }
//            if (loginResult.getSuccess() != null) {
//                updateUiWithUser(loginResult.getSuccess());
//            }
//            setResult(Activity.RESULT_OK);
//
//            //Complete and destroy login activity once successful
//            finish();
//        });

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

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showLoadingProgress(true);

                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), rememberCheckBox.isChecked());
                return true;
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            showLoadingProgress(true);

            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(), rememberCheckBox.isChecked());

        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome, model.getDisplayName());
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
    private void showLoadingProgress(boolean show) {
        binding.cbRemember.setVisibility(show ? View.GONE: View.VISIBLE);
        binding.username.setVisibility(show ? View.GONE: View.VISIBLE);
        binding.tvRemember.setVisibility(show ? View.GONE: View.VISIBLE);
        binding.login.setVisibility(show ? View.GONE : View.VISIBLE);
        binding.loading.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.password.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public static final String SHOULD_CLEAR_CACHED_USER_DATA = "clear_user_data";
}