package mod.agus.jcoderz.dx.cf.direct;

import java.io.IOException;

import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstAnnotation;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstBoolean;
import mod.agus.jcoderz.dx.rop.cst.CstByte;
import mod.agus.jcoderz.dx.rop.cst.CstChar;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstEnumRef;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstShort;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class AnnotationParser {
    private final ByteArray bytes;
    private final DirectClassFile cf;
    private final ByteArray.MyDataInputStream input;
    private final ParseObserver observer;
    private final ConstantPool pool;
    private int parseCursor;

    public AnnotationParser(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (directClassFile == null) {
            throw new NullPointerException("cf == null");
        }
        this.cf = directClassFile;
        this.pool = directClassFile.getConstantPool();
        this.observer = parseObserver;
        this.bytes = directClassFile.getBytes().slice(i, i + i2);
        this.input = this.bytes.makeDataInputStream();
        this.parseCursor = 0;
    }

    public Constant parseValueAttribute() {
        try {
            Constant parseValue = parseValue();
            if (this.input.available() == 0) {
                return parseValue;
            }
            throw new ParseException("extra data in attribute");
        } catch (IOException e) {
            throw new RuntimeException("shouldn't happen", e);
        }
    }

    public AnnotationsList parseParameterAttribute(AnnotationVisibility annotationVisibility) {
        try {
            AnnotationsList parseAnnotationsList = parseAnnotationsList(annotationVisibility);
            if (this.input.available() == 0) {
                return parseAnnotationsList;
            }
            throw new ParseException("extra data in attribute");
        } catch (IOException e) {
            throw new RuntimeException("shouldn't happen", e);
        }
    }

    public Annotations parseAnnotationAttribute(AnnotationVisibility annotationVisibility) {
        try {
            Annotations parseAnnotations = parseAnnotations(annotationVisibility);
            if (this.input.available() == 0) {
                return parseAnnotations;
            }
            throw new ParseException("extra data in attribute");
        } catch (IOException e) {
            throw new RuntimeException("shouldn't happen", e);
        }
    }

    private AnnotationsList parseAnnotationsList(AnnotationVisibility annotationVisibility) throws IOException {
        int readUnsignedByte = this.input.readUnsignedByte();
        if (this.observer != null) {
            parsed(1, "num_parameters: " + Hex.u1(readUnsignedByte));
        }
        AnnotationsList annotationsList = new AnnotationsList(readUnsignedByte);
        for (int i = 0; i < readUnsignedByte; i++) {
            if (this.observer != null) {
                parsed(0, "parameter_annotations[" + i + "]:");
                changeIndent(1);
            }
            annotationsList.set(i, parseAnnotations(annotationVisibility));
            if (this.observer != null) {
                this.observer.changeIndent(-1);
            }
        }
        annotationsList.setImmutable();
        return annotationsList;
    }

    private Annotations parseAnnotations(AnnotationVisibility annotationVisibility) throws IOException {
        int readUnsignedShort = this.input.readUnsignedShort();
        if (this.observer != null) {
            parsed(2, "num_annotations: " + Hex.u2(readUnsignedShort));
        }
        Annotations annotations = new Annotations();
        for (int i = 0; i < readUnsignedShort; i++) {
            if (this.observer != null) {
                parsed(0, "annotations[" + i + "]:");
                changeIndent(1);
            }
            annotations.add(parseAnnotation(annotationVisibility));
            if (this.observer != null) {
                this.observer.changeIndent(-1);
            }
        }
        annotations.setImmutable();
        return annotations;
    }

    private Annotation parseAnnotation(AnnotationVisibility annotationVisibility) throws IOException {
        requireLength(4);
        int readUnsignedShort = this.input.readUnsignedShort();
        int readUnsignedShort2 = this.input.readUnsignedShort();
        CstType cstType = new CstType(Type.intern(((CstString) this.pool.get(readUnsignedShort)).getString()));
        if (this.observer != null) {
            parsed(2, "type: " + cstType.toHuman());
            parsed(2, "num_elements: " + readUnsignedShort2);
        }
        Annotation annotation = new Annotation(cstType, annotationVisibility);
        for (int i = 0; i < readUnsignedShort2; i++) {
            if (this.observer != null) {
                parsed(0, "elements[" + i + "]:");
                changeIndent(1);
            }
            annotation.add(parseElement());
            if (this.observer != null) {
                changeIndent(-1);
            }
        }
        annotation.setImmutable();
        return annotation;
    }

    private NameValuePair parseElement() throws IOException {
        requireLength(5);
        CstString cstString = (CstString) this.pool.get(this.input.readUnsignedShort());
        if (this.observer != null) {
            parsed(2, "element_name: " + cstString.toHuman());
            parsed(0, "value: ");
            changeIndent(1);
        }
        Constant parseValue = parseValue();
        if (this.observer != null) {
            changeIndent(-1);
        }
        return new NameValuePair(cstString, parseValue);
    }

    private Constant parseValue() throws IOException {
        int readUnsignedByte = this.input.readUnsignedByte();
        if (this.observer != null) {
            parsed(1, "tag: " + new CstString(Character.toString((char) readUnsignedByte)).toQuoted());
        }
        switch (readUnsignedByte) {
            case 64:
                return new CstAnnotation(parseAnnotation(AnnotationVisibility.EMBEDDED));
            case 66:
                return CstByte.make(((CstInteger) parseConstant()).getValue());
            case 67:
                CstInteger cstInteger = (CstInteger) parseConstant();
                cstInteger.getValue();
                return CstChar.make(cstInteger.getValue());
            case 68:
                return (CstDouble) parseConstant();
            case 70:
                return (CstFloat) parseConstant();
            case 73:
                return (CstInteger) parseConstant();
            case 74:
                return (CstLong) parseConstant();
            case 83:
                return CstShort.make(((CstInteger) parseConstant()).getValue());
            case 90:
                return CstBoolean.make(((CstInteger) parseConstant()).getValue());
            case 91:
                requireLength(2);
                int readUnsignedShort = this.input.readUnsignedShort();
                CstArray.List list = new CstArray.List(readUnsignedShort);
                if (this.observer != null) {
                    parsed(2, "num_values: " + readUnsignedShort);
                    changeIndent(1);
                }
                for (int i = 0; i < readUnsignedShort; i++) {
                    if (this.observer != null) {
                        changeIndent(-1);
                        parsed(0, "element_value[" + i + "]:");
                        changeIndent(1);
                    }
                    list.set(i, parseValue());
                }
                if (this.observer != null) {
                    changeIndent(-1);
                }
                list.setImmutable();
                return new CstArray(list);
            case 99:
                Type internReturnType = Type.internReturnType(((CstString) this.pool.get(this.input.readUnsignedShort())).getString());
                if (this.observer != null) {
                    parsed(2, "class_info: " + internReturnType.toHuman());
                }
                return new CstType(internReturnType);
            case 101:
                requireLength(4);
                int readUnsignedShort2 = this.input.readUnsignedShort();
                int readUnsignedShort3 = this.input.readUnsignedShort();
                CstString cstString = (CstString) this.pool.get(readUnsignedShort2);
                CstString cstString2 = (CstString) this.pool.get(readUnsignedShort3);
                if (this.observer != null) {
                    parsed(2, "type_name: " + cstString.toHuman());
                    parsed(2, "const_name: " + cstString2.toHuman());
                }
                return new CstEnumRef(new CstNat(cstString2, cstString));
            case 115:
                return parseConstant();
            default:
                throw new ParseException("unknown annotation tag: " + Hex.u1(readUnsignedByte));
        }
    }

    private Constant parseConstant() throws IOException {
        String human;
        Constant constant = this.pool.get(this.input.readUnsignedShort());
        if (this.observer != null) {
            if (constant instanceof CstString) {
                human = ((CstString) constant).toQuoted();
            } else {
                human = constant.toHuman();
            }
            parsed(2, "constant_value: " + human);
        }
        return constant;
    }

    private void requireLength(int i) throws IOException {
        if (this.input.available() < i) {
            throw new ParseException("truncated annotation attribute");
        }
    }

    private void parsed(int i, String str) {
        this.observer.parsed(this.bytes, this.parseCursor, i, str);
        this.parseCursor += i;
    }

    private void changeIndent(int i) {
        this.observer.changeIndent(i);
    }
}
