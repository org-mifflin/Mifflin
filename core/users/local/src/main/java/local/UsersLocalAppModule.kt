package local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersLocalAppModule {

    @Singleton
    @Provides
    fun providesNotableDatabase(@ApplicationContext context: Context): MifflinDatabase {
        return Room.databaseBuilder(context, MifflinDatabase::class.java, "mifflin.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesArticleDao(db: MifflinDatabase): UserDao {
        return db.userDao()
    }
}
