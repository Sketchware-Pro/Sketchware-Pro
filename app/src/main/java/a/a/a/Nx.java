package a.a.a;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import org.apache.http.client.utils.URLEncodedUtils;

import java.util.ArrayList;

public class Nx {

    public String a;
    public int b;
    public String c;
    public boolean d;
    public ArrayList<a> e;
    public ArrayList<Nx> f;
    public String g;

    public Nx(String str) {
        this(str, false);
    }

    public Nx(String str, boolean z) {
        this.d = z;
        this.a = str;
        this.b = 0;
        this.e = new ArrayList<>();
        this.f = new ArrayList<>();
    }

    public final String a() {
        return a(0);
    }

    public final String a(int indentSize) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < b + indentSize; i++) {
            str.append("\t");
        }
        return str.toString();
    }

    public void a(int position, String namespace, String attr, String value) {
        e.add(position, new a(this, namespace, attr, value));
    }

    public void a(Nx xmlBuilder) {
        xmlBuilder.b(b + 1);
        f.add(xmlBuilder);
    }

    public void a(String str) {
        c = str;
    }

    public void a(String namespace, String attr, String value) {
        e.add(new a(this, namespace, attr, value));
    }

    public void b(String value) {
        e.add(new a(this, value));
    }

    public String b() {
        StringBuilder resultCode = new StringBuilder();
        resultCode.append(a());
        resultCode.append("<");
        resultCode.append(a);
        for (Nx.a attr : e) {
            if (e.size() <= 1 || d) {
                resultCode.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            } else {
                resultCode.append("\r\n");
                resultCode.append(a(1));
                g = "\r\n" + a(1);
            }
            resultCode.append(attr.a());
        }
        if (f.size() <= 0) {
            if (c == null || c.length() <= 0) {
                resultCode.append(" />");
            } else {
                resultCode.append(">");
                resultCode.append(c);
                resultCode.append("</");
                resultCode.append(a);
                resultCode.append(">");
            }
        } else {
            resultCode.append(">");
            resultCode.append("\r\n");
            for (Nx xmlBuilder : f) {
                resultCode.append(xmlBuilder.b());
            }
            resultCode.append(a());
            resultCode.append("</");
            resultCode.append(a);
            resultCode.append(">");
        }
        resultCode.append("\r\n");
        return resultCode.toString();
    }

    public String c() {
        return a.replaceAll("\\w*\\..*\\.", "");
    }

    public void b(int indentSize) {
        b = indentSize;
        if (f != null) {
            for (Nx nx : f) {
                nx.b(indentSize + 1);
            }
        }
    }

    class a {
        public final Nx nx;
        public String str;
        public String str2;
        public String str3;

        public a(Nx xmlBuilder, String namespace, String attr, String value) {
            nx = xmlBuilder;
            str = namespace;
            str2 = attr;
            str3 = value;
        }

        public a(Nx xmlBuilder, String value) {
            nx = xmlBuilder;
            str3 = value;
        }

        public String a() {
            if (str != null && str.length() > 0) {
                return str + ":" + str2 + URLEncodedUtils.NAME_VALUE_SEPARATOR + "\"" + str3 + "\"";
            } else if (str2 == null || str2.length() <= 0) {
                return str3.replaceAll("\n", Nx.this.g);
            } else {
                return str2 + URLEncodedUtils.NAME_VALUE_SEPARATOR + "\"" + str3 + "\"";
            }
        }
    }
}