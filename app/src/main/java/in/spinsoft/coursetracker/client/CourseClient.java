package in.spinsoft.coursetracker.client;

import java.util.List;

import in.spinsoft.coursetracker.model.Course;
import in.spinsoft.coursetracker.model.CourseTopic;
import in.spinsoft.coursetracker.model.UserCourse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseClient {

    @GET("api/v1/courses")
    Call<List<Course>> list();

    @GET("api/v1/usercourses/users/{userId}")
    Call<List<UserCourse>> list(@Path("userId") String userId);

    @GET("api/v1/courses/{courseId}")
    Call<Course> find(@Path("courseId") String courseId);


    @GET("api/v1/coursetopics/{courseId}/topics/{userId}")
    Call<List<CourseTopic>> listTopics(@Path("courseId") String courseId, @Path("userId") String username);

}
