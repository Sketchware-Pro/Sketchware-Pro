package mod.khaled.librarymanager;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sketchware.remod.R;

public class ExternalLibraryManagerActivity extends AppCompatActivity implements ExternalLibraryDownloaderDialog.DialogDismissedListener {

    private String sc_id;
    private RecyclerView recyclerview;
    private ExternalLibraryManager externalLibraryManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sc_id = getIntent().getStringExtra("sc_id");
        externalLibraryManager = new ExternalLibraryManager(sc_id);

        initializeView();
        initializeLogic();
        setResult(RESULT_OK);
    }

    private void initializeView() {
        setContentView(R.layout.external_library_manager);
        LinearLayout toolbar = findViewById(R.id.toolbar);

        toolbar.findViewById(R.id.ig_toolbar_back).setOnClickListener((v) -> finish());
        ((TextView) toolbar.findViewById(R.id.tx_toolbar_title)).setText("External Library Manager");

        findViewById(R.id.addNewFab).setOnClickListener((v) ->
                new ExternalLibraryDownloaderDialog().show(getSupportFragmentManager(), ""));
        recyclerview = findViewById(R.id.libraryListRecycler);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLibraryList);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerview.setAdapter(new ExternalLibraryListAdapter(this, externalLibraryManager.loadExternalLibraries(), sc_id));
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void initializeLogic() {
        recyclerview.setAdapter(new ExternalLibraryListAdapter(this, externalLibraryManager.getExternalLibraryItemArrayList(), sc_id));
    }

    @Override
    public void onDismissDownloaderDialog() {
        swipeRefreshLayout.setRefreshing(true);
        recyclerview.setAdapter(new ExternalLibraryListAdapter(this, externalLibraryManager.loadExternalLibraries(), sc_id));
        swipeRefreshLayout.setRefreshing(false);
    }
}
