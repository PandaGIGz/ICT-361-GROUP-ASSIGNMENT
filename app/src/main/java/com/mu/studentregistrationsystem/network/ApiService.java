package com.mu.studentregistrationsystem.network;

import com.mu.studentregistrationsystem.network.models.ApiResponse;
import com.mu.studentregistrationsystem.network.models.ForgotPasswordRequest;
import com.mu.studentregistrationsystem.network.models.LecturerRegisterRequest;
import com.mu.studentregistrationsystem.network.models.LoginRequest;
import com.mu.studentregistrationsystem.network.models.ResetPasswordRequest;
import com.mu.studentregistrationsystem.network.models.StudentRegisterRequest;
import com.mu.studentregistrationsystem.network.models.VerifyCodeRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest request);

    @POST("auth/login")
    Call<ApiResponse> authLogin(@Body LoginRequest request);

    @POST("register/student")
    Call<ApiResponse> registerStudent(@Body StudentRegisterRequest request);

    @POST("register/lecturer")
    Call<ApiResponse> registerLecturer(@Body LecturerRegisterRequest request);

    @POST("forgot-password")
    Call<ApiResponse> forgotPassword(@Body ForgotPasswordRequest request);

    @POST("verify-code")
    Call<ApiResponse> verifyCode(@Body VerifyCodeRequest request);

    @POST("reset-password")
    Call<ApiResponse> resetPassword(@Body ResetPasswordRequest request);

    @GET("groups/my-groups")
    Call<ApiResponse> getMyGroups(@Header("Authorization") String token);

    @POST("groups/create")
    Call<ApiResponse> createGroup(@Header("Authorization") String token, @Body Object groupBody);

    @POST("groups/{id}/join")
    Call<ApiResponse> joinGroup(@Header("Authorization") String token, @Path("id") int groupId);
}
