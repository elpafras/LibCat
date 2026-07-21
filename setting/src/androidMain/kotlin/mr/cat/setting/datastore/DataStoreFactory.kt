@file:JvmName("DataStoreFactoryAndroid")
package mr.cat.setting.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import java.io.File

private lateinit var dataStore: DataStore<Preferences>
private val lock = Any()

/**
 * Implementasi Android untuk membuat DataStore.
 */
actual fun createDataStore(context: Any?): DataStore<Preferences> {
    synchronized(lock) {
        if (::dataStore.isInitialized) return dataStore

        val ctx = context as Context
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = {
                ctx.filesDir.resolve(DATASTORE_FILE_NAME)
            }
        )
        return dataStore
    }
}
