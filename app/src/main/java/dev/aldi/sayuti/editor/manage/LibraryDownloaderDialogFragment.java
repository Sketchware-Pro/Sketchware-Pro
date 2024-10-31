package dev.aldi.sayuti.editor.manage;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import pro.sketchware.databinding.LibraryDownloaderDialogBinding;

import org.cosmic.ide.dependency.resolver.api.Artifact;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuiltInLibraries;
import mod.pranav.dependency.resolver.DependencyResolver;

public class LibraryDownloaderDialogFragment extends DialogFragment {
    private LibraryDownloaderDialogBinding binding;
    private final Gson gson = new Gson();
    private boolean notAssociatedWithProject = false;
    private String dependencyName;
    private BuildSettings buildSettings;
    private String local_lib_file = "";
    private LibraryDownloaderListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = LibraryDownloaderDialogBinding.inflate(LayoutInflater.from(getContext()));
        initVariables();

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        builder.setView(binding.getRoot());

        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    public void setListener(LibraryDownloaderListener listener) {
        this.listener = listener;
    }

    public interface LibraryDownloaderListener {
        void onTaskCompleted();
    }

    private void initVariables() {
        if (getArguments() == null) return;

        notAssociatedWithProject = getArguments().getBoolean("notAssociatedWithProject", false);
        buildSettings = (BuildSettings) getArguments().getSerializable("buildSettings");
        local_lib_file = getArguments().getString("local_lib_file");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.downloadButton.setOnClickListener(v1 -> initDownloadFlow());
    }

    private void initDownloadFlow() {
        dependencyName = Objects.requireNonNull(binding.dependencyInput.getText()).toString();
        if (dependencyName.isEmpty()) {
            SketchwareUtil.toastError("Please enter a dependency");
            return;
        }
        var parts = dependencyName.split(":");
        if (parts.length != 3) {
            SketchwareUtil.toastError("Invalid dependency format");
            return;
        }

        binding.downloadStatusTxt.setText("Looking for dependency...");
        setDownloadState(true);

        var group = parts[0];
        var artifact = parts[1];
        var version = parts[2];
        var resolver = new DependencyResolver(group, artifact, version, binding.skipSubDependenciesCheckBox.isChecked(), buildSettings);
        var handler = new Handler(Looper.getMainLooper());

        class SetTextRunnable implements Runnable {
            private final String message;

            SetTextRunnable(String message) {
                this.message = message;
            }

            @Override
            public void run() {
                binding.downloadStatusTxt.setText(message);
            }
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            BuiltInLibraries.maybeExtractAndroidJar(progress -> handler.post(new SetTextRunnable(progress)));
            BuiltInLibraries.maybeExtractCoreLambdaStubsJar();

            resolver.resolveDependency(new DependencyResolver.DependencyResolverCallback() {
                @Override
                public void onResolving(@NonNull Artifact artifact, @NonNull Artifact dependency) {
                    handler.post(new SetTextRunnable("Resolving " + dependency + " for " + artifact + "..."));
                }

                @Override
                public void onResolutionComplete(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Dependency " + dep + " resolved"));
                }

                @Override
                public void onArtifactFound(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Found " + dep + " in " + dep.getRepository().getName()));
                }

                @Override
                public void onArtifactNotFound(@NonNull Artifact dep) {
                    handler.post(() -> {
                        setDownloadState(false);
                        SketchwareUtil.showAnErrorOccurredDialog(getActivity(), "Dependency '" + dep + "' not found");
                    });
                }

                @Override
                public void onSkippingResolution(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Skipping resolution for " + dep));
                }

                @Override
                public void onVersionNotFound(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Version not available for " + dep));
                }

                @Override
                public void onDependenciesNotFound(@NonNull Artifact dep) {
                    handler.post(() -> new SetTextRunnable("Dependencies not found for \"" + dep + "\"").run());
                }

                @Override
                public void onInvalidScope(@NonNull Artifact dep, @NonNull String scope) {
                    handler.post(new SetTextRunnable("Invalid scope for " + dep + ": " + scope));
                }

                @Override
                public void invalidPackaging(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Invalid packaging for dependency " + dep));
                }

                @Override
                public void onDownloadStart(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Downloading dependency " + dep + "..."));
                }

                @Override
                public void onDownloadEnd(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Dependency " + dep + " downloaded"));
                }

                @Override
                public void onDownloadError(@NonNull Artifact dep, @NonNull Throwable e) {
                    handler.post(() -> {
                        setDownloadState(false);
                        SketchwareUtil.showAnErrorOccurredDialog(getActivity(), "Downloading dependency '" + dep + "' failed: " + Log.getStackTraceString(e));
                    });
                }

                @Override
                public void unzipping(@NonNull Artifact artifact) {
                    handler.post(new SetTextRunnable("Unzipping dependency " + artifact));
                }

                @Override
                public void dexing(@NonNull Artifact dep) {
                    handler.post(new SetTextRunnable("Dexing dependency " + dep));
                }

                @Override
                public void dexingFailed(@NonNull Artifact dependency, @NonNull Exception e) {
                    handler.post(() -> {
                        setDownloadState(false);
                        SketchwareUtil.showAnErrorOccurredDialog(getActivity(), "Dexing dependency '" + dependency + "' failed: " + Log.getStackTraceString(e));
                    });
                }

                @Override
                public void onTaskCompleted(@NonNull List<String> dependencies) {
                    handler.post(() -> {
                        SketchwareUtil.toast("Library downloaded successfully");
                        if (!notAssociatedWithProject) {
                            new SetTextRunnable("Adding dependencies to project...").run();
                            var fileContent = FileUtil.readFile(local_lib_file);
                            var enabledLibs = gson.fromJson(fileContent, Helper.TYPE_MAP_LIST);
                            enabledLibs.addAll(dependencies.stream()
                                    .map(name -> ManageLocalLibraryActivity.createLibraryMap(name, dependencyName))
                                    .toList());
                            FileUtil.writeFile(local_lib_file, gson.toJson(enabledLibs));
                        }
                        if (getActivity() == null) return;
                        dismiss();
                        if (listener != null) listener.onTaskCompleted();
                    });
                }
            });
        });
    }

    private void setDownloadState(boolean downloading) {
        binding.downloadButton.setEnabled(!downloading);
        binding.dependencyInput.setEnabled(!downloading);
        binding.downloadButton.setText(downloading ? "Downloading..." : "Download");
        binding.downloadProgressCardView.setVisibility(downloading ? View.VISIBLE : View.GONE);
        binding.hintText.setVisibility(downloading ? View.GONE : View.VISIBLE);
        binding.skipSubDependenciesCheckBox.setVisibility(downloading ? View.GONE : View.VISIBLE);
        setCancelable(!downloading);
    }
}

