package cc.zafar.todolist;

import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by ZafarTakhirov on 10/1/16.
 */

public class MyAlertDialogFragment extends DialogFragment {
	public MyAlertDialogFragment() {
		// Empty constructor required for DialogFragment
	}

	public static MyAlertDialogFragment newInstance(String title) {
		MyAlertDialogFragment frag = new MyAlertDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage("Are you sure?");
		alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// on success
			}
		});
		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		return alertDialogBuilder.create();
	}
}