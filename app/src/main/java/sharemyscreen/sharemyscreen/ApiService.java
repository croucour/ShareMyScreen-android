package sharemyscreen.sharemyscreen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.Entities.Room;

/**
 * Created by cleme_000 on 29/02/2016.
 */
public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("v1/rooms")
    Call<List<Room>> getRooms();
}
