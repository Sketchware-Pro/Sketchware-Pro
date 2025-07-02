package dev.aldi.sayuti.editor.manage;

import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.createLibraryMap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;

import org.cosmic.ide.dependency.resolver.api.Artifact;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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

    private LibraryDownloadAdapter libraryAdapter;
    private int totalLibraries = 0;
    private int completedLibraries = 0;

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

        libraryAdapter = new LibraryDownloadAdapter();
        binding.librariesRecyclerView.setAdapter(libraryAdapter);
    }

    public void setOnLibraryDownloadedTask(OnLibraryDownloadedTask onLibraryDownloadedTask) {
        this.onLibraryDownloadedTask = onLibraryDownloadedTask;
    }

    private boolean isNetworkAvailable() {
        var connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            var network = connectivityManager.getActiveNetwork();
            if (network == null) return false;

            var capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        } else {
            var activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }

    private void initDownloadFlow() {
        if (!isNetworkAvailable()) {
            SketchwareUtil.showAnErrorOccurredDialog(getActivity(),
                    "No internet connection available");
            return;
        }
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
        binding.dependencyInfo.setVisibility(View.GONE);

        binding.linearProgressIndicator.setIndeterminate(true);
        binding.linearProgressIndicator.setVisibility(View.VISIBLE);

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
            try {
                BuiltInLibraries.maybeExtractAndroidJar((message, progress) -> handler.post(new SetTextRunnable(message)));
                BuiltInLibraries.maybeExtractCoreLambdaStubsJar();
                resolver.resolveDependency(new DependencyResolver.DependencyResolverCallback() {
                    @Override
                    public void onResolving(@NonNull Artifact artifact, @NonNull Artifact dependency) {
                        handler.post(() -> {
                            libraryAdapter.updateLibraryProgress(dependency.toString(),
                                    LibraryDownloadItem.Status.SEARCHING, "", 0, 0);
                            binding.librariesRecyclerView.setVisibility(View.VISIBLE);

                            binding.cbSkipSubdependencies.setVisibility(View.GONE);
                            binding.btnDownload.setVisibility(View.GONE);
                        });
                    }

                    @Override
                    public void onResolutionComplete(@NonNull Artifact dep) {
                        handler.post(() -> {
                            if (totalLibraries == 0) {
                                totalLibraries++;
                                updateLinearProgress();
                            }

                            libraryAdapter.updateLibraryProgress(dep.toString(),
                                    LibraryDownloadItem.Status.FOUND, "", 0, 0);
                            binding.librariesRecyclerView.setVisibility(View.VISIBLE);
                        });
                    }

                    @Override
                    public void onArtifactFound(@NonNull Artifact dep) {
                        handler.post(() -> {
                            totalLibraries++;
                            libraryAdapter.updateLibraryProgress(dep.toString(),
                                    LibraryDownloadItem.Status.FOUND, "", 0, 0);
                            binding.librariesRecyclerView.setVisibility(View.VISIBLE);
                            updateLinearProgress();
                        });
                    }

                    @Override
                    public void onArtifactNotFound(@NonNull Artifact dep) {
                        handler.post(() -> {
                            binding.linearProgressIndicator.setVisibility(View.GONE);
                            binding.dependencyInfo.setVisibility(View.VISIBLE);
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
                        handler.post(() -> {
                            libraryAdapter.updateLibraryProgress(dep.toString(),
                                    LibraryDownloadItem.Status.DOWNLOADING, "Starting download...", 0, 0);
                        });
                    }

                    @Override
                    public void onDownloadEnd(@NonNull Artifact dep) {
                        handler.post(() -> {
                            completedLibraries++;
                            libraryAdapter.updateLibraryProgress(dep.toString(),
                                    LibraryDownloadItem.Status.COMPLETED, "Completed", 0, 0);
                            updateLinearProgress();
                        });
                    }

                    @Override
                    public void onDownloadError(@NonNull Artifact dep, @NonNull Throwable e) {
                        handler.post(() -> {
                            completedLibraries++;
                            libraryAdapter.updateLibraryProgress(dep.toString(),
                                    LibraryDownloadItem.Status.ERROR, "Error: " + e.getMessage(), 0, 0);
                            updateLinearProgress();
                        });
                    }

                    @Override
                    public void unzipping(@NonNull Artifact artifact) {
                        handler.post(new SetTextRunnable("Unzipping dependency " + artifact));
                    }

                    @Override
                    public void dexing(@NonNull Artifact dep) {
                        handler.post(() -> {
                            completedLibraries++;
                            libraryAdapter.updateLibraryProgress(dep.toString(),
                                    LibraryDownloadItem.Status.COMPLETED, "Indexed", 0, 0);
                            updateLinearProgress();
                        });
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
                            binding.linearProgressIndicator.setIndeterminate(true);
                            binding.dependencyInfo.setVisibility(View.VISIBLE);
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

                    @Override
                    public void onDownloadProgress(@NonNull Artifact artifact, long bytesDownloaded, long totalBytes) {
                        handler.post(() -> {
                            String progressText = formatBytes(bytesDownloaded) + " / " + formatBytes(totalBytes);
                            libraryAdapter.updateLibraryProgress(artifact.toString(),
                                    LibraryDownloadItem.Status.DOWNLOADING, progressText, bytesDownloaded, totalBytes);
                        });
                    }
                });
            } catch (Exception e) {
                handler.post(() -> {
                    binding.linearProgressIndicator.setVisibility(View.GONE);
                    binding.dependencyInfo.setVisibility(View.VISIBLE);
                    setDownloadState(false);

                    String errorMessage = "Network error occurred";
                    if (e.getCause() instanceof ConnectException) {
                        errorMessage = "No internet connection available";
                    } else if (e.getCause() instanceof SocketTimeoutException) {
                        errorMessage = "Connection timeout";
                    }

                    SketchwareUtil.showAnErrorOccurredDialog(getActivity(), "Failed to resolve dependency: " + errorMessage);
                });
            }
        });
    }

    private void setDownloadState(boolean downloading) {
        binding.btnCancel.setVisibility(downloading ? View.GONE : View.VISIBLE);
        binding.btnDownload.setEnabled(!downloading);
        binding.dependencyInput.setEnabled(!downloading);
        binding.cbSkipSubdependencies.setEnabled(!downloading);
        setCancelable(!downloading);

        if (!downloading) {
            binding.linearProgressIndicator.setVisibility(View.GONE);
            binding.dependencyInfo.setVisibility(View.VISIBLE);
            binding.dependencyInfo.setText(R.string.local_library_manager_dependency_info);

            binding.cbSkipSubdependencies.setVisibility(View.VISIBLE);
            binding.btnDownload.setVisibility(View.VISIBLE);

            totalLibraries = 0;
            completedLibraries = 0;
        }
    }

    public interface OnLibraryDownloadedTask {
        void invoke();
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }

    public static class LibraryDownloadItem {
        public enum Status {
            SEARCHING, FOUND, DOWNLOADING, INDEXING, COMPLETED, ERROR
        }

        private String name;
        private Status status;
        private String progressText;
        private long bytesDownloaded;
        private long totalBytes;

        public LibraryDownloadItem(String name) {
            this.name = name;
            this.status = Status.SEARCHING;
            this.progressText = "";
        }

        public String getName() { return name; }
        public Status getStatus() { return status; }
        public void setStatus(Status status) { this.status = status; }
        public String getProgressText() { return progressText; }
        public void setProgressText(String progressText) { this.progressText = progressText; }
        public long getBytesDownloaded() { return bytesDownloaded; }
        public void setBytesDownloaded(long bytesDownloaded) { this.bytesDownloaded = bytesDownloaded; }
        public long getTotalBytes() { return totalBytes; }
        public void setTotalBytes(long totalBytes) { this.totalBytes = totalBytes; }
    }

    private class LibraryDownloadAdapter extends RecyclerView.Adapter<LibraryDownloadAdapter.ViewHolder> {
        private List<LibraryDownloadItem> libraries = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.library_download_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            LibraryDownloadItem item = libraries.get(position);
            holder.libraryName.setText(item.getName());
            holder.libraryProgressText.setText(item.getProgressText());

            holder.libraryProgressText.setVisibility(
                    item.getProgressText().isEmpty() ? View.GONE : View.VISIBLE);

            configureProgressBar(holder.circularProgress, item);
        }

        @Override
        public int getItemCount() {
            return libraries.size();
        }

        private void configureProgressBar(CircularProgressIndicator progressBar, LibraryDownloadItem item) {
            switch (item.getStatus()) {
                case SEARCHING:
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(View.VISIBLE);
                    break;

                case FOUND:
                    progressBar.setIndeterminate(false);
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.VISIBLE);
                    break;

                case DOWNLOADING:
                    progressBar.setIndeterminate(false);
                    if (item.getTotalBytes() > 0) {
                        int progress = (int) ((item.getBytesDownloaded() * 100) / item.getTotalBytes());
                        progressBar.setProgress(progress);
                    } else {
                        progressBar.setProgress(0);
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    break;

                case INDEXING:
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(View.VISIBLE);
                    break;

                case ERROR:
                    progressBar.setIndeterminate(false);
                    progressBar.setProgress(0);
                    progressBar.setIndicatorColor(MaterialColors.getColor(progressBar, R.attr.colorError));
                    progressBar.setVisibility(View.VISIBLE);
                    break;

                case COMPLETED:
                    progressBar.setIndeterminate(false);
                    progressBar.setProgress(100);
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            }
        }

        public void updateLibraryProgress(String name, LibraryDownloadItem.Status status, String progressText, long bytesDownloaded, long totalBytes) {
            for (int i = 0; i < libraries.size(); i++) {
                LibraryDownloadItem item = libraries.get(i);
                if (item.getName().equals(name)) {
                    if (item.getStatus() == LibraryDownloadItem.Status.COMPLETED &&
                            status != LibraryDownloadItem.Status.COMPLETED) {
                        return;
                    }

                    item.setStatus(status);
                    item.setProgressText(progressText);
                    item.setBytesDownloaded(bytesDownloaded);
                    item.setTotalBytes(totalBytes);
                    notifyItemChanged(i);
                    return;
                }
            }

            LibraryDownloadItem newItem = new LibraryDownloadItem(name);
            newItem.setStatus(status);
            newItem.setProgressText(progressText);
            newItem.setBytesDownloaded(bytesDownloaded);
            newItem.setTotalBytes(totalBytes);
            libraries.add(newItem);
            notifyItemInserted(libraries.size() - 1);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView libraryName, libraryProgressText;
            CircularProgressIndicator circularProgress;

            ViewHolder(View itemView) {
                super(itemView);
                libraryName = itemView.findViewById(R.id.library_name);
                libraryProgressText = itemView.findViewById(R.id.library_progress_text);
                circularProgress = itemView.findViewById(R.id.library_progress_circular);
            }
        }
    }

    private void updateLinearProgress() {
        if (totalLibraries > 0) {
            binding.linearProgressIndicator.setIndeterminate(false);
            binding.linearProgressIndicator.setMax(totalLibraries);
            binding.linearProgressIndicator.setProgress(completedLibraries);
        }
    }
}