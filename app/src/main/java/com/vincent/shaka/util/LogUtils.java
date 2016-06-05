package com.vincent.shaka.util;

import android.util.Log;

import com.facebook.stetho.common.LogRedirector;

import java.util.Locale;

/**
 * Created by mowenlong on 16-6-2.
 */
public class LogUtils {

    private static final String TAG = "Shaka";

    public static void e(String format, Object... args) {
        e(format(format, args));
    }

    public static void e(Throwable t, String format, Object... args) {
        e(t, format(format, args));
    }

    public static void e(String message) {
        if (isLoggable(Log.ERROR)) {
            LogRedirector.e(TAG, message);
        }
    }

    public static void e(Throwable t, String message) {
        if (isLoggable(Log.ERROR)) {
            LogRedirector.e(TAG, message, t);
        }
    }

    public static void w(String format, Object... args) {
        w(format(format, args));
    }

    public static void w(Throwable t, String format, Object... args) {
        w(t, format(format, args));
    }

    public static void w(String message) {
        if (isLoggable(Log.WARN)) {
            LogRedirector.w(TAG, message);
        }
    }

    public static void w(Throwable t, String message) {
        if (isLoggable(Log.WARN)) {
            LogRedirector.w(TAG, message, t);
        }
    }

    public static void i(String format, Object... args) {
        i(format(format, args));
    }

    public static void i(Throwable t, String format, Object... args) {
        i(t, format(format, args));
    }

    public static void i(String message) {
        if (isLoggable(Log.INFO)) {
            LogRedirector.i(TAG, message);
        }
    }

    public static void i(Throwable t, String message) {
        if (isLoggable(Log.INFO)) {
            LogRedirector.i(TAG, message, t);
        }
    }

    public static void d(String format, Object... args) {
        d(format(format, args));
    }

    public static void d(Throwable t, String format, Object... args) {
        d(t, format(format, args));
    }

    public static void d(String message) {
        if (isLoggable(Log.DEBUG)) {
            LogRedirector.d(TAG, message);
        }
    }

    public static void d(Throwable t, String message) {
        if (isLoggable(Log.DEBUG)) {
            LogRedirector.d(TAG, message, t);
        }
    }

    public static void v(String format, Object... args) {
        v(format(format, args));
    }

    public static void v(Throwable t, String format, Object... args) {
        v(t, format(format, args));
    }

    public static void v(String message) {
        if (isLoggable(Log.VERBOSE)) {
            LogRedirector.v(TAG, message);
        }
    }

    public static void v(Throwable t, String message) {
        if (isLoggable(Log.VERBOSE)) {
            LogRedirector.v(TAG, message, t);
        }
    }

    private static String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    /**
     * Applies an internal policy on whether to use {@link LogRedirector#isLoggable(String, int)}.
     * This interface is sometimes sidestepped to avoid Android's default fairly awkward
     * {@link Log#isLoggable(String, int)} interface from blocking important messages while still
     * offering users of Stetho to suppress our error/warning logs via
     * {@link LogRedirector#setLogger(LogRedirector.Logger)}.
     */
    public static boolean isLoggable(int priority) {
        switch (priority) {
            case Log.ERROR:
            case Log.WARN:
                return true;
            default:
                return LogRedirector.isLoggable(TAG, priority);
        }
    }
}