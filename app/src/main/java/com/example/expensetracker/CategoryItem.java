package com.example.expensetracker;

public class CategoryItem {
    int categoryImgId;
    String categoryImgName;
    int categoryImgPic;

    public CategoryItem(int categoryImgId, String categoryImgName, int categoryImgPic) {
        this.categoryImgId = categoryImgId;
        this.categoryImgName = categoryImgName;
        this.categoryImgPic = categoryImgPic;
    }

    public int getCategoryImgId() {
        return categoryImgId;
    }

    public void setCategoryImgId(int categoryImgId) {
        this.categoryImgId = categoryImgId;
    }

    public String getCategoryImgName() {
        return categoryImgName;
    }

    public void setCategoryImgName(String categoryImgName) {
        this.categoryImgName = categoryImgName;
    }

    public int getCategoryImgPic() {
        return categoryImgPic;
    }

    public void setCategoryImgPic(int categoryImgPic) {
        this.categoryImgPic = categoryImgPic;
    }
}
