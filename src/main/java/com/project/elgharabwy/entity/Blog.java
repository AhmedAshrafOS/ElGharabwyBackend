package com.project.elgharabwy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "blogs") // Specifies the MongoDB collection
public class Blog {

    @Id
    private String id;

    private String title;

    private String coverImageUrl;

    private List<Section> sections;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    // Default constructor
    public Blog() {}

    // Parameterized constructor
    public Blog(String title,LocalDate date, String coverImageUrl, List<Section> sections) {
        this.title = title;
        this.date = date;
        this.coverImageUrl = coverImageUrl;
        this.sections = sections;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    // Nested static class for Section
    public static class Section {
        private String heading;
        private String body; // HTML content for rich text
        private String type; // "text", "image", or "video"
        private String mediaUrl; // URL if type is "image" or "video"

        // Default constructor
        public Section() {}

        // Parameterized constructor
        public Section(String heading, String body, String type, String mediaUrl) {
            this.heading = heading;
            this.body = body;
            this.type = type;
            this.mediaUrl = mediaUrl;
        }

        // Getters and setters
        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }
    }
}
