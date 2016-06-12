package ru.arnis.newcurrency.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by arnis on 03/05/16.
 */
public interface API {
    @GET
    Call<Results> get(@Url String url);

    @GET
    Call<Results> getList(@Url String url);

}
