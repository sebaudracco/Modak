
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMainService {

    @GET("events?fields=id,title,title_display,date_display,image_url,description")
    suspend fun getArtWorks(
    ) : ApiSearchResultRawA
}