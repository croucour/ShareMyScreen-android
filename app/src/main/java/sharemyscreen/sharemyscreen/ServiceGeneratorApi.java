package sharemyscreen.sharemyscreen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.Services.RequestOfflineService;

/**
 * Created by cleme_000 on 29/02/2016.
 */
public class ServiceGeneratorApi {
    public static final String API_BASE_URL = "http://sharemyscreen-api.herokuapp.com";

    public final static String CLIENT = "sqE1rRxhjPbmwgWc";
    public final static String SECRET = "TvfCZag4DRfqLsa8anETSxRNWstscQQK";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // set your desired log level

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL+"/v1/")
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, Context pContext) {

        return createService(serviceClass, null, pContext);
    }

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    public static <S> S createService(Class<S> serviceClass, final TokenEntity tokenEntity, final Context pContext) {

        final RequestOfflineManager requestOfflineManager = new RequestOfflineManager(pContext);

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(logging);

        client.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();

                if (tokenEntity == null || Objects.equals(tokenEntity.get_token_type(), "Basic")) {
                    requestBuilder.header("Authorization", "Basic " + encodeKeySecret64());
                } else {
                    requestBuilder.header("Authorization", "Bearer " + tokenEntity.get_access_token());
                }

                Request build = requestBuilder.build();

                if (!haveInternetConnection(pContext) && tokenEntity != null && !Objects.equals(build.method(), "GET")) {
                    RequestOfflineEntity requestOfflineEntity = new RequestOfflineEntity(build, tokenEntity);
                    requestOfflineManager.add(requestOfflineEntity);
                }

                okhttp3.Response response = chain.proceed(build);
                return response;
            }
        });




        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL+"/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client.build()).build();

        return retrofit.create(serviceClass);
    }



    public static String encodeKeySecret64()
    {
        try {
            return Base64.encodeToString((CLIENT + ":" + SECRET).getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean haveInternetConnection(Context pContext){
        NetworkInfo network = ((ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return !(network == null || !network.isConnected());
    }

    public static RequestOfflineService.IAPIService createService(Class<RequestOfflineService.IAPIService> iapiServiceClass, final TokenEntity tokenEntity, final RequestOfflineEntity requestOfflineEntity, final Context pContext) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(logging);

        client.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();

                if (tokenEntity == null || Objects.equals(tokenEntity.get_token_type(), "Basic")) {
                    requestBuilder.header("Authorization", "Basic " + encodeKeySecret64());
                } else {
                    requestBuilder.header("Authorization", "Bearer " + tokenEntity.get_access_token());
                }
//                RequestBody requestBody = RequestBody.create(MediaType.parse("UTF-8"), requestOfflineEntity.get_dataParams());
//                requestBuilder.method(requestOfflineEntity.get_methode(), requestBody);

                Request build = requestBuilder.build();

                okhttp3.Response response = chain.proceed(build);
                return response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestOfflineEntity.get_url())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()).build();

        return retrofit.create(iapiServiceClass);
    }
}
