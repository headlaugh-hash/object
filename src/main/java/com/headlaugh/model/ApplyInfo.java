package com.headlaugh.model;

import java.sql.Timestamp;

public class ApplyInfo {
    private long id;
    private long jobId;
    private long userId;
    private String resume; // 简历文本或链接
    private String status; // PENDING / APPROVED / REJECTED
    private Timestamp applyTime;

    public ApplyInfo() {}

    public ApplyInfo(long jobId, long userId, String resume) {
        this.jobId = jobId;
        this.userId = userId;
        this.resume = resume;
        this.status = "PENDING";
        this.applyTime = new Timestamp(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    @Override
    public String toString() {
        return "ApplyInfo{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", userId=" + userId +
                ", resume='" + resume + '\'' +
                ", status='" + status + '\'' +
                ", applyTime=" + applyTime +
                '}';
    }
}