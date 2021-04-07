package com.ld.usersnews.models;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleId;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.ALL})
    private List<Comment> commentsList;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String articleText;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date articleDate;

    private String title;

    private String articleImageName;

    private boolean isApproved;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date resultDate) {
        this.articleDate = resultDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleImageName() {
        return articleImageName;
    }

    public void setArticleImageName(String articleImage) {
        this.articleImageName = articleImage;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

}
