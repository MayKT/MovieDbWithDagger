package com.mkttestprojects.moviedbwithdagger.di.login;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.di.ViewModelKey;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelModule  {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

}
