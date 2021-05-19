package mod.agus.jcoderz.dx.cf.direct;

import java.io.IOException;

import mod.agus.jcoderz.dx.cf.attrib.AttAnnotationDefault;
import mod.agus.jcoderz.dx.cf.attrib.AttCode;
import mod.agus.jcoderz.dx.cf.attrib.AttConstantValue;
import mod.agus.jcoderz.dx.cf.attrib.AttDeprecated;
import mod.agus.jcoderz.dx.cf.attrib.AttEnclosingMethod;
import mod.agus.jcoderz.dx.cf.attrib.AttExceptions;
import mod.agus.jcoderz.dx.cf.attrib.AttInnerClasses;
import mod.agus.jcoderz.dx.cf.attrib.AttLineNumberTable;
import mod.agus.jcoderz.dx.cf.attrib.AttLocalVariableTable;
import mod.agus.jcoderz.dx.cf.attrib.AttLocalVariableTypeTable;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttSignature;
import mod.agus.jcoderz.dx.cf.attrib.AttSourceFile;
import mod.agus.jcoderz.dx.cf.attrib.AttSynthetic;
import mod.agus.jcoderz.dx.cf.attrib.InnerClassList;
import mod.agus.jcoderz.dx.cf.code.ByteCatchList;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.code.LineNumberList;
import mod.agus.jcoderz.dx.cf.code.LocalVariableList;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.cf.iface.StdAttributeList;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public class StdAttributeFactory extends AttributeFactory {
    public static final StdAttributeFactory THE_ONE = new StdAttributeFactory();

    private static Attribute throwSeverelyTruncated() {
        throw new ParseException("severely truncated attribute");
    }

    private static Attribute throwTruncated() {
        throw new ParseException("truncated attribute");
    }

    private static Attribute throwBadLength(int i) {
        throw new ParseException("bad attribute length; expected length " + Hex.u4(i));
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.cf.direct.AttributeFactory
    public Attribute parse0(DirectClassFile directClassFile, int i, String str, int i2, int i3, ParseObserver parseObserver) {
        switch (i) {
            case 0:
                if (str == AttDeprecated.ATTRIBUTE_NAME) {
                    return deprecated(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttEnclosingMethod.ATTRIBUTE_NAME) {
                    return enclosingMethod(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttInnerClasses.ATTRIBUTE_NAME) {
                    return innerClasses(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME) {
                    return runtimeInvisibleAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME) {
                    return runtimeVisibleAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSynthetic.ATTRIBUTE_NAME) {
                    return synthetic(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSignature.ATTRIBUTE_NAME) {
                    return signature(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSourceFile.ATTRIBUTE_NAME) {
                    return sourceFile(directClassFile, i2, i3, parseObserver);
                }
                break;
            case 1:
                if (str == AttConstantValue.ATTRIBUTE_NAME) {
                    return constantValue(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttDeprecated.ATTRIBUTE_NAME) {
                    return deprecated(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME) {
                    return runtimeInvisibleAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME) {
                    return runtimeVisibleAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSignature.ATTRIBUTE_NAME) {
                    return signature(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSynthetic.ATTRIBUTE_NAME) {
                    return synthetic(directClassFile, i2, i3, parseObserver);
                }
                break;
            case 2:
                if (str == AttAnnotationDefault.ATTRIBUTE_NAME) {
                    return annotationDefault(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttCode.ATTRIBUTE_NAME) {
                    return code(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttDeprecated.ATTRIBUTE_NAME) {
                    return deprecated(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttExceptions.ATTRIBUTE_NAME) {
                    return exceptions(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME) {
                    return runtimeInvisibleAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME) {
                    return runtimeVisibleAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeInvisibleParameterAnnotations.ATTRIBUTE_NAME) {
                    return runtimeInvisibleParameterAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttRuntimeVisibleParameterAnnotations.ATTRIBUTE_NAME) {
                    return runtimeVisibleParameterAnnotations(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSignature.ATTRIBUTE_NAME) {
                    return signature(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttSynthetic.ATTRIBUTE_NAME) {
                    return synthetic(directClassFile, i2, i3, parseObserver);
                }
                break;
            case 3:
                if (str == AttLineNumberTable.ATTRIBUTE_NAME) {
                    return lineNumberTable(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttLocalVariableTable.ATTRIBUTE_NAME) {
                    return localVariableTable(directClassFile, i2, i3, parseObserver);
                }
                if (str == AttLocalVariableTypeTable.ATTRIBUTE_NAME) {
                    return localVariableTypeTable(directClassFile, i2, i3, parseObserver);
                }
                break;
        }
        return super.parse0(directClassFile, i, str, i2, i3, parseObserver);
    }

    private Attribute annotationDefault(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            throwSeverelyTruncated();
        }
        return new AttAnnotationDefault(new AnnotationParser(directClassFile, i, i2, parseObserver).parseValueAttribute(), i2);
    }

    private Attribute code(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        ByteCatchList byteCatchList;
        String human;
        if (i2 < 12) {
            return throwSeverelyTruncated();
        }
        ByteArray bytes = directClassFile.getBytes();
        ConstantPool constantPool = directClassFile.getConstantPool();
        int unsignedShort = bytes.getUnsignedShort(i);
        int unsignedShort2 = bytes.getUnsignedShort(i + 2);
        int i3 = bytes.getInt(i + 4);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "max_stack: " + Hex.u2(unsignedShort));
            parseObserver.parsed(bytes, i + 2, 2, "max_locals: " + Hex.u2(unsignedShort2));
            parseObserver.parsed(bytes, i + 4, 4, "code_length: " + Hex.u4(i3));
        }
        int i4 = i + 8;
        int i5 = i2 - 8;
        if (i5 < i3 + 4) {
            return throwTruncated();
        }
        int i6 = i4 + i3;
        int i7 = i5 - i3;
        BytecodeArray bytecodeArray = new BytecodeArray(bytes.slice(i4, i3 + i4), constantPool);
        if (parseObserver != null) {
            bytecodeArray.forEach(new CodeObserver(bytecodeArray.getBytes(), parseObserver));
        }
        int unsignedShort3 = bytes.getUnsignedShort(i6);
        if (unsignedShort3 == 0) {
            byteCatchList = ByteCatchList.EMPTY;
        } else {
            byteCatchList = new ByteCatchList(unsignedShort3);
        }
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i6, 2, "exception_table_length: " + Hex.u2(unsignedShort3));
        }
        int i8 = i6 + 2;
        int i9 = i7 - 2;
        if (i9 < (unsignedShort3 * 8) + 2) {
            return throwTruncated();
        }
        int i10 = 0;
        int i11 = i9;
        int i12 = i8;
        while (i10 < unsignedShort3) {
            if (parseObserver != null) {
                parseObserver.changeIndent(1);
            }
            int unsignedShort4 = bytes.getUnsignedShort(i12);
            int unsignedShort5 = bytes.getUnsignedShort(i12 + 2);
            int unsignedShort6 = bytes.getUnsignedShort(i12 + 4);
            CstType cstType = (CstType) constantPool.get0Ok(bytes.getUnsignedShort(i12 + 6));
            byteCatchList.set(i10, unsignedShort4, unsignedShort5, unsignedShort6, cstType);
            if (parseObserver != null) {
                StringBuilder append = new StringBuilder(Hex.u2(unsignedShort4)).append("..").append(Hex.u2(unsignedShort5)).append(" -> ").append(Hex.u2(unsignedShort6)).append(" ");
                if (cstType == null) {
                    human = "<any>";
                } else {
                    human = cstType.toHuman();
                }
                parseObserver.parsed(bytes, i12, 8, append.append(human).toString());
            }
            int i13 = i12 + 8;
            int i14 = i11 - 8;
            if (parseObserver != null) {
                parseObserver.changeIndent(-1);
            }
            i10++;
            i11 = i14;
            i12 = i13;
        }
        byteCatchList.setImmutable();
        AttributeListParser attributeListParser = new AttributeListParser(directClassFile, 3, i12, this);
        attributeListParser.setObserver(parseObserver);
        StdAttributeList list = attributeListParser.getList();
        list.setImmutable();
        int endOffset = attributeListParser.getEndOffset() - i12;
        if (endOffset != i11) {
            return throwBadLength((i12 - i) + endOffset);
        }
        return new AttCode(unsignedShort, unsignedShort2, bytecodeArray, byteCatchList, list);
    }

    private Attribute constantValue(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 != 2) {
            return throwBadLength(2);
        }
        ByteArray bytes = directClassFile.getBytes();
        TypedConstant typedConstant = (TypedConstant) directClassFile.getConstantPool().get(bytes.getUnsignedShort(i));
        AttConstantValue attConstantValue = new AttConstantValue(typedConstant);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "value: " + typedConstant);
        }
        return attConstantValue;
    }

    private Attribute deprecated(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 != 0) {
            return throwBadLength(0);
        }
        return new AttDeprecated();
    }

    private Attribute enclosingMethod(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 != 4) {
            throwBadLength(4);
        }
        ByteArray bytes = directClassFile.getBytes();
        ConstantPool constantPool = directClassFile.getConstantPool();
        CstType cstType = (CstType) constantPool.get(bytes.getUnsignedShort(i));
        CstNat cstNat = (CstNat) constantPool.get0Ok(bytes.getUnsignedShort(i + 2));
        AttEnclosingMethod attEnclosingMethod = new AttEnclosingMethod(cstType, cstNat);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "class: " + cstType);
            parseObserver.parsed(bytes, i + 2, 2, "method: " + DirectClassFile.stringOrNone(cstNat));
        }
        return attEnclosingMethod;
    }

    private Attribute exceptions(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            return throwSeverelyTruncated();
        }
        ByteArray bytes = directClassFile.getBytes();
        int unsignedShort = bytes.getUnsignedShort(i);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "number_of_exceptions: " + Hex.u2(unsignedShort));
        }
        int i3 = i + 2;
        if (i2 - 2 != unsignedShort * 2) {
            throwBadLength((unsignedShort * 2) + 2);
        }
        return new AttExceptions(directClassFile.makeTypeList(i3, unsignedShort));
    }

    private Attribute innerClasses(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            return throwSeverelyTruncated();
        }
        ByteArray bytes = directClassFile.getBytes();
        ConstantPool constantPool = directClassFile.getConstantPool();
        int unsignedShort = bytes.getUnsignedShort(i);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "number_of_classes: " + Hex.u2(unsignedShort));
        }
        int i3 = i + 2;
        if (i2 - 2 != unsignedShort * 8) {
            throwBadLength((unsignedShort * 8) + 2);
        }
        InnerClassList innerClassList = new InnerClassList(unsignedShort);
        int i4 = 0;
        int i5 = i3;
        while (i4 < unsignedShort) {
            int unsignedShort2 = bytes.getUnsignedShort(i5);
            int unsignedShort3 = bytes.getUnsignedShort(i5 + 2);
            int unsignedShort4 = bytes.getUnsignedShort(i5 + 4);
            int unsignedShort5 = bytes.getUnsignedShort(i5 + 6);
            CstType cstType = (CstType) constantPool.get(unsignedShort2);
            CstType cstType2 = (CstType) constantPool.get0Ok(unsignedShort3);
            CstString cstString = (CstString) constantPool.get0Ok(unsignedShort4);
            innerClassList.set(i4, cstType, cstType2, cstString, unsignedShort5);
            if (parseObserver != null) {
                parseObserver.parsed(bytes, i5, 2, "inner_class: " + DirectClassFile.stringOrNone(cstType));
                parseObserver.parsed(bytes, i5 + 2, 2, "  outer_class: " + DirectClassFile.stringOrNone(cstType2));
                parseObserver.parsed(bytes, i5 + 4, 2, "  name: " + DirectClassFile.stringOrNone(cstString));
                parseObserver.parsed(bytes, i5 + 6, 2, "  access_flags: " + AccessFlags.innerClassString(unsignedShort5));
            }
            i4++;
            i5 += 8;
        }
        innerClassList.setImmutable();
        return new AttInnerClasses(innerClassList);
    }

    private Attribute lineNumberTable(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            return throwSeverelyTruncated();
        }
        ByteArray bytes = directClassFile.getBytes();
        int unsignedShort = bytes.getUnsignedShort(i);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "line_number_table_length: " + Hex.u2(unsignedShort));
        }
        int i3 = i + 2;
        if (i2 - 2 != unsignedShort * 4) {
            throwBadLength((unsignedShort * 4) + 2);
        }
        LineNumberList lineNumberList = new LineNumberList(unsignedShort);
        for (int i4 = 0; i4 < unsignedShort; i4++) {
            int unsignedShort2 = bytes.getUnsignedShort(i3);
            int unsignedShort3 = bytes.getUnsignedShort(i3 + 2);
            lineNumberList.set(i4, unsignedShort2, unsignedShort3);
            if (parseObserver != null) {
                parseObserver.parsed(bytes, i3, 4, Hex.u2(unsignedShort2) + " " + unsignedShort3);
            }
            i3 += 4;
        }
        lineNumberList.setImmutable();
        return new AttLineNumberTable(lineNumberList);
    }

    private Attribute localVariableTable(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            return throwSeverelyTruncated();
        }
        ByteArray bytes = directClassFile.getBytes();
        int unsignedShort = bytes.getUnsignedShort(i);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "local_variable_table_length: " + Hex.u2(unsignedShort));
        }
        return new AttLocalVariableTable(parseLocalVariables(bytes.slice(i + 2, i + i2), directClassFile.getConstantPool(), parseObserver, unsignedShort, false));
    }

    private Attribute localVariableTypeTable(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            return throwSeverelyTruncated();
        }
        ByteArray bytes = directClassFile.getBytes();
        int unsignedShort = bytes.getUnsignedShort(i);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "local_variable_type_table_length: " + Hex.u2(unsignedShort));
        }
        return new AttLocalVariableTypeTable(parseLocalVariables(bytes.slice(i + 2, i + i2), directClassFile.getConstantPool(), parseObserver, unsignedShort, true));
    }

    private LocalVariableList parseLocalVariables(ByteArray byteArray, ConstantPool constantPool, ParseObserver parseObserver, int i, boolean z) {
        if (byteArray.size() != i * 10) {
            throwBadLength((i * 10) + 2);
        }
        ByteArray.MyDataInputStream makeDataInputStream = byteArray.makeDataInputStream();
        LocalVariableList localVariableList = new LocalVariableList(i);
        for (int i2 = 0; i2 < i; i2++) {
            try {
                int readUnsignedShort = makeDataInputStream.readUnsignedShort();
                int readUnsignedShort2 = makeDataInputStream.readUnsignedShort();
                int readUnsignedShort3 = makeDataInputStream.readUnsignedShort();
                int readUnsignedShort4 = makeDataInputStream.readUnsignedShort();
                int readUnsignedShort5 = makeDataInputStream.readUnsignedShort();
                CstString cstString = (CstString) constantPool.get(readUnsignedShort3);
                CstString cstString2 = (CstString) constantPool.get(readUnsignedShort4);
                CstString cstString3 = null;
                CstString cstString4 = null;
                if (z) {
                    cstString4 = cstString2;
                } else {
                    cstString3 = cstString2;
                }
                localVariableList.set(i2, readUnsignedShort, readUnsignedShort2, cstString, cstString3, cstString4, readUnsignedShort5);
                if (parseObserver != null) {
                    parseObserver.parsed(byteArray, i2 * 10, 10, Hex.u2(readUnsignedShort) + ".." + Hex.u2(readUnsignedShort + readUnsignedShort2) + " " + Hex.u2(readUnsignedShort5) + " " + cstString.toHuman() + " " + cstString2.toHuman());
                }
            } catch (IOException e) {
                throw new RuntimeException("shouldn't happen", e);
            }
        }
        localVariableList.setImmutable();
        return localVariableList;
    }

    private Attribute runtimeInvisibleAnnotations(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            throwSeverelyTruncated();
        }
        return new AttRuntimeInvisibleAnnotations(new AnnotationParser(directClassFile, i, i2, parseObserver).parseAnnotationAttribute(AnnotationVisibility.BUILD), i2);
    }

    private Attribute runtimeVisibleAnnotations(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            throwSeverelyTruncated();
        }
        return new AttRuntimeVisibleAnnotations(new AnnotationParser(directClassFile, i, i2, parseObserver).parseAnnotationAttribute(AnnotationVisibility.RUNTIME), i2);
    }

    private Attribute runtimeInvisibleParameterAnnotations(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            throwSeverelyTruncated();
        }
        return new AttRuntimeInvisibleParameterAnnotations(new AnnotationParser(directClassFile, i, i2, parseObserver).parseParameterAttribute(AnnotationVisibility.BUILD), i2);
    }

    private Attribute runtimeVisibleParameterAnnotations(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 < 2) {
            throwSeverelyTruncated();
        }
        return new AttRuntimeVisibleParameterAnnotations(new AnnotationParser(directClassFile, i, i2, parseObserver).parseParameterAttribute(AnnotationVisibility.RUNTIME), i2);
    }

    private Attribute signature(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 != 2) {
            throwBadLength(2);
        }
        ByteArray bytes = directClassFile.getBytes();
        CstString cstString = (CstString) directClassFile.getConstantPool().get(bytes.getUnsignedShort(i));
        AttSignature attSignature = new AttSignature(cstString);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "signature: " + cstString);
        }
        return attSignature;
    }

    private Attribute sourceFile(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 != 2) {
            throwBadLength(2);
        }
        ByteArray bytes = directClassFile.getBytes();
        CstString cstString = (CstString) directClassFile.getConstantPool().get(bytes.getUnsignedShort(i));
        AttSourceFile attSourceFile = new AttSourceFile(cstString);
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i, 2, "source: " + cstString);
        }
        return attSourceFile;
    }

    private Attribute synthetic(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (i2 != 0) {
            return throwBadLength(0);
        }
        return new AttSynthetic();
    }
}
