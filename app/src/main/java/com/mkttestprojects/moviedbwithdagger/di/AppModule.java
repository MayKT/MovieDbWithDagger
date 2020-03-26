package com.mkttestprojects.moviedbwithdagger.di;
import android.app.Application;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.mkttestprojects.moviedbwithdagger.R;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application){
        return ContextCompat.getDrawable(application, R.drawable.ic_launcher_foreground);
    }
}
