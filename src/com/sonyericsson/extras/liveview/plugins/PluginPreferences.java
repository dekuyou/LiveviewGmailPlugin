/*
 * Copyright (c) 2010 Sony Ericsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.sonyericsson.extras.liveview.plugins;

import jp.ddo.dekuyou.android.util.Log;
import jp.ddo.dekuyou.android.util.PaydVersionConfirm;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

/**
 * Implements PreferenceActivity and sets the project preferences to the shared
 * preferences of the current user session.
 */
public class PluginPreferences extends PreferenceActivity {
	AccountManager mAccountManager;
	Account[] accounts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(getResources().getIdentifier("preferences",
				"xml", getPackageName()));

		PreferenceScreen ps = getPreferenceManager().createPreferenceScreen(
				this);

		mAccountManager = AccountManager.get(this);
		accounts = mAccountManager.getAccountsByType("com.google");

		for (Account account : accounts) {
			String name = account.name;
			String type = account.type;
			int describeContents = account.describeContents();
			int hashCode = account.hashCode();

			Log.d("name = " + name + "\ntype = " + type
					+ "\ndescribeContents = " + describeContents
					+ "\nhashCode = " + hashCode);

			EditTextPreference pf = new EditTextPreference(this);
			pf.setTitle(account.name);
			pf.setKey(account.name + "_");
			pf.setSummary(account.type);

			ps.addPreference(pf);

		}

		Preference pf = new Preference(this);
		pf.setTitle("Summary Notification");
		pf.setKey("show_summary");

		pf.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				//
				return getPaydVersion();
			}
		});
		ps.addPreference(pf);
		
		setPreferenceScreen(ps);
		
		getPaydVersion();

	}
	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		getPreferenceScreen().getSharedPreferences()
//				.registerOnSharedPreferenceChangeListener(listener);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		getPreferenceScreen().getSharedPreferences()
//				.unregisterOnSharedPreferenceChangeListener(listener);
//	}
//
//	private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
//
//		public void onSharedPreferenceChanged(
//				SharedPreferences sharedPreferences, String key) {
//			
//			getPaydVersion();
//			
//		}
//	};

	private boolean getPaydVersion() {
		// PaydVersionConfirm
		Intent intent = new Intent(this, PaydVersionConfirm.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);


		return true;
	}
	
	

}
