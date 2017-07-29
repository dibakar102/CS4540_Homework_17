package com.example.android.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by Dibakar on 7/27/17.
 */

public class Contract {
    public static final class NewsItem implements BaseColumns{
        public static final String TABLE_NAME = "news_items";

        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "published_at";
    }
}
