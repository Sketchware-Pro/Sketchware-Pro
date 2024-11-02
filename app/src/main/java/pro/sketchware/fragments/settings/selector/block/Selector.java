package pro.sketchware.fragments.settings.selector.block;

import java.util.ArrayList;
import java.util.List;

public class Selector {
    private List<String> data;
    private String name;
    private String title;

    public Selector() {
        this.data = new ArrayList<>();
        this.name = "";
        this.title = "";
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
}

