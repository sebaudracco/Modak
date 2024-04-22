

interface IMainRepository {
    @Throws(Exception::class)
    suspend fun getArtWorks(): List<DetailEntityResponse>

}