package uni.dubna.app.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import uni.dubna.app.R;
import uni.dubna.app.data.Constants;
import uni.dubna.app.data.LoginRepository;
import uni.dubna.app.data.model.UserData;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    // private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;
    public MutableLiveData<UserData> haveCachedUserData = new MutableLiveData<>();
    public MutableLiveData<UserData> onSignedIn = new MutableLiveData<>();
    public MutableLiveData<String> showErrorToast = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();


    LoginViewModel(LoginRepository loginRepository, Application application) {
        super(application);
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

//    LiveData<LoginResult> getLoginResult() {
//        return loginResult;
//    }

    private UserData getCachedUserData() {
        return loginRepository.getCachedUserData(getPrefs());
    }

    public void login(String username, String password, boolean remember) {
        // can be launched in a separate asynchronous job
        disposable.add(Single.create((SingleOnSubscribe<UserData>) e -> {
                    try {
                        UserData userData = loginRepository.login(username, password);
                        if (remember) {
                            loginRepository.saveUserData(getPrefs(), username, password, userData.getRole().toString());
                        }
                        e.onSuccess(userData);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(t -> {
                    onSignedIn.setValue(t);
                }, e -> showErrorToast.setValue(e.getMessage())));
    }

    public void checkCachedUserData() {
        UserData userData = getCachedUserData();
        if (userData.getLogin() != null && userData.getPassword() != null) {
            haveCachedUserData.setValue(userData);
        }
    }

    public void clearUserData() {
        loginRepository.saveUserData(getPrefs(), null, null, null);
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

    @Override
    protected void onCleared() {
        disposable.dispose();
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