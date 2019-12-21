package in.spinsoft.coursetracker.client;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static String BASE_URL = "https://apiknowledgetrackerin.cfapps.io/";

    private static Retrofit retrofit = null;

    private static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CourseClient getCourseInstance() {
        return getInstance().create(CourseClient.class);
    }

    public static AuthClient getAuthClient(){
        return getInstance().create(AuthClient.class);
    }
}
