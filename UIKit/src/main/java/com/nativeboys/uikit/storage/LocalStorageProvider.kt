package com.nativeboys.uikit.storage

import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.CancellationSignal
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsProvider
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileNotFoundException
import android.provider.DocumentsContract.Document
import android.provider.DocumentsContract.Root
import android.util.Log
import java.io.FileOutputStream
import java.io.IOException

class LocalStorageProvider : DocumentsProvider() {

    override fun queryRoots(projection: Array<out String>?): Cursor {
        // Create a cursor with either the requested fields, or the default
        // projection if "projection" is null.
        val result = MatrixCursor(projection ?: DEFAULT_ROOT_PROJECTION)
        // Add Home directory
        val homeDir = Environment.getExternalStorageDirectory()
        val row: MatrixCursor.RowBuilder = result.newRow()
        // These columns are required
        row.add(Root.COLUMN_ROOT_ID, homeDir.absolutePath)
        row.add(Root.COLUMN_DOCUMENT_ID, homeDir.absolutePath)
        row.add(Root.COLUMN_TITLE, "Internal Storage")
        row.add(Root.COLUMN_FLAGS, Root.FLAG_LOCAL_ONLY or Root.FLAG_SUPPORTS_CREATE)
        // row.add(Root.COLUMN_ICON, R.drawable.ic_provider)
        // These columns are optional
        row.add(Root.COLUMN_AVAILABLE_BYTES, homeDir.freeSpace)
        // Root.COLUMN_MIME_TYPE is another optional column and useful if you
        // have multiple roots with different
        // types of mime types (roots that don't match the requested mime type
        // are automatically hidden)
        return result
    }

    override fun createDocument(
        parentDocumentId: String?,
        mimeType: String?,
        displayName: String?
    ): String {
        return try {
            if (displayName == null) throw IOException()
            val newFile = File(parentDocumentId, displayName)
            newFile.createNewFile()
            newFile.absolutePath
        } catch (e: IOException) {
            Log.e(LocalStorageProvider::class.java.simpleName, "Error creating new file")
            ""
        }
    }

    override fun openDocumentThumbnail(
        documentId: String?,
        sizeHint: Point?,
        signal: CancellationSignal?
    ): AssetFileDescriptor {
        // Assume documentId points to an image file. Build a thumbnail no
        // larger than twice the sizeHint
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(documentId, options)
        val targetHeight = 2 * (sizeHint?.y ?: 1)
        val targetWidth = 2 * (sizeHint?.x ?: 1)
        val height = options.outHeight
        val width = options.outWidth
        options.inSampleSize = 1
        if (height > targetHeight || width > targetWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / options.inSampleSize > targetHeight || halfWidth / options.inSampleSize > targetWidth) {
                options.inSampleSize *= 2
            }
        }
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeFile(documentId, options)
        // Write out the thumbnail to a temporary file
        var tempFile: File? = null
        var out: FileOutputStream? = null
        try {
            tempFile = File.createTempFile("thumbnail", null, requireContext().cacheDir)
            out = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
        } catch (e: IOException) {
            Log.e(LocalStorageProvider::class.java.simpleName, "Error writing thumbnail", e)
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                Log.e(LocalStorageProvider::class.java.simpleName, "Error closing thumbnail", e)
            }
        }
        // It appears the Storage Framework UI caches these results quite
        // aggressively so there is little reason to
        // write your own caching layer beyond what you need to return a single
        // AssetFileDescriptor
        return AssetFileDescriptor(
            ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY),
            0,
            AssetFileDescriptor.UNKNOWN_LENGTH
        )
    }

    override fun queryChildDocuments(
        parentDocumentId: String?,
        projection: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        // Create a cursor with either the requested fields, or the default
        // projection if "projection" is null.
        val result = MatrixCursor(projection ?: DEFAULT_DOCUMENT_PROJECTION)
        parentDocumentId?.let {
            val parent = File(it)
            val listFiles = parent.listFiles() ?: return@let
            for (file in listFiles) {
                // Don't show hidden files/folders
                if (!file.name.startsWith(".")) {
                    // Adds the file's display name, MIME type, size, and so on.
                    includeFile(result, file)
                }
            }
        }
        return result
    }

    override fun queryDocument(documentId: String?, projection: Array<out String>?): Cursor {
        // Create a cursor with either the requested fields, or the default
        // projection if "projection" is null.
        val result = MatrixCursor(projection ?: DEFAULT_DOCUMENT_PROJECTION)
        documentId?.let { includeFile(result, File(it)) }
        return result
    }

    @Throws(FileNotFoundException::class)
    private fun includeFile(result: MatrixCursor, file: File) {
        val row: MatrixCursor.RowBuilder = result.newRow()
        // These columns are required
        row.add(Document.COLUMN_DOCUMENT_ID, file.absolutePath)
        row.add(Document.COLUMN_DISPLAY_NAME, file.name)
        val mimeType = getDocumentType(file.absolutePath)
        row.add(Document.COLUMN_MIME_TYPE, mimeType)
        var flags = if (file.canWrite()) Document.FLAG_SUPPORTS_DELETE or Document.FLAG_SUPPORTS_WRITE else 0
        // We only show thumbnails for image files - expect a call to
        // openDocumentThumbnail for each file that has
        // this flag set
        if (mimeType.startsWith("image/")) flags = flags or Document.FLAG_SUPPORTS_THUMBNAIL
        row.add(Document.COLUMN_FLAGS, flags)
        // COLUMN_SIZE is required, but can be null
        row.add(Document.COLUMN_SIZE, file.length())
        // These columns are optional
        row.add(Document.COLUMN_LAST_MODIFIED, file.lastModified())
        // Document.COLUMN_ICON can be a resource id identifying a custom icon.
        // The system provides default icons
        // based on mime type
        // Document.COLUMN_SUMMARY is optional additional information about the
        // file
    }

    override fun getDocumentType(documentId: String?): String {
        documentId?.let {
            val file = File(it)
            if (file.isDirectory) return Document.MIME_TYPE_DIR
            // From FileProvider.getType(Uri)
            val lastDot = file.name.lastIndexOf('.')
            if (lastDot >= 0) {
                val extension: String = file.name.substring(lastDot + 1)
                val mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
                if (mime != null) {
                    return mime
                }
            }
        }
        return "application/octet-stream"
    }

    override fun deleteDocument(documentId: String?) {
        documentId?.let { File(it).delete() }
    }

    override fun openDocument(
        documentId: String?,
        mode: String?,
        signal: CancellationSignal?
    ): ParcelFileDescriptor {
        val file = File(documentId!!)
        val isWrite = mode?.indexOf('w') != -1
        return if (isWrite) ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE)
        else ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    override fun onCreate() = true

    companion object {

        const val AUTHORITY = "com.ianhanniballake.localstorage.documents"

        /**
         * Default root projection: everything but Root.COLUMN_MIME_TYPES
         */
        private val DEFAULT_ROOT_PROJECTION = arrayOf(
            Root.COLUMN_ROOT_ID,
            Root.COLUMN_FLAGS, Root.COLUMN_TITLE, Root.COLUMN_DOCUMENT_ID, Root.COLUMN_ICON,
            Root.COLUMN_AVAILABLE_BYTES
        )

        /**
         * Default document projection: everything but Document.COLUMN_ICON and
         * Document.COLUMN_SUMMARY
         */
        private val DEFAULT_DOCUMENT_PROJECTION = arrayOf(
            Document.COLUMN_DOCUMENT_ID,
            Document.COLUMN_DISPLAY_NAME, Document.COLUMN_FLAGS, Document.COLUMN_MIME_TYPE,
            Document.COLUMN_SIZE,
            Document.COLUMN_LAST_MODIFIED
        )

    }

}