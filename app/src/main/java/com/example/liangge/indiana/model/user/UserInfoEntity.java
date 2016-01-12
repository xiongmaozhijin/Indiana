package com.example.liangge.indiana.model.user;

import android.content.Context;
import android.text.TextUtils;

import com.example.liangge.indiana.R;

import java.util.List;

/**
 * 用户信息实体
 * Created by baoxing on 2015/12/26.
 */
public class UserInfoEntity {

    private long id;

    private String nickname;

    private String token;

    private int balance;

    private String photo;

    private String phone_number;

    private List<UserAddress> address;

    private long time;

    private String token2;

    /**
     * 收货地址
     */
    public static class UserAddress {
        private long id;
        private String name;
        private String phone;
        private String province;
        private String city;
        private String area;
        private String detail;
        private int set_default;

        public UserAddress() {
        }

        public UserAddress(long id, String name, String phone, String province, String city, String area, String detail, int set_default) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.province = province;
            this.city = city;
            this.area = area;
            this.detail = detail;
            this.set_default = set_default;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getSet_default() {
            return set_default;
        }

        public void setSet_default(int set_default) {
            this.set_default = set_default;
        }


        @Override
        public String toString() {
            return "UserAddress{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", detail='" + detail + '\'' +
                    ", set_default=" + set_default +
                    '}';
        }
    }



    public UserInfoEntity() {
    }


    public UserInfoEntity(long id, String nickname, String token, int balance, String photo, String phone_number, List<UserAddress> address, long time, String token2) {
        this.id = id;
        this.nickname = nickname;
        this.token = token;
        this.balance = balance;
        this.photo = photo;
        this.phone_number = phone_number;
        this.address = address;
        this.time = time;
        this.token2 = token2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public List<UserAddress> getAddress() {
        return address;
    }

    public void setAddress(List<UserAddress> address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    /**
     * 返回可读的地址1
     */
    public String getHumanReadableAddress1(Context context) {
        String address = context.getResources().getString(R.string.activity_edit_user_info_no_address);
        if (getAddress() != null && getAddress().size() > 0) {
            if (getAddress().get(0) != null) {
                String temp = getAddress().get(0).getDetail();
                if (temp!=null && !TextUtils.isEmpty(temp)) {
                    address = temp;
                }
            }
        }

        return address;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                ", balance=" + balance +
                ", photo='" + photo + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", address=" + address +
                ", time=" + time +
                ", token2='" + token2 + '\'' +
                '}';
    }
}
