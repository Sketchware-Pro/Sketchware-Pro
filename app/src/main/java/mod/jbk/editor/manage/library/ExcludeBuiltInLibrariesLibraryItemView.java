package mod.jbk.editor.manage.library;

import android.content.Context;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.sketchware.remod.R;

public class ExcludeBuiltInLibrariesLibraryItemView extends LibraryItemView {
    private final String sc_id;

    public ExcludeBuiltInLibrariesLibraryItemView(Context context, String sc_id) {
        super(context);
        this.sc_id = sc_id;
    }

    @Override
    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {
        icon.setImageResource(R.drawable.ic_detail_setting_48dp);
        title.setText("(Advanced) Exclude built-in libraries");
        description.setText("Use custom Library versions");
        enabled.setText(false ? "ON" : "OFF");
        enabled.setSelected(false);
    }
}
