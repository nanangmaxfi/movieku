package id.nanangmaxfi.movieku.core.data.source

import id.nanangmaxfi.movieku.core.data.source.remote.network.ApiResponse
import id.nanangmaxfi.movieku.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private var result : Flow<id.nanangmaxfi.movieku.core.data.source.Resource<ResultType>> = flow {
        emit(id.nanangmaxfi.movieku.core.data.source.Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(id.nanangmaxfi.movieku.core.data.source.Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        id.nanangmaxfi.movieku.core.data.source.Resource.Success(
                            it
                        )
                    })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map {
                        id.nanangmaxfi.movieku.core.data.source.Resource.Success(
                            it
                        )
                    })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(
                        id.nanangmaxfi.movieku.core.data.source.Resource.Error<ResultType>(
                            apiResponse.errorMessage
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map { id.nanangmaxfi.movieku.core.data.source.Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<id.nanangmaxfi.movieku.core.data.source.Resource<ResultType>> = result

}