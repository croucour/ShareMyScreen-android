package sharemyscreen.sharemyscreen.Services;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import sharemyscreen.sharemyscreen.*;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.MyService;

/**
 * Created by cleme_000 on 22/02/2016.
 */
public class RequestOfflineService extends MyService {

    public interface IAPIService {
        @Headers("Content-Type: application/json")
        @PUT
        Call<String> executePut(@Url String url, @Body Map<String, String> params);
        @Headers("Content-Type: application/json")
        @POST
        Call<String> executePost(@Url String url, @Body Map<String, String> params);
        @Headers("Content-Type: application/json")
        @DELETE
        Call<String> executeDelete(@Url String url, @Body Map<String, String> params);
    }
    private RequestOfflineManager _requestOfflineManager = null;

    public RequestOfflineService(Manager manager) {
        super(manager);
    }

    public void runRequest(final RequestOfflineEntity requestOfflineEntity) throws JSONException {

        TokenEntity tokenEntity = _manager._tokenManager.selectById(requestOfflineEntity.get_token_id());
        if (tokenEntity != null) {

            IAPIService api = ServiceGeneratorApi.createService(IAPIService.class, tokenEntity, requestOfflineEntity, _manager);

            JSONObject json = new JSONObject(requestOfflineEntity.get_dataParams());
            Iterator<String> keys = json.keys();
            HashMap<String, String> params = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();

                params.put(key, json.getString(key));
            }

            String method = requestOfflineEntity.get_methode();
            Call call = null;
            if (Objects.equals(method, "POST")) {
                call = api.executePost(requestOfflineEntity.get_request(), params);
            }
            else if (Objects.equals(method, "PUT")) {
                call = api.executePut(requestOfflineEntity.get_request(), params);
            }
            else {
                call = api.executeDelete(requestOfflineEntity.get_request(), params);
            }
            if (call != null) {
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //TODO set le code
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // TODO : set error msg
                    }
                });
            }
        }
    }
}
