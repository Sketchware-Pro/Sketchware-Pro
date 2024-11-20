package mod.elfilibustero.sketch.lib.ui;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import a.a.a.aB;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.util.Helper;

public class SketchFilePickerDialog extends aB {
    private final Activity activity;
    private String currentPath;
    private OnFileSelectedListener onFileSelectedListener;
    private File filePath;

    private FileAdapter adapter;

    private final List<File> fileList = new ArrayList<>();
    private final List<String> extensions = new ArrayList<>();

    public interface OnFileSelectedListener {
        void onFileSelected(DialogInterface dialog, File file);
    }

    public interface OnItemClickListener {
        void onItemClick(File file);
    }

    public SketchFilePickerDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public SketchFilePickerDialog(Activity activity, String initialPath) {
        super(activity);
        this.activity = activity;
        currentPath = initialPath;
    }

    public SketchFilePickerDialog setFilePath(String initialPath) {
        currentPath = initialPath;
        return this;
    }

    public SketchFilePickerDialog allowExtension(String extension) {
        extensions.add(extension);
        return this;
    }

    public SketchFilePickerDialog setOnFileSelectedListener(OnFileSelectedListener listener) {
        onFileSelectedListener = listener;
        return this;
    }

    public void backPressed(DialogInterface dialog) {
        if (currentPath.equals(FileUtil.getExternalStorageDir())) {
            dialog.dismiss();
        } else if (adapter != null) {
            var lastPath = currentPath.substring(0, currentPath.lastIndexOf(File.separator));
            var currentDirectory = new File(lastPath);
            if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
                currentDirectory = new File(FileUtil.getExternalStorageDir());
                currentPath = currentDirectory.getAbsolutePath();
            } else {
                currentPath = lastPath;
            }
            loadFiles(currentDirectory, fileList, adapter);
        }
    }

    public void setTitle(String title) {
        b(title);
    }

    public void setMessage(String message) {
        a(message);
    }

    public void init() {
        var recyclerView = new RecyclerView(activity);
        recyclerView.setPadding(
            (int) getDip(20),
            (int) getDip(8),
            (int) getDip(20),
            (int) getDip(0)
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new FileAdapter(fileList);
        recyclerView.setAdapter(adapter);
        a(recyclerView);
        var currentDirectory = new File(currentPath);
        if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
            currentDirectory = new File(FileUtil.getExternalStorageDir());
            currentPath = currentDirectory.getAbsolutePath();
        }
        loadFiles(currentDirectory, fileList, adapter);
        adapter.setOnItemClickListener(file -> {
            filePath = file;
            if (file.isDirectory()) {
                currentPath = file.getAbsolutePath();
                loadFiles(file, fileList, adapter);
            }
        });
        b("Select", v -> {
            if (filePath != null && !filePath.isDirectory()) {
                if (onFileSelectedListener != null) {
                    onFileSelectedListener.onFileSelected(this, filePath);
                }
            }
        });
        a("Cancel", Helper.getDialogDismissListener(this));
    }

    private void loadFiles(File directory, List<File> fileList, FileAdapter adapter) {
        fileList.clear();
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.sort(files, (file1, file2) -> {
                if (file1.isDirectory() && !file2.isDirectory()) {
                    return -1;
                } else if (!file1.isDirectory() && file2.isDirectory()) {
                    return 1;
                } else {
                    return file1.getName().compareToIgnoreCase(file2.getName());
                }
            });
            for (File file : files) {
                if (isFileExtensionAllowed(file)) {
                    fileList.add(file);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isFileExtensionAllowed(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (extensions.isEmpty()) {
            return true;
        }
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            String extension = fileName.substring(dotIndex + 1).toLowerCase();
            return extensions.contains(extension);
        }
        return false;
    }

    private static class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
        private final List<File> fileList;
        private OnItemClickListener itemClickListener;
        private int lastCheckedPosition = -1;

        public FileAdapter(List<File> fileList) {
            this.fileList = fileList;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.itemClickListener = listener;
        }

        @NonNull
        @Override
        public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_file_picker_list_item, parent, false);
            return new FileViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
            var file = fileList.get(position);
            holder.name.setText(file.getName());
            holder.setFileImageView(file);
            if (!file.isDirectory() && position == lastCheckedPosition) {
                holder.imageSelected.setVisibility(View.VISIBLE);
            } else {
                holder.imageSelected.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }

        public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final TextView name;
            public final FrameLayout fileArea;
            public final ImageView imageSelected;
            public final ImageView fileIconImageView;

            public FileViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.tv_file_name);
                fileArea = itemView.findViewById(R.id.file_area);
                imageSelected = itemView.findViewById(R.id.img_selected);
                fileIconImageView = new ImageView(itemView.getContext());
                fileArea.addView(fileIconImageView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                var file = fileList.get(getAdapterPosition());
                if (!file.isDirectory()) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                } else {
                    lastCheckedPosition = -1;
                }
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(file);
                }
            }

            public void setFileImageView(File file) {
                if (file.isDirectory()) {
                    fileIconImageView.setImageResource(R.drawable.ic_folder_48dp);
                } else {
                    fileIconImageView.setImageResource(R.drawable.file_48_blue);
                }
            }
        }
    }
}
