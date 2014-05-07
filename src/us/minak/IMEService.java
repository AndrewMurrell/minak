package us.minak;

import android.inputmethodservice.InputMethodService;
import android.view.View;

/**
 * Represent the application input service.
 */
public class IMEService extends InputMethodService implements InputConnectionGetter {
	@Override
	public View onCreateInputView() {
		final IMEView minakView = (IMEView) getLayoutInflater().inflate(R.layout.ime, null);
		minakView.setInputConnectionGetter(this);
		return minakView;
	}
}
