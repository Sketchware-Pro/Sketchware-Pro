package mod.hey.studios.lib.prdownloader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PRDownloader {

    private PRDownloader() {
    }

    public static void initialize(Context context) {
        initialize(context, PRDownloaderConfig.newBuilder().build());
    }

    public static void initialize(Context context, PRDownloaderConfig config) {
        ComponentHolder.getInstance().init(context, config);
        DownloadRequestQueue.initialize();
    }

    public static DownloadRequestBuilder download(String url, String dirPath, String fileName) {
        return new DownloadRequestBuilder(url, dirPath, fileName);
    }

    public static void pause(int downloadId) {
        DownloadRequestQueue.getInstance().pause(downloadId);
    }

    public static void resume(int downloadId) {
        DownloadRequestQueue.getInstance().resume(downloadId);
    }

    public static void cancel(int downloadId) {
        DownloadRequestQueue.getInstance().cancel(downloadId);
    }

    public static void cancel(Object tag) {
        DownloadRequestQueue.getInstance().cancel(tag);
    }

    public static void cancelAll() {
        DownloadRequestQueue.getInstance().cancelAll();
    }

    public static Status getStatus(int downloadId) {
        return DownloadRequestQueue.getInstance().getStatus(downloadId);
    }

    public static void cleanUp(int days) {
        Utils.deleteUnwantedModelsAndTempFiles(days);
    }

    public static void shutDown() {
        Core.shutDown();
    }


    public enum Status {
        QUEUED,
        RUNNING,
        PAUSED,
        COMPLETED,
        CANCELLED,
        UNKNOWN
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        IMMEDIATE
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public interface OnDownloadListener {
        void onDownloadComplete();

        void onError(Error error);
    }


    public interface OnPauseListener {
        void onPause();
    }


    public interface OnProgressListener {
        void onProgress(Progress progress);
    }

    public interface OnStartOrResumeListener {
        void onStartOrResume();
    }

    public interface HttpClient extends Cloneable {
        HttpClient clone();

        void connect(DownloadRequest request) throws IOException;

        int getResponseCode() throws IOException;

        InputStream getInputStream() throws IOException;

        long getContentLength();

        String getResponseHeader(String name);

        void close();
    }

    public interface RequestBuilder {
        RequestBuilder setHeader(String name, String value);

        RequestBuilder setPriority(Priority priority);

        RequestBuilder setTag(Object tag);

        RequestBuilder setReadTimeout(int readTimeout);

        RequestBuilder setConnectTimeout(int connectTimeout);

        RequestBuilder setUserAgent(String userAgent);
    }

    public interface ExecutorSupplier {
        DownloadExecutor forDownloadTasks();

        Executor forBackgroundTasks();

        Executor forMainThreadTasks();
    }

    public interface FileDownloadOutputStream {
        void write(byte[] b, int off, int len) throws IOException;

        void flushAndSync() throws IOException;

        void close() throws IOException;

        void seek(long offset) throws IOException, IllegalAccessException;

        void setLength(final long newLength) throws IOException, IllegalAccessException;
    }

    public interface DbHelper {
        DownloadModel find(int id);

        void insert(DownloadModel model);

        void update(DownloadModel model);

        void updateProgress(int id, long downloadedBytes, long lastModifiedAt);

        void remove(int id);

        List<DownloadModel> getUnwantedModels(int days);

        void clear();
    }

    public static class Response {

        private Error error;
        private boolean isSuccessful;
        private boolean isPaused;
        private boolean isCancelled;

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }

        public boolean isPaused() {
            return isPaused;
        }

        public void setPaused(boolean paused) {
            isPaused = paused;
        }

        public boolean isCancelled() {
            return isCancelled;
        }

        public void setCancelled(boolean cancelled) {
            isCancelled = cancelled;
        }
    }

    public static class Progress implements Serializable {

        public long currentBytes;
        public long totalBytes;

        public Progress(long currentBytes, long totalBytes) {
            this.currentBytes = currentBytes;
            this.totalBytes = totalBytes;
        }

        @Override
        public String toString() {
            return "Progress{" +
                    "currentBytes=" + currentBytes +
                    ", totalBytes=" + totalBytes +
                    '}';
        }
    }

    public static class PRDownloaderConfig {

        private int readTimeout;
        private int connectTimeout;
        private String userAgent;
        private HttpClient httpClient;
        private boolean databaseEnabled;

        private PRDownloaderConfig(Builder builder) {
            readTimeout = builder.readTimeout;
            connectTimeout = builder.connectTimeout;
            userAgent = builder.userAgent;
            httpClient = builder.httpClient;
            databaseEnabled = builder.databaseEnabled;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public HttpClient getHttpClient() {
            return httpClient;
        }

        public void setHttpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
        }

        public boolean isDatabaseEnabled() {
            return databaseEnabled;
        }

        public void setDatabaseEnabled(boolean databaseEnabled) {
            this.databaseEnabled = databaseEnabled;
        }

        public static class Builder {

            int readTimeout = Constants.DEFAULT_READ_TIMEOUT_IN_MILLS;
            int connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT_IN_MILLS;
            String userAgent = Constants.DEFAULT_USER_AGENT;
            HttpClient httpClient = new DefaultHttpClient();
            boolean databaseEnabled = false;

            public Builder setReadTimeout(int readTimeout) {
                this.readTimeout = readTimeout;
                return this;
            }

            public Builder setConnectTimeout(int connectTimeout) {
                this.connectTimeout = connectTimeout;
                return this;
            }

            public Builder setUserAgent(String userAgent) {
                this.userAgent = userAgent;
                return this;
            }

            public Builder setHttpClient(HttpClient httpClient) {
                this.httpClient = httpClient;
                return this;
            }

            public Builder setDatabaseEnabled(boolean databaseEnabled) {
                this.databaseEnabled = databaseEnabled;
                return this;
            }

            public PRDownloaderConfig build() {
                return new PRDownloaderConfig(this);
            }
        }
    }

    public static final class Constants {

        public static final int UPDATE = 0x01;
        public static final String RANGE = "Range";
        public static final String ETAG = "ETag";
        public static final String USER_AGENT = "User-Agent";
        public static final String DEFAULT_USER_AGENT = "Gymkhana-Studio";
        public static final int DEFAULT_READ_TIMEOUT_IN_MILLS = 20_000;
        public static final int DEFAULT_CONNECT_TIMEOUT_IN_MILLS = 20_000;
        public static final int HTTP_RANGE_NOT_SATISFIABLE = 416;
        public static final int HTTP_TEMPORARY_REDIRECT = 307;
        public static final int HTTP_PERMANENT_REDIRECT = 308;

        private Constants() {
        }
    }

    public static class Error {

        private boolean isServerError;
        private boolean isConnectionError;

        public boolean isServerError() {
            return isServerError;
        }

        public void setServerError(boolean serverError) {
            isServerError = serverError;
        }

        public boolean isConnectionError() {
            return isConnectionError;
        }

        public void setConnectionError(boolean connectionError) {
            isConnectionError = connectionError;
        }
    }

    public static class ProgressHandler extends Handler {

        private final OnProgressListener listener;

        public ProgressHandler(OnProgressListener listener) {
            super(Looper.getMainLooper());
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constants.UPDATE) {
                if (listener != null) {
                    final Progress progress = (Progress) msg.obj;
                    listener.onProgress(progress);
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }

    public static class DefaultHttpClient implements HttpClient {

        private URLConnection connection;

        public DefaultHttpClient() {
        }

        @Override
        public HttpClient clone() {
            return new DefaultHttpClient();
        }

        @Override
        public void connect(DownloadRequest request) throws IOException {
            connection = new URL(request.getUrl()).openConnection();
            connection.setReadTimeout(request.getReadTimeout());
            connection.setConnectTimeout(request.getConnectTimeout());

            final String range = String.format(
                    Locale.ENGLISH,
                    "bytes=%d-",
                    request.getDownloadedBytes()
            );

            connection.addRequestProperty(Constants.RANGE, range);
            connection.addRequestProperty(Constants.USER_AGENT, request.getUserAgent());

            addHeaders(request);
            connection.connect();
        }

        @Override
        public int getResponseCode() throws IOException {
            int responseCode = 0;

            if (connection instanceof HttpURLConnection) {
                responseCode = ((HttpURLConnection) connection).getResponseCode();
            }

            return responseCode;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return connection.getInputStream();
        }

        @Override
        public long getContentLength() {
            String length = connection.getHeaderField("Content-Length");

            try {
                return Long.parseLong(length);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        @Override
        public String getResponseHeader(String name) {
            return connection.getHeaderField(name);
        }

        @Override
        public void close() {
        }

        private void addHeaders(DownloadRequest request) {
            final HashMap<String, List<String>> headers = request.getHeaders();

            if (headers != null) {
                Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
                for (Map.Entry<String, List<String>> entry : entries) {
                    String name = entry.getKey();
                    List<String> list = entry.getValue();

                    if (list != null) {
                        for (String value : list) {
                            connection.addRequestProperty(name, value);
                        }
                    }
                }
            }
        }

    }

    public static class DownloadRequest {

        private final HashMap<String, List<String>> headerMap;
        private Priority priority;
        private Object tag;
        private String url;
        private String dirPath;
        private String fileName;
        private int sequenceNumber;
        private Future<?> future;
        private long downloadedBytes;
        private long totalBytes;
        private int readTimeout;
        private int connectTimeout;
        private String userAgent;
        private OnProgressListener onProgressListener;
        private OnDownloadListener onDownloadListener;
        private OnStartOrResumeListener onStartOrResumeListener;
        private OnPauseListener onPauseListener;
        private OnCancelListener onCancelListener;
        private int downloadId;
        private Status status;

        DownloadRequest(DownloadRequestBuilder builder) {
            url = builder.url;
            dirPath = builder.dirPath;
            fileName = builder.fileName;
            headerMap = builder.headerMap;
            priority = builder.priority;
            tag = builder.tag;
            readTimeout =
                    builder.readTimeout != 0 ?
                            builder.readTimeout :
                            getReadTimeoutFromConfig();
            connectTimeout =
                    builder.connectTimeout != 0 ?
                            builder.connectTimeout :
                            getConnectTimeoutFromConfig();
            userAgent = builder.userAgent;
        }

        public Priority getPriority() {
            return priority;
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getSequenceNumber() {
            return sequenceNumber;
        }

        public void setSequenceNumber(int sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }

        public HashMap<String, List<String>> getHeaders() {
            return headerMap;
        }

        public Future<?> getFuture() {
            return future;
        }

        public void setFuture(Future<?> future) {
            this.future = future;
        }

        public long getDownloadedBytes() {
            return downloadedBytes;
        }

        public void setDownloadedBytes(long downloadedBytes) {
            this.downloadedBytes = downloadedBytes;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(long totalBytes) {
            this.totalBytes = totalBytes;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public String getUserAgent() {
            if (userAgent == null) {
                userAgent = ComponentHolder.getInstance().getUserAgent();
            }

            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public int getDownloadId() {
            return downloadId;
        }

        public void setDownloadId(int downloadId) {
            this.downloadId = downloadId;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public OnProgressListener getOnProgressListener() {
            return onProgressListener;
        }

        public DownloadRequest setOnProgressListener(OnProgressListener onProgressListener) {
            this.onProgressListener = onProgressListener;
            return this;
        }

        public DownloadRequest setOnStartOrResumeListener(OnStartOrResumeListener onStartOrResumeListener) {
            this.onStartOrResumeListener = onStartOrResumeListener;
            return this;
        }

        public DownloadRequest setOnPauseListener(OnPauseListener onPauseListener) {
            this.onPauseListener = onPauseListener;
            return this;
        }

        public DownloadRequest setOnCancelListener(OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public int start(OnDownloadListener onDownloadListener) {
            this.onDownloadListener = onDownloadListener;
            downloadId = Utils.getUniqueId(url, dirPath, fileName);
            DownloadRequestQueue.getInstance().addRequest(this);
            return downloadId;
        }

        public Response executeSync() {
            downloadId = Utils.getUniqueId(url, dirPath, fileName);
            return new SynchronousCall(this).execute();
        }

        public void deliverError(final Error error) {
            if (status != Status.CANCELLED) {
                Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                        .execute(() -> {
                            if (onDownloadListener != null) {
                                onDownloadListener.onError(error);
                            }
                            finish();
                        });
            }
        }

        public void deliverSuccess() {
            if (status != Status.CANCELLED) {
                setStatus(Status.COMPLETED);
                Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                        .execute(() -> {
                            if (onDownloadListener != null) {
                                onDownloadListener.onDownloadComplete();
                            }
                            finish();
                        });
            }
        }

        public void deliverStartEvent() {
            if (status != Status.CANCELLED) {
                Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                        .execute(() -> {
                            if (onStartOrResumeListener != null) {
                                onStartOrResumeListener.onStartOrResume();
                            }
                        });
            }
        }

        public void deliverPauseEvent() {
            if (status != Status.CANCELLED) {
                Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                        .execute(() -> {
                            if (onPauseListener != null) {
                                onPauseListener.onPause();
                            }
                        });
            }
        }

        private void deliverCancelEvent() {
            Core.getInstance().getExecutorSupplier().forMainThreadTasks()
                    .execute(() -> {
                        if (onCancelListener != null) {
                            onCancelListener.onCancel();
                        }
                    });
        }

        public void cancel() {
            status = Status.CANCELLED;
            if (future != null) {
                future.cancel(true);
            }
            deliverCancelEvent();
            Utils.deleteTempFileAndDatabaseEntryInBackground(Utils.getTempPath(dirPath, fileName), downloadId);
        }

        private void finish() {
            destroy();
            DownloadRequestQueue.getInstance().finish(this);
        }

        private void destroy() {
            onProgressListener = null;
            onDownloadListener = null;
            onStartOrResumeListener = null;
            onPauseListener = null;
            onCancelListener = null;
        }

        private int getReadTimeoutFromConfig() {
            return ComponentHolder.getInstance().getReadTimeout();
        }

        private int getConnectTimeoutFromConfig() {
            return ComponentHolder.getInstance().getConnectTimeout();
        }
    }

    public static class DownloadRequestBuilder implements RequestBuilder {

        String url;
        String dirPath;
        String fileName;
        Priority priority = Priority.MEDIUM;
        Object tag;
        int readTimeout;
        int connectTimeout;
        String userAgent;
        HashMap<String, List<String>> headerMap;

        public DownloadRequestBuilder(String url, String dirPath, String fileName) {
            this.url = url;
            this.dirPath = dirPath;
            this.fileName = fileName;
        }

        @Override
        public DownloadRequestBuilder setHeader(String name, String value) {
            if (headerMap == null) {
                headerMap = new HashMap<>();
            }

            List<String> list = headerMap.get(name);
            if (list == null) {
                list = new ArrayList<>();
                headerMap.put(name, list);
            }

            if (!list.contains(value)) {
                list.add(value);
            }

            return this;
        }

        @Override
        public DownloadRequestBuilder setPriority(Priority priority) {
            this.priority = priority;
            return this;
        }

        @Override
        public DownloadRequestBuilder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public DownloadRequestBuilder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        @Override
        public DownloadRequestBuilder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        @Override
        public DownloadRequestBuilder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public DownloadRequest build() {
            return new DownloadRequest(this);
        }
    }

    public static final class Utils {

        private final static int MAX_REDIRECTION = 10;

        private Utils() {
        }

        public static String getPath(String dirPath, String fileName) {
            return dirPath + File.separator + fileName;
        }

        public static String getTempPath(String dirPath, String fileName) {
            return getPath(dirPath, fileName) + ".temp";
        }

        public static void renameFileName(String oldPath, String newPath) throws IOException {
            final File oldFile = new File(oldPath);
            try {
                final File newFile = new File(newPath);

                if (newFile.exists()) {
                    if (!newFile.delete()) {
                        throw new IOException("Deletion Failed");
                    }
                }

                if (!oldFile.renameTo(newFile)) {
                    throw new IOException("Rename Failed");
                }
            } finally {
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }

        public static void deleteTempFileAndDatabaseEntryInBackground(final String path, final int downloadId) {
            Core.getInstance().getExecutorSupplier().forBackgroundTasks()
                    .execute(() -> {
                        ComponentHolder.getInstance().getDbHelper().remove(downloadId);
                        File file = new File(path);

                        if (file.exists()) {
                            file.delete();
                        }
                    });
        }

        public static void deleteUnwantedModelsAndTempFiles(final int days) {
            Core.getInstance().getExecutorSupplier().forBackgroundTasks()
                    .execute(() -> {
                        List<DownloadModel> models = ComponentHolder.getInstance()
                                .getDbHelper()
                                .getUnwantedModels(days);

                        if (models != null) {
                            for (DownloadModel model : models) {
                                final String tempPath = getTempPath(model.getDirPath(), model.getFileName());
                                ComponentHolder.getInstance().getDbHelper().remove(model.getId());
                                File file = new File(tempPath);

                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        }
                    });
        }

        public static int getUniqueId(String url, String dirPath, String fileName) {
            String string = url + File.separator + dirPath + File.separator + fileName;
            byte[] hash;

            try {
                hash = MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("NoSuchAlgorithmException", e);
            }

            StringBuilder hex = new StringBuilder(hash.length * 2);

            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }

            return hex.toString().hashCode();

        }

        public static HttpClient getRedirectedConnectionIfAny(HttpClient httpClient,
                                                              DownloadRequest request)
                throws IOException, IllegalAccessException {
            int redirectTimes = 0;
            int code = httpClient.getResponseCode();
            String location = httpClient.getResponseHeader("Location");
            while (isRedirection(code)) {
                if (location == null) {
                    throw new IllegalAccessException("Location is null");
                }
                httpClient.close();
                request.setUrl(location);
                httpClient = ComponentHolder.getInstance().getHttpClient();
                httpClient.connect(request);
                code = httpClient.getResponseCode();
                location = httpClient.getResponseHeader("Location");
                redirectTimes++;
                if (redirectTimes >= MAX_REDIRECTION) {
                    throw new IllegalAccessException("Max redirection done");
                }
            }
            return httpClient;
        }

        private static boolean isRedirection(int code) {
            return code == HttpURLConnection.HTTP_MOVED_PERM
                    || code == HttpURLConnection.HTTP_MOVED_TEMP
                    || code == HttpURLConnection.HTTP_SEE_OTHER
                    || code == HttpURLConnection.HTTP_MULT_CHOICE
                    || code == Constants.HTTP_TEMPORARY_REDIRECT
                    || code == Constants.HTTP_PERMANENT_REDIRECT;
        }
    }

    public static class Core {

        private static Core instance = null;
        private final ExecutorSupplier executorSupplier;

        private Core() {
            executorSupplier = new DefaultExecutorSupplier();
        }

        public static Core getInstance() {
            if (instance == null) {
                synchronized (Core.class) {
                    if (instance == null) {
                        instance = new Core();
                    }
                }
            }
            return instance;
        }

        public static void shutDown() {
            if (instance != null) {
                instance = null;
            }
        }

        public ExecutorSupplier getExecutorSupplier() {
            return executorSupplier;
        }
    }

    public static class PriorityThreadFactory implements ThreadFactory {

        private final int mThreadPriority;

        PriorityThreadFactory(int threadPriority) {
            mThreadPriority = threadPriority;
        }

        @Override
        public Thread newThread(final Runnable runnable) {
            Runnable wrapperRunnable = () -> {
                try {
                    android.os.Process.setThreadPriority(mThreadPriority);
                } catch (Throwable ignored) {

                }
                runnable.run();
            };
            return new Thread(wrapperRunnable);
        }
    }

    public static class MainThreadExecutor implements Executor {

        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    }

    public static class DefaultExecutorSupplier implements ExecutorSupplier {

        private static final int DEFAULT_MAX_NUM_THREADS = 2 * Runtime.getRuntime().availableProcessors() + 1;
        private final DownloadExecutor networkExecutor;
        private final Executor backgroundExecutor;
        private final Executor mainThreadExecutor;

        DefaultExecutorSupplier() {
            ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            networkExecutor = new DownloadExecutor(DEFAULT_MAX_NUM_THREADS, backgroundPriorityThreadFactory);
            backgroundExecutor = Executors.newSingleThreadExecutor();
            mainThreadExecutor = new MainThreadExecutor();
        }

        @Override
        public DownloadExecutor forDownloadTasks() {
            return networkExecutor;
        }

        @Override
        public Executor forBackgroundTasks() {
            return backgroundExecutor;
        }

        @Override
        public Executor forMainThreadTasks() {
            return mainThreadExecutor;
        }
    }

    public static class FileDownloadRandomAccessFile implements FileDownloadOutputStream {

        private final BufferedOutputStream out;
        private final FileDescriptor fd;
        private final RandomAccessFile randomAccess;

        private FileDownloadRandomAccessFile(File file) throws IOException {
            randomAccess = new RandomAccessFile(file, "rw");
            fd = randomAccess.getFD();
            out = new BufferedOutputStream(new FileOutputStream(randomAccess.getFD()));
        }

        public static FileDownloadOutputStream create(File file) throws IOException {
            return new FileDownloadRandomAccessFile(file);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
        }

        @Override
        public void flushAndSync() throws IOException {
            out.flush();
            fd.sync();
        }

        @Override
        public void close() throws IOException {
            out.close();
            randomAccess.close();
        }

        @Override
        public void seek(long offset) throws IOException {
            randomAccess.seek(offset);
        }

        @Override
        public void setLength(long totalBytes) throws IOException {
            randomAccess.setLength(totalBytes);
        }
    }

    public static class DownloadRunnable implements Runnable {

        public final Priority priority;
        public final int sequence;
        public final DownloadRequest request;

        DownloadRunnable(DownloadRequest request) {
            this.request = request;
            priority = request.getPriority();
            sequence = request.getSequenceNumber();
        }

        @Override
        public void run() {
            request.setStatus(Status.RUNNING);
            DownloadTask downloadTask = DownloadTask.create(request);
            Response response = downloadTask.run();
            if (response.isSuccessful()) {
                request.deliverSuccess();
            } else if (response.isPaused()) {
                request.deliverPauseEvent();
            } else if (response.getError() != null) {
                request.deliverError(response.getError());
            } else if (!response.isCancelled()) {
                request.deliverError(new Error());
            }
        }
    }

    public static class SynchronousCall {

        public final DownloadRequest request;

        public SynchronousCall(DownloadRequest request) {
            this.request = request;
        }

        public Response execute() {
            DownloadTask downloadTask = DownloadTask.create(request);
            return downloadTask.run();
        }
    }

    public static class ComponentHolder {

        private final static ComponentHolder INSTANCE = new ComponentHolder();
        private int readTimeout;
        private int connectTimeout;
        private String userAgent;
        private HttpClient httpClient;
        private DbHelper dbHelper;

        public static ComponentHolder getInstance() {
            return INSTANCE;
        }

        public void init(Context context, PRDownloaderConfig config) {
            readTimeout = config.getReadTimeout();
            connectTimeout = config.getConnectTimeout();
            userAgent = config.getUserAgent();
            httpClient = config.getHttpClient();
            dbHelper = config.isDatabaseEnabled() ? new AppDbHelper(context) : new NoOpsDbHelper();
            if (config.isDatabaseEnabled()) {
                PRDownloader.cleanUp(30);
            }
        }

        public int getReadTimeout() {
            if (readTimeout == 0) {
                synchronized (ComponentHolder.class) {
                    if (readTimeout == 0) {
                        readTimeout = Constants.DEFAULT_READ_TIMEOUT_IN_MILLS;
                    }
                }
            }
            return readTimeout;
        }

        public int getConnectTimeout() {
            if (connectTimeout == 0) {
                synchronized (ComponentHolder.class) {
                    if (connectTimeout == 0) {
                        connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT_IN_MILLS;
                    }
                }
            }
            return connectTimeout;
        }

        public String getUserAgent() {
            if (userAgent == null) {
                synchronized (ComponentHolder.class) {
                    if (userAgent == null) {
                        userAgent = Constants.DEFAULT_USER_AGENT;
                    }
                }
            }
            return userAgent;
        }

        public DbHelper getDbHelper() {
            if (dbHelper == null) {
                synchronized (ComponentHolder.class) {
                    if (dbHelper == null) {
                        dbHelper = new NoOpsDbHelper();
                    }
                }
            }
            return dbHelper;
        }

        public HttpClient getHttpClient() {
            if (httpClient == null) {
                synchronized (ComponentHolder.class) {
                    if (httpClient == null) {
                        httpClient = new DefaultHttpClient();
                    }
                }
            }
            return httpClient.clone();
        }
    }

    public static class DownloadTask {

        private static final int BUFFER_SIZE = 1024 * 4;
        private static final long TIME_GAP_FOR_SYNC = 2000;
        private static final long MIN_BYTES_FOR_SYNC = 65536;
        private final DownloadRequest request;
        private ProgressHandler progressHandler;
        private long lastSyncTime;
        private long lastSyncBytes;
        private InputStream inputStream;
        private FileDownloadOutputStream outputStream;
        private HttpClient httpClient;
        private long totalBytes;
        private int responseCode;
        private String eTag;
        private boolean isResumeSupported;
        private String tempPath;

        private DownloadTask(DownloadRequest request) {
            this.request = request;
        }

        static DownloadTask create(DownloadRequest request) {
            return new DownloadTask(request);
        }

        Response run() {
            Response response = new Response();
            if (request.getStatus() == Status.CANCELLED) {
                response.setCancelled(true);
                return response;
            } else if (request.getStatus() == Status.PAUSED) {
                response.setPaused(true);
                return response;
            }
            try {
                if (request.getOnProgressListener() != null) {
                    progressHandler = new ProgressHandler(request.getOnProgressListener());
                }
                tempPath = Utils.getTempPath(request.getDirPath(), request.getFileName());
                File file = new File(tempPath);
                DownloadModel model = getDownloadModelIfAlreadyPresentInDatabase();
                if (model != null) {
                    if (file.exists()) {
                        request.setTotalBytes(model.getTotalBytes());
                        request.setDownloadedBytes(model.getDownloadedBytes());
                    } else {
                        removeNoMoreNeededModelFromDatabase();
                        request.setDownloadedBytes(0);
                        request.setTotalBytes(0);
                        model = null;
                    }
                }
                httpClient = ComponentHolder.getInstance().getHttpClient();
                httpClient.connect(request);
                if (request.getStatus() == Status.CANCELLED) {
                    response.setCancelled(true);
                    return response;
                } else if (request.getStatus() == Status.PAUSED) {
                    response.setPaused(true);
                    return response;
                }
                httpClient = Utils.getRedirectedConnectionIfAny(httpClient, request);
                responseCode = httpClient.getResponseCode();
                eTag = httpClient.getResponseHeader(Constants.ETAG);
                if (checkIfFreshStartRequiredAndStart(model)) {
                    model = null;
                }
                if (!isSuccessful()) {
                    Error error = new Error();
                    error.setServerError(true);
                    response.setError(error);
                    return response;
                }
                setResumeSupportedOrNot();
                totalBytes = request.getTotalBytes();
                if (!isResumeSupported) {
                    deleteTempFile();
                }
                if (totalBytes == 0) {
                    totalBytes = httpClient.getContentLength();
                    request.setTotalBytes(totalBytes);
                }
                if (isResumeSupported && model == null) {
                    createAndInsertNewModel();
                }
                if (request.getStatus() == Status.CANCELLED) {
                    response.setCancelled(true);
                    return response;
                } else if (request.getStatus() == Status.PAUSED) {
                    response.setPaused(true);
                    return response;
                }
                request.deliverStartEvent();
                inputStream = httpClient.getInputStream();
                byte[] buff = new byte[BUFFER_SIZE];
                if (!file.exists()) {
                    if (file.getParentFile() != null && !file.getParentFile().exists()) {
                        if (file.getParentFile().mkdirs()) {
                            file.createNewFile();
                        }
                    } else {
                        file.createNewFile();
                    }
                }
                outputStream = FileDownloadRandomAccessFile.create(file);
                if (isResumeSupported && request.getDownloadedBytes() != 0) {
                    outputStream.seek(request.getDownloadedBytes());
                }
                if (request.getStatus() == Status.CANCELLED) {
                    response.setCancelled(true);
                    return response;
                } else if (request.getStatus() == Status.PAUSED) {
                    response.setPaused(true);
                    return response;
                }
                do {
                    final int byteCount = inputStream.read(buff, 0, BUFFER_SIZE);
                    if (byteCount == -1) {
                        break;
                    }
                    outputStream.write(buff, 0, byteCount);
                    request.setDownloadedBytes(request.getDownloadedBytes() + byteCount);
                    sendProgress();
                    syncIfRequired(outputStream);
                    if (request.getStatus() == Status.CANCELLED) {
                        response.setCancelled(true);
                        return response;
                    } else if (request.getStatus() == Status.PAUSED) {
                        sync(outputStream);
                        response.setPaused(true);
                        return response;
                    }
                } while (true);
                final String path = Utils.getPath(request.getDirPath(), request.getFileName());
                Utils.renameFileName(tempPath, path);
                response.setSuccessful(true);
                if (isResumeSupported) {
                    removeNoMoreNeededModelFromDatabase();
                }
            } catch (IOException | IllegalAccessException e) {
                if (!isResumeSupported) {
                    deleteTempFile();
                }
                Error error = new Error();
                error.setConnectionError(true);
                response.setError(error);
            } finally {
                closeAllSafely(outputStream);
            }
            return response;
        }

        private void deleteTempFile() {
            File file = new File(tempPath);
            if (file.exists()) {
                file.delete();
            }
        }

        private boolean isSuccessful() {
            return responseCode >= HttpURLConnection.HTTP_OK
                    && responseCode < HttpURLConnection.HTTP_MULT_CHOICE;
        }

        private void setResumeSupportedOrNot() {
            isResumeSupported = (responseCode == HttpURLConnection.HTTP_PARTIAL);
        }

        private boolean checkIfFreshStartRequiredAndStart(DownloadModel model) throws IOException,
                IllegalAccessException {
            if (responseCode == Constants.HTTP_RANGE_NOT_SATISFIABLE || isETagChanged(model)) {
                if (model != null) {
                    removeNoMoreNeededModelFromDatabase();
                }
                deleteTempFile();
                request.setDownloadedBytes(0);
                request.setTotalBytes(0);
                httpClient = ComponentHolder.getInstance().getHttpClient();
                httpClient.connect(request);
                httpClient = Utils.getRedirectedConnectionIfAny(httpClient, request);
                responseCode = httpClient.getResponseCode();
                return true;
            }
            return false;
        }

        private boolean isETagChanged(DownloadModel model) {
            return !(eTag == null || model == null || model.getETag() == null)
                    && !model.getETag().equals(eTag);
        }

        private DownloadModel getDownloadModelIfAlreadyPresentInDatabase() {
            return ComponentHolder.getInstance().getDbHelper().find(request.getDownloadId());
        }

        private void createAndInsertNewModel() {
            DownloadModel model = new DownloadModel();
            model.setId(request.getDownloadId());
            model.setUrl(request.getUrl());
            model.setETag(eTag);
            model.setDirPath(request.getDirPath());
            model.setFileName(request.getFileName());
            model.setDownloadedBytes(request.getDownloadedBytes());
            model.setTotalBytes(totalBytes);
            model.setLastModifiedAt(System.currentTimeMillis());
            ComponentHolder.getInstance().getDbHelper().insert(model);
        }

        private void removeNoMoreNeededModelFromDatabase() {
            ComponentHolder.getInstance().getDbHelper().remove(request.getDownloadId());
        }

        private void sendProgress() {
            if (request.getStatus() != Status.CANCELLED) {
                if (progressHandler != null) {
                    progressHandler
                            .obtainMessage(Constants.UPDATE,
                                    new Progress(request.getDownloadedBytes(),
                                            totalBytes)).sendToTarget();
                }
            }
        }

        private void syncIfRequired(FileDownloadOutputStream outputStream) {
            final long currentBytes = request.getDownloadedBytes();
            final long currentTime = System.currentTimeMillis();
            final long bytesDelta = currentBytes - lastSyncBytes;
            final long timeDelta = currentTime - lastSyncTime;
            if (bytesDelta > MIN_BYTES_FOR_SYNC && timeDelta > TIME_GAP_FOR_SYNC) {
                sync(outputStream);
                lastSyncBytes = currentBytes;
                lastSyncTime = currentTime;
            }
        }

        private void sync(FileDownloadOutputStream outputStream) {
            boolean success;
            try {
                outputStream.flushAndSync();
                success = true;
            } catch (IOException e) {
                success = false;
                e.printStackTrace();
            }
            if (success && isResumeSupported) {
                ComponentHolder.getInstance().getDbHelper()
                        .updateProgress(request.getDownloadId(),
                                request.getDownloadedBytes(),
                                System.currentTimeMillis());
            }
        }

        private void closeAllSafely(FileDownloadOutputStream outputStream) {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (outputStream != null) {
                    try {
                        sync(outputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } finally {
                if (outputStream != null)
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static class DownloadRequestQueue {

        private static DownloadRequestQueue instance;
        private final Map<Integer, DownloadRequest> currentRequestMap;
        private final AtomicInteger sequenceGenerator;

        private DownloadRequestQueue() {
            currentRequestMap = new ConcurrentHashMap<>();
            sequenceGenerator = new AtomicInteger();
        }

        public static void initialize() {
            getInstance();
        }

        public static DownloadRequestQueue getInstance() {
            if (instance == null) {
                synchronized (DownloadRequestQueue.class) {
                    if (instance == null) {
                        instance = new DownloadRequestQueue();
                    }
                }
            }
            return instance;
        }

        private int getSequenceNumber() {
            return sequenceGenerator.incrementAndGet();
        }

        public void pause(int downloadId) {
            DownloadRequest request = currentRequestMap.get(downloadId);
            if (request != null) {
                request.setStatus(Status.PAUSED);
            }
        }

        public void resume(int downloadId) {
            DownloadRequest request = currentRequestMap.get(downloadId);
            if (request != null) {
                request.setStatus(Status.QUEUED);
                request.setFuture(Core.getInstance()
                        .getExecutorSupplier()
                        .forDownloadTasks()
                        .submit(new DownloadRunnable(request)));
            }
        }

        private void cancelAndRemoveFromMap(DownloadRequest request) {
            if (request != null) {
                request.cancel();
                currentRequestMap.remove(request.getDownloadId());
            }
        }

        public void cancel(int downloadId) {
            DownloadRequest request = currentRequestMap.get(downloadId);
            cancelAndRemoveFromMap(request);
        }

        public void cancel(Object tag) {
            for (Map.Entry<Integer, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
                DownloadRequest request = currentRequestMapEntry.getValue();
                if (request.getTag() instanceof String && tag instanceof String) {
                    final String tempRequestTag = (String) request.getTag();
                    final String tempTag = (String) tag;
                    if (tempRequestTag.equals(tempTag)) {
                        cancelAndRemoveFromMap(request);
                    }
                } else if (request.getTag().equals(tag)) {
                    cancelAndRemoveFromMap(request);
                }
            }
        }

        public void cancelAll() {
            for (Map.Entry<Integer, DownloadRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
                DownloadRequest request = currentRequestMapEntry.getValue();
                cancelAndRemoveFromMap(request);
            }
        }

        public Status getStatus(int downloadId) {
            DownloadRequest request = currentRequestMap.get(downloadId);
            if (request != null) {
                return request.getStatus();
            }
            return Status.UNKNOWN;
        }

        public void addRequest(DownloadRequest request) {
            currentRequestMap.put(request.getDownloadId(), request);
            request.setStatus(Status.QUEUED);
            request.setSequenceNumber(getSequenceNumber());
            request.setFuture(Core.getInstance()
                    .getExecutorSupplier()
                    .forDownloadTasks()
                    .submit(new DownloadRunnable(request)));
        }

        public void finish(DownloadRequest request) {
            currentRequestMap.remove(request.getDownloadId());
        }
    }

    public static class DownloadModel {

        static final String ID = "id";
        static final String URL = "url";
        static final String ETAG = "etag";
        static final String DIR_PATH = "dir_path";
        static final String FILE_NAME = "file_name";
        static final String TOTAL_BYTES = "total_bytes";
        static final String DOWNLOADED_BYTES = "downloaded_bytes";
        static final String LAST_MODIFIED_AT = "last_modified_at";
        private int id;
        private String url;
        private String eTag;
        private String dirPath;
        private String fileName;
        private long totalBytes;
        private long downloadedBytes;
        private long lastModifiedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getETag() {
            return eTag;
        }

        public void setETag(String eTag) {
            this.eTag = eTag;
        }

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(long totalBytes) {
            this.totalBytes = totalBytes;
        }

        public long getDownloadedBytes() {
            return downloadedBytes;
        }

        public void setDownloadedBytes(long downloadedBytes) {
            this.downloadedBytes = downloadedBytes;
        }

        public long getLastModifiedAt() {
            return lastModifiedAt;
        }

        public void setLastModifiedAt(long lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
        }
    }

    public static class AppDbHelper implements DbHelper {

        public static final String TABLE_NAME = "prdownloader";
        private final SQLiteDatabase db;

        public AppDbHelper(Context context) {
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context);
            db = databaseOpenHelper.getWritableDatabase();
        }

        @Override
        public DownloadModel find(int id) {
            Cursor cursor = null;
            DownloadModel model = null;
            try {
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                        DownloadModel.ID + " = " + id, null);
                if (cursor != null && cursor.moveToFirst()) {
                    model = new DownloadModel();
                    model.setId(id);
                    model.setUrl(cursor.getString(cursor.getColumnIndex(DownloadModel.URL)));
                    model.setETag(cursor.getString(cursor.getColumnIndex(DownloadModel.ETAG)));
                    model.setDirPath(cursor.getString(cursor.getColumnIndex(DownloadModel.DIR_PATH)));
                    model.setFileName(cursor.getString(cursor.getColumnIndex(DownloadModel.FILE_NAME)));
                    model.setTotalBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.TOTAL_BYTES)));
                    model.setDownloadedBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.DOWNLOADED_BYTES)));
                    model.setLastModifiedAt(cursor.getLong(cursor.getColumnIndex(DownloadModel.LAST_MODIFIED_AT)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return model;
        }

        @Override
        public void insert(DownloadModel model) {
            try {
                ContentValues values = new ContentValues();
                values.put(DownloadModel.ID, model.getId());
                values.put(DownloadModel.URL, model.getUrl());
                values.put(DownloadModel.ETAG, model.getETag());
                values.put(DownloadModel.DIR_PATH, model.getDirPath());
                values.put(DownloadModel.FILE_NAME, model.getFileName());
                values.put(DownloadModel.TOTAL_BYTES, model.getTotalBytes());
                values.put(DownloadModel.DOWNLOADED_BYTES, model.getDownloadedBytes());
                values.put(DownloadModel.LAST_MODIFIED_AT, model.getLastModifiedAt());
                db.insert(TABLE_NAME, null, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(DownloadModel model) {
            try {
                ContentValues values = new ContentValues();
                values.put(DownloadModel.URL, model.getUrl());
                values.put(DownloadModel.ETAG, model.getETag());
                values.put(DownloadModel.DIR_PATH, model.getDirPath());
                values.put(DownloadModel.FILE_NAME, model.getFileName());
                values.put(DownloadModel.TOTAL_BYTES, model.getTotalBytes());
                values.put(DownloadModel.DOWNLOADED_BYTES, model.getDownloadedBytes());
                values.put(DownloadModel.LAST_MODIFIED_AT, model.getLastModifiedAt());
                db.update(TABLE_NAME, values, DownloadModel.ID + " = ? ",
                        new String[]{String.valueOf(model.getId())});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void updateProgress(int id, long downloadedBytes, long lastModifiedAt) {
            try {
                ContentValues values = new ContentValues();
                values.put(DownloadModel.DOWNLOADED_BYTES, downloadedBytes);
                values.put(DownloadModel.LAST_MODIFIED_AT, lastModifiedAt);
                db.update(TABLE_NAME, values, DownloadModel.ID + " = ? ",
                        new String[]{String.valueOf(id)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void remove(int id) {
            try {
                db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                        DownloadModel.ID + " = " + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<DownloadModel> getUnwantedModels(int days) {
            List<DownloadModel> models = new ArrayList<>();
            Cursor cursor = null;
            try {
                final long daysInMillis = days * 24 * 60 * 60 * 1000L;
                final long beforeTimeInMillis = System.currentTimeMillis() - daysInMillis;
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                        DownloadModel.LAST_MODIFIED_AT + " <= " + beforeTimeInMillis, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        DownloadModel model = new DownloadModel();
                        model.setId(cursor.getInt(cursor.getColumnIndex(DownloadModel.ID)));
                        model.setUrl(cursor.getString(cursor.getColumnIndex(DownloadModel.URL)));
                        model.setETag(cursor.getString(cursor.getColumnIndex(DownloadModel.ETAG)));
                        model.setDirPath(cursor.getString(cursor.getColumnIndex(DownloadModel.DIR_PATH)));
                        model.setFileName(cursor.getString(cursor.getColumnIndex(DownloadModel.FILE_NAME)));
                        model.setTotalBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.TOTAL_BYTES)));
                        model.setDownloadedBytes(cursor.getLong(cursor.getColumnIndex(DownloadModel.DOWNLOADED_BYTES)));
                        model.setLastModifiedAt(cursor.getLong(cursor.getColumnIndex(DownloadModel.LAST_MODIFIED_AT)));
                        models.add(model);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return models;
        }

        @Override
        public void clear() {
            try {
                db.delete(TABLE_NAME, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class DatabaseOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

        private static final String DATABASE_NAME = "prdownloader.db";
        private static final int DATABASE_VERSION = 1;

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    AppDbHelper.TABLE_NAME + "( " +
                    DownloadModel.ID + " INTEGER PRIMARY KEY, " +
                    DownloadModel.URL + " VARCHAR, " +
                    DownloadModel.ETAG + " VARCHAR, " +
                    DownloadModel.DIR_PATH + " VARCHAR, " +
                    DownloadModel.FILE_NAME + " VARCHAR, " +
                    DownloadModel.TOTAL_BYTES + " INTEGER, " +
                    DownloadModel.DOWNLOADED_BYTES + " INTEGER, " +
                    DownloadModel.LAST_MODIFIED_AT + " INTEGER " +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        }
    }

    public static class NoOpsDbHelper implements DbHelper {

        public NoOpsDbHelper() {
        }

        @Override
        public DownloadModel find(int id) {
            return null;
        }

        @Override
        public void insert(DownloadModel model) {
        }

        @Override
        public void update(DownloadModel model) {
        }

        @Override
        public void updateProgress(int id, long downloadedBytes, long lastModifiedAt) {
        }

        @Override
        public void remove(int id) {
        }

        @Override
        public List<DownloadModel> getUnwantedModels(int days) {
            return null;
        }

        @Override
        public void clear() {
        }
    }

    public static class DownloadFutureTask extends FutureTask<DownloadRunnable> implements Comparable<DownloadFutureTask> {

        private final DownloadRunnable runnable;

        DownloadFutureTask(DownloadRunnable downloadRunnable) {
            super(downloadRunnable, null);
            runnable = downloadRunnable;
        }

        @Override
        public int compareTo(DownloadFutureTask other) {
            Priority p1 = runnable.priority;
            Priority p2 = other.runnable.priority;
            return (p1 == p2 ? runnable.sequence - other.runnable.sequence : p2.ordinal() - p1.ordinal());
        }
    }

    public static class DownloadExecutor extends ThreadPoolExecutor {

        DownloadExecutor(int maxNumThreads, ThreadFactory threadFactory) {
            super(maxNumThreads, maxNumThreads, 0, TimeUnit.MILLISECONDS,
                    new PriorityBlockingQueue<>(), threadFactory);
        }

        @Override
        public Future<?> submit(Runnable task) {
            DownloadFutureTask futureTask = new DownloadFutureTask((DownloadRunnable) task);
            execute(futureTask);
            return futureTask;
        }
    }
}
