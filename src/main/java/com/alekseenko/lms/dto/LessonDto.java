package com.alekseenko.lms.dto;

import javax.validation.constraints.NotBlank;

public class LessonDto {

    private Long id;

    @NotBlank(message = "Title shouldn't be empty")
    private String title;

    @NotBlank(message = "Text shouldn't be empty")
    private String text;

    private Long courseId;

    public LessonDto() {
    }

    public LessonDto(Long courseId) {
        this.courseId = courseId;
    }

    public LessonDto(Long id, String title, Long courseId) {
        this.id = id;
        this.title = title;
        this.courseId = courseId;
    }

    public LessonDto(Long id, String title, String text, Long courseId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}

