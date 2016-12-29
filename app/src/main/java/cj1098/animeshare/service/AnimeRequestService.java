package cj1098.animeshare.service;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cj1098.animeshare.userList.AccessTokenInformation;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.util.DaggerUtil;
import cj1098.event.AccessTokenRetrievedEvent;
import cj1098.event.BatchResponseFinishedEvent;
import cj1098.event.RxBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by chrisjohnson on 20/10/15.
 */
public class AnimeRequestService {
    private static final String TAG = AnimeRequestService.class.getName();

    private static final String MASHAPE_BASE_URL = "https://hummingbirdv1.p.mashape.com";

    // ANN == Anime News network
    private static final String ANN_BASE_URL = "http://www.animenewsnetwork.com/";

    private static final String ANI_LIST_BASE_URL = "https://anilist.co/api/";

    private static final String GRANT_TYPE = "client_credentials";
    private static final String CLIENT_ID = "cj1098-m2vgc";
    private static final String CLIENT_SECRET = "l2VK1hrdw3LQrrpfJPMSGbaALvUbi";


    private static final String MASHAPE_DEBUG_KEY = "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy";

    private final AniListService mAniListService;

    public AnimeRequestService() {
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
        OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                chain.request().newBuilder().addHeader("Accept", "application/json");
                                Request request = chain.request().newBuilder().addHeader("X-Mashape-Key", MASHAPE_DEBUG_KEY).addHeader("accept", "application/json").build();
                                return chain.proceed(request);
                            }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ANI_LIST_BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        mAniListService = retrofit.create(AniListService.class);
    }

    interface AniListService {
        @GET("encyclopedia/reports.xml?id=155&nlist=all&type=anime")
        Call<AnimeObject> getFullList();

        // NOTE: Possibly later add the ability for customization for the user to add how many items they want to batch at first.
        @GET("browse/anime")
        Call<List<AnimeObject>> getBatchResponse(@QueryMap Map<String, String> options);

        @POST("auth/access_token")
        Call<AccessTokenInformation> grabAuthAccessToken(@QueryMap Map<String, String> options);
    }

    public void getAccessTokenAndMakeFirstBatchRequest() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("grant_type", GRANT_TYPE);
        queryMap.put("client_id", CLIENT_ID);
        queryMap.put("client_secret", CLIENT_SECRET);
        Call<AccessTokenInformation> call = mAniListService.grabAuthAccessToken(queryMap);
        call.enqueue(new Callback<AccessTokenInformation>() {
            @Override
            public void onResponse(Call<AccessTokenInformation> call, Response<AccessTokenInformation> response) {
                AccessTokenRetrievedEvent accessTokenRetrievedEvent = new AccessTokenRetrievedEvent(response.body().getAccessToken(), response.body().getExpiresIn());
                RxBus.getInstance().post(accessTokenRetrievedEvent);
                getAnimeBatch(response.body().getAccessToken(), "1");
            }

            @Override
            public void onFailure(Call<AccessTokenInformation> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    // NOTE: divide by 40 to get the correct page **** 0 and 1 are the same page. ****
    public void getAnimeBatch(String access_token, String page) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("access_token", access_token);
        queryMap.put("page", page);
        Call<List<AnimeObject>> call = mAniListService.getBatchResponse(queryMap);
        call.enqueue(new Callback<List<AnimeObject>>() {
            @Override
            public void onResponse(Call<List<AnimeObject>> call, Response<List<AnimeObject>> response) {
                BatchResponseFinishedEvent batchResponseFinishedEvent = new BatchResponseFinishedEvent(response.body());
                RxBus.getInstance().post(batchResponseFinishedEvent);
            }

            @Override
            public void onFailure(Call<List<AnimeObject>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}