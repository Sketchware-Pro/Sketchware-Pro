package mod.khaled.librarymanager;

import android.content.Context;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.sketchware.remod.R;

public class ExternalLibraryManagerActivityItemView extends LibraryItemView {
    private final String sc_id;

    public ExternalLibraryManagerActivityItemView(Context context, String sc_id) {
        super(context);
        this.sc_id = sc_id;
    }

    @Override
    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {

        int usedExternalLibsCount = ExternalLibraryManager.getLibrariesInProjectHashes(sc_id).size();
        icon.setImageResource(R.drawable.colored_box_96);
        title.setText("External Library Manager");
        description.setText("Add 3rd party libraries to project");
        enabled.setText(String.valueOf(usedExternalLibsCount));
        enabled.setSelected(usedExternalLibsCount != 0);
    }
}
