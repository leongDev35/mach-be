package com.leong.mach.walletApp.category;

import com.leong.mach.user.User;

enum TransactionType {
    INCOME, EXPENSE, LOAN
}

public class Category {
    private Integer id;
    private String name;
    private String icon;
    private TransactionType type;
    private User user;
}
