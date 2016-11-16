package cj1098.animeshare.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.userList.ListItem;
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
    private boolean mIsLoading;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ListItem> mUserList;
    private Context mContext;


    public AnimeRequestService(Context context, boolean isLoading, RecyclerView mRecyclerView, ArrayList<ListItem> userList) {
        this.mContext = context; // TODO do we need to worry about holding onto this Context?
        this.mIsLoading = isLoading;
        this.mRecyclerView = mRecyclerView;
        this.mUserList = userList;

        OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    chain.request().newBuilder().addHeader("Accept", "application/json");
                                    Request request = chain.request().newBuilder().addHeader("X-Mashape-Key", MASHAPE_DEBUG_KEY).addHeader("accept", "application/json").build();
                                    return chain.proceed(request);
                                }
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
        Call<ListItem> fetchList(@Path("id") int id);
    }

    public void callService(int startingId, final int endingId) {
        for (; startingId <= endingId; startingId++) {
            Call<ListItem> call = mMashapeService.fetchList(startingId);
            call.enqueue(new Callback<ListItem>() {
                @Override
                public void onResponse(Call<ListItem> call, retrofit2.Response<ListItem> response) {
                    mUserList.add(response.body());
                    if (response.body().getId() == endingId) {
                        if (mIsLoading) {
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                            //call eventbus or something to tell the UI to update. It has loaded 10 items.
                        } else {
                            mAdapter = new ShowsRecyclerAdapter(mContext, mUserList);
                            mAdapter.setHasStableIds(true);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListItem> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }
}