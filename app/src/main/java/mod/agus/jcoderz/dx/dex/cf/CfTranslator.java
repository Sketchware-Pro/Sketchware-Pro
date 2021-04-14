package mod.agus.jcoderz.dx.dex.cf;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.cf.code.ConcreteMethod;
import mod.agus.jcoderz.dx.cf.code.Ropper;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.iface.Field;
import mod.agus.jcoderz.dx.cf.iface.FieldList;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.dex.code.RopTranslator;
import mod.agus.jcoderz.dx.dex.file.ClassDefItem;
import mod.agus.jcoderz.dx.dex.file.DexFile;
import mod.agus.jcoderz.dx.dex.file.EncodedField;
import mod.agus.jcoderz.dx.dex.file.EncodedMethod;
import mod.agus.jcoderz.dx.dex.file.FieldIdsSection;
import mod.agus.jcoderz.dx.dex.file.MethodIdsSection;
import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.code.DexTranslationAdvice;
import mod.agus.jcoderz.dx.rop.code.LocalVariableExtractor;
import mod.agus.jcoderz.dx.rop.code.LocalVariableInfo;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstBoolean;
import mod.agus.jcoderz.dx.rop.cst.CstByte;
import mod.agus.jcoderz.dx.rop.cst.CstChar;
import mod.agus.jcoderz.dx.rop.cst.CstEnumRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstInterfaceMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstShort;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.ssa.Optimizer;

public class CfTranslator {
    private static final boolean DEBUG = false;

    private CfTranslator() {
    }

    public static ClassDefItem translate(DirectClassFile directClassFile, byte[] bArr, CfOptions cfOptions, DexOptions dexOptions, DexFile dexFile) {
        try {
            return translate0(directClassFile, bArr, cfOptions, dexOptions, dexFile);
        } catch (RuntimeException e) {
            throw ExceptionWithContext.withContext(e, "...while processing " + directClassFile.getFilePath());
        }
    }

    private static ClassDefItem translate0(DirectClassFile directClassFile, byte[] bArr, CfOptions cfOptions, DexOptions dexOptions, DexFile dexFile) {
        CstString sourceFile;
        OptimizerOptions.loadOptimizeLists(cfOptions.optimizeListFile, cfOptions.dontOptimizeListFile);
        CstType thisClass = directClassFile.getThisClass();
        int accessFlags = directClassFile.getAccessFlags() & -33;
        if (cfOptions.positionInfo == 1) {
            sourceFile = null;
        } else {
            sourceFile = directClassFile.getSourceFile();
        }
        ClassDefItem classDefItem = new ClassDefItem(thisClass, accessFlags, directClassFile.getSuperclass(), directClassFile.getInterfaces(), sourceFile);
        Annotations classAnnotations = AttributeTranslator.getClassAnnotations(directClassFile, cfOptions);
        if (classAnnotations.size() != 0) {
            classDefItem.setClassAnnotations(classAnnotations, dexFile);
        }
        FieldIdsSection fieldIds = dexFile.getFieldIds();
        MethodIdsSection methodIds = dexFile.getMethodIds();
        processFields(directClassFile, classDefItem, dexFile);
        processMethods(directClassFile, cfOptions, dexOptions, classDefItem, dexFile);
        ConstantPool constantPool = directClassFile.getConstantPool();
        int size = constantPool.size();
        for (int i = 0; i < size; i++) {
            Constant orNull = constantPool.getOrNull(i);
            if (orNull instanceof CstMethodRef) {
                methodIds.intern((CstBaseMethodRef) orNull);
            } else if (orNull instanceof CstInterfaceMethodRef) {
                methodIds.intern(((CstInterfaceMethodRef) orNull).toMethodRef());
            } else if (orNull instanceof CstFieldRef) {
                fieldIds.intern((CstFieldRef) orNull);
            } else if (orNull instanceof CstEnumRef) {
                fieldIds.intern(((CstEnumRef) orNull).getFieldRef());
            }
        }
        return classDefItem;
    }

    private static void processFields(DirectClassFile directClassFile, ClassDefItem classDefItem, DexFile dexFile) {
        CstType thisClass = directClassFile.getThisClass();
        FieldList fields = directClassFile.getFields();
        int size = fields.size();
        for (int i = 0; i < size; i++) {
            Field field = fields.get(i);
            try {
                CstFieldRef cstFieldRef = new CstFieldRef(thisClass, field.getNat());
                int accessFlags = field.getAccessFlags();
                if (AccessFlags.isStatic(accessFlags)) {
                    TypedConstant constantValue = field.getConstantValue();
                    EncodedField encodedField = new EncodedField(cstFieldRef, accessFlags);
                    if (constantValue != null) {
                        constantValue = coerceConstant(constantValue, cstFieldRef.getType());
                    }
                    classDefItem.addStaticField(encodedField, constantValue);
                } else {
                    classDefItem.addInstanceField(new EncodedField(cstFieldRef, accessFlags));
                }
                Annotations annotations = AttributeTranslator.getAnnotations(field.getAttributes());
                if (annotations.size() != 0) {
                    classDefItem.addFieldAnnotations(cstFieldRef, annotations, dexFile);
                }
                dexFile.getFieldIds().intern(cstFieldRef);
            } catch (RuntimeException e) {
                throw ExceptionWithContext.withContext(e, "...while processing " + field.getName().toHuman() + " " + field.getDescriptor().toHuman());
            }
        }
    }

    private static TypedConstant coerceConstant(TypedConstant typedConstant, Type type) {
        if (typedConstant.getType().equals(type)) {
            return typedConstant;
        }
        switch (type.getBasicType()) {
            case 1:
                return CstBoolean.make(((CstInteger) typedConstant).getValue());
            case 2:
                return CstByte.make(((CstInteger) typedConstant).getValue());
            case 3:
                return CstChar.make(((CstInteger) typedConstant).getValue());
            case 4:
            case 5:
            case 6:
            case 7:
            default:
                throw new UnsupportedOperationException("can't coerce " + typedConstant + " to " + type);
            case 8:
                return CstShort.make(((CstInteger) typedConstant).getValue());
        }
    }

    private static void processMethods(DirectClassFile directClassFile, CfOptions cfOptions, DexOptions dexOptions, ClassDefItem classDefItem, DexFile dexFile) {
        DalvCode dalvCode;
        int i;
        RopMethod ropMethod;
        CstType thisClass = directClassFile.getThisClass();
        MethodList methods = directClassFile.getMethods();
        int size = methods.size();
        for (int i2 = 0; i2 < size; i2++) {
            Method method = methods.get(i2);
            try {
                CstMethodRef cstMethodRef = new CstMethodRef(thisClass, method.getNat());
                int accessFlags = method.getAccessFlags();
                boolean isStatic = AccessFlags.isStatic(accessFlags);
                boolean isPrivate = AccessFlags.isPrivate(accessFlags);
                boolean isNative = AccessFlags.isNative(accessFlags);
                boolean isAbstract = AccessFlags.isAbstract(accessFlags);
                boolean z = cstMethodRef.isInstanceInit() || cstMethodRef.isClassInit();
                if (isNative || isAbstract) {
                    dalvCode = null;
                } else {
                    ConcreteMethod concreteMethod = new ConcreteMethod(method, directClassFile, cfOptions.positionInfo != 1, cfOptions.localInfo);
                    DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
                    RopMethod convert = Ropper.convert(concreteMethod, dexTranslationAdvice, methods);
                    int parameterWordCount = cstMethodRef.getParameterWordCount(isStatic);
                    String str = String.valueOf(thisClass.getClassType().getDescriptor()) + "." + method.getName().getString();
                    if (!cfOptions.optimize || !OptimizerOptions.shouldOptimize(str)) {
                        ropMethod = convert;
                        convert = null;
                    } else {
                        ropMethod = Optimizer.optimize(convert, parameterWordCount, isStatic, cfOptions.localInfo, dexTranslationAdvice);
                        if (cfOptions.statistics) {
                            CodeStatistics.updateRopStatistics(convert, ropMethod);
                        }
                    }
                    LocalVariableInfo localVariableInfo = null;
                    if (cfOptions.localInfo) {
                        localVariableInfo = LocalVariableExtractor.extract(ropMethod);
                    }
                    DalvCode translate = RopTranslator.translate(ropMethod, cfOptions.positionInfo, localVariableInfo, parameterWordCount, dexOptions);
                    if (cfOptions.statistics && convert != null) {
                        updateDexStatistics(cfOptions, dexOptions, ropMethod, convert, localVariableInfo, parameterWordCount, concreteMethod.getCode().size());
                    }
                    dalvCode = translate;
                }
                if (AccessFlags.isSynchronized(accessFlags)) {
                    i = 131072 | accessFlags;
                    if (!isNative) {
                        i &= -33;
                    }
                } else {
                    i = accessFlags;
                }
                if (z) {
                    i |= 65536;
                }
                EncodedMethod encodedMethod = new EncodedMethod(cstMethodRef, i, dalvCode, AttributeTranslator.getExceptions(method));
                if (cstMethodRef.isInstanceInit() || cstMethodRef.isClassInit() || isStatic || isPrivate) {
                    classDefItem.addDirectMethod(encodedMethod);
                } else {
                    classDefItem.addVirtualMethod(encodedMethod);
                }
                Annotations methodAnnotations = AttributeTranslator.getMethodAnnotations(method);
                if (methodAnnotations.size() != 0) {
                    classDefItem.addMethodAnnotations(cstMethodRef, methodAnnotations, dexFile);
                }
                AnnotationsList parameterAnnotations = AttributeTranslator.getParameterAnnotations(method);
                if (parameterAnnotations.size() != 0) {
                    classDefItem.addParameterAnnotations(cstMethodRef, parameterAnnotations, dexFile);
                }
                dexFile.getMethodIds().intern(cstMethodRef);
            } catch (RuntimeException e) {
                throw ExceptionWithContext.withContext(e, "...while processing " + method.getName().toHuman() + " " + method.getDescriptor().toHuman());
            }
        }
    }

    private static void updateDexStatistics(CfOptions cfOptions, DexOptions dexOptions, RopMethod ropMethod, RopMethod ropMethod2, LocalVariableInfo localVariableInfo, int i, int i2) {
        DalvCode translate = RopTranslator.translate(ropMethod, cfOptions.positionInfo, localVariableInfo, i, dexOptions);
        DalvCode translate2 = RopTranslator.translate(ropMethod2, cfOptions.positionInfo, localVariableInfo, i, dexOptions);
        DalvCode.AssignIndicesCallback assignIndicesCallback = new DalvCode.AssignIndicesCallback() {

            @Override // mod.agus.jcoderz.dx.dex.code.DalvCode.AssignIndicesCallback
            public int getIndex(Constant constant) {
                return 0;
            }
        };
        translate.assignIndices(assignIndicesCallback);
        translate2.assignIndices(assignIndicesCallback);
        CodeStatistics.updateDexStatistics(translate2, translate);
        CodeStatistics.updateOriginalByteCount(i2);
    }
}
