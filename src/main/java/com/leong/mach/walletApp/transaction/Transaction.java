package com.leong.mach.walletApp.transaction;

import java.time.LocalDate;

import com.leong.mach.user.User;
import com.leong.mach.walletApp.category.Category;
import com.leong.mach.walletApp.categoryChild.CategoryChild;
import com.leong.mach.walletApp.wallet.Wallet;

public class Transaction {
    
    private Integer id;
    private String name;
    private Double amount;
    private String note;
    private LocalDate transactionDate;
    private Wallet wallet;
    private User user;
    private Category category;
    private CategoryChild categoryChild;

}
