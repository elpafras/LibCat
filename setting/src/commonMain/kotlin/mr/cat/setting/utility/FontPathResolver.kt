package mr.cat.setting.utility

/**
 * Menyelesaikan path font asset lintas platform.
 *
 * @param fileName Nama file font tanpa ekstensi.
 * @return Path absolut atau URL yang valid untuk digunakan di WebView (src: url()).
 */
expect fun resolveFontAssetPath(fileName: String): String
