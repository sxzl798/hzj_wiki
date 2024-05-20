package com.hzj.wiki.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class StatisticResp implements Serializable {

    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date date;

    private Integer viewCount;

    private Integer voteCount;

    private Integer viewIncrease;

    private Integer voteIncrease;

    private static final long serialVersionUID = 1L;



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
        StatisticResp other = (StatisticResp) that;
        return
            (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getViewCount() == null ? other.getViewCount() == null : this.getViewCount().equals(other.getViewCount()))
            && (this.getVoteCount() == null ? other.getVoteCount() == null : this.getVoteCount().equals(other.getVoteCount()))
            && (this.getViewIncrease() == null ? other.getViewIncrease() == null : this.getViewIncrease().equals(other.getViewIncrease()))
            && (this.getVoteIncrease() == null ? other.getVoteIncrease() == null : this.getVoteIncrease().equals(other.getVoteIncrease()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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