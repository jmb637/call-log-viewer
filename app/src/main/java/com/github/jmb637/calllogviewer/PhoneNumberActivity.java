package com.github.jmb637.calllogviewer;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.github.jmb637.calllogviewer.model.CallLogCursorParser;
import com.github.jmb637.calllogviewer.model.PhoneNumberCursorParser;

/**
 * A simple activity that uses a fragment to display a recycler view containing every phone number
 * found in the call log.
 */
public class PhoneNumberActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int REQUEST_CALL_LOG = 0;
    private PhoneNumberFragment phoneNumberFragment;
    private PhoneNumberCursorParser cursorParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        phoneNumberFragment = (PhoneNumberFragment)
                getSupportFragmentManager().findFragmentById(R.id.phoneNumberFragment);

        cursorParser = (PhoneNumberCursorParser)
                getIntent().getSerializableExtra("phoneNumberCursorParser");
        if (cursorParser == null) {
            cursorParser = new CallLogCursorParser();
        }

        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) ==
                PackageManager.PERMISSION_GRANTED) {
            getLoaderManager().initLoader(0, savedInstanceState, this);
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CALL_LOG);
        }
    }

    /**
     * @return a cursor loader for fetching phone calls from the call log
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return cursorParser.getCursorLoader(this);
    }

    /**
     * Populate the adapter with data from the cursor.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        phoneNumberFragment.setPhoneNumbers(cursorParser.parsePhoneNumbers(data));
    }

    /**
     * Clear the adapter.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        phoneNumberFragment.setPhoneNumbers(null);
    }

    /**
     * Begin loading from the call log when the user gives permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLoaderManager().initLoader(0, null, this);
            } else {
                throw new RuntimeException();
            }
        }
    }
}
