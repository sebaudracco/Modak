
import retrofit2.http.GET

interface IMainService {

    @GET("events?fields=id,title,title_display,date_display,image_url,description,list_description,api_link,hero_caption")
    suspend fun getArtWorks(
    ) : ApiSearchResult
}