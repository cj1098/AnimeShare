package cj1098.animeshare.service;

import android.util.Log;

import cj1098.animeshare.util.DaggerUtil;
import cj1098.animeshare.viewmodels.FullListResponse;
import cj1098.event.InitialXMLParseFinishedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

/**
 * Created by chrisjohnson on 20/10/15.
 */
public class AnimeRequestService {
    private static final String TAG = AnimeRequestService.class.getName();

    private static final String MASHAPE_BASE_URL = "https://hummingbirdv1.p.mashape.com";

    // ANN == Anime News network
    private static final String ANN_BASE_URL = "http://www.animenewsnetwork.com/";

    private static final String MASHAPE_DEBUG_KEY = "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy";

    private final MashapeService mMashapeService;

    public AnimeRequestService() {
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
        OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                chain.request().newBuilder().addHeader("Accept", "application/json");
                                Request request = chain.request().newBuilder().addHeader("X-Mashape-Key", MASHAPE_DEBUG_KEY).addHeader("accept", "application/json").build();
                                return chain.proceed(request);
                            }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ANN_BASE_URL)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        mMashapeService = retrofit.create(MashapeService.class);
    }

    public interface MashapeService {
        @GET("encyclopedia/reports.xml?id=155&nlist=all&type=anime")
        Call<FullListResponse> getFullList();
    }

    public void fetchFullAnimeList() {
        Call<FullListResponse> call = mMashapeService.getFullList();
        call.enqueue(new Callback<FullListResponse>() {
            @Override
            public void onResponse(Call<FullListResponse> call, retrofit2.Response<FullListResponse> response) {
                InitialXMLParseFinishedEvent event = new InitialXMLParseFinishedEvent(response.body().getItemList());

            }

            @Override
            public void onFailure(Call<FullListResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}