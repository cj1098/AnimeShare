package cj1098.animeshare.service;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.userList.ListItem;
import cj1098.animeshare.userList.UserList;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by chrisjohnson on 20/10/15.
 */
public class AnimeRequestService {
    public static String MASHAPE_BASE_URL = "https://hummingbirdv1.p.mashape.com";
    private static String MASHAPE_DEBUG_KEY = "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy";
    private final MashapeService mashapeService;
    private final String TAG = AnimeRequestService.class.getName();
    private boolean isLoading;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ListItem> userList;
    private Context context;


    public AnimeRequestService(Context context, boolean isLoading, RecyclerView mRecyclerView, ArrayList<ListItem> userList) {

        this.context = context;
        this.isLoading = isLoading;
        this.mRecyclerView = mRecyclerView;
        this.userList = userList;
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

        mashapeService = retrofit.create(MashapeService.class);

    }


    public interface MashapeService {
        @GET("/anime/{id}")
        Call<ListItem> fetchList(@Path("id") int id);
    }

    public void callService(int startingId, final int endingId) {
        for (; startingId <= endingId; startingId++) {
            Call<ListItem> call = mashapeService.fetchList(startingId);
            call.enqueue(new Callback<ListItem>() {
                @Override
                public void onResponse(Response<ListItem> response, Retrofit retrofit) {
                        userList.add(response.body());
                        if (response.body().getId() == endingId) {
                            if (isLoading) {
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                                //call eventbus or something to tell the UI to update. It has loaded 10 items.
                            }
                            else {
                                mAdapter = new ShowsRecyclerAdapter(context, userList);
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
