package mod.khaled.librarymanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import mod.SketchwareUtil;

public class ExternalLibraryDownloaderDialog extends DialogFragment {

    TextView startButton, cancelButton, stopButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ExpandedDialogFragment);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (getActivity() instanceof DialogDismissedListener)
            ((DialogDismissedListener) getActivity()).onDismissDownloaderDialog();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.common_dialog_layout, container, false);
        root.setPadding(0, 0, 0, 0);

        ((ImageView) root.findViewById(R.id.common_dialog_icon)).setImageResource(R.drawable.language_download_96);
        ((TextView) root.findViewById(R.id.common_dialog_tv_title)).setText("Download New Library");

        startButton = root.findViewById(R.id.common_dialog_ok_button);
        stopButton = root.findViewById(R.id.common_dialog_cancel_button);
        cancelButton = root.findViewById(R.id.common_dialog_default_button);

        cancelButton.setText("Cancel");
        startButton.setText("Next");
        stopButton.setText("Stop");
        stopButton.setVisibility(View.GONE); //TODO:Implement

        cancelButton.setOnClickListener((v) -> dismiss());

        View contentLayout = inflater.inflate(R.layout.new_external_library_dialog, container, false);
        ((LinearLayout) root.findViewById(R.id.common_dialog_content)).addView(contentLayout);

        TextInputLayout gradleImplementationInput = contentLayout.findViewById(R.id.gradleImplementationInput);
        LinearLayout libraryDetailsView = contentLayout.findViewById(R.id.libraryDetailsView);
        TextInputLayout libraryNameInput = contentLayout.findViewById(R.id.libraryNameInput);
        TextInputLayout libraryPkgInput = contentLayout.findViewById(R.id.libraryPkgInput);


        startButton.setOnClickListener((v) -> {
            String libraryPkg = parseGradleImplementation(gradleImplementationInput.getEditText().getText().toString());
            String libraryName = parseLibraryName(libraryPkg);
            if (libraryPkg.isEmpty()) {
                gradleImplementationInput.setError("Parsing of gradle implementation failed.");
                return;
            }

            if (libraryDetailsView.getVisibility() != View.VISIBLE) {
                libraryDetailsView.setVisibility(View.VISIBLE);
                ((View) gradleImplementationInput.getParent()).setVisibility(View.GONE);
                libraryNameInput.getEditText().setText(libraryName);
                libraryPkgInput.getEditText().setText(libraryPkg);
                startButton.setText("Download");
                return;
            }

            if (libraryDetailsView.getVisibility() == View.VISIBLE && !startButton.getText().equals("Save")) {
                startDownloadingLibrary(libraryNameInput.getEditText().getText().toString(),
                        libraryPkgInput.getEditText().getText().toString(), error -> {
                            if (error == null) {
                                SketchwareUtil.toast("Downloaded library " + libraryNameInput.getEditText().getText());
                                dismiss();
                                return;
                            }
                            SketchwareUtil.toastError(error);
                            startButton.setText("Retry");
                            startButton.setVisibility(View.VISIBLE);
                            stopButton.setVisibility(View.GONE);
                        });

                stopButton.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.GONE);
            }
        });

        return root;
    }

    private String parseGradleImplementation(String input) {
        return input;
    }

    private String parseLibraryName(String input) {
        return input;
    }

    private void startDownloadingLibrary(String libraryName, String libraryPkg, DownloadFinishedCallback callback) {

        callback.complete(null);
    }

    private interface DownloadFinishedCallback {
        void complete(@Nullable String error);
    }

    public interface DialogDismissedListener {
        void onDismissDownloaderDialog();
    }
}
