package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20)
    private String name;
    private Integer age;
    // 소유자는 한명, 대출 기록은 여러개, 즉 1 : N
    // mappedBy : 연관관계의 주인을 설정(주인이 아닌 쪽), 주인이 가지는 필드 이름을 입력
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {}

    public User(String name, Integer age) {
        System.out.println(name);
        if (name == null || name.isBlank())
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 입력 되었습니다.", name));
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
            .filter(history -> history.getBookName().equals(bookName))
            .findFirst()
             .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }
}
