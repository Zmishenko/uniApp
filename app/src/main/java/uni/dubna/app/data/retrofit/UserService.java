package uni.dubna.app.data.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import uni.dubna.app.data.model.UserData;

public interface UserService {
    @GET("authentication/login/{login}/{password}")
    Call<UserData> login(@Path("login") String login, @Path("password") String password);
}
