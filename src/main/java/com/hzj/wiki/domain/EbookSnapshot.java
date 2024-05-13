package com.hzj.wiki.domain;

import java.io.Serializable;
import java.util.Date;

public class EbookSnapshot implements Serializable {
    private Long id;

    private Long ebookId;

    private Date date;

    private Integer viewCount;

    private Integer voteCount;

    private Integer viewIncrease;

    private Integer voteIncrease;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEbookId() {
        return ebookId;
    }

    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getViewIncrease() {
        return viewIncrease;
    }

    public void setViewIncrease(Integer viewIncrease) {
        this.viewIncrease = viewIncrease;
    }

    public Integer getVoteIncrease() {
        return voteIncrease;
    }

    public void setVoteIncrease(Integer voteIncrease) {
        this.voteIncrease = voteIncrease;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EbookSnapshot other = (EbookSnapshot) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEbookId() == null ? other.getEbookId() == null : this.getEbookId().equals(other.getEbookId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getViewCount() == null ? other.getViewCount() == null : this.getViewCount().equals(other.getViewCount()))
            && (this.getVoteCount() == null ? other.getVoteCount() == null : this.getVoteCount().equals(other.getVoteCount()))
            && (this.getViewIncrease() == null ? other.getViewIncrease() == null : this.getViewIncrease().equals(other.getViewIncrease()))
            && (this.getVoteIncrease() == null ? other.getVoteIncrease() == null : this.getVoteIncrease().equals(other.getVoteIncrease()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEbookId() == null) ? 0 : getEbookId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getViewCount() == null) ? 0 : getViewCount().hashCode());
        result = prime * result + ((getVoteCount() == null) ? 0 : getVoteCount().hashCode());
        result = prime * result + ((getViewIncrease() == null) ? 0 : getViewIncrease().hashCode());
        result = prime * result + ((getVoteIncrease() == null) ? 0 : getVoteIncrease().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ebookId=").append(ebookId);
        sb.append(", date=").append(date);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", voteCount=").append(voteCount);
        sb.append(", viewIncrease=").append(viewIncrease);
        sb.append(", voteIncrease=").append(voteIncrease);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}