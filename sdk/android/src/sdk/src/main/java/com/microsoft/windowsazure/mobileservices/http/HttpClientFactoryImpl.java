/*
Copyright (c) Microsoft Open Technologies, Inc.
All Rights Reserved
Apache 2.0 License
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 
See the Apache Version 2.0 License for specific language governing permissions and limitations under the License.
 */

/**
 * AndroidHttpClientFactoryImpl.java
 */
package com.microsoft.windowsazure.mobileservices.http;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.apache.OkApacheClient;

import org.apache.http.client.HttpClient;

import java.io.IOException;

/**
 * Default implementation for HttpClientFactory
 */
public class HttpClientFactoryImpl implements HttpClientFactory {

    @Override
    public HttpClient createHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new UserAgentInterceptor(MobileServiceConnection.getUserAgent()));
        return new OkApacheClient();
    }

    private class UserAgentInterceptor
            implements Interceptor {

        private final String userAgent;

        public UserAgentInterceptor(final String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public Response intercept(Interceptor.Chain chain)
                throws IOException {

            final Request originalRequest = chain.request();
            final Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }
}

