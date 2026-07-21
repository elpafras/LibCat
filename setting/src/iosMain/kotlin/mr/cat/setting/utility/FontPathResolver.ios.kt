package mr.cat.setting.utility

import platform.Foundation.NSBundle

/**
 * Implementasi iOS: Font diresolve dari main bundle.
 */
actual fun resolveFontAssetPath(fileName: String): String {
    val path = NSBundle.mainBundle.pathForResource(fileName, "ttf")
        ?: NSBundle.mainBundle.pathForResource(fileName, "otf")
        ?: ""
    
    return if (path.isNotEmpty()) "file://$path" else ""
}
