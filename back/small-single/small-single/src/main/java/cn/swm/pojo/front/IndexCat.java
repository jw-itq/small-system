package cn.swm.pojo.front;

import java.io.Serializable;
import java.util.List;

public class IndexCat implements Serializable {

    private String title;

    private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
