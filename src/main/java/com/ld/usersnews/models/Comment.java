package com.ld.usersnews.models;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

/*
 * Класс Article, используется для хранения информации об одной комментарии, оставленном пользователем к статье,
 * имеет связь многие-к-одному с классом User (таблица users),
 * а также, имеет связь многие-к-одному с классом Article (таблица articles).
 */

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name="article_id")
    private Article article;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;

    private String commentText;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
