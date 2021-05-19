package mod.agus.jcoderz.dx.command.annotool;

import org.eclipse.jdt.internal.compiler.util.Util;

import java.lang.annotation.ElementType;
import java.util.EnumSet;
import java.util.Locale;

import mod.agus.jcoderz.multidex.ClassPathElement;

public class Main {

    private Main() {
    }

    public static void main(String[] strArr) {
        Arguments arguments = new Arguments();
        try {
            arguments.parse(strArr);
            new AnnotationLister(arguments).process();
        } catch (InvalidArgumentException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("usage");
        }
    }

    public enum PrintType {
        CLASS,
        INNERCLASS,
        METHOD,
        PACKAGE
    }

    public static class InvalidArgumentException extends Exception {
        InvalidArgumentException() {
        }

        InvalidArgumentException(String str) {
            super(str);
        }
    }

    public static class Arguments {
        String aclass;
        EnumSet<ElementType> eTypes = EnumSet.noneOf(ElementType.class);
        String[] files;
        EnumSet<PrintType> printTypes = EnumSet.noneOf(PrintType.class);

        Arguments() {
        }

        public void parse(String[] strArr) throws InvalidArgumentException {
            int i = 0;
            while (true) {
                if (i >= strArr.length) {
                    break;
                }
                String str = strArr[i];
                if (str.startsWith("--annotation=")) {
                    String substring = str.substring(str.indexOf(61) + 1);
                    if (this.aclass != null) {
                        throw new InvalidArgumentException("--annotation can only be specified once.");
                    }
                    this.aclass = substring.replace(Util.C_DOT, ClassPathElement.SEPARATOR_CHAR);
                } else if (!str.startsWith("--element=")) {
                    if (!str.startsWith("--print=")) {
                        this.files = new String[(strArr.length - i)];
                        System.arraycopy(strArr, i, this.files, 0, this.files.length);
                        break;
                    }
                    try {
                        String[] split = str.substring(str.indexOf(61) + 1).split(",");
                        for (String str2 : split) {
                            this.printTypes.add(PrintType.valueOf(str2.toUpperCase(Locale.ROOT)));
                        }
                    } catch (IllegalArgumentException e) {
                        throw new InvalidArgumentException("invalid --print");
                    }
                } else {
                    try {
                        String[] split2 = str.substring(str.indexOf(61) + 1).split(",");
                        for (String str3 : split2) {
                            this.eTypes.add(ElementType.valueOf(str3.toUpperCase(Locale.ROOT)));
                        }
                    } catch (IllegalArgumentException e2) {
                        throw new InvalidArgumentException("invalid --element");
                    }
                }
                i++;
            }
            if (this.aclass == null) {
                throw new InvalidArgumentException("--annotation must be specified");
            }
            if (this.printTypes.isEmpty()) {
                this.printTypes.add(PrintType.CLASS);
            }
            if (this.eTypes.isEmpty()) {
                this.eTypes.add(ElementType.TYPE);
            }
            EnumSet<ElementType> clone = this.eTypes.clone();
            clone.remove(ElementType.TYPE);
            clone.remove(ElementType.PACKAGE);
            if (!clone.isEmpty()) {
                throw new InvalidArgumentException("only --element parameters 'type' and 'package' supported");
            }
        }
    }
}
