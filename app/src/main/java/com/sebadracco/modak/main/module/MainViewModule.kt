package com.sebadracco.modak.main.module

import IMainRepository
import IMainService
import MainRepository
import MainUseCase
import com.sebadracco.modak.core.koin.module.RETROFIT_API_AUTH
import com.sebadracco.modak.main.viewmodel.MainBindingDelegate
import com.sebadracco.modak.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val mainViewModule: Module = module {

    //Inject viewModel
    viewModel {
        MainViewModel(
            mainViewUseCase = get(),
            bindingDelegate = get()
        )
    }

    factory { providerMainViewBindingDelegate() }
    factory { provideRepository(get()) }


    //Inject service
    single(named("ApiPrivate")) {
        providerMainViewService(
            get(named(RETROFIT_API_AUTH))
        )
    }

    //Inject repository
    single<IMainRepository> { MainRepository(get(named("ApiPrivate"))) }

    //Inject useCase
    single { providerMainViewUseCase(get()) }

}




fun provideRepository(
    iService: IMainService): MainRepository {
    return MainRepository(iService)
}


fun providerMainViewBindingDelegate(): MainBindingDelegate {
    return MainBindingDelegate()
}

fun providerMainViewUseCase(mainViewRepository: IMainRepository): MainUseCase {
    return MainUseCase(mainViewRepository)
}

fun providerMainViewService(retrofit: Retrofit): IMainService {
    return retrofit.create(IMainService::class.java)
}




