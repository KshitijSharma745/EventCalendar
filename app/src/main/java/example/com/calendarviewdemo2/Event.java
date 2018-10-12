package example.com.calendarviewdemo2;

public class Event {

    int id;
    String name;
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    String orgSociety;
    String details;
    String location;
    String link;
    String createdAt;
    String updatedAt;

    public Event(int id,
                 String name,
                 String startDate,
                 String endDate,
                 String startTime,
                 String endTime,
                 String orgSociety,
                 String details,
                 String location,
                 String link,
                 String createdAt,
                 String updatedAt){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orgSociety = orgSociety;
        this.details = details;
        this.location = location;
        this.link = link;
        this.createdAt =createdAt;
        this.updatedAt = updatedAt;
    }

    public String getLocation() {
        return location;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDetails() {
        return details;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getOrgSociety() {
        return orgSociety;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLink() {
        return link;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrgSociety(String orgSociety) {
        this.orgSociety = orgSociety;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
