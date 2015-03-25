package com.rgy.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncTaskImageLoad extends AsyncTask<String, Integer, Bitmap> {

	private Context context = null;
	private ImageView image = null;

	public AsyncTaskImageLoad(Context context, ImageView image) {
		this.context = context;
		this.image = image;
	}

	// 运行在子线程中
	@Override
	protected Bitmap doInBackground(String... params) {
		String path = params[0];

		ImageCacheUtils imageCacheUtils = new ImageCacheUtils(context);

		Bitmap bitmap = imageCacheUtils.getBitmap(path);

		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (image != null && result != null) {
			image.setImageBitmap(result);
		}
		super.onPostExecute(result);
	}

}
