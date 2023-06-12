package uni.dubna.app.data;

import android.content.SharedPreferences;

import uni.dubna.app.data.model.Role;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.utils.Result;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }


    public void saveUserData(SharedPreferences preferences, String username, String password, String role) {
        preferences.edit().putString(USERNAME_PREFERENCES_KEY, username)
                .putString(PASSWORD_PREFERENCES_KEY, password)
                .putString(ROLE_PREFERENCES_KEY, role)
                .apply();
    }

    public UserData getCachedUserData(SharedPreferences preferences) {
        String username = preferences.getString(USERNAME_PREFERENCES_KEY, null);
        String password = preferences.getString(PASSWORD_PREFERENCES_KEY, null);
        String roleName = preferences.getString(ROLE_PREFERENCES_KEY, null);
        Role role;
        if (roleName == null) {
            role = Role.UNKNOWN;
        } else {
            role = Role.valueOf(roleName.toUpperCase());
        }
        return new UserData(username, password, role);
    }

    public UserData login(String username, String password) throws Exception {
        return dataSource.login(username, password);
    }

    private final String USERNAME_PREFERENCES_KEY = "username";
    private final String PASSWORD_PREFERENCES_KEY = "password";
    private final String ROLE_PREFERENCES_KEY = "role";
}