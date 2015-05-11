package com.rgy.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.rgy.tlrobot.MainActivity;

public class ImageCacheUtils {
	
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;

	public ImageCacheUtils(Context context) {
		memoryCache = new ImageMemoryCache(context);
		fileCache = new ImageFileCache();
	}

	public Bitmap getBitmap(String url) {
		// ���ڴ滺���л�ȡͼƬ
		Bitmap result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			// �ļ������л�ȡ
			result = fileCache.getImage(url);
			if (result == null) {
				// �������ȡ
				result = getBitmapFromWeb(url);
				if (result != null) {
					fileCache.saveBitmap(result, url);
					memoryCache.addBitmapToCache(url, result);
					System.out.println("ͼƬ�������л�ȡ");
				}
			} else {
				// ��ӵ��ڴ滺��
				memoryCache.addBitmapToCache(url, result);
				System.out.println("ͼƬ���ļ������л�ȡ");
			}	
		}else{
			System.out.println("ͼƬ���ڴ滺���л�ȡ");
		}
		
		return result;
	}// end getBitmap
	
	/**
	 * �������л�ȡ
	 * @param path
	 * @return
	 */
	private Bitmap getBitmapFromWeb(String path){
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);
			if (conn.getResponseCode() == 200) {
				InputStream input = conn.getInputStream();
				FlushedInputStream flushIS = new FlushedInputStream(input);
				
				BitmapFactory.Options options = new BitmapFactory.Options();
	            options.inSampleSize = 2;//ͼƬ��߶�Ϊԭ���Ķ���֮һ����ͼƬΪԭ�����ķ�֮һ
	            Bitmap bitmap = BitmapFactory.decodeStream(flushIS,null,options);
	            
	            input.close();
	            flushIS.close();
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Android����InputStream���и�Сbug���������������¿��ܲ����жϣ�
	 * ���Կ�����дFilterInputStream����skip������������bug��
	 * BitmapFactory���decodeStream���������糬ʱ�������ʱ���޷���ȡ���������ݣ� ������
	 * ��ͨ���̳�FilterInputStream���skip������ǿ��ʵ��flush���е����ݣ�
	 * ��Ҫԭ����Ǽ���Ƿ��ļ�ĩ�ˣ�����http���Ƿ����
	 */
	private class FlushedInputStream extends FilterInputStream {

		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {

			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0)
						break; // we reached EOF
					else
						bytesSkipped = 1; // we read one byte

				}// end if

				totalBytesSkipped += bytesSkipped;
			}// end while

			return totalBytesSkipped;
		}// end skip

	}// end Class FlushedInputStream
	
	
	//---------------------------------------------------------ImageMemoryCache

	public class ImageMemoryCache {
		/**
		 * ���ڴ��ȡ�����ٶ������ģ�Ϊ�˸����޶�ʹ���ڴ棬����ʹ�������㻺�档
		 * Ӳ���û��治�����ױ����գ��������泣�����ݣ������õ�ת�������û��档
		 */
		private static final int SOFT_CACHE_SIZE = 15; // �����û�������
		private LruCache<String, Bitmap> mLruCache; // Ӳ���û���
		private LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; // �����û���

		public ImageMemoryCache(Context context) {
			ActivityManager manager = (ActivityManager) context
					.getSystemService(MainActivity.ACTIVITY_SERVICE);
			int memClass = manager.getMemoryClass();
			int cacheSize = 1024 * 1024 * memClass / 10; // Ӳ���û���������Ϊϵͳ�����ڴ��1/10
			mLruCache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					if (value != null)
						return value.getRowBytes() * value.getHeight();
					else
						return 0;
				}

				@Override
				protected void entryRemoved(boolean evicted, String key,
						Bitmap oldValue, Bitmap newValue) {
					if (oldValue != null){
						// Ӳ���û�����������ʱ�򣬻����LRU�㷨�����û�б�ʹ�õ�ͼƬת��������û���
						mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
					}
				}
			};// end LruCache

			mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
					SOFT_CACHE_SIZE, 0.75f, true) {
				private static final long serialVersionUID = 6040103833179403725L;

				@Override
				protected boolean removeEldestEntry(
						Entry<String, SoftReference<Bitmap>> eldest) {
					if (size() > SOFT_CACHE_SIZE) {
						return true;
					}
					return false;
				}
			};// end LinkedHashMap

		}// end ImageMeroyCache

		/**
		 * �ӻ����л�ȡͼƬ
		 */
		public Bitmap getBitmapFromCache(String url) {
			Bitmap bitmap;
			// �ȴ�Ӳ���û����л�ȡ
			synchronized (mLruCache) {
				bitmap = mLruCache.get(url);
				if (bitmap != null) {
					// ����ҵ��Ļ�����Ԫ���Ƶ�LinkedHashMap����ǰ�棬�Ӷ���֤��LRU�㷨�������ɾ��
					mLruCache.remove(url);
					mLruCache.put(url, bitmap);
					System.out.println("Ӳ�������ҵ���");
					return bitmap;
				}else {
					System.out.println("Ӳ������û�ҵ�");
				}
			}
			// ���Ӳ���û������Ҳ������������û�������
			synchronized (mSoftCache) {
				SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
				if (bitmapReference != null) {
					bitmap = bitmapReference.get();
					if (bitmap != null) {
						// ��ͼƬ�ƻ�Ӳ����
						mLruCache.put(url, bitmap);
						mSoftCache.remove(url);
						System.out.println("���������ҵ���");
						return bitmap;
					} else {
						mSoftCache.remove(url);
						System.out.println("��������û�ҵ�");
					}
				}
			}
			return null;
		}

		/**
		 * ���ͼƬ������
		 */
		public void addBitmapToCache(String url, Bitmap bitmap) {
			if (bitmap != null) {
				synchronized (mLruCache) {
					mLruCache.put(url, bitmap);
					System.out.println("ͼƬ����ӵ��ڴ滺��");
				}
			}
		}

		public void clearCache() {
			mSoftCache.clear();
		}
	}// end ImageMemoryCache
	
	//----------------------------------------------------------------ImageFileCache

	public class ImageFileCache {
		private static final String CACHDIR = "ImgCach";
		private static final String WHOLESALE_CONV = ".cach";

		private static final int MB = 1024 * 1024;
		private static final int CACHE_SIZE = 10;
		private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;

		public ImageFileCache() {
			// �����ļ�����
			removeCache(getDirectory());
		}

		/** �ӻ����л�ȡͼƬ **/
		public Bitmap getImage(final String url) {
			final String path = getDirectory() + "/"
					+ convertUrlToFileName(url);
			File file = new File(path);
			if (file.exists()) {
				Bitmap bmp = BitmapFactory.decodeFile(path);
				if (bmp == null) {
					file.delete();
				} else {
					updateFileTime(path);
					return bmp;
				}
			}
			return null;
		}

		/** ��ͼƬ�����ļ����� **/
		public void saveBitmap(Bitmap bm, String url) {
			if (bm == null) {
				return;
			}
			// �ж�sdcard�ϵĿռ�
			if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
				// SD�ռ䲻��
				System.out.println("SD���ռ䲻��");
				return;
			}
			String filename = convertUrlToFileName(url);
			System.out.println(filename);
			String dir = getDirectory();
			File dirFile = new File(dir);
			if (!dirFile.exists()){
				dirFile.mkdirs();
				System.out.println("��ӡĿ¼:"+dirFile.toString());
			}	
			File file = new File(dir +"/"+ filename);
			try {
				file.createNewFile();
				OutputStream outStream = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.flush();
				outStream.close();
			} catch (FileNotFoundException e) {
				Log.w("ImageFileCache", "FileNotFoundException");
				System.out.println("�ļ�û�ҵ���δ˳�������ļ�");
			} catch (IOException e) {
				Log.w("ImageFileCache", "IOException");
				System.out.println("io�쳣��δ˳�������ļ�");
				e.printStackTrace();
			}
		}

		/**
		 * ����洢Ŀ¼�µ��ļ���С��
		 * ���ļ��ܴ�С���ڹ涨��CACHE_SIZE����sdcardʣ��ռ�С��FREE_SD_SPACE_NEEDED_TO_CACHE�Ĺ涨
		 * ��ôɾ��40%���û�б�ʹ�õ��ļ�
		 */
		private boolean removeCache(String dirPath) {
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			if (files == null) {
				return true;
			}
			if (!android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				return false;
			}

			int dirSize = 0;
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().contains(WHOLESALE_CONV)) {
					dirSize += files[i].length();
				}
			}

			if (dirSize > CACHE_SIZE * MB
					|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
				int removeFactor = (int) ((0.4 * files.length) + 1);
				Arrays.sort(files, new FileLastModifSort());
				for (int i = 0; i < removeFactor; i++) {
					if (files[i].getName().contains(WHOLESALE_CONV)) {
						files[i].delete();
					}
				}
			}

			if (freeSpaceOnSd() <= CACHE_SIZE) {
				return false;
			}

			return true;
		}

		/** �޸��ļ�������޸�ʱ�� **/
		public void updateFileTime(String path) {
			File file = new File(path);
			long newModifiedTime = System.currentTimeMillis();
			file.setLastModified(newModifiedTime);
		}

		/** ����sdcard�ϵ�ʣ��ռ� **/
		private int freeSpaceOnSd() {
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			@SuppressWarnings("deprecation")
			double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
					.getBlockSize()) / MB;
			return (int) sdFreeMB;
		}

		/** ��urlת���ļ��� **/
		private String convertUrlToFileName(String url) {
			String[] strs = url.split("/");
			return strs[strs.length - 1] + WHOLESALE_CONV;
		}

		/** ��û���Ŀ¼ **/
		private String getDirectory() {
			String dir = getSDPath() + "/" + CACHDIR;
			return dir;
		}

		/** ȡSD��·�� **/
		private String getSDPath() {
			File sdDir = null;
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
			if (sdCardExist) {
				sdDir = Environment.getExternalStorageDirectory(); // ��ȡ��Ŀ¼
			}
			if (sdDir != null) {
				return sdDir.toString();
			} else {
				return "";
			}
		}

		/**
		 * �����ļ�������޸�ʱ���������
		 */
		private class FileLastModifSort implements Comparator<File> {
			public int compare(File arg0, File arg1) {
				if (arg0.lastModified() > arg1.lastModified()) {
					return 1;
				} else if (arg0.lastModified() == arg1.lastModified()) {
					return 0;
				} else {
					return -1;
				}
			}
		}

	}//end ImageFileCache

}// end ImageCacheUtils
