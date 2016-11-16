package com.example.administrator.teaencyclopediademo.url;

public class AppInterface {
    public static final String OTHER_NEWS="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=%d&page=%d&rows=15";
    public static final String HEAD_NEWS="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=%d&rows=15";
    public static final String HEADER_VIEW_NEWS="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";
    public static final String NEWS_DETIAL="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%d";
    public static final String SEARCH_NEWS="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&page=%d&rows=10&search=%s";

}
