package mr.cat.chat.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createChatDataStore(context: Any? = null): DataStore<Preferences>

internal const val CHAT_DATASTORE_FILE_NAME = "mr_cat_chat.preferences_pb"
