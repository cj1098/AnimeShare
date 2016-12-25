package cj1098.animeshare.service;

import org.json.JSONObject;

import cj1098.animeshare.viewmodels.UserViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by chris on 11/27/16.
 */

public interface UserInformationService {
    @POST("/sendInformation.php")
    Call<UserViewModel> sendUserInfo(@Body UserViewModel userViewModel);

    @GET("/testphp.php")
    Call<String> getUserInfo();
}
