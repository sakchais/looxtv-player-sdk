package net.thaicom.sdk.looxtv;

import java.util.Collections;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {
    private boolean HTTP_LOGGING = false;
    private ConnectionSpec Spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_0)
            .allEnabledCipherSuites()
            .build();
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private Retrofit retrofit = null;
    public Retrofit getClient(String baseUrl) {
        if(HTTP_LOGGING == true){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);;
        }

        if (baseUrl.startsWith("https")) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                    .client(httpClient.connectionSpecs(Collections.singletonList(Spec)).build())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}


