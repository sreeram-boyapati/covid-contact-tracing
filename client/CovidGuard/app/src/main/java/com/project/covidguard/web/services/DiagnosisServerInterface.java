package com.project.covidguard.web.services;

import com.project.covidguard.web.requests.UploadTEKRequest;
import com.project.covidguard.web.responses.ens.DownloadTEKResponse;
import com.project.covidguard.web.responses.ens.UploadDiagnosisKeyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DiagnosisServerInterface {

    String BASE_URL_PROD = "https://ens-server.ts.r.appspot.com";

    @Headers({"Content-Type: application/json"})
    @POST("upload-diagnosis-keys")
    Call<UploadDiagnosisKeyResponse> uploadTEKs(@Body UploadTEKRequest request);

    @GET("download-diagnosis-keys")
    Call<DownloadTEKResponse> downloadTEKs();
}
