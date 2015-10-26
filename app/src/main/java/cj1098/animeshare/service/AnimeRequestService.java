package cj1098.animeshare.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;

import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.userList.ListItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

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

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                chain.request().newBuilder().addHeader("Accept", "application/json");
                Request request = chain.request().newBuilder().addHeader("X-Mashape-Key", MASHAPE_DEBUG_KEY).addHeader("accept", "application/json").build();
                return chain.proceed(request);
            }
        });

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
                public void onResponse(Response<ListItem> response, Retrofit retrofit) {
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
                public void onFailure(Throwable t) {
                    Log.i(TAG, t.toString());
                }
            });
        }
    }
}