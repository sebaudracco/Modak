

import javax.inject.Inject

class MainRepository @Inject constructor(
    private val iService: IMainService,
) : IMainRepository {


    @Throws(Exception::class)
    override suspend fun getArtWorks(): List<DetailEntityResponse>{
        return iService.getArtWorks().internalData
    }
}