package com.origin.zapgrupos.repository;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.origin.zapgrupos.R;
import com.origin.zapgrupos.models.AdiconarGrupo.ErrosResponse;
import com.origin.zapgrupos.models.Error.ErrorMensage;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class ResponseRetrofit<T> {

    Callback<T> getCallback(MutableLiveData<T> livedata){
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.isSuccessful()){
                    Log.i("requested: ",response.raw().request().url().toString());
                    T body = response.body();
                    livedata.setValue(body);
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                Log.i("requested: ",call.request().url().toString());

            }
        };
    }


    Callback<T> getCallback(MutableLiveData<T> livedataSucess,
                            MutableLiveData<ErrorMensage> livedataError){
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.isSuccessful()){
                    T body = response.body();
                    livedataSucess.setValue(body);
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                ErrorMensage erro = new ErrorMensage(R.string.net_work_error);
                livedataError.setValue(erro);
            }
        };
    }
    Callback<T> getCallback(
            MutableLiveData<T> livedataSucess,
            MutableLiveData<ErrosResponse> livedataErrorResponse,
            MutableLiveData<ErrorMensage> livedataError){

        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.isSuccessful()){
                    T body = response.body();
                    livedataSucess.setValue(body);
                }else if(response.errorBody() != null){
                    try {
                        Converter<ResponseBody, ErrosResponse> errorConverter =
                                Services.retrofit.responseBodyConverter(ErrosResponse.class, new Annotation[0]);
                        try {
                            ErrosResponse errors = errorConverter.convert(response.errorBody());
                            livedataErrorResponse.setValue(errors);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {

            }

        };
    }

}


