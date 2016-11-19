package cj1098.animeshare.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import cj1098.event.AnimeObjectReceivedEvent;
import cj1098.event.RxBus;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.userList.AnimeObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chrisjohnson on 20/10/15.
 */
public class AnimeRequestService {
    private static final String TAG = AnimeRequestService.class.getName();

    public static final String MASHAPE_BASE_URL = "https://hummingbirdv1.p.mashape.com";

    private static final String MASHAPE_DEBUG_KEY = "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy";

    private final MashapeService mMashapeService;


    public AnimeRequestService() {
        OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                chain.request().newBuilder().addHeader("Accept", "application/json");
                                Request request = chain.request().newBuilder().addHeader("X-Mashape-Key", MASHAPE_DEBUG_KEY).addHeader("accept", "application/json").build();
                                return chain.proceed(request);
                            }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MASHAPE_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        mMashapeService = retrofit.create(MashapeService.class);
    }

    public interface MashapeService {
        @GET("/anime/{id}")
        Call<AnimeObject> getAnimeById(@Path("id") int id);
    }

    public void callService(int startingId, final int endingId) {
        for (; startingId <= endingId; startingId++) {
            Call<AnimeObject> call = mMashapeService.getAnimeById(startingId);
            call.enqueue(new Callback<AnimeObject>() {
                @Override
                public void onResponse(Call<AnimeObject> call, retrofit2.Response<AnimeObject> response) {
                    AnimeObjectReceivedEvent animeObjectReceivedEvent = new AnimeObjectReceivedEvent(response.body());
                    RxBus.getInstance().post(animeObjectReceivedEvent);
                }

                @Override
                public void onFailure(Call<AnimeObject> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }
}