package mod.khaled.externallibrarymanager;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;

import java.util.ArrayList;

public class ExternalLibraryManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        initializeLogic();
    }

    private void initializeView() {
        setContentView(R.layout.external_library_manager);
        LinearLayout toolbar = findViewById(R.id.toolbar);

        toolbar.findViewById(R.id.ig_toolbar_back).setOnClickListener((v) -> finish());
        ((TextView) toolbar.findViewById(R.id.tx_toolbar_title)).setText("External Library Manager");

        findViewById(R.id.addNewFab).setOnClickListener((v) ->
                new ExternalLibraryDownloaderDialog().show(getSupportFragmentManager(), ""));
        recyclerview = findViewById(R.id.libraryListRecycler);
    }

    private void initializeLogic() {
        ArrayList<ExternalLibraryItem> externalLibraryItemArrayList = new ArrayList<>();
        externalLibraryItemArrayList.add(new ExternalLibraryItem());
        externalLibraryItemArrayList.add(new ExternalLibraryItem());
        externalLibraryItemArrayList.add(new ExternalLibraryItem());
        recyclerview.setAdapter(new ExternalLibraryListAdapter(externalLibraryItemArrayList));

    }
}
