package cj1098.animeshare.home;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import cj1098.animeshare.HomeActivity;
import cj1098.animeshare.constants.NetworkConstants;
import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.animeshare.service.UserInformationService;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.DeviceUtil;
import cj1098.animeshare.viewmodels.UserViewModel;
import cj1098.base.BasePresenter;
import cj1098.event.NoNetworkEvent;
import cj1098.event.RxBus;
import cj1098.event.SlowNetworkEvent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 11/20/16.
 */

public class HomeHeadlessPresenter extends BasePresenter<HomeHeadlessMvp.View> implements
            HomeHeadlessMvp.Presenter {

    private static final String TAG = HomeHeadlessPresenter.class.getSimpleName();

    private CompositeSubscription mCompositeSubscription;
    private DeviceUtil mDeviceUtil;
    private DatabaseUtil mDatabaseUtil;
    private RxBus mRxBus;

    public HomeHeadlessPresenter(DeviceUtil deviceUtil, DatabaseUtil databaseUtil) {
        mDeviceUtil = deviceUtil;
        mDatabaseUtil = databaseUtil;
        mRxBus = RxBus.getInstance();
    }

    @Override
    public void attachView(@NonNull HomeHeadlessMvp.View view) throws ViewAlreadyAttachedException {
        super.attachView(view);
        sendUserInformation();
    }

    @Override
    public void subscribeToObservables() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(mRxBus.register(NoNetworkEvent.class, this::showNoNetworkDialog));
        mCompositeSubscription.add(mRxBus.register(SlowNetworkEvent.class, this::showSlowNetworkDialog));
    }

    @Override
    public void unsubscribeFromObservables() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }


    private void showNoNetworkDialog(NoNetworkEvent event) {
        if (isAttached()) {
            getView().showNoNetworkDialog();
        }
    }

    private void showSlowNetworkDialog(SlowNetworkEvent event) {
        if (isAttached()) {
            getView().showSlowNetworkDialog();
        }
    }

    private void initialDBPopulation() {
        //mDatabaseUtil.setupInitialAnimeFetchData(animeIdList);
    }

    public void sendUserInformation() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(objectMapper);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d(TAG, message));
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.CHRIS_SERVER_URL)
                .client(client.build())
                .addConverterFactory(jacksonConverterFactory)
                .build();

        UserInformationService userService = retrofit.create(UserInformationService.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "test2");
            jsonObject.put("device_manufacturer", "duhh");
            jsonObject.put("android_version", "6.5.2");
            jsonObject.put("phone_model", "nexus6");
            jsonObject.put("locale", "US");
            jsonObject.put("password", "password1");
        } catch (JSONException ignored) {

        }
        UserViewModel viewmodel = new UserViewModel("cj1098", "Samsung", "6.5.2", "nexus6", "US", "password");
        Call<UserViewModel> call = userService.sendUserInfo(viewmodel);
        call.enqueue(new Callback<UserViewModel>() {
            @Override
            public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {
                // TODO: something here
            }

            @Override
            public void onFailure(Call<UserViewModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}
