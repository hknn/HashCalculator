package info.sarhadmac.hashcalculator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import android.app.Activity;
import android.drm.DrmUtils.ExtendedMetadataParser;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{
	String textToGetTheHashesFor;
	TextView tvMD5, tvSHA1, tvSHA256, tvSHA512;
	EditText etTextToGetTheHashFor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeGUI();
	}

	private void initializeGUI()
	{
		tvMD5 = (TextView) findViewById(R.id.textViewMD5);
		tvSHA1 = (TextView) findViewById(R.id.textViewSHA1);
		tvSHA256 = (TextView) findViewById(R.id.textViewSHA256);
		tvSHA512 = (TextView) findViewById(R.id.textViewSHA512);
		etTextToGetTheHashFor = (EditText) findViewById(R.id.editTextTextToGetTheHashFor);
		etTextToGetTheHashFor.addTextChangedListener(watcher);
		tvMD5.setFocusable(true);
		tvMD5.requestFocus();
		
	}
	
	

	@Override
	protected void onResume()
	{
		super.onResume();
		etTextToGetTheHashFor.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private static String encryptPassword(String password, String hashingFormat)
	{
		String hashedValue = "";
		try
		{
			MessageDigest crypt = MessageDigest.getInstance(hashingFormat);
			System.out.print(hashingFormat + " ("
					+ Integer.toString(crypt.getDigestLength() * 8) + ") bits"
					+ "\t" + " : ");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			hashedValue = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return hashedValue;
	}

	private static String byteToHex(final byte[] hash)
	{
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private TextWatcher watcher = new TextWatcher()
	{

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			textToGetTheHashesFor = etTextToGetTheHashFor.getText().toString();

			tvMD5.setText(encryptPassword(textToGetTheHashesFor, "MD5"));
			tvSHA1.setText(encryptPassword(textToGetTheHashesFor, "SHA-1"));
			tvSHA256.setText(encryptPassword(textToGetTheHashesFor, "SHA-256"));
			tvSHA512.setText(encryptPassword(textToGetTheHashesFor, "SHA-512"));
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}

		@Override
		public void afterTextChanged(Editable s)
		{

		}
	};
}
