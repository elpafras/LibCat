@file:JvmName("ChatDataStoreFactoryAndroid")
package mr.cat.chat.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

private lateinit var dataStore: DataStore<Preferences>

actual fun createChatDataStore(context: Any?): DataStore<Preferences> {
    if (::dataStore.isInitialized) return dataStore
    val ctx = context as Context
    dataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = { ctx.filesDir.resolve(CHAT_DATASTORE_FILE_NAME).absolutePath.toPath() }
    )
    return dataStore
}
