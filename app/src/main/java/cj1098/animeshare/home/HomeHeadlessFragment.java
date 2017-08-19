package cj1098.animeshare.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import cj1098.animeshare.HomeActivity;
import cj1098.animeshare.service.UserInformationService;
import cj1098.animeshare.util.DaggerUtil;
import cj1098.animeshare.viewmodels.UserViewModel;
import cj1098.base.BaseFragment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by chris on 11/18/16.
 */

public class HomeHeadlessFragment extends BaseFragment implements HomeHeadlessMvp.View {

    @Inject
    HomeHeadlessMvp.Presenter mHomeHeadlessPresenter;

    public static String TAG = HomeHeadlessFragment.class.getSimpleName();

    public static HomeHeadlessFragment newInstance() {
        return new HomeHeadlessFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomeHeadlessPresenter.attachView(this);
        mHomeHeadlessPresenter.subscribeToObservables();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHomeHeadlessPresenter.unsubscribeFromObservables();
        mHomeHeadlessPresenter.detachView();
    }

    @Override
    public void showNoNetworkDialog() {

    }

    @Override
    public void showSlowNetworkDialog() {

    }
}
