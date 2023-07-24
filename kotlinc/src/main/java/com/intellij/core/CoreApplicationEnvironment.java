/*
 * This file is part of Cosmic IDE.
 * Cosmic IDE is a free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * Cosmic IDE is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.intellij.core;

import com.intellij.codeInsight.folding.CodeFoldingSettings;
import com.intellij.concurrency.JobLauncher;
import com.intellij.ide.plugins.DisabledPluginsState;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.lang.DefaultASTFactory;
import com.intellij.lang.DefaultASTFactoryImpl;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageExtension;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.impl.PsiBuilderFactoryImpl;
import com.intellij.mock.MockApplication;
import com.intellij.mock.MockFileDocumentManagerImpl;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.impl.ApplicationInfoImpl;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.impl.CoreCommandProcessor;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.extensions.BaseExtensionPointName;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.ExtensionPoint.Kind;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.extensions.ExtensionsArea;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeExtension;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.impl.CoreProgressManager;
import com.intellij.openapi.util.ClassExtension;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.VirtualFileManagerListener;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.encoding.EncodingManager;
import com.intellij.openapi.vfs.impl.CoreVirtualFilePointerManager;
import com.intellij.openapi.vfs.impl.VirtualFileManagerImpl;
import com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem;
import com.intellij.openapi.vfs.local.CoreLocalFileSystem;
import com.intellij.openapi.vfs.pointers.VirtualFilePointerManager;
import com.intellij.psi.PsiReferenceService;
import com.intellij.psi.PsiReferenceServiceImpl;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistryImpl;
import com.intellij.psi.stubs.CoreStubTreeLoader;
import com.intellij.psi.stubs.StubTreeLoader;
import com.intellij.util.KeyedLazyInstanceEP;
import com.intellij.util.graph.GraphAlgorithms;
import com.intellij.util.graph.impl.GraphAlgorithmsImpl;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.resolve.diagnostics.DiagnosticSuppressor;
import org.picocontainer.MutablePicoContainer;

import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CoreApplicationEnvironment {
    protected final MockApplication myApplication;
    protected final VirtualFileSystem myJarFileSystem;
    private final CoreFileTypeRegistry myFileTypeRegistry;
    private final CoreLocalFileSystem myLocalFileSystem;
    private final VirtualFileSystem myJrtFileSystem;

    private final Disposable myParentDisposable;
    private final boolean myUnitTestMode;

    public CoreApplicationEnvironment(Disposable parentDisposable) {
        this(parentDisposable, true);
    }

    public CoreApplicationEnvironment(Disposable parentDisposable, boolean unitTestMode) {
        super();
        this.myParentDisposable = parentDisposable;
        this.myUnitTestMode = unitTestMode;
        DisabledPluginsState.dontLoadDisabledPlugins();
        this.myFileTypeRegistry = new CoreFileTypeRegistry();
        this.myApplication = this.createApplication(this.myParentDisposable);
        ApplicationManager.setApplication(
                this.myApplication, () -> this.myFileTypeRegistry, this.myParentDisposable);
        this.myLocalFileSystem = this.createLocalFileSystem();
        this.myJarFileSystem = this.createJarFileSystem();
        this.myJrtFileSystem = this.createJrtFileSystem();
        this.registerApplicationService(
                FileDocumentManager.class,
                new MockFileDocumentManagerImpl(null, DocumentImpl::new));
        registerApplicationExtensionPoint(
                new ExtensionPointName<>("com.intellij.virtualFileManagerListener"),
                VirtualFileManagerListener.class);
        List<VirtualFileSystem> fs =
                this.myJrtFileSystem != null
                        ? Arrays.asList(
                        this.myLocalFileSystem, this.myJarFileSystem, this.myJrtFileSystem)
                        : Arrays.asList(this.myLocalFileSystem, this.myJarFileSystem);
        this.registerApplicationService(VirtualFileManager.class, new VirtualFileManagerImpl(fs));
        registerApplicationExtensionPoint(
                new ExtensionPointName<>("com.intellij.virtualFileSystem"),
                KeyedLazyInstanceEP.class);
        registerApplicationExtensionPoint(
                DiagnosticSuppressor.Companion.getEP_NAME(), DiagnosticSuppressor.class);
        this.registerApplicationService(EncodingManager.class, new CoreEncodingRegistry());
        this.registerApplicationService(
                VirtualFilePointerManager.class, this.createVirtualFilePointerManager());
        this.registerApplicationService(DefaultASTFactory.class, new DefaultASTFactoryImpl());
        this.registerApplicationService(PsiBuilderFactory.class, new PsiBuilderFactoryImpl());
        this.registerApplicationService(
                ReferenceProvidersRegistry.class, new ReferenceProvidersRegistryImpl());
        this.registerApplicationService(StubTreeLoader.class, new CoreStubTreeLoader());
        this.registerApplicationService(PsiReferenceService.class, new PsiReferenceServiceImpl());
        this.registerApplicationService(
                ProgressManager.class, this.createProgressIndicatorProvider());
        this.registerApplicationService(JobLauncher.class, this.createJobLauncher());
        this.registerApplicationService(CodeFoldingSettings.class, new CodeFoldingSettings());
        this.registerApplicationService(CommandProcessor.class, new CoreCommandProcessor());
        this.registerApplicationService(GraphAlgorithms.class, new GraphAlgorithmsImpl());
        this.myApplication.registerService(ApplicationInfo.class, ApplicationInfoImpl.class);
    }

    public static <T> void registerComponentInstance(
            MutablePicoContainer container, Class<T> key, T implementation) {
        container.unregisterComponent(key);
        container.registerComponentInstance(key, implementation);
    }

    public static <T> void registerExtensionPoint(
            ExtensionsArea area,
            ExtensionPointName<T> extensionPointName,
            Class<? extends T> aClass) {
        registerExtensionPoint(area, extensionPointName.getName(), aClass);
    }

    public static <T> void registerExtensionPoint(
            ExtensionsArea area,
            BaseExtensionPointName<T> extensionPointName,
            Class<? extends T> aClass) {
        registerExtensionPoint(area, extensionPointName.getName(), aClass);
    }

    public static <T> void registerExtensionPoint(
            ExtensionsArea area, String name, Class<? extends T> aClass) {
        if (!area.hasExtensionPoint(name)) {
            Kind kind =
                    !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers())
                            ? Kind.BEAN_CLASS
                            : Kind.INTERFACE;
            area.registerExtensionPoint(name, aClass.getName(), kind);
        }
    }

    public static <T> void registerDynamicExtensionPoint(
            ExtensionsArea area, String name, Class<? extends T> aClass) {
        if (!area.hasExtensionPoint(name)) {
            Kind kind =
                    !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers())
                            ? Kind.BEAN_CLASS
                            : Kind.INTERFACE;
            area.registerDynamicExtensionPoint(name, aClass.getName(), kind);
        }
    }

    public static <T> void registerApplicationDynamicExtensionPoint(String name, Class<T> clazz) {
        registerDynamicExtensionPoint(Extensions.getRootArea(), name, clazz);
    }

    public static <T> void registerApplicationExtensionPoint(
            ExtensionPointName<T> extensionPointName, Class<? extends T> aClass) {
        registerExtensionPoint(Extensions.getRootArea(), extensionPointName, aClass);
    }

    public static void registerExtensionPointAndExtensions(
            Path pluginRoot, String fileName, ExtensionsArea area) {
        PluginManagerCore.registerExtensionPointAndExtensions(pluginRoot, fileName, area);
    }

    public <T> void registerApplicationService(Class<T> serviceInterface, T serviceImplementation) {
        this.myApplication.registerService(serviceInterface, serviceImplementation);
    }

    protected VirtualFilePointerManager createVirtualFilePointerManager() {
        return new CoreVirtualFilePointerManager();
    }

    protected MockApplication createApplication(Disposable parentDisposable) {
        return new MockApplication(parentDisposable) {
            public boolean isUnitTestMode() {
                return CoreApplicationEnvironment.this.myUnitTestMode;
            }
        };
    }

    protected JobLauncher createJobLauncher() {
        return new JobLauncher() {
        };
    }

    protected ProgressManager createProgressIndicatorProvider() {
        return new CoreProgressManager();
    }

    protected VirtualFileSystem createJarFileSystem() {
        return new CoreJarFileSystem();
    }

    protected CoreLocalFileSystem createLocalFileSystem() {
        return new CoreLocalFileSystem();
    }

    @Nullable
    protected VirtualFileSystem createJrtFileSystem() {
        return null;
    }

    public MockApplication getApplication() {
        return this.myApplication;
    }

    public Disposable getParentDisposable() {
        return this.myParentDisposable;
    }

    public <T> void registerApplicationComponent(Class<T> interfaceClass, T implementation) {
        registerComponentInstance(
                this.myApplication.getPicoContainer(), interfaceClass, implementation);
        if (implementation instanceof Disposable) {
            Disposer.register(this.myApplication, (Disposable) implementation);
        }
    }

    public void registerFileType(FileType fileType, String extension) {
        this.myFileTypeRegistry.registerFileType(fileType, extension);
    }

    public void registerParserDefinition(ParserDefinition definition) {

        this.addExplicitExtension(
                LanguageParserDefinitions.INSTANCE,
                definition.getFileNodeType().getLanguage(),
                definition);
    }

    public <T> void addExplicitExtension(
            LanguageExtension<T> instance, Language language, T object) {
        instance.addExplicitExtension(language, object, this.myParentDisposable);
    }

    public void registerParserDefinition(Language language, ParserDefinition parserDefinition) {

        this.addExplicitExtension(
                LanguageParserDefinitions.INSTANCE, language, parserDefinition);
    }

    public <T> void addExplicitExtension(
            FileTypeExtension<T> instance, FileType fileType, T object) {
        instance.addExplicitExtension(fileType, object, this.myParentDisposable);
    }

    public <T> void addExplicitExtension(ClassExtension<T> instance, Class<T> aClass, T object) {
        instance.addExplicitExtension(aClass, object, this.myParentDisposable);
    }

    public <T> void addExtension(ExtensionPointName<T> name, T extension) {
        ExtensionPoint<T> extensionPoint = Extensions.getRootArea().getExtensionPoint(name);
        extensionPoint.registerExtension(extension, this.myParentDisposable);
    }

    public CoreLocalFileSystem getLocalFileSystem() {
        return this.myLocalFileSystem;
    }

    public VirtualFileSystem getJarFileSystem() {
        return this.myJarFileSystem;
    }

    @Nullable
    public VirtualFileSystem getJrtFileSystem() {
        return this.myJrtFileSystem;
    }
}