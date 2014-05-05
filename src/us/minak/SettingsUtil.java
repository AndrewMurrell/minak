package us.minak;

import android.content.ContextWrapper;
import android.gesture.GestureLibrary;
import android.gesture.GestureLibraries;
import java.io.File;

public class SettingsUtil {
	private static File            sGestureFile = null;
	private static GestureLibrary  sGestureLibrary = null;

	public static File           getGestureFile(ContextWrapper context) {
		if (sGestureFile == null)
			sGestureFile = new File(context.getExternalFilesDir(null), "gestures.ttf");
		return sGestureFile;
	}
	public static GestureLibrary getGestureLibrary(ContextWrapper context) {
		if (sGestureLibrary == null)
			sGestureLibrary = GestureLibraries.fromFile(getGestureFile(context));
		return sGestureLibrary;
	}
}
