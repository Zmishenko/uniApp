package uni.dubna.app.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.SharedPreferences;
import android.util.Patterns;

import uni.dubna.app.MainActivity;
import uni.dubna.app.data.Constants;
import uni.dubna.app.data.LoginRepository;
import uni.dubna.app.data.Result;
import uni.dubna.app.data.model.LoggedInUser;
import uni.dubna.app.R;
import uni.dubna.app.data.model.UserData;

import java.time.Instant;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private MutableLiveData<UserData> _haveCachedUserData = new MutableLiveData<>();
    public LiveData<UserData> haveCachedUserData = _haveCachedUserData;

    LoginViewModel(LoginRepository loginRepository, Application application) {
        super(application);
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    private UserData getCachedUserData() {
        return loginRepository.getCachedUserData(getPrefs());
    }
    public Intent login(String username, String password, boolean remember) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);
        Intent intent = null;
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();

            if (remember) {
                loginRepository.saveUserData(getPrefs(), username, password, data.getRole().toString());
            }

            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            intent = new Intent(getApplication(), MainActivity.class)
                    .putExtra(MainActivity.NAME_ARGUMENT, data.getDisplayName())
                    .putExtra(MainActivity.ID_ARGUMENT,data.getUserId())
                    .putExtra(MainActivity.ROLE_ARGUMENT,data.getRole().toString());
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }

        return intent;
    }

    public void checkCachedUserData() {
        UserData userData = getCachedUserData();
        if (userData.getUsername()!=null && userData.getPassword() != null) {
            _haveCachedUserData.setValue(userData);
        }
    }
    public void clearUserData() {
        loginRepository.saveUserData(getPrefs(),null,null,null);
    }
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private SharedPreferences getPrefs() {
        return getApplication().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}