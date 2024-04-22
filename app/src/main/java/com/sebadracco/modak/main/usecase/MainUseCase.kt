import com.sebadracco.core.base.useCase.BaseUseCase


class MainUseCase (private val mainViewRepository: IMainRepository) :
    BaseUseCase<Unit, List<DetailEntityResponse>>() {

    override suspend fun run(params: Unit) : List<DetailEntityResponse>{
        return mainViewRepository.getArtWorks()
    }
}