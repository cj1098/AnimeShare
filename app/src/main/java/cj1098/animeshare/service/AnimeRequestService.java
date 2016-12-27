package cj1098.animeshare.service;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.List;

import cj1098.animeshare.util.DaggerUtil;
import cj1098.animeshare.xmlobjects.Anime;
import cj1098.animeshare.xmlobjects.BatchResponse;
import cj1098.animeshare.xmlobjects.FullListResponse;
import cj1098.animeshare.xmlobjects.Info;
import cj1098.event.BatchResponseFinishedEvent;
import cj1098.event.InitialXMLParseFinishedEvent;
import cj1098.event.RxBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chrisjohnson on 20/10/15.
 */
public class AnimeRequestService {
    private static final String TAG = AnimeRequestService.class.getName();

    private static final String MASHAPE_BASE_URL = "https://hummingbirdv1.p.mashape.com";

    // ANN == Anime News network
    private static final String ANN_BASE_URL = "http://www.animenewsnetwork.com/";

    private static final String MASHAPE_DEBUG_KEY = "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy";

    private final ANNService mANNService;

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

        mANNService = retrofit.create(ANNService.class);
    }

    public interface ANNService {
        @GET("encyclopedia/reports.xml?id=155&nlist=all&type=anime")
        Call<ResponseBody> getFullList();

        // NOTE: Possibly later add the ability for customization for the user to add how many items they want to batch at first.
        @GET("encyclopedia/api.xml?")
        Call<ResponseBody> getBatchResponse(@Query("title") String ids);
    }

    public void fetchFullAnimeList() {
        Call<ResponseBody> call = mANNService.getFullList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    InitialXMLParseFinishedEvent event = new InitialXMLParseFinishedEvent(response.body().string());
                    RxBus.getInstance().post(event);
                }
                catch (IOException io) {
                    io.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public void fetchNextFiftyAnimeObjects(String idList) {
        Call<ResponseBody> call = mANNService.getBatchResponse(idList);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    BatchResponseFinishedEvent event = new BatchResponseFinishedEvent(response.body().string());
                    RxBus.getInstance().post(event);
                }
                catch (IOException io) {
                    io.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}