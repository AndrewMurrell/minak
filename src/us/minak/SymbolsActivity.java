package us.minak;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * Represents the window for choosing additional characters.
 */
public class SymbolsActivity extends Activity {
	public static final String INTENT_ACTION = "com.samsung.penboard.SYMBOL_ENTERED";
	public static final String INTENT_EXTRA_NAME = "symbol";
	private static final Character[] SYMBOLS = new Character[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '!',
			'@', '#', '$', '%', '^', '&', '*', '(', ')', '`', '-', '=', '~', '_', '+', '[', ']', '\\', '{', '}', '|',
			';', '\'', ':', '\'', ',', '.', '/', '<', '>', '?' };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.symbols);
		final GridView gridView = (GridView) findViewById(R.id.symbols_gridview);
		final ArrayAdapter<Character> adapter = new ArrayAdapter<Character>(this, android.R.layout.simple_list_item_1,
				SYMBOLS);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Intent intent = new Intent(INTENT_ACTION);
				intent.putExtra(INTENT_EXTRA_NAME, SYMBOLS[position]);
				LocalBroadcastManager.getInstance(SymbolsActivity.this).sendBroadcast(intent);
			}
		});
	}
}
