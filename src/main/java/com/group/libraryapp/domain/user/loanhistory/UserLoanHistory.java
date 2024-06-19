package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    // 대출 기록은 여러개, 기록을 소유한 사용자는 1명, 즉 N : 1
    // 연관관계의 주인이 활용할 수 있는 @JoinColumn
    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;
    private String bookName;
    private Boolean isReturn;

    protected UserLoanHistory() {};

    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getBookName() {
        return bookName;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void doReturn() {
        this.isReturn = true;
    }
}
