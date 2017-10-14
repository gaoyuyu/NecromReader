package com.gaoyy.necromreader.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by gaoyy on 2017/10/14 0014.
 */
@Entity
public class GankTag implements Serializable, Comparable<GankTag>
{
    @Id
    private Long id;
    private int tagId;
    private int sort;
    private String TagName;
    @Transient
    private boolean isShowDelete = false; // not persisted

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

    public int getSort()
    {
        return this.sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public boolean isShowDelete()
    {
        return isShowDelete;
    }

    public void setShowDelete(boolean showDelete)
    {
        isShowDelete = showDelete;
    }

    public String getTagName()
    {
        return TagName;
    }

    public void setTagName(String tagName)
    {
        TagName = tagName;
    }

    @Generated(hash = 950428366)
    public GankTag(Long id, int tagId, int sort, String TagName) {
        this.id = id;
        this.tagId = tagId;
        this.sort = sort;
        this.TagName = TagName;
    }

    @Generated(hash = 920171179)
    public GankTag()
    {
    }


    @Override
    public String toString()
    {
        return "GankTag{" +
                "id=" + id +
                ", tagId=" + tagId +
                ", sort=" + sort +
                ", TagName='" + TagName + '\'' +
                ", isShowDelete=" + isShowDelete +
                '}';
    }

    @Override
    public int compareTo(GankTag gankTag)
    {
        int i = this.getSort() - gankTag.getSort();
        return i;
    }
}
