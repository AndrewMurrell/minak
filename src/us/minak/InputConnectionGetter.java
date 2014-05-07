package us.minak;

import android.view.inputmethod.InputConnection;

public interface InputConnectionGetter {
	public InputConnection getCurrentInputConnection();

	public static class NullGetter implements InputConnectionGetter{
		@Override
		public InputConnection getCurrentInputConnection() {
			return null;
		}
	}
}
