package com.uncommontrade.e2b.utilities;

import android.util.Log;


/**
 * This class is common logger for project.
 * You can not a object of this class.
 * @author ThoiNX
 */
public final class L
{
    private static final String TAG = "INVIPI";
    private static final String DEFAULT_STRING_NULL = "null";

    /**this variable allow show log*/
    private static final boolean ENABLE_SHOW_LOG = true;

    private L()
    {
    }

    /**
     * Send an ERROR log message.
     * @param msg The message you would like logged.
     */
    public static void e(String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        Log.e(TAG, msg);
    }

    /**
     * Send an ERROR log message.
     * @param tag The tag you would like logged.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        if (null == tag)
        {
            tag = TAG;
        }
        Log.e(tag, msg);
    }

    /**
     * Send an DEBUG log message.
     * @param msg The message you would like logged.
     */
    public static void d(String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        Log.d(TAG, msg);
    }

    /**
     * Send an DEBUG log message.
     * @param tag The tag you would like logged.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        if (null == tag)
        {
            tag = TAG;
        }
        Log.d(tag, msg);
    }

    /**
     * Send an INFOR log message.
     * @param msg The message you would like logged.
     */
    public static void i(String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        Log.i(TAG, msg);
    }

    /**
     * Send an INFOR log message.
     * @param tag The tag you would like logged.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        if (null == tag)
        {
            tag = TAG;
        }
        Log.i(tag, msg);
    }

    /**
     * Send an WARN log message.
     * @param msg The message you would like logged.
     */
    public static void w(String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        Log.w(TAG, msg);
    }

    /**
     * Send an WARN log message.
     * @param tag The tag you would like logged.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == msg)
        {
            msg = DEFAULT_STRING_NULL;
        }
        if (null == tag)
        {
            tag = TAG;
        }
        Log.i(tag, msg);
    }

    /**
     * Send an THROWABLE ERROR log message.
     * @param e The exception
     */
    public static void ee(Throwable e)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null != e)
        {
            Log.e(TAG, e.toString(), e);
        }
        else
        {
            Log.e(TAG, DEFAULT_STRING_NULL);
        }
    }

    /**
     * Send an THROWABLE ERROR log message.
     * @param tag The tag you would like logged.
     * @param e The exception
     */
    public static void ee(String tag, Throwable e)
    {
        if (!ENABLE_SHOW_LOG)
        {
            //don't show log
            return;
        }
        if (null == tag)
        {
            tag = TAG;
        }
        if (null != e)
        {
            Log.e(tag, e.toString(), e.getCause());
            StackTraceElement[] arrSte = e.getStackTrace();
            for (StackTraceElement ste : arrSte)
            {
                Log.e(tag, "at " + ste.getClassName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")");
            }
        }
        else
        {
            Log.e(tag, DEFAULT_STRING_NULL);
        }
    }

    /**
     * print out Throwable
     */
    public static void e(Throwable ex) {
        // Log.e(TAG, GlobalParams.BLANK_STRING, ex);
        ee(ex);
    }

}
