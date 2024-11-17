package com.leong.mach.walletApp.wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

import com.leong.mach.user.User;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double total;
    private String walletIcon;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
