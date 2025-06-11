package com.project.gudasi;
public class Subscription {
    private int id;
    private String icon;
    private String app_name;
    private int price;
    private String renewal_date;

    public Subscription(int id, String icon, String app_name, int price, String renewal_date) {
        this.id = id;
        this.icon = icon;
        this.app_name = app_name;
        this.price = price;
        this.renewal_date = renewal_date;
    }

    // getter 메서드들
    public int getId() { return id; }
    public String getIcon() { return icon; }
    public String getApp_name() { return app_name; }
    public int getPrice() { return price; }
    public String getRenewal_date() { return renewal_date; }

    // 필요하면 setter도 추가 가능
}

