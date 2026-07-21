package mr.cat.setting.utility

/**
 * Implementasi Android: Font disimpan di assets/font/.
 */
actual fun resolveFontAssetPath(fileName: String): String {
    return "file:///android_asset/font/$fileName.ttf"
}
