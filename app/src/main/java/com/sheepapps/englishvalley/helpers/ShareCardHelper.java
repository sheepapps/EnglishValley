package com.sheepapps.englishvalley.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.sheepapps.englishvalley.BuildConfig;
import com.sheepapps.englishvalley.app.ValleyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareCardHelper {

    public void shareImage(Bitmap bitmap){
        Context context = ValleyApp.getInstance().getApplicationContext();
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            Log.e("shareImage", e.getMessage());
        }
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID
                + ".fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(contentUri, context.getContentResolver()
                    .getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            Intent lastShareIntent = Intent.createChooser(shareIntent, "Choose an app");
            lastShareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lastShareIntent);
        }
    }

    public Bitmap loadBitmapFromView(View viewToShare) {
        Bitmap bitmap = Bitmap.createBitmap(viewToShare.getMeasuredWidth(),
                viewToShare.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        viewToShare.layout(viewToShare.getLeft(), viewToShare.getTop(), viewToShare.getRight(),
                viewToShare.getBottom());
        viewToShare.draw(canvas);
        return bitmap;
    }
}
