package com.salton123.video.bean;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/27 17:36
 * ModifyTime: 17:36
 * Description:
 */
//http://disp.titan.mgtv.com/vod.do?fmt=2&pm=5mSPnO3g7R5Avoy0DI3yPtoWVrw2XKAnJ0M~t5ypoftOGWtVPE_vudadcC7lP8xV1nHSwGVpcSwzJrJWoyGh98mOLCwH7mhLzBAk42Hxhdb__gMiF6gc9ycQ9XwhcGlqnv2Ea_PCTAF5onCd9JfGu~M9Jr_MABJ2BQR0BWn7YlEoWXWZ2MwUQpbtS1lFT~TmZazo1jbAjiUmE2bN3ahMD~8yxIP_1bq0GGdPGcC75jk-&fid=8498881928BBBC78CAA9A9BDC889D211&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&&mf=Xiaomi&mod=
public class VodInfo {

    /**
     * ver : 4.3.1
     * info : http://pcvideows.titan.mgtv.com/c1/internettv/2018/06/08_0/0E7583C43FC4C165ECC4FF154748E27B_20180608_1_1_910_ts/F54CF8FF4BC8BE6957C1433EC78D38AD.m3u8?arange=0&pm=qmSeYm8ijZWQiVWvpnIqgzNW8gZ6AAM0a~Z6YG8e8aTDvpoOd_RJIFlvcijgD_l5HAopRykbhWKByQMag5Q8Dqlk76dTU~s56o9e0Nq2fPzDyAICsLh3aSbq1XeuHv3oQgBKO8j8rtcXuLgWm1uXC4qRBDGJazU_dAO28TNDzSoed6sgGwAgJ8e6k4au9o5Tdt0fbPhgRRyGoMB_gDgGrkMfNcQ916BIcw1iNKuItzqblX20XI39VoJCwdyUYoNz4i53hccx5z~5d5zJXNB7XoLa0A1nCbSm0oJYJThxQCo18Zko9Pi4M2jLgGyYakkm5r0h_me1eqSe7tf0qV6TmyVKQjpYaeeoZh6y1JNBAzeQUOUFjroeS3OMf_qP43OvvnxU5K1m5yTw85m6TvxZOUglKiBj1DJh016bsXy2XFyGHHy_sngHkLV8EkGdwODs~ejCDpVP_3brixBr7UyFrw--&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&mf=Xiaomi&mod=&vcdn=0&scid=25003
     * t : 1551259923
     * isothercdn : 1
     * loc : 372-183.62.134.100
     * status : ok
     * idc : chinanet_center
     */

    private String ver;
    private String info;
    private int t;
    private int isothercdn;
    private String loc;
    private String status;
    private String idc;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getIsothercdn() {
        return isothercdn;
    }

    public void setIsothercdn(int isothercdn) {
        this.isothercdn = isothercdn;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }
}
