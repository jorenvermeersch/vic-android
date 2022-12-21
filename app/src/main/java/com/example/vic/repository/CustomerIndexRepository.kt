package com.example.vic.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.vic.database.VicDatabase
import com.example.vic.database.asDomainModel
import com.example.vic.domain.entities.CustomerIndex
import com.example.vic.misc.GlobalMethods
import com.example.vic.network.CustomerApi
import com.example.vic.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CustomerIndexRepository(private val database: VicDatabase, private val context: Context) {

    val customerIndexes: LiveData<List<CustomerIndex>> = Transformations.map(database.customerIndexDatabaseDao.getAll()) {
        it.asDomainModel()
    }

    suspend fun refreshCustomerIndexes() {

        if (GlobalMethods.isOnline(context)) {
            withContext(Dispatchers.IO) {
                val results = CustomerApi.retrofitService.getCustomerIndexes().await()
                database.customerIndexDatabaseDao.insertAll(*results.asDatabaseModel())
            }
        }
    }

//    suspend fun getCustomerById(): Customer {
//        val results = CustomerApi.retrofitService.getCustomerById().await()
//        return results.asDomainModel()
//    }
}

//
// class JokeRepository(private val database: JokeDatabase) {
//
//    //Network call
//    //get jokes from the database, but transform them with map
//    val jokes: LiveData<List<Joke>> =
//        Transformations.map(database.jokeDatabaseDao.getAllJokesLive()){
//            it.asDomainModel()
//        }
//
//    //Database call
//    suspend fun refreshJokes(){
//        //switch context to IO thread
//        withContext(Dispatchers.IO){
//            val jokes = JokeApi.retrofitService.getJokes().await()
//            //'*': kotlin spread operator.
//            //Used for functions that expect a vararg param
//            //just spreads the array into separate fields
//            database.jokeDatabaseDao.insertAll(*jokes.asDatabaseModel())
//            Timber.i("end suspend")
//        }
//    }
//
//    //create a new joke + return the resulting joke
//    suspend fun createJoke(newJoke: Joke): Joke {
//        //create a Data Transfer Object (Dto)
//        val newApiJoke = ApiJoke(
//            jokeSetup = newJoke.jokeSetup,
//            punchline = newJoke.punchline,
//            jokeType = newJoke.jokeType)
//        //use retrofit to put the joke.
//        //a put function usually returns the object that was put
//
//        //val checkApiJoke = JokeApi.retrofitService.putJoke(newApiJoke).await()
//        val checkApiJoke = JokeApi.retrofitService.mockPutJoke(newApiJoke)
//
//        //the put results in a JokeApi object
//        //when the put is done, update the local db as well
//        database.jokeDatabaseDao.insert(checkApiJoke.asDatabaseJoke())
//        //the returned joke can be used to pass as save arg to the next fragment (e.g)
//        return newJoke
//    }
// }
