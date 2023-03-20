package ru.vysokov.binchecker.Model.RetrofitHelper

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.vysokov.binchecker.Model.API.BinData

interface BinService {
    @GET("{bin}")
    suspend fun getData(@Path("bin") bin : String) : Response<BinData>

}