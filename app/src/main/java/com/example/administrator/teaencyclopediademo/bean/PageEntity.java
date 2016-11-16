package com.example.administrator.teaencyclopediademo.bean;

import java.io.Serializable;
import java.util.List;

public class PageEntity {
    private List<PageDetial> data;

    public List<PageDetial> getData() {
        return data;
    }

    public void setData(List<PageDetial> data) {
        this.data = data;
    }

    public PageDetial getPage(){
        PageDetial detial = new PageDetial();
        return detial;
    }

    public class PageDetial implements Serializable {
        private String id;
        private String wap_thumb;
        private String title;
        private String source;
        private String description;
        private String create_time;
        private String nickname;

        public PageDetial() {
        }

        public PageDetial(String id, String wap_thumb, String title, String source, String description, String create_time, String nickname) {
            this.id = id;
            this.wap_thumb = wap_thumb;
            this.title = title;
            this.source = source;
            this.description = description;
            this.create_time = create_time;
            this.nickname = nickname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWap_thumb() {
            return wap_thumb;
        }

        public void setWap_thumb(String wap_thumb) {
            this.wap_thumb = wap_thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
