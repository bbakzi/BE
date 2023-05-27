package com.sparta.ourportfolio.portfolio.dto;

import com.sparta.ourportfolio.portfolio.entity.Portfolio;
import com.sparta.ourportfolio.user.entity.User;
import lombok.Getter;

@Getter
public class PortfolioResponseDto {
    private Long id;
    private String portfolioTitle;
    private String portfolioImage;
    private String userProfileImage;
    private String userName;
    private String category;
    private String filter;

    public PortfolioResponseDto(Portfolio portfolio, User user) {
        this.id = portfolio.getId();
        this.portfolioTitle = portfolio.getPortfolioTitle();
        this.portfolioImage = portfolio.getPortfolioImage();
        this.userProfileImage = user.getProfileImage();
        this.userName = user.getNickname();
        this.category = portfolio.getCategory();
        this.filter = portfolio.getFilter();
    }
}
