package com.tarek360.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tarek360.instacapture.Instacapture;
import com.tarek360.sample.uncapturableViews.AlertDialogFragment;
import com.tarek360.sample.utility.Utility;

import java.io.File;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public abstract class BaseSampleActivity extends AppCompatActivity
        implements AlertDialogFragment.OnAlertDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instacapture.INSTANCE.enableLogging(true);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void showAlertDialog() {
        AlertDialogFragment.newInstance(R.string.dialog_title, R.string.dialog_message)
                .show(getSupportFragmentManager(), "dialogFragment");
    }

    protected void captureScreenshot(@Nullable View... ignoredViews) {

        Instacapture.INSTANCE.captureRx(this, ignoredViews).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(@NonNull Bitmap bitmap) throws Exception {
                Utility.getScreenshotFileObservable(BaseSampleActivity.this, bitmap)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(@NonNull File file) throws Exception {
                                startActivity(ShowScreenShotActivity.buildIntent(BaseSampleActivity.this,
                                        file.getAbsolutePath()));
                            }
                        });
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnPositiveButtonClick() {
        captureScreenshot();
    }
}
