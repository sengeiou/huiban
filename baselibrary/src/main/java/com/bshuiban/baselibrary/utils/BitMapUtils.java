package com.bshuiban.baselibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * bitmap工具类
 * 
 * @author lihong
 *
 */
public class BitMapUtils {
	/**
	 * 保存到sd卡指定路径
	 * 
	 * @param path
	 * @param bitmap
	 */
	public static void saveToSdCard(String path, Bitmap bitmap) {
		if (null != bitmap && null != path && !path.equalsIgnoreCase("")) {
			try {
				File file = new File(path);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.PNG, 30, bos);
				bos.flush();
				bos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		}
	}

	/**
	 * 复制bitmap,指定长宽
	 * 
	 * @param bmpSrc
	 * @param width
	 * @param height
	 * @return bitmap 位图
	 */
	public static Bitmap duplicateBitmap(Bitmap bmpSrc, int width, int height) {
		if (null == bmpSrc) {
			return null;
		}

		int bmpSrcWidth = bmpSrc.getWidth();
		int bmpSrcHeight = bmpSrc.getHeight();

		Bitmap bmpDest = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		if (null != bmpDest) {
			Canvas canvas = new Canvas(bmpDest);
			Rect viewRect = new Rect();
			final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
			if (bmpSrcWidth <= width && bmpSrcHeight <= height) {
				viewRect.set(rect);
			} else if (bmpSrcHeight > height && bmpSrcWidth <= width) {
				viewRect.set(0, 0, bmpSrcWidth, height);
			} else if (bmpSrcHeight <= height && bmpSrcWidth > width) {
				viewRect.set(0, 0, width, bmpSrcWidth);
			} else if (bmpSrcHeight > height && bmpSrcWidth > width) {
				viewRect.set(0, 0, width, height);
			}
			canvas.drawBitmap(bmpSrc, rect, viewRect, null);
		}

		return bmpDest;
	}

	/**
	 * 未指定长宽复制bitmap
	 * 
	 * @param bmpSrc
	 * @return bitmap位图
	 */
	public static Bitmap duplicateBitmap(Bitmap bmpSrc) {
		if (null == bmpSrc) {
			return null;
		}

		int bmpSrcWidth = bmpSrc.getWidth();
		int bmpSrcHeight = bmpSrc.getHeight();

		Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Config.ARGB_8888);
		if (null != bmpDest) {
			Canvas canvas = new Canvas(bmpDest);
			final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);

			canvas.drawBitmap(bmpSrc, rect, rect, null);
		}

		return bmpDest;
	}

	/**
	 * 将bitmap转为字节数组
	 * 
	 * @param bitmap
	 * @return 数组
	 */
	public static byte[] bitampToByteArray(Bitmap bitmap) {
		byte[] array = null;
		try {
			if (null != bitmap) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
				array = os.toByteArray();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}
	/**
	 * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
	 * more memory that there is already allocated.
	 *
	 * @param imgIn - Source image. It will be released, and should not be used more
	 * @return a copy of imgIn, but muttable.
	 */
	public static Bitmap convertToMutable(Bitmap imgIn) {
		try {
			//this is the file going to use temporally to save the bytes.
			// This file will not be a image, it will store the raw image data.
			File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

			//Open an RandomAccessFile
			//Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
			//into AndroidManifest.xml file
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

			// get the width and height of the source bitmap.
			int width = imgIn.getWidth();
			int height = imgIn.getHeight();
			Config type = imgIn.getConfig();

			//Copy the byte to the file
			//Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
			FileChannel channel = randomAccessFile.getChannel();
			MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
			imgIn.copyPixelsToBuffer(map);
			//recycle the source bitmap, this will be no longer used.
			imgIn.recycle();
			System.gc();// try to force the bytes from the imgIn to be released

			//Create a new bitmap to load the bitmap again. Probably the memory will be available.
			imgIn = Bitmap.createBitmap(width, height, type);
			map.position(0);
			//load it back from temporary
			imgIn.copyPixelsFromBuffer(map);
			//close the temporary file and channel , then delete that also
			channel.close();
			randomAccessFile.close();

			// delete the temp file
			file.delete();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imgIn;
	}
	public static void saveBitmap(String path, Bitmap bitmap) {
		if (null != bitmap && null != path && !path.equalsIgnoreCase("")) {
			try {
				File file = new File(path);
				if(file.exists()){
					file.delete();
				}
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.PNG, 30, bos);
				bos.flush();
				bos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
