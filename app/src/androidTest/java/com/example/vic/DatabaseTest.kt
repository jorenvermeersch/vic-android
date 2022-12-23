package com.example.vic

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.vic.database.CustomerIndexDatabaseDao
import com.example.vic.database.VicDatabase
import java.io.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var customerDao: CustomerIndexDatabaseDao
    private lateinit var db: VicDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, VicDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        customerDao = db.customerIndexDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllTest() {
        val response = customerDao.getAll()
        assertEquals(response.value, customerDao.getAll().value)
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.vic", appContext.packageName)
    }
}
