package uni.dubna.app.data;

import retrofit2.Response;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.data.retrofit.UserService;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final UserService userService;

    public LoginDataSource(UserService userService) {
        this.userService = userService;
    }

    public UserData login(String username, String password) throws Exception {
        Response<UserData> userDataCall = userService.login(username, password).execute();
        UserData userData = userDataCall.body();
        if (userData == null) {
            throw new Exception("Неправильный логин/пароль");
        }

        return userData;
    }
}