package mod.khaled.librarymanager;

import android.content.Context;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.sketchware.remod.R;

public class ExternalLibraryManagerActivityItemView extends LibraryItemView {
    private String sc_id;

    public ExternalLibraryManagerActivityItemView(Context context, String sc_id) {
        super(context);
        this.sc_id = sc_id;
    }

    public ExternalLibraryManagerActivityItemView(Context context) {
        super(context);
    }

    @Override
    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {

        int usedExternalLibsCount = sc_id != null ? ExternalLibraryManager.getLibrariesInProjectHashes(sc_id).size() : 0;
        icon.setImageResource(R.drawable.colored_box_96);
        title.setText(R.string.external_library_manager);
        description.setText(R.string.external_bib_manager_description);
        enabled.setText(String.valueOf(usedExternalLibsCount));
        enabled.setSelected(usedExternalLibsCount != 0);
    }
}
