package mr.cat.setting.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Platform-specific factory untuk membuat DataStore.
 */
expect fun createDataStore(context: Any? = null): DataStore<Preferences>

/**
 * Nama file DataStore yang digunakan di seluruh platform.
 */
internal const val DATASTORE_FILE_NAME = "mr_cat_setting.preferences_pb"
