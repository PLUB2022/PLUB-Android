package com.plub.data.repository

import android.util.Log
import com.plub.data.api.SampleApi
import com.plub.data.mapper.Mapper
import com.plub.data.model.SampleAccountResponse
import com.plub.domain.UiState
import com.plub.domain.model.SampleAccount
import com.plub.domain.repository.SampleAccountRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class SampleAccountImpl @Inject constructor(private val sampleApi: SampleApi) : SampleAccountRepository {

    override fun checkNickname(): Flow<UiState<SampleAccount>> = flow {
        //emit(UiState.Loading)
        //emit(UiState.Success(Mapper.mapperToSampleAccount(sampleApi.checkNickname("123"))))
        runBlocking {
            sampleApi.checkNickname("123")
                .enqueue(object : retrofit2.Callback<SampleAccountResponse> {
                    override fun onResponse(
                        call: Call<SampleAccountResponse>,
                        response: Response<SampleAccountResponse>
                    ) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성고된 경우
                            var result: SampleAccountResponse? = response.body()
                            Log.d("YMC", "onResponse 성공: " + result?.toString());
                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d("YMC", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<SampleAccountResponse>, t: Throwable) {
                        Log.d("가나ㅏ난다다라ㅏ라", "[Fail]${t.toString()}")
                    }

                })
        }
    }
}