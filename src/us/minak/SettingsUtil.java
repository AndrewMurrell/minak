package us.minak;

import android.content.Context;
import android.gesture.GestureLibrary;
import android.gesture.GestureLibraries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SettingsUtil {
	private static File            sGestureFile = null;
	private static GestureLibrary  sGestureLibrary = null;

	public static File           getGestureFile(Context context) {
		if (sGestureFile == null)
			sGestureFile = new File(context.getExternalFilesDir(null), "gestures");
		// If the gestures file doesn't exist, copy the default gestures to it
		if (!sGestureFile.exists()) {
			try {
				InputStream  in = context.getResources().openRawResource(R.raw.gestures);
				OutputStream out = new FileOutputStream(sGestureFile);
				byte[] buf = new byte[1024];
				int len;
				while ( (len = in.read(buf, 0, buf.length)) != -1)
					out.write(buf, 0, len);
				in.close();
				out.close();
			} catch (Exception e) {
				// TODO: better error handling
			}
		}
		return sGestureFile;
	}
	public static GestureLibrary getGestureLibrary(Context context) {
		if (sGestureLibrary == null)
			sGestureLibrary = GestureLibraries.fromFile(getGestureFile(context));
		return sGestureLibrary;
	}
}
