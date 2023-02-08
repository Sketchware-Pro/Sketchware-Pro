package mod.khaled.librarymanager;

import static mod.khaled.librarymanager.ExternalLibraryDownloader.writeCustomRepositoryFile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sketchware.remod.R;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class ExternalLibraryManagerActivity extends AppCompatActivity implements ExternalLibraryDownloaderDialog.DialogDismissedListener {

    private String sc_id;
    private RecyclerView recyclerview;
    private ExternalLibraryManager externalLibraryManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sc_id = getIntent().hasExtra("sc_id") ? getIntent().getStringExtra("sc_id") : null;
        externalLibraryManager = new ExternalLibraryManager(sc_id);

        initializeView();
        initializeLogic();
        setResult(RESULT_OK);
    }

    private void initializeView() {
        setContentView(R.layout.external_library_manager);
        LinearLayout toolbar = findViewById(R.id.toolbar);

        toolbar.findViewById(R.id.ig_toolbar_back).setOnClickListener((v) -> finish());
        ((TextView) toolbar.findViewById(R.id.tx_toolbar_title)).setText(R.string.external_library_manager);
        ImageView moreIcon = toolbar.findViewById(R.id.ig_toolbar_load_file);
        moreIcon.setVisibility(View.VISIBLE);
        moreIcon.setImageResource(R.drawable.ic_more_vert_white_24dp);

        moreIcon.setOnClickListener((v -> {
            PopupMenu menu = new PopupMenu(this, moreIcon);
            if (sc_id != null)
                menu.getMenu().add(0, 0, 0, R.string.reset_library_selections);
            menu.getMenu().add(0, 1, 1, R.string.custom_repositories);

            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case 0: // Reset library selections
                        FileUtil.deleteFile(new FilePathUtil().getPathExternalLibrary(sc_id));
                        onDismissDownloaderDialog();
                        SketchwareUtil.toast("Done");
                        break;

                    case 1: // Custom Repositories
                        SketchwareUtil.toast("Custom repositories can be loaded by adding entries to: "
                                + FilePathUtil.getCustomExternalRepositoriesFile()
                                + "\nOpen the file in a text editor to learn how.");
                        if (FileUtil.readFile(FilePathUtil.getCustomExternalRepositoriesFile()).isBlank())
                            writeCustomRepositoryFile();
                        break;
                }
                return true;
            });

            menu.show();
        }));

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
