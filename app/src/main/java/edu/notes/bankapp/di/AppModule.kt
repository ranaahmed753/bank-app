package edu.notes.bankapp.di

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.notes.bankapp.dao.BankDao
import edu.notes.bankapp.database.BankDatabase
import edu.notes.bankapp.databinding.adapter.BankAccountAdapter
import edu.notes.bankapp.entity.BankEntity
import edu.notes.bankapp.view.MainActivity
import edu.notes.bankapp.viewmodel.BankViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @InternalCoroutinesApi
    @Singleton
    @Provides
    fun provideBankDatabase(context:Application):BankDatabase{
        return BankDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideBankDao(bankDatabase: BankDatabase):BankDao{
       return bankDatabase.bankDao()
    }

    @Singleton
    @Provides
    fun provideAdapter(context: Application,list:List<BankEntity>):BankAccountAdapter{
        return BankAccountAdapter(context,list)
    }

}