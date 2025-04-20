package dev.aldi.sayuti.editor.manage;

import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.createLibraryMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.cosmic.ide.dependency.resolver.api.Artifact;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuiltInLibraries;
import mod.pranav.dependency.resolver.DependencyResolver;
import pro.sketchware.R;
import pro.sketchware.databinding.LibraryDownloaderDialogBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class LibraryDownloaderDialogFragment extends BottomSheetDialogFragment {
    private LibraryDownloaderDialogBinding binding;

    private final Gson gson = new Gson();
    private BuildSettings buildSettings;

    private boolean notAssociatedWithProject;
    private String dependencyName;
    private String localLibFile;
    private OnLibraryDownloadedTask onLibraryDownloadedTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LibraryDownloaderDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) return;

        notAssociatedWithProject = getArguments().getBoolean("notAssociatedWithProject", false);
        buildSettings = (BuildSettings) getArguments().getSerializable("buildSettings");
        localLibFile = getArguments().getString("localLibFile");

        binding.btnCancel.setOnClickListener(v -> dismiss());
        binding.btnDownload.setOnClickListener(v -> initDownloadFlow());
    }

    public void setOnLibraryDownloadedTask(OnLibraryDownloadedTask onLibraryDownloadedTask) {
        this.onLibraryDownloadedTask = onLibraryDownloadedTask;
    }

    private void initDownloadFlow() {
        dependencyName = Helper.getText(binding.dependencyInput);
        if (dependencyName == null || dependencyName.isEmpty()) {
            binding.dependencyInputLayout.setError("Please enter a dependency");
            binding.dependencyInputLayout.setErrorEnabled(true);
            return;
        }

        var parts = dependencyName.split(":");
        if (parts.length != 3) {
            binding.dependencyInputLayout.setError("Invalid dependency format");
            binding.dependencyInputLayout.setErrorEnabled(true);
            return;
        }

        binding.dependencyInfo.setText("Looking for dependency...");
        binding.dependencyInputLayout.setErrorEnabled(false);
        setDownloadState(true);

        var group = parts[0];
        var artifact = parts[1];
        var version = parts[2];
        var resolver = new DependencyResolver(group, artifact, version, binding.cbSkipSubdependencies.isChecked(), buildSettings);
        var handler = new Handler(Looper.getMainLooper());

        class SetTextRunnable implements Runnable {
            private final String text;

            SetTextRunnable(String text) {
                this.text = text;
            }

            @Override
            public void run() {
                binding.dependencyInfo.setText(text);
            }
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            BuiltInLibraries.maybeExtractAndroidJar((message, progress) -> handler.post(new SetTextRunnable(message)));
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
                            var fileContent = FileUtil.readFile(localLibFile);
                            var enabledLibs = gson.fromJson(fileContent, Helper.TYPE_MAP_LIST);
                            enabledLibs.addAll(dependencies.stream()
                                    .map(name -> createLibraryMap(name, dependencyName))
                                    .collect(Collectors.toList()));
                            FileUtil.writeFile(localLibFile, gson.toJson(enabledLibs));
                        }
                        if (getActivity() == null) return;
                        dismiss();
                        if (onLibraryDownloadedTask != null) onLibraryDownloadedTask.invoke();
                    });
                }
            });
        });
    }

    private void setDownloadState(boolean downloading) {
        binding.btnCancel.setVisibility(downloading ? View.GONE : View.VISIBLE);
        binding.btnDownload.setEnabled(!downloading);
        binding.dependencyInput.setEnabled(!downloading);
        binding.cbSkipSubdependencies.setEnabled(!downloading);
        setCancelable(!downloading);

        if (!downloading) {
            binding.dependencyInfo.setText(R.string.local_library_manager_dependency_info);
        }
    }

    public interface OnLibraryDownloadedTask {
        void invoke();
    }
}
