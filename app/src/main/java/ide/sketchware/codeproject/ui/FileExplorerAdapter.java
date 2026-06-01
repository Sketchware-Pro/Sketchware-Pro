package ide.sketchware.codeproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ide.sketchware.R;
import ide.sketchware.databinding.ItemFileExplorerBinding;

public class FileExplorerAdapter extends RecyclerView.Adapter<FileExplorerAdapter.ViewHolder> {

    private final List<FileNode> displayedNodes = new ArrayList<>();
    private final OnFileClickListener listener;
    private OnFileLongClickListener longClickListener;

    public FileExplorerAdapter(File rootDir, OnFileClickListener listener) {
        this.listener = listener;
        if (rootDir != null && rootDir.exists() && rootDir.isDirectory()) {
            FileNode root = buildTree(rootDir, 0);
            if (root != null && root.children != null) {
                displayedNodes.addAll(root.children);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFileExplorerBinding binding = ItemFileExplorerBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileNode node = displayedNodes.get(position);
        int padding = node.level * (int) (16 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
        holder.binding.getRoot().setPaddingRelative(
                padding + (int) (12 * holder.itemView.getContext().getResources().getDisplayMetrics().density),
                holder.binding.getRoot().getPaddingTop(),
                holder.binding.getRoot().getPaddingEnd(),
                holder.binding.getRoot().getPaddingBottom());

        holder.binding.fileName.setText(node.file.getName());

        if (node.isDirectory) {
            holder.binding.icon.setImageResource(node.isExpanded
                    ? R.drawable.ic_folder_open_24
                    : R.drawable.ic_folder_24);
        } else {
            String name = node.file.getName().toLowerCase();
            if (name.endsWith(".java")) {
                holder.binding.icon.setImageResource(R.drawable.ic_file_java_24);
            } else if (name.endsWith(".kt")) {
                holder.binding.icon.setImageResource(R.drawable.ic_file_kotlin_24);
            } else if (name.endsWith(".xml")) {
                holder.binding.icon.setImageResource(R.drawable.ic_file_xml_24);
            } else {
                holder.binding.icon.setImageResource(R.drawable.ic_file_24);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAbsoluteAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            FileNode clickedNode = displayedNodes.get(pos);
            if (clickedNode.isDirectory) {
                toggleExpand(clickedNode, pos);
            } else {
                if (listener != null) {
                    listener.onFileClick(clickedNode.file);
                }
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            int pos = holder.getAbsoluteAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return false;
            FileNode longClickedNode = displayedNodes.get(pos);
            if (longClickListener != null) {
                longClickListener.onFileLongClick(longClickedNode.file, v);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return displayedNodes.size();
    }

    private void toggleExpand(FileNode node, int position) {
        if (node.isExpanded) {
            int count = countVisibleDescendants(node);
            displayedNodes.subList(position + 1, position + 1 + count).clear();
            node.isExpanded = false;
            collapseNestedState(node);
            notifyItemChanged(position);
            notifyItemRangeRemoved(position + 1, count);
        } else {
            node.isExpanded = true;
            if (node.children != null) {
                displayedNodes.addAll(position + 1, node.children);
                notifyItemChanged(position);
                notifyItemRangeInserted(position + 1, node.children.size());
            }
        }
    }

    /**
     * Counts total visible descendants (those currently in displayedNodes)
     * without modifying the list.
     */
    private int countVisibleDescendants(FileNode node) {
        int count = 0;
        if (node.children != null) {
            for (FileNode child : node.children) {
                count++;
                if (child.isExpanded && child.isDirectory) {
                    count += countVisibleDescendants(child);
                }
            }
        }
        return count;
    }

    /**
     * Recursively resets isExpanded on nested directories so their
     * logical state matches the UI (all collapsed).
     */
    private void collapseNestedState(FileNode node) {
        if (node.children != null) {
            for (FileNode child : node.children) {
                if (child.isDirectory && child.isExpanded) {
                    child.isExpanded = false;
                    collapseNestedState(child);
                }
            }
        }
    }

    private FileNode buildTree(File file, int level) {
        FileNode node = new FileNode();
        node.file = file;
        node.level = level;
        node.isDirectory = file.isDirectory();
        node.isExpanded = false;

        if (node.isDirectory) {
            node.children = new ArrayList<>();
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File a, File b) {
                        if (a.isDirectory() && !b.isDirectory()) return -1;
                        if (!a.isDirectory() && b.isDirectory()) return 1;
                        return a.getName().compareToIgnoreCase(b.getName());
                    }
                });
                for (File child : files) {
                    node.children.add(buildTree(child, level + 1));
                }
            }
        }
        return node;
    }

    public void refresh(File rootDir) {
        displayedNodes.clear();
        if (rootDir != null && rootDir.exists() && rootDir.isDirectory()) {
            FileNode root = buildTree(rootDir, 0);
            if (root != null && root.children != null) {
                displayedNodes.addAll(root.children);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnFileClickListener {
        void onFileClick(File file);
    }

    public interface OnFileLongClickListener {
        void onFileLongClick(File file, View view);
    }

    public void setOnFileLongClickListener(OnFileLongClickListener listener) {
        this.longClickListener = listener;
    }

    static class FileNode {
        File file;
        int level;
        boolean isDirectory;
        boolean isExpanded;
        List<FileNode> children;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemFileExplorerBinding binding;

        ViewHolder(ItemFileExplorerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
