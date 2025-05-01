package pro.sketchware.fragments.settings.block.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Selector {
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
}

