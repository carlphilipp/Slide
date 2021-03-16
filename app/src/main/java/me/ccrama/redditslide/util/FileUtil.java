package me.ccrama.redditslide.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {
    private FileUtil() {
    }

    /**
     * Modifies an {@code Intent} to open a file with the FileProvider
     *
     * @param file    the {@code File} to open
     * @param intent  the {@Intent} to modify
     * @param context Current context
     * @return a base {@code Intent} with read and write permissions granted to the receiving
     * application
     */
    public static Intent getFileIntent(File file, Intent intent, Context context) {
        Uri selectedUri = getFileUri(file, context);

        intent.setDataAndType(selectedUri, context.getContentResolver().getType(selectedUri));
        intent.setFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        return intent;
    }

    /**
     * Gets a valid File Uri to a file in the system
     *
     * @param file    the {@code File} to open
     * @param context Current context
     * @return a File Uri to the given file
     */
    public static Uri getFileUri(File file, Context context) {
        String packageName = context.getApplicationContext().getPackageName() + ".provider";
        Uri selectedUri = FileProvider.getUriForFile(context, packageName, file);
        context.grantUriPermission(packageName, selectedUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return selectedUri;
    }

    /**
     * Deletes all files in a folder
     *
     * @param dir to clear contents
     */
    public static void deleteFilesInDir(File dir) {
        for (File child : dir.listFiles()) {
            child.delete();
        }
    }

    /**
     * Convert a byte count into a human-readable size
     *
     * @param size Byte count
     * @return Human-readable size
     */
    public static String readableFileSize(final long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        final int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups))
                + " "
                + units[digitGroups];
    }
}
