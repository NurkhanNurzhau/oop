package org.example.entity;

public class Shopper {
    private int shopperId;
    private String fullName;
    private String email;
    private String phone;

    public Shopper() {}

    public Shopper(int shopperId, String fullName, String email, String phone) {
        this.shopperId = shopperId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public int getShopperId() { return shopperId; }
    public void setShopperId(int shopperId) { this.shopperId = shopperId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Shopper{" +
                "shopperId=" + shopperId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
