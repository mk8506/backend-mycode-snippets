package kr.minj.helper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    //make it singleton patterned
    private static RetrofitHelper current;

    public static RetrofitHelper getInstance() {
        if (current == null) {
            current = new RetrofitHelper();
        }

        return current;
    }

    private RetrofitHelper() {
        super();
    }

    //methods

    /**
     * basic settings and return instance
     * @param baseUrl
     * @return
     */
    public Retrofit getRetrofit(String baseUrl) {
      //instance for OkHttpClient.Builder
      // OkHttpClient.Builder
      // :inner class of OkHttpClient (main class of okhttp3)
      // :creates objects for communication.
      OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

      //object for communication.
      // :created by OkHttpClient.Builder's build() method
      OkHttpClient httpClient = httpClientBuilder.build();

      //instance for Retrofit.Builder
      // Retrofit.Builder
      // :inner class of Retrofit
      // :creates Retrofit
      Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

      retrofitBuilder.baseUrl(baseUrl);
      retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
      retrofitBuilder.client(httpClient);

      //object for Retrofit
      // :created by Retrofit.Builder's build() method
      Retrofit retrofit = retrofitBuilder.build();
      return retrofit;

      //https://square.github.io/retrofit/
    }

    public void shutdown() {
      
    }    
}
