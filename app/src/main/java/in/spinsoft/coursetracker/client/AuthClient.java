package in.spinsoft.coursetracker.client;

import java.util.List;

import in.spinsoft.coursetracker.dto.LoginDTO;
import in.spinsoft.coursetracker.model.Course;
import in.spinsoft.coursetracker.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthClient {

    @POST("api/v1/auth/login")
    Call<User> login(@Body LoginDTO user);
}
