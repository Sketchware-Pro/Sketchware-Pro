package pro.sketchware.fragments.settings.block.selector;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Selector implements Parcelable {
    private List<String> data;
    private String name;
    private String title;

    public Selector() {
        data = new ArrayList<>();
        name = "";
        title = "";
    }

    public Selector(String title, String name, List<String> data) {
        this.data = data;
        this.name = name;
        this.title = title;
    }

    public static final Creator<Selector> CREATOR = new Creator<Selector>() {
        @Override
        public Selector createFromParcel(Parcel in) {
            return new Selector(in);
        }

        @Override
        public Selector[] newArray(int size) {
            return new Selector[size];
        }
    };

    protected Selector(Parcel in) {
        data = in.createStringArrayList();
        name = in.readString();
        title = in.readString();
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Selector selector = (Selector) o;
        return Objects.equals(getData(), selector.getData())
                && Objects.equals(getName(), selector.getName())
                && Objects.equals(getTitle(), selector.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), getName(), getTitle());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(name);
        dest.writeStringList(data);
    }
}

