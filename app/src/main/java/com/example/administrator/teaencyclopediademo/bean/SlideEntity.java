package com.example.administrator.teaencyclopediademo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class SlideEntity {
    private List<SlideShow> data;

    public List<SlideShow> getData() {
        return data;
    }

    public void setData(List<SlideShow> data) {
        this.data = data;
    }

    public class SlideShow{
        private String id;
        private String title;
        private String name;
        private String link;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
