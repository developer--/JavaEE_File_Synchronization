import okhttp3.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by user-00 on 4/7/17.
 */
public interface NetworkApi {

    String uploadFile = "synchronization_war_exploded/upload";

    @Multipart
    @POST(uploadFile)
    Call<ResponseBody> upload(@Part("videofile") RequestBody description, @Part MultipartBody.Part file
    );

    class Factory {
        private Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        private static NetworkApi.Factory serverApi;

        public static NetworkApi getApi() {
            httpClient.addInterceptor(chain -> {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader("Content-Type", "text/html");
                return chain.proceed(builder.build());
            });
            if (serverApi == null){
                serverApi = new NetworkApi.Factory();
            }
            return serverApi.retrofit.create(NetworkApi.class);
        }
    }
}
