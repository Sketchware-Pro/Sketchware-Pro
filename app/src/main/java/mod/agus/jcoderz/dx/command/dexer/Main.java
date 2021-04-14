package mod.agus.jcoderz.dx.command.dexer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dex.util.FileUtils;
import mod.agus.jcoderz.dx.cf.code.SimException;
import mod.agus.jcoderz.dx.cf.direct.ClassPathOpener;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.command.DxConsole;
import mod.agus.jcoderz.dx.command.UsageException;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.dex.cf.CfOptions;
import mod.agus.jcoderz.dx.dex.cf.CfTranslator;
import mod.agus.jcoderz.dx.dex.cf.CodeStatistics;
import mod.agus.jcoderz.dx.dex.file.ClassDefItem;
import mod.agus.jcoderz.dx.dex.file.DexFile;
import mod.agus.jcoderz.dx.dex.file.EncodedMethod;
import mod.agus.jcoderz.dx.merge.CollisionPolicy;
import mod.agus.jcoderz.dx.merge.DexMerger;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstString;

public class Main {
    static final boolean assertionsDisabled;
    private static final Attributes.Name CREATED_BY = new Attributes.Name("Created-By");
    private static final String DEX_EXTENSION = ".dex";
    private static final String DEX_PREFIX = "classes";
    private static final String IN_RE_CORE_CLASSES = "Ill-advised or mistaken usage of a core class (java.* or javax.*)\nwhen not building a core library.\n\nThis is often due to inadvertently including a core library file\nin your application's project, when using an IDE (such as\nEclipse). If you are sure you're not intentionally defining a\ncore class, then this is the most likely explanation of what's\ngoing on.\n\nHowever, you might actually be trying to define a class in a core\nnamespace, the source of which you may have taken, for example,\nfrom a non-Android virtual machine project. This will most\nassuredly not work. At a minimum, it jeopardizes the\ncompatibility of your app with future versions of the platform.\nIt is also often of questionable legality.\n\nIf you really intend to build a core library -- which is only\nappropriate as part of creating a full virtual machine\ndistribution, as opposed to compiling an application -- then use\nthe \"--core-library\" option to suppress this error message.\n\nIf you go ahead and use \"--core-library\" but are in fact\nbuilding an application, then be forewarned that your application\nwill still fail to build or run, at some point. Please be\nprepared for angry customers who find, for example, that your\napplication ceases to function once they upgrade their operating\nsystem. You will be to blame for this problem.\n\nIf you are legitimately using some code that happens to be in a\ncore package, then the easiest safe alternative you have is to\nrepackage that code. That is, move the classes in question into\nyour own package namespace. This means that they will never be in\nconflict with core system classes. JarJar is a tool that may help\nyou in this endeavor. If you find that you cannot do this, then\nthat is an indication that the path you are on will ultimately\nlead to pain, suffering, grief, and lamentation.\n";
    private static final String[] JAVAX_CORE = {"accessibility", "crypto", "imageio", "management", "naming", "net", "print", "rmi", "security", "sip", "sound", "sql", "swing", "transaction", "xml"};
    private static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
    private static final int MAX_FIELD_ADDED_DURING_DEX_CREATION = 9;
    private static final int MAX_METHOD_ADDED_DURING_DEX_CREATION = 2;
    private static List<Future<Boolean>> addToDexFutures = new ArrayList();
    private static volatile boolean anyFilesProcessed;
    private static Arguments args;
    private static ExecutorService classDefItemConsumer;
    private static ExecutorService classTranslatorPool;
    private static Set<String> classesInMainDex = null;
    private static ExecutorService dexOutPool;
    public static List<byte[]> dexOutputArrays = new ArrayList();
    public static List<Future<byte[]>> dexOutputFutures = new ArrayList();
    private static Object dexRotationLock = new Object();
    private static AtomicInteger errors = new AtomicInteger(0);
    private static OutputStreamWriter humanOutWriter = null;
    private static final List<byte[]> libraryDexBuffers = new ArrayList();
    private static int maxFieldIdsInProcess = 0;
    private static int maxMethodIdsInProcess = 0;
    private static long minimumFileAge = 0;
    private static DexFile outputDex;
    private static TreeMap<String, byte[]> outputResources;

    static {
        boolean z;
        if (!Main.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        assertionsDisabled = z;
    }

    private Main() {
    }

    public static void main(String[] strArr) throws IOException {
        Arguments arguments = new Arguments();
        arguments.parse(strArr);
        int run = run(arguments);
        if (run != 0) {
            System.err.println("Exit code " + run);
        }
    }

    public static int run(Arguments arguments) throws IOException {
        OutputStream outputStream;
        errors.set(0);
        libraryDexBuffers.clear();
        args = arguments;
        args.makeOptionsObjects();
        if (args.humanOutName != null) {
            OutputStream openOutput = openOutput(args.humanOutName);
            humanOutWriter = new OutputStreamWriter(openOutput);
            outputStream = openOutput;
        } else {
            outputStream = null;
        }
        try {
            if (args.multiDex) {
                return runMultiDex();
            }
            int runMonoDex = runMonoDex();
            closeOutput(outputStream);
            return runMonoDex;
        } finally {
            closeOutput(outputStream);
        }
    }

    public static String getTooManyIdsErrorMessage() {
        if (args.multiDex) {
            return "The list of classes given in --main-dex-list is too big and does not fit in the main dex.";
        }
        return "You may try using --multi-dex option.";
    }

    private static int runMonoDex() throws IOException {
        File file;
        byte[] bArr;
        if (!args.incremental) {
            file = null;
        } else if (args.outName == null) {
            System.err.println("error: no incremental output name specified");
            return -1;
        } else {
            file = new File(args.outName);
            if (file.exists()) {
                minimumFileAge = file.lastModified();
            }
        }
        if (!processAllFiles()) {
            return 1;
        }
        if (args.incremental && !anyFilesProcessed) {
            return 0;
        }
        if (!outputDex.isEmpty() || args.humanOutName != null) {
            bArr = writeDex(outputDex);
            if (bArr == null) {
                return 2;
            }
        } else {
            bArr = null;
        }
        if (args.incremental) {
            bArr = mergeIncremental(bArr, file);
        }
        byte[] mergeLibraryDexBuffers = mergeLibraryDexBuffers(bArr);
        if (args.jarOutput) {
            outputDex = null;
            if (mergeLibraryDexBuffers != null) {
                outputResources.put("classes.dex", mergeLibraryDexBuffers);
            }
            if (!createJar(args.outName)) {
                return 3;
            }
        } else if (!(mergeLibraryDexBuffers == null || args.outName == null)) {
            OutputStream openOutput = openOutput(args.outName);
            openOutput.write(mergeLibraryDexBuffers);
            closeOutput(openOutput);
        }
        return 0;
    }

    /* JADX INFO: finally extract failed */
    private static int runMultiDex() throws IOException {
        if (assertionsDisabled || !args.incremental) {
            if (args.mainDexListFile != null) {
                classesInMainDex = new HashSet();
                readPathsFromFile(args.mainDexListFile, classesInMainDex);
            }
            dexOutPool = Executors.newFixedThreadPool(args.numThreads);
            if (!processAllFiles()) {
                return 1;
            }
            if (!libraryDexBuffers.isEmpty()) {
                throw new DexException("Library dex files are not supported in multi-dex mode");
            }
            if (outputDex != null) {
                dexOutputFutures.add(dexOutPool.submit(new DexWriter(outputDex, null)));
                outputDex = null;
            }
            try {
                dexOutPool.shutdown();
                if (!dexOutPool.awaitTermination(600, TimeUnit.SECONDS)) {
                    throw new RuntimeException("Timed out waiting for dex writer threads.");
                }
                for (Future<byte[]> future : dexOutputFutures) {
                    dexOutputArrays.add(future.get());
                }
                if (args.jarOutput) {
                    for (int i = 0; i < dexOutputArrays.size(); i++) {
                        outputResources.put(getDexFileName(i), dexOutputArrays.get(i));
                    }
                    if (!createJar(args.outName)) {
                        return 3;
                    }
                    return 0;
                } else if (args.outName == null) {
                    return 0;
                } else {
                    File file = new File(args.outName);
                    if (assertionsDisabled || file.isDirectory()) {
                        for (int i2 = 0; i2 < dexOutputArrays.size(); i2++) {
                            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, getDexFileName(i2)));
                            try {
                                fileOutputStream.write(dexOutputArrays.get(i2));
                                closeOutput(fileOutputStream);
                            } catch (Throwable th) {
                                closeOutput(fileOutputStream);
                                throw th;
                            }
                        }
                        return 0;
                    }
                    throw new AssertionError();
                }
            } catch (InterruptedException e) {
                dexOutPool.shutdownNow();
                throw new RuntimeException("A dex writer thread has been interrupted.");
            } catch (Exception e2) {
                dexOutPool.shutdownNow();
                throw new RuntimeException("Unexpected exception in dex writer thread");
            }
        } else {
            throw new AssertionError();
        }
    }

    private static String getDexFileName(int i) {
        if (i == 0) {
            return "classes.dex";
        }
        return DEX_PREFIX + (i + 1) + DEX_EXTENSION;
    }

    public static void readPathsFromFile(String str, Collection<String> collection) throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(str));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    collection.add(fixPath(readLine));
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                    }
                    throw th;
                }
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (Throwable th2) {
            Throwable th3 = th2;
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            try {
                throw th3;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private static byte[] mergeIncremental(byte[] bArr, File file) throws IOException {
        Dex dex;
        Dex dex2;
        if (bArr != null) {
            dex = new Dex(bArr);
        } else {
            dex = null;
        }
        if (file.exists()) {
            dex2 = new Dex(file);
        } else {
            dex2 = null;
        }
        if (dex == null && dex2 == null) {
            return null;
        }
        if (dex == null) {
            dex = dex2;
        } else if (dex2 != null) {
            dex = new DexMerger(new Dex[]{dex, dex2}, CollisionPolicy.KEEP_FIRST).merge();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        dex.writeTo(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] mergeLibraryDexBuffers(byte[] bArr) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (bArr != null) {
            arrayList.add(new Dex(bArr));
        }
        for (byte[] bArr2 : libraryDexBuffers) {
            arrayList.add(new Dex(bArr2));
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new DexMerger((Dex[]) arrayList.toArray(new Dex[arrayList.size()]), CollisionPolicy.FAIL).merge().getBytes();
    }

    private static boolean processAllFiles() {
        ClassPathOpener.FileNameFilter bestEffortMainDexListFilter;
        createDexFile();
        if (args.jarOutput) {
            outputResources = new TreeMap<>();
        }
        anyFilesProcessed = assertionsDisabled;
        String[] strArr = args.fileNames;
        Arrays.sort(strArr);
        classTranslatorPool = new ThreadPoolExecutor(args.numThreads, args.numThreads, 0, TimeUnit.SECONDS, new ArrayBlockingQueue(args.numThreads * 2, true), new ThreadPoolExecutor.CallerRunsPolicy());
        classDefItemConsumer = Executors.newSingleThreadExecutor();
        try {
            if (args.mainDexListFile != null) {
                if (args.strictNameCheck) {
                    bestEffortMainDexListFilter = new MainDexListFilter(null);
                } else {
                    bestEffortMainDexListFilter = new BestEffortMainDexListFilter();
                }
                for (String str : strArr) {
                    processOne(str, bestEffortMainDexListFilter);
                }
                if (dexOutputFutures.size() > 0) {
                    throw new DexException("Too many classes in --main-dex-list, main dex capacity exceeded");
                }
                if (args.minimalMainDex) {
                    synchronized (dexRotationLock) {
                        while (true) {
                            if (maxMethodIdsInProcess > 0 || maxFieldIdsInProcess > 0) {
                                try {
                                    dexRotationLock.wait();
                                } catch (InterruptedException e) {
                                }
                            }
                            break;
                        }
                    }
                    rotateDexFile();
                }
                for (String str2 : strArr) {
                    processOne(str2, new NotFilter(bestEffortMainDexListFilter, null));
                }
            } else {
                for (String str3 : strArr) {
                    processOne(str3, ClassPathOpener.acceptAll);
                }
            }
        } catch (StopProcessing e2) {
        }
        try {
            classTranslatorPool.shutdown();
            classTranslatorPool.awaitTermination(600, TimeUnit.SECONDS);
            classDefItemConsumer.shutdown();
            classDefItemConsumer.awaitTermination(600, TimeUnit.SECONDS);
            for (Future<Boolean> future : addToDexFutures) {
                try {
                    future.get();
                } catch (ExecutionException e3) {
                    if (errors.incrementAndGet() < 10) {
                        DxConsole.err.println("Uncaught translation error: " + e3.getCause());
                    } else {
                        throw new InterruptedException("Too many errors");
                    }
                }
            }
            int i = errors.get();
            if (i != 0) {
                DxConsole.err.println(String.valueOf(i) + " error" + (i == 1 ? "" : "s") + "; aborting");
                return assertionsDisabled;
            } else if (args.incremental && !anyFilesProcessed) {
                return true;
            } else {
                if (anyFilesProcessed || args.emptyOk) {
                    if (args.optimize && args.statistics) {
                        CodeStatistics.dumpStatistics(DxConsole.out);
                    }
                    return true;
                }
                DxConsole.err.println("no classfiles specified");
                return assertionsDisabled;
            }
        } catch (InterruptedException e4) {
            classTranslatorPool.shutdownNow();
            classDefItemConsumer.shutdownNow();
            throw new RuntimeException("Translation has been interrupted", e4);
        } catch (Exception e5) {
            classTranslatorPool.shutdownNow();
            classDefItemConsumer.shutdownNow();
            e5.printStackTrace(System.out);
            throw new RuntimeException("Unexpected exception in translator thread.", e5);
        }
    }

    private static void createDexFile() {
        outputDex = new DexFile(args.dexOptions);
        if (args.dumpWidth != 0) {
            outputDex.setDumpWidth(args.dumpWidth);
        }
    }

    /* access modifiers changed from: private */
    public static void rotateDexFile() {
        if (outputDex != null) {
            if (dexOutPool != null) {
                dexOutputFutures.add(dexOutPool.submit(new DexWriter(outputDex, null)));
            } else {
                try {
                    dexOutputArrays.add(writeDex(outputDex));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        createDexFile();
    }

    private static void processOne(String str, ClassPathOpener.FileNameFilter fileNameFilter) {
        if (new ClassPathOpener(str, true, fileNameFilter, new FileBytesConsumer(null)).process()) {
            updateStatus(true);
        }
    }

    public static void updateStatus(boolean z) {
        anyFilesProcessed |= z;
    }

    public static boolean processFileBytes(String str, long j, byte[] bArr) {
        boolean endsWith = str.endsWith(".class");
        boolean equals = str.equals("classes.dex");
        boolean z = outputResources != null;
        if (endsWith || equals || z) {
            if (args.verbose) {
                DxConsole.out.println("processing " + str + "...");
            }
            String fixPath = fixPath(str);
            if (endsWith) {
                if (z && args.keepClassesInJar) {
                    synchronized (outputResources) {
                        outputResources.put(fixPath, bArr);
                    }
                }
                if (j < minimumFileAge) {
                    return true;
                }
                processClass(fixPath, bArr);
                return assertionsDisabled;
            } else if (equals) {
                synchronized (libraryDexBuffers) {
                    libraryDexBuffers.add(bArr);
                }
                return true;
            } else {
                synchronized (outputResources) {
                    outputResources.put(fixPath, bArr);
                }
                return true;
            }
        } else if (!args.verbose) {
            return assertionsDisabled;
        } else {
            DxConsole.out.println("ignored resource " + str);
            return assertionsDisabled;
        }
    }

    private static boolean processClass(String str, byte[] bArr) {
        if (!args.coreLibrary) {
            checkClassName(str);
        }
        try {
            new DirectClassFileConsumer(str, bArr, null, null).call(new ClassParserTask(str, bArr, null).call());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Exception parsing classes", e);
        }
    }

    public static DirectClassFile parseClass(String str, byte[] bArr) {
        DirectClassFile directClassFile = new DirectClassFile(bArr, str, args.cfOptions.strictNameCheck);
        directClassFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        directClassFile.getMagic();
        return directClassFile;
    }

    public static ClassDefItem translateClass(byte[] bArr, DirectClassFile directClassFile) {
        try {
            return CfTranslator.translate(directClassFile, bArr, args.cfOptions, args.dexOptions, outputDex);
        } catch (ParseException e) {
            DxConsole.err.println("\ntrouble processing:");
            if (args.debug) {
                e.printStackTrace(DxConsole.err);
            } else {
                e.printContext(DxConsole.err);
            }
            errors.incrementAndGet();
            return null;
        }
    }

    public static boolean addClassToDex(ClassDefItem classDefItem) {
        synchronized (outputDex) {
            outputDex.add(classDefItem);
        }
        return true;
    }

    private static void checkClassName(String str) {
        boolean z = true;
        if (!str.startsWith("java/")) {
            if (str.startsWith("javax/")) {
                int indexOf = str.indexOf(47, 6);
                if (indexOf != -1) {
                    if (Arrays.binarySearch(JAVAX_CORE, str.substring(6, indexOf)) < 0) {
                        z = false;
                    }
                }
            } else {
                z = false;
            }
        }
        if (z) {
            DxConsole.err.println("\ntrouble processing \"" + str + "\":\n\n" + IN_RE_CORE_CLASSES);
            errors.incrementAndGet();
            throw new StopProcessing(null);
        }
    }

    public static byte[] writeDex(DexFile dexFile) throws IOException {
        byte[] dex;
        try {
            if (args.methodToDump != null) {
                dexFile.toDex(null, assertionsDisabled);
                dumpMethod(dexFile, args.methodToDump, humanOutWriter);
                dex = null;
            } else {
                dex = dexFile.toDex(humanOutWriter, args.verboseDump);
            }
            if (args.statistics) {
                DxConsole.out.println(dexFile.getStatistics().toHuman());
            }
            try {
                if (humanOutWriter == null) {
                    return dex;
                }
                humanOutWriter.flush();
                return dex;
            } catch (Exception e) {
                if (args.debug) {
                    DxConsole.err.println("\ntrouble writing output:");
                    e.printStackTrace(DxConsole.err);
                } else {
                    DxConsole.err.println("\ntrouble writing output: " + e.getMessage());
                }
                return null;
            }
        } catch (Throwable th) {
                if (humanOutWriter != null) {
                    humanOutWriter.flush();
                }
            throw th;
        }
    }

    private static boolean createJar(String str) {
        try {
            Manifest makeManifest = makeManifest();
            OutputStream openOutput = openOutput(str);
            JarOutputStream jarOutputStream = new JarOutputStream(openOutput, makeManifest);
            try {
                for (Map.Entry<String, byte[]> entry : outputResources.entrySet()) {
                    String key = entry.getKey();
                    byte[] value = entry.getValue();
                    JarEntry jarEntry = new JarEntry(key);
                    int length = value.length;
                    if (args.verbose) {
                        DxConsole.out.println("writing " + key + "; size " + length + "...");
                    }
                    jarEntry.setSize((long) length);
                    jarOutputStream.putNextEntry(jarEntry);
                    jarOutputStream.write(value);
                    jarOutputStream.closeEntry();
                }
                jarOutputStream.finish();
                jarOutputStream.flush();
                closeOutput(openOutput);
                return true;
            } catch (Throwable th) {
                jarOutputStream.finish();
                jarOutputStream.flush();
                closeOutput(openOutput);
                throw th;
            }
        } catch (Exception e) {
            if (args.debug) {
                DxConsole.err.println("\ntrouble writing output:");
                e.printStackTrace(DxConsole.err);
            } else {
                DxConsole.err.println("\ntrouble writing output: " + e.getMessage());
            }
            return assertionsDisabled;
        }
    }

    private static Manifest makeManifest() throws IOException {
        Manifest manifest;
        Attributes mainAttributes;
        String str;
        byte[] bArr = outputResources.get(MANIFEST_NAME);
        if (bArr == null) {
            manifest = new Manifest();
            mainAttributes = manifest.getMainAttributes();
            mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        } else {
            manifest = new Manifest(new ByteArrayInputStream(bArr));
            mainAttributes = manifest.getMainAttributes();
            outputResources.remove(MANIFEST_NAME);
        }
        String value = mainAttributes.getValue(CREATED_BY);
        if (value == null) {
            str = "";
        } else {
            str = String.valueOf(value) + " + ";
        }
        mainAttributes.put(CREATED_BY, String.valueOf(str) + "dx 1.11");
        mainAttributes.putValue("Dex-Location", "classes.dex");
        return manifest;
    }

    private static OutputStream openOutput(String str) throws IOException {
        if (str.equals("-") || str.startsWith("-.")) {
            return System.out;
        }
        return new FileOutputStream(str);
    }

    private static void closeOutput(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            outputStream.flush();
            if (outputStream != System.out) {
                outputStream.close();
            }
        }
    }

    public static String fixPath(String str) {
        if (File.separatorChar == '\\') {
            str = str.replace('\\', '/');
        }
        int lastIndexOf = str.lastIndexOf("/./");
        if (lastIndexOf != -1) {
            return str.substring(lastIndexOf + 3);
        }
        if (str.startsWith("./")) {
            return str.substring(2);
        }
        return str;
    }

    private static void dumpMethod(DexFile dexFile, String str, OutputStreamWriter outputStreamWriter) {
        String str2;
        boolean endsWith = str.endsWith("*");
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf <= 0 || lastIndexOf == str.length() - 1) {
            DxConsole.err.println("bogus fully-qualified method name: " + str);
            return;
        }
        String replace = str.substring(0, lastIndexOf).replace('.', '/');
        String substring = str.substring(lastIndexOf + 1);
        ClassDefItem classOrNull = dexFile.getClassOrNull(replace);
        if (classOrNull == null) {
            DxConsole.err.println("no such class: " + replace);
            return;
        }
        if (endsWith) {
            str2 = substring.substring(0, substring.length() - 1);
        } else {
            str2 = substring;
        }
        ArrayList<EncodedMethod> methods = classOrNull.getMethods();
        TreeMap treeMap = new TreeMap();
        Iterator<EncodedMethod> it = methods.iterator();
        while (it.hasNext()) {
            EncodedMethod next = it.next();
            String string = next.getName().getString();
            if ((endsWith && string.startsWith(str2)) || (!endsWith && string.equals(str2))) {
                treeMap.put(next.getRef().getNat(), next);
            }
        }
        if (treeMap.size() == 0) {
            DxConsole.err.println("no such method: " + str);
            return;
        }
        PrintWriter printWriter = new PrintWriter(outputStreamWriter);
        for (Iterator iterator = treeMap.values().iterator(); iterator.hasNext(); ) {
            EncodedMethod encodedMethod = (EncodedMethod) iterator.next();
            encodedMethod.debugPrint(printWriter, args.verboseDump);
            CstString sourceFile = classOrNull.getSourceFile();
            if (sourceFile != null) {
                printWriter.println("  source file: " + sourceFile.toQuoted());
            }
            Annotations methodAnnotations = classOrNull.getMethodAnnotations(encodedMethod.getRef());
            AnnotationsList parameterAnnotations = classOrNull.getParameterAnnotations(encodedMethod.getRef());
            if (methodAnnotations != null) {
                printWriter.println("  method annotations:");
                Iterator<Annotation> it2 = methodAnnotations.getAnnotations().iterator();
                while (it2.hasNext()) {
                    printWriter.println("    " + it2.next());
                }
            }
            if (parameterAnnotations != null) {
                printWriter.println("  parameter annotations:");
                int size = parameterAnnotations.size();
                for (int i = 0; i < size; i++) {
                    printWriter.println("    parameter " + i);
                    Iterator<Annotation> it3 = parameterAnnotations.get(i).getAnnotations().iterator();
                    while (it3.hasNext()) {
                        printWriter.println("      " + it3.next());
                    }
                }
            }
        }
        printWriter.flush();
    }

    private static class NotFilter implements ClassPathOpener.FileNameFilter {
        private final ClassPathOpener.FileNameFilter filter;

        private NotFilter(ClassPathOpener.FileNameFilter fileNameFilter) {
            this.filter = fileNameFilter;
        }

        NotFilter(ClassPathOpener.FileNameFilter fileNameFilter, NotFilter notFilter) {
            this(fileNameFilter);
        }

        @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.FileNameFilter
        public boolean accept(String str) {
            if (this.filter.accept(str)) {
                return Main.assertionsDisabled;
            }
            return true;
        }
    }

    private static class MainDexListFilter implements ClassPathOpener.FileNameFilter {
        private MainDexListFilter() {
        }

        MainDexListFilter(MainDexListFilter mainDexListFilter) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.FileNameFilter
        public boolean accept(String str) {
            if (!str.endsWith(".class")) {
                return true;
            }
            return Main.classesInMainDex.contains(Main.fixPath(str));
        }
    }

    private static class BestEffortMainDexListFilter implements ClassPathOpener.FileNameFilter {
        Map<String, List<String>> map = new HashMap();

        public BestEffortMainDexListFilter() {
            for (String str : Main.classesInMainDex) {
                String fixPath = Main.fixPath(str);
                String simpleName = getSimpleName(fixPath);
                List<String> list = this.map.get(simpleName);
                if (list == null) {
                    list = new ArrayList<>(1);
                    this.map.put(simpleName, list);
                }
                list.add(fixPath);
            }
        }

        private static String getSimpleName(String str) {
            int lastIndexOf = str.lastIndexOf(47);
            if (lastIndexOf >= 0) {
                return str.substring(lastIndexOf + 1);
            }
            return str;
        }

        @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.FileNameFilter
        public boolean accept(String str) {
            if (!str.endsWith(".class")) {
                return true;
            }
            String fixPath = Main.fixPath(str);
            List<String> list = this.map.get(getSimpleName(fixPath));
            if (list != null) {
                for (String str2 : list) {
                    if (fixPath.endsWith(str2)) {
                        return true;
                    }
                }
            }
            return Main.assertionsDisabled;
        }
    }

    private static class StopProcessing extends RuntimeException {
        private StopProcessing() {
        }

        StopProcessing(StopProcessing stopProcessing) {
            this();
        }
    }

    public static class Arguments {
        private static final String INCREMENTAL_OPTION = "--incremental";
        private static final String INPUT_LIST_OPTION = "--input-list";
        private static final String MAIN_DEX_LIST_OPTION = "--main-dex-list";
        private static final String MINIMAL_MAIN_DEX_OPTION = "--minimal-main-dex";
        private static final String MULTI_DEX_OPTION = "--multi-dex";
        private static final String NUM_THREADS_OPTION = "--num-threads";
        public CfOptions cfOptions;
        public boolean coreLibrary = Main.assertionsDisabled;
        public boolean debug = Main.assertionsDisabled;
        public DexOptions dexOptions;
        public String dontOptimizeListFile = null;
        public int dumpWidth = 0;
        public boolean emptyOk = Main.assertionsDisabled;
        public String[] fileNames;
        public boolean forceJumbo = Main.assertionsDisabled;
        public String humanOutName = null;
        public boolean incremental = Main.assertionsDisabled;
        private List<String> inputList = null;
        public boolean jarOutput = Main.assertionsDisabled;
        public boolean keepClassesInJar = Main.assertionsDisabled;
        public boolean localInfo = true;
        public String mainDexListFile = null;
        private int maxNumberOfIdxPerDex = AccessFlags.ACC_CONSTRUCTOR;
        public String methodToDump = null;
        public boolean minimalMainDex = Main.assertionsDisabled;
        public boolean multiDex = Main.assertionsDisabled;
        public int numThreads = 1;
        public boolean optimize = true;
        public String optimizeListFile = null;
        public String outName = null;
        public int positionInfo = 2;
        public boolean statistics;
        public boolean strictNameCheck = true;
        public boolean verbose = Main.assertionsDisabled;
        public boolean verboseDump = Main.assertionsDisabled;
        public boolean warnings = true;

        public void parse(String[] strArr) {
            ArgumentsParser argumentsParser = new ArgumentsParser(strArr);
            boolean z = false;
            boolean z2 = false;
            while (argumentsParser.getNext()) {
                if (argumentsParser.isArg("--debug")) {
                    this.debug = true;
                } else if (argumentsParser.isArg("--no-warning")) {
                    this.warnings = Main.assertionsDisabled;
                } else if (argumentsParser.isArg("--verbose")) {
                    this.verbose = true;
                } else if (argumentsParser.isArg("--verbose-dump")) {
                    this.verboseDump = true;
                } else if (argumentsParser.isArg("--no-files")) {
                    this.emptyOk = true;
                } else if (argumentsParser.isArg("--no-optimize")) {
                    this.optimize = Main.assertionsDisabled;
                } else if (argumentsParser.isArg("--no-strict")) {
                    this.strictNameCheck = Main.assertionsDisabled;
                } else if (argumentsParser.isArg("--core-library")) {
                    this.coreLibrary = true;
                } else if (argumentsParser.isArg("--statistics")) {
                    this.statistics = true;
                } else if (argumentsParser.isArg("--optimize-list=")) {
                    if (this.dontOptimizeListFile != null) {
                        System.err.println("--optimize-list and --no-optimize-list are incompatible.");
                        throw new UsageException();
                    } else {
                        this.optimize = true;
                        this.optimizeListFile = argumentsParser.getLastValue();
                    }
                } else if (argumentsParser.isArg("--no-optimize-list=")) {
                    if (this.dontOptimizeListFile != null) {
                        System.err.println("--optimize-list and --no-optimize-list are incompatible.");
                        throw new UsageException();
                    } else {
                        this.optimize = true;
                        this.dontOptimizeListFile = argumentsParser.getLastValue();
                    }
                } else if (argumentsParser.isArg("--keep-classes")) {
                    this.keepClassesInJar = true;
                } else if (argumentsParser.isArg("--output=")) {
                    this.outName = argumentsParser.getLastValue();
                    if (new File(this.outName).isDirectory()) {
                        this.jarOutput = Main.assertionsDisabled;
                        z2 = true;
                    } else if (FileUtils.hasArchiveSuffix(this.outName)) {
                        this.jarOutput = true;
                    } else if (this.outName.endsWith(Main.DEX_EXTENSION) || this.outName.equals("-")) {
                        this.jarOutput = Main.assertionsDisabled;
                        z = true;
                    } else {
                        System.err.println("unknown output extension: " + this.outName);
                        throw new UsageException();
                    }
                } else if (argumentsParser.isArg("--dump-to=")) {
                    this.humanOutName = argumentsParser.getLastValue();
                } else if (argumentsParser.isArg("--dump-width=")) {
                    this.dumpWidth = Integer.parseInt(argumentsParser.getLastValue());
                } else if (argumentsParser.isArg("--dump-method=")) {
                    this.methodToDump = argumentsParser.getLastValue();
                    this.jarOutput = Main.assertionsDisabled;
                } else if (argumentsParser.isArg("--positions=")) {
                    String intern = argumentsParser.getLastValue().intern();
                    if (intern == "none") {
                        this.positionInfo = 1;
                    } else if (intern == "important") {
                        this.positionInfo = 3;
                    } else if (intern == "lines") {
                        this.positionInfo = 2;
                    } else {
                        System.err.println("unknown positions option: " + intern);
                        throw new UsageException();
                    }
                } else if (argumentsParser.isArg("--no-locals")) {
                    this.localInfo = Main.assertionsDisabled;
                } else if (argumentsParser.isArg("--num-threads=")) {
                    this.numThreads = Integer.parseInt(argumentsParser.getLastValue());
                } else if (argumentsParser.isArg(INCREMENTAL_OPTION)) {
                    this.incremental = true;
                } else if (argumentsParser.isArg("--force-jumbo")) {
                    this.forceJumbo = true;
                } else if (argumentsParser.isArg(MULTI_DEX_OPTION)) {
                    this.multiDex = true;
                } else if (argumentsParser.isArg("--main-dex-list=")) {
                    this.mainDexListFile = argumentsParser.getLastValue();
                } else if (argumentsParser.isArg(MINIMAL_MAIN_DEX_OPTION)) {
                    this.minimalMainDex = true;
                } else if (argumentsParser.isArg("--set-max-idx-number=")) {
                    this.maxNumberOfIdxPerDex = Integer.parseInt(argumentsParser.getLastValue());
                } else if (argumentsParser.isArg("--input-list=")) {
                    File file = new File(argumentsParser.getLastValue());
                    try {
                        this.inputList = new ArrayList();
                        Main.readPathsFromFile(file.getAbsolutePath(), this.inputList);
                    } catch (IOException e) {
                        System.err.println("Unable to read input list file: " + file.getName());
                        throw new UsageException();
                    }
                } else {
                    System.err.println("unknown option: " + argumentsParser.getCurrent());
                    throw new UsageException();
                }
            }
            this.fileNames = argumentsParser.getRemaining();
            if (this.inputList != null && !this.inputList.isEmpty()) {
                this.inputList.addAll(Arrays.asList(this.fileNames));
                this.fileNames = (String[]) this.inputList.toArray(new String[this.inputList.size()]);
            }
            if (this.fileNames.length == 0) {
                if (!this.emptyOk) {
                    System.err.println("no input files specified");
                    throw new UsageException();
                }
            } else if (this.emptyOk) {
                System.out.println("ignoring input files");
            }
            if (this.humanOutName == null && this.methodToDump != null) {
                this.humanOutName = "-";
            }
            if (this.mainDexListFile != null && !this.multiDex) {
                System.err.println("--main-dex-list is only supported in combination with --multi-dex");
                throw new UsageException();
            } else if (this.minimalMainDex && (this.mainDexListFile == null || !this.multiDex)) {
                System.err.println("--minimal-main-dex is only supported in combination with --multi-dex and --main-dex-list");
                throw new UsageException();
            } else if (this.multiDex && this.incremental) {
                System.err.println("--incremental is not supported with --multi-dex");
                throw new UsageException();
            } else if (!this.multiDex || !z) {
                if (z2 && !this.multiDex) {
                    this.outName = new File(this.outName, "classes.dex").getPath();
                }
                makeOptionsObjects();
            } else {
                System.err.println("Unsupported output \"" + this.outName + "\". " + MULTI_DEX_OPTION + " supports only archive or directory output");
                throw new UsageException();
            }
        }

        public void makeOptionsObjects() {
            this.cfOptions = new CfOptions();
            this.cfOptions.positionInfo = this.positionInfo;
            this.cfOptions.localInfo = this.localInfo;
            this.cfOptions.strictNameCheck = this.strictNameCheck;
            this.cfOptions.optimize = this.optimize;
            this.cfOptions.optimizeListFile = this.optimizeListFile;
            this.cfOptions.dontOptimizeListFile = this.dontOptimizeListFile;
            this.cfOptions.statistics = this.statistics;
            if (this.warnings) {
                this.cfOptions.warn = DxConsole.err;
            } else {
                this.cfOptions.warn = DxConsole.noop;
            }
            this.dexOptions = new DexOptions();
            this.dexOptions.forceJumbo = this.forceJumbo;
        }

        private static class ArgumentsParser {
            private final String[] arguments;
            private String current;
            private int index = 0;
            private String lastValue;

            public ArgumentsParser(String[] strArr) {
                this.arguments = strArr;
            }

            public String getCurrent() {
                return this.current;
            }

            public String getLastValue() {
                return this.lastValue;
            }

            public boolean getNext() {
                if (this.index >= this.arguments.length) {
                    return Main.assertionsDisabled;
                }
                this.current = this.arguments[this.index];
                if (this.current.equals("--") || !this.current.startsWith("--")) {
                    return Main.assertionsDisabled;
                }
                this.index++;
                return true;
            }

            private boolean getNextValue() {
                if (this.index >= this.arguments.length) {
                    return Main.assertionsDisabled;
                }
                this.current = this.arguments[this.index];
                this.index++;
                return true;
            }

            public String[] getRemaining() {
                int length = this.arguments.length - this.index;
                String[] strArr = new String[length];
                if (length > 0) {
                    System.arraycopy(this.arguments, this.index, strArr, 0, length);
                }
                return strArr;
            }

            public boolean isArg(String str) {
                int length = str.length();
                if (length <= 0 || str.charAt(length - 1) != '=') {
                    return this.current.equals(str);
                }
                if (this.current.startsWith(str)) {
                    this.lastValue = this.current.substring(length);
                    return true;
                }
                String substring = str.substring(0, length - 1);
                if (!this.current.equals(substring)) {
                    return Main.assertionsDisabled;
                }
                if (getNextValue()) {
                    this.lastValue = this.current;
                    return true;
                }
                System.err.println("Missing value after parameter " + substring);
                throw new UsageException();
            }
        }
    }

    private static class FileBytesConsumer implements ClassPathOpener.Consumer {
        private FileBytesConsumer() {
        }

        FileBytesConsumer(FileBytesConsumer fileBytesConsumer) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.Consumer
        public boolean processFileBytes(String str, long j, byte[] bArr) {
            return Main.processFileBytes(str, j, bArr);
        }

        @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.Consumer
        public void onException(Exception exc) {
            if (exc instanceof StopProcessing) {
                throw ((StopProcessing) exc);
            }
            if (exc instanceof SimException) {
                DxConsole.err.println("\nEXCEPTION FROM SIMULATION:");
                DxConsole.err.println(String.valueOf(exc.getMessage()) + "\n");
                DxConsole.err.println(((SimException) exc).getContext());
            } else {
                DxConsole.err.println("\nUNEXPECTED TOP-LEVEL EXCEPTION:");
                exc.printStackTrace(DxConsole.err);
            }
            Main.errors.incrementAndGet();
        }

        @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.Consumer
        public void onProcessArchiveStart(File file) {
            if (Main.args.verbose) {
                DxConsole.out.println("processing archive " + file + "...");
            }
        }
    }

    private static class ClassParserTask implements Callable<DirectClassFile> {
        byte[] bytes;
        String name;

        private ClassParserTask(String str, byte[] bArr) {
            this.name = str;
            this.bytes = bArr;
        }

        /* synthetic */ ClassParserTask(String str, byte[] bArr, ClassParserTask classParserTask) {
            this(str, bArr);
        }

        @Override // java.util.concurrent.Callable
        public DirectClassFile call() throws Exception {
            return Main.parseClass(this.name, this.bytes);
        }
    }

    private static class DirectClassFileConsumer implements Callable<Boolean> {
        byte[] bytes;
        Future<DirectClassFile> dcff;
        String name;

        private DirectClassFileConsumer(String str, byte[] bArr, Future<DirectClassFile> future) {
            this.name = str;
            this.bytes = bArr;
            this.dcff = future;
        }

        DirectClassFileConsumer(String str, byte[] bArr, Future future, DirectClassFileConsumer directClassFileConsumer) {
            this(str, bArr, future);
        }

        @Override // java.util.concurrent.Callable
        public Boolean call() throws Exception {
            return call(this.dcff.get());
        }

        public Boolean call(DirectClassFile directClassFile) {
            int i;
            int size;
            int size2;
            int i2 = 0;
            if (Main.args.multiDex) {
                int size3 = directClassFile.getConstantPool().size();
                i = directClassFile.getMethods().size() + size3 + 2;
                i2 = size3 + directClassFile.getFields().size() + 9;
                synchronized (Main.dexRotationLock) {
                    synchronized (Main.outputDex) {
                        size = Main.outputDex.getMethodIds().items().size();
                        size2 = Main.outputDex.getFieldIds().items().size();
                    }
                    while (true) {
                        if (size + i + Main.maxMethodIdsInProcess <= Main.args.maxNumberOfIdxPerDex && size2 + i2 + Main.maxFieldIdsInProcess <= Main.args.maxNumberOfIdxPerDex) {
                            break;
                        }
                        if (Main.maxMethodIdsInProcess <= 0 && Main.maxFieldIdsInProcess <= 0) {
                            if (Main.outputDex.getClassDefs().items().size() <= 0) {
                                break;
                            }
                            Main.rotateDexFile();
                        } else {
                            try {
                                Main.dexRotationLock.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                        synchronized (Main.outputDex) {
                            size = Main.outputDex.getMethodIds().items().size();
                            size2 = Main.outputDex.getFieldIds().items().size();
                        }
                    }
                    Main.maxMethodIdsInProcess += i;
                    Main.maxFieldIdsInProcess += i2;
                }
            } else {
                i = 0;
            }
            Main.addToDexFutures.add(Main.classDefItemConsumer.submit(new ClassDefItemConsumer(this.name, Main.classTranslatorPool.submit(new ClassTranslatorTask(this.name, this.bytes, directClassFile, null)), i, i2, null)));
            return true;
        }
    }

    private static class ClassTranslatorTask implements Callable<ClassDefItem> {
        byte[] bytes;
        DirectClassFile classFile;
        String name;

        private ClassTranslatorTask(String str, byte[] bArr, DirectClassFile directClassFile) {
            this.name = str;
            this.bytes = bArr;
            this.classFile = directClassFile;
        }

        ClassTranslatorTask(String str, byte[] bArr, DirectClassFile directClassFile, ClassTranslatorTask classTranslatorTask) {
            this(str, bArr, directClassFile);
        }

        @Override // java.util.concurrent.Callable
        public ClassDefItem call() {
            return Main.translateClass(this.bytes, this.classFile);
        }
    }

    private static class ClassDefItemConsumer implements Callable<Boolean> {
        Future<ClassDefItem> futureClazz;
        int maxFieldIdsInClass;
        int maxMethodIdsInClass;
        String name;

        private ClassDefItemConsumer(String str, Future<ClassDefItem> future, int i, int i2) {
            this.name = str;
            this.futureClazz = future;
            this.maxMethodIdsInClass = i;
            this.maxFieldIdsInClass = i2;
        }

        ClassDefItemConsumer(String str, Future future, int i, int i2, ClassDefItemConsumer classDefItemConsumer) {
            this(str, future, i, i2);
        }

        @Override // java.util.concurrent.Callable
        public Boolean call() throws Exception {
            try {
                ClassDefItem classDefItem = this.futureClazz.get();
                if (classDefItem != null) {
                    Main.addClassToDex(classDefItem);
                    Main.updateStatus(true);
                }
                if (Main.args.multiDex) {
                    synchronized (Main.dexRotationLock) {
                        Main.maxMethodIdsInProcess -= this.maxMethodIdsInClass;
                        Main.maxFieldIdsInProcess -= this.maxFieldIdsInClass;
                        Main.dexRotationLock.notifyAll();
                    }
                }
                return true;
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof Exception) {
                    throw ((Exception) cause);
                }
                throw e;
            } catch (Throwable th) {
                if (Main.args.multiDex) {
                    synchronized (Main.dexRotationLock) {
                        Main.maxMethodIdsInProcess -= this.maxMethodIdsInClass;
                        Main.maxFieldIdsInProcess -= this.maxFieldIdsInClass;
                        Main.dexRotationLock.notifyAll();
                    }
                }
                throw th;
            }
        }
    }

    private static class DexWriter implements Callable<byte[]> {
        private DexFile dexFile;

        private DexWriter(DexFile dexFile2) {
            this.dexFile = dexFile2;
        }

        DexWriter(DexFile dexFile2, DexWriter dexWriter) {
            this(dexFile2);
        }

        @Override // java.util.concurrent.Callable
        public byte[] call() throws IOException {
            return Main.writeDex(this.dexFile);
        }
    }
}
