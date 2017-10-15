package com.gaoyy.necromreader.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by gaoyy on 2017/10/15 0015.
 */
@Entity
public class NewTag implements Serializable, Comparable<GankTag>
{
    @Id
    private Long id;
    private int tagId;
    private int sort;
    private String TagName;
    @Transient
    private boolean isShowDelete = false; // not persisted


    @Generated(hash = 1222918255)
    public NewTag(Long id, int tagId, int sort, String TagName)
    {
        this.id = id;
        this.tagId = tagId;
        this.sort = sort;
        this.TagName = TagName;
    }


    @Generated(hash = 951539180)
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
