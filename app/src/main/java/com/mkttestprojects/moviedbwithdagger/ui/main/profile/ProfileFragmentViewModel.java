package com.mkttestprojects.moviedbwithdagger.ui.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.models.AccountModel;
import com.mkttestprojects.moviedbwithdagger.network.main.MainApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;

public class ProfileFragmentViewModel extends ViewModel {

    MediatorLiveData<Resource<AccountModel>> account;
    private MainApi mainApi;

    @Inject
    public ProfileFragmentViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    public LiveData<Resource<AccountModel>> observeAccountBySessionId(String sessionId){
        if(account == null){
            account = new MediatorLiveData<>();
            account.setValue(Resource.loading(null));
            final  LiveData<Resource<AccountModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getAccountBySessionId(DEVELOPER_KEY,sessionId)
                    .onErrorReturn(new Function<Throwable, AccountModel>() {
                        @Override
                        public AccountModel apply(Throwable throwable) throws Exception {
                            return null;
                        }
                    })
                    .map(new Function<AccountModel, Resource<AccountModel>>() {
                        @Override
                        public Resource<AccountModel> apply(AccountModel accountModel) throws Exception {
                            if(accountModel == null){
                                return Resource.error("Something went wrong",null);
                            }
                            return Resource.success(accountModel);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );
            account.addSource(resourceLiveData, new Observer<Resource<AccountModel>>() {
                @Override
                public void onChanged(Resource<AccountModel> accountModelResource) {
                    account.setValue(accountModelResource);
                    account.removeSource(resourceLiveData);
                }
            });
        }
        return account;
    }
}
