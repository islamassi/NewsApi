package com.islamassi.latestnews.api

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Adapter for Retrofit to convert Call into LiveData.
 * This will let us use LiveData as the return type of a retrofit request instead of Call
 */
class LiveDataCallAdapter<R>(private val responseType: Type): CallAdapter<R, LiveData<ApiResponse<R>>> {
    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var isSuccess = false

            override fun onActive() {
                super.onActive()
                if (!isSuccess) enqueue()
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() {
                call.enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        postValue(ApiResponse<R>(t))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(ApiResponse(response))
                        isSuccess = true
                    }
                })
            }
        }
    }

    override fun responseType(): Type = responseType
}