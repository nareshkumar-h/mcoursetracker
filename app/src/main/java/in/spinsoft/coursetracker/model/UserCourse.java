package in.spinsoft.coursetracker.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCourse implements Serializable {

    @SerializedName("course_id")
    @Expose
    private String courseId;

    @SerializedName("course_name")
    @Expose
    private String courseName;


    private String category;

    @SerializedName("completed_topics")
    @Expose
    private Integer completedTopics;
    @SerializedName("pending_topics")
    @Expose
    private Integer pendingTopics;

    public String getTopicSummary(){
        return "(" + getPercentage() + "%  - " + completedTopics +"/" + (pendingTopics + completedTopics ) + ")";
    }

    @Override
    public String toString() {
        return "UserCourse{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", category='" + category + '\'' +
                ", completedTopics=" + completedTopics +
                ", pendingTopics=" + pendingTopics +
                ", displayOrder=" + displayOrder +
                '}';
    }

    @SerializedName("display_order")
    @Expose
    private Integer displayOrder;

    public Integer getPercentage() {
        Integer percent = Math.round(100*completedTopics / ( pendingTopics + completedTopics));
        return percent;
    }


    private Integer percentage;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCompletedTopics() {
        return completedTopics;
    }

    public void setCompletedTopics(Integer completedTopics) {
        this.completedTopics = completedTopics;
    }

    public Integer getPendingTopics() {
        return pendingTopics;
    }

    public void setPendingTopics(Integer pendingTopics) {
        this.pendingTopics = pendingTopics;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
