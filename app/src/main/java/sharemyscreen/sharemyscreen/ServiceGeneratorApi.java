package sharemyscreen.sharemyscreen;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.Interceptor.AuthInterceptor;
import sharemyscreen.sharemyscreen.Interceptor.ConnexionInterceptor;
import sharemyscreen.sharemyscreen.Interceptor.TokenAuthenticator;
import sharemyscreen.sharemyscreen.Services.RequestOfflineService;

/**
 * Created by cleme_000 on 29/02/2016.
 */
public class ServiceGeneratorApi {

    /**
     * Heroku
     */
    public static final String API_BASE_URL = "http://sharemyscreen-api.herokuapp.com";
    public final static String CLIENT = "sqE1rRxhjPbmwgWc";
    public final static String SECRET = "TvfCZag4DRfqLsa8anETSxRNWstscQQK";

    /**
     * Justin
     */
//    public static final String API_BASE_URL = "http://192.168.0.18:4000";
//    public final static String CLIENT = "2JHaiQuF2ehBqgxg";
//    public final static String SECRET = "EkG8hDlDUZMh4AY3DLonCPNTdPFgFBxQ";
//
    public static  <S> S createService(Class<S> serviceClass, Manager manager) {
        return createService(serviceClass, null, manager);
    }

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> serviceClass, TokenEntity tokenEntity, final Manager manager) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.authenticator(new TokenAuthenticator(tokenEntity, manager));
        client.addInterceptor(new ConnexionInterceptor(tokenEntity, manager));
        client.addNetworkInterceptor(new AuthInterceptor(tokenEntity, manager));
        client.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL+"/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client.build()).build();

        return retrofit.create(serviceClass);
    }

    public static RequestOfflineService.IAPIService createService(Class<RequestOfflineService.IAPIService> iapiServiceClass, final TokenEntity tokenEntity, final RequestOfflineEntity requestOfflineEntity, final Manager manager) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.authenticator(new TokenAuthenticator(tokenEntity, manager));
        client.addNetworkInterceptor(new AuthInterceptor(tokenEntity, manager));
        client.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestOfflineEntity.get_url())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build()).build();

        return retrofit.create(iapiServiceClass);
    }
}
