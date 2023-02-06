package mod.khaled.librarymanager;

import android.content.Context;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.LibraryItemView;

import mod.jbk.editor.manage.library.ExcludeBuiltInLibrariesActivity;

public class ExternalLibraryManagerActivityItemView extends LibraryItemView {

    public ExternalLibraryManagerActivityItemView(Context context) {
        super(context);
    }

    @Override
    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {
        icon.setImageResource(ExcludeBuiltInLibrariesActivity.getItemIcon());
        title.setText("External Library Manager");
        description.setText("Add 3rd party libraries to project");
        enabled.setText("ON");
        enabled.setSelected(true);
    }
}
