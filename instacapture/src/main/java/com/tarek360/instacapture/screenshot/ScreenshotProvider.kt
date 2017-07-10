package com.tarek360.instacapture.screenshot

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import io.reactivex.Observable


/**
 * Created by tarek on 5/17/16.
 */
class ScreenshotProvider {

    fun getScreenshotBitmap(activity: Activity, removedViews: Array<out View>): Observable<Bitmap> =
            ViewsBitmapObservable[activity, removedViews]
}
