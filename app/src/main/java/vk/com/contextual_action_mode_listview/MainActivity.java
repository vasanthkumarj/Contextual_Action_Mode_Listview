package vk.com.contextual_action_mode_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> version_array_list = new ArrayList<>();
    private String[] version_array;
    private ArrayAdapter<String> adapter;
    private List action_mode_temporary_list = new ArrayList();
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        version_array = getResources().getStringArray(R.array.android_versions_list);

        for(String item : version_array)
        {
            version_array_list.add(item);
        }
        adapter = new ArrayAdapter<>(this, R.layout.my_listview_template, R.id.text_view_layout,
                version_array_list);
        listView.setAdapter(adapter);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b)
                {
                    action_mode_temporary_list.add(version_array_list.get(i));
                    size = action_mode_temporary_list.size();
                    actionMode.setTitle(size+" item selected");
                }
                else
                {
                    action_mode_temporary_list.remove(version_array_list.get(i));
                    size = action_mode_temporary_list.size();
                    actionMode.setTitle(size+" item selected");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.my_action_mode_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.delete)
                {
                    for(Object item : action_mode_temporary_list)
                    {
                        version_array_list.remove(String.valueOf(item));
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),action_mode_temporary_list.size()+" item deleted", Toast.LENGTH_LONG).show();
                    actionMode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                 action_mode_temporary_list.clear();
            }
        });
    }
}
