package com.candou.ic.navigation.jcwx.bean;

import java.util.List;

public class App {

    private List<Article> articles;
    private List<Like> likes;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

}
