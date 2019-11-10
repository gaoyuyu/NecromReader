package com.moudle.necromreader.greendao.entity;

import java.io.Serializable;

/**
 * Created by moudle on 2017/10/15 0015.
 */
public class NewTag implements Serializable, Comparable<GankTag>
{
    private Long id;
    private int tagId;
    private int sort;
    private String TagName;
    private boolean isShowDelete = false; // not persisted


    public NewTag(Long id, int tagId, int sort, String TagName)
    {
        this.id = id;
        this.tagId = tagId;
        this.sort = sort;
        this.TagName = TagName;
    }


    public NewTag()
    {
    }


    @Override
    public String toString()
    {
        return "NewTag{" +
                "id=" + id +
                ", tagId=" + tagId +
                ", sort=" + sort +
                ", TagName='" + TagName + '\'' +
                ", isShowDelete=" + isShowDelete +
                '}';
    }


    public String getTagName()
    {
        return this.TagName;
    }


    public void setTagName(String TagName)
    {
        this.TagName = TagName;
    }


    public int getSort()
    {
        return this.sort;
    }


    public void setSort(int sort)
    {
        this.sort = sort;
    }


    public int getTagId()
    {
        return this.tagId;
    }


    public void setTagId(int tagId)
    {
        this.tagId = tagId;
    }


    public Long getId()
    {
        return this.id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }

    public boolean isShowDelete()
    {
        return isShowDelete;
    }

    public void setShowDelete(boolean showDelete)
    {
        isShowDelete = showDelete;
    }

    @Override
    public int compareTo(GankTag gankTag)
    {
        int i = this.getSort() - gankTag.getSort();
        return i;
    }
}
