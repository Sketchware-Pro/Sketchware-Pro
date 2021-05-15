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

    public final String a(int i) {
        StringBuilder str = new StringBuilder();
        for (int i2 = 0; i2 < this.b + i; i2++) {
            str.append("\t");
        }
        return str.toString();
    }

    public void a(int i, String str, String str2, String str3) {
        this.e.add(i, new a(this, str, str2, str3));
    }

    public void a(Nx nx) {
        nx.b(this.b + 1);
        this.f.add(nx);
    }

    public void a(String str) {
        this.c = str;
    }

    public void a(String str, String str2, String str3) {
        this.e.add(new a(this, str, str2, str3));
    }

    public void b(String str) {
        this.e.add(new a(this, str));
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        sb.append("<");
        sb.append(this.a);
        for (Nx.a aVar : this.e) {
            if (this.e.size() <= 1 || this.d) {
                sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            } else {
                sb.append("\r\n");
                sb.append(a(1));
                this.g = "\r\n" + a(1);
            }
            sb.append(aVar.a());
        }
        if (this.f.size() <= 0) {
            if (this.c == null || this.c.length() <= 0) {
                sb.append(" />");
            } else {
                sb.append(">");
                sb.append(this.c);
                sb.append("</");
                sb.append(this.a);
                sb.append(">");
            }
        } else {
            sb.append(">");
            sb.append("\r\n");
            for (Nx nx : this.f) {
                sb.append(nx.b());
            }
            sb.append(a());
            sb.append("</");
            sb.append(this.a);
            sb.append(">");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    public String c() {
        return this.a.replaceAll("\\w*\\..*\\.", "");
    }

    public void b(int i) {
        this.b = i;
        if (this.f != null) {
            for (Nx nx : this.f) {
                nx.b(i + 1);
            }
        }
    }

    class a {
        public final Nx nx;
        public String str;
        public String str2;
        public String str3;

        public a(Nx nx2, String str4, String str5, String str6) {
            this.nx = nx2;
            this.str = str4;
            this.str2 = str5;
            this.str3 = str6;
        }

        public a(Nx nx2, String str4) {
            this.nx = nx2;
            this.str3 = str4;
        }

        public String a() {
            if (this.str != null && this.str.length() > 0) {
                return this.str + ":" + this.str2 + URLEncodedUtils.NAME_VALUE_SEPARATOR + "\"" + this.str3 + "\"";
            } else if (this.str2 == null || this.str2.length() <= 0) {
                return this.str3.replaceAll("\n", Nx.this.g);
            } else {
                return this.str2 + URLEncodedUtils.NAME_VALUE_SEPARATOR + "\"" + this.str3 + "\"";
            }
        }
    }
}