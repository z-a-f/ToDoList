package cc.zafar.todolist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// showEditDialog();

		lvItems = (ListView)findViewById(R.id.lvItems);
		items = new ArrayList<>();

		readItems();

		itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		// items.add("First item");
		// items.add("Second item");
		setupListViewListener(); // Setup deleting routine
	}

	private void setupListViewListener() {
		/* Take care of long click delete function */
		lvItems.setOnItemLongClickListener(
			new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick (
						AdapterView<?> adapter,
						View item,
						int pos,
						long id) {
					if (showAlertDialog()) {
						items.remove(pos);
						itemsAdapter.notifyDataSetChanged();
						writeItems();
					}
					return true;
				}
			});
		lvItems.setOnItemClickListener(
			new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick (AdapterView<?> adapter, View item, int pos, long id) {
					String txt = ((TextView)item).getText().toString();
					showEditDialog("Edit item", txt); // Maybe we should replace it with the item text?
					// showAlertDialog();

				}
			});
	}

	public void onAddItem (View v) {
		/* Adding new item to the list */
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		String itemText = etNewItem.getText().toString();
		itemsAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
	}

	/* File IO */
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<>();
		}
	}

	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Additional stuff */
	private void showEditDialog(String title, String text) {


		FragmentManager fm = getSupportFragmentManager();
		EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(title);
		editNameDialogFragment.show(fm, "fragment_edit_name");
//		EditText etFragment = (EditText) findViewById(R.id.fragment_edit_text);
//		Log.d(TAG, text);
//		etFragment.setText(text, TextView.BufferType.EDITABLE);
	}

	private boolean showAlertDialog() {
		FragmentManager fm = getSupportFragmentManager();
		MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance("Some title");
		alertDialog.show(fm, "fragment_alert");
		return true;
	}

}
