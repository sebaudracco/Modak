package com.sebadracco.modak.detail.module


import com.sebadracco.modak.core.koin.module.RETROFIT_API_AUTH
import com.sebadracco.modak.detail.datasource.repository.DetailRepository
import com.sebadracco.modak.detail.datasource.repository.IDetailRepository
import com.sebadracco.modak.detail.datasource.service.IDetailService
import com.sebadracco.modak.detail.usecase.DetailUseCase
import com.sebadracco.modak.detail.viewModel.DetailBindingDelegate
import com.sebadracco.modak.detail.viewModel.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val detailViewModule: Module = module {

    //Inject viewModel
    viewModel {
        DetailViewModel(
            detailViewUseCase = get(),
            bindingDelegate = get()
        )
    }

    factory { providerDetailViewBindingDelegate() }
    factory { provideRepository(get()) }



    //Inject service
    single(named("ApiPrivate")) {
        providerDetailViewService(
            get(named(RETROFIT_API_AUTH))
        )
    }

    //Inject repository
    single<IDetailRepository> { DetailRepository(get(named("ApiPrivate"))) }

    //Inject useCase
    single { providerDetailViewUseCase(get()) }

}


fun provideRepository(
    iService: IDetailService): DetailRepository {
    return DetailRepository(iService)
}

fun providerDetailViewBindingDelegate(): DetailBindingDelegate {
    return DetailBindingDelegate()
}

fun providerDetailViewUseCase(mainViewRepository: IDetailRepository): DetailUseCase {
    return DetailUseCase(mainViewRepository)
}

fun providerDetailViewService(retrofit: Retrofit): IDetailService {
    return retrofit.create(IDetailService::class.java)
}



