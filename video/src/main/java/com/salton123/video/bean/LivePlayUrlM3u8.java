package com.salton123.video.bean;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/27 14:01
 * ModifyTime: 14:01
 * Description:
 */
// 获取播放链接：http://ott.liveapi.mgtv.com/v1/epg/turnplay/getLivePlayUrlM3u8?mac_id=F4-60-E2-AE-B8-92&net_id=&device_id=71f110234939485693ecdef05cf7f2a93ddaaa6b&uuid=mgtvmacF460E2AEB892&license=ZgOOgo5MjkyOTAUOvw4OBkuqtA2qDbR8hyANqnY7e3a%2FBoc7vwW%2FS5UNqnt7lZWVIJmqjkyOTI5MZgOOgg%3D%3D&ticket=&buss_id=1000014&version=5.6.307.200.2.DBEI.0.0_Release&platform=3&type=3&mf=Xiaomi&mode=Redmi+6+Pro&_support=00100000011&definition=2&after_day=1&channel_id=1092
public class LivePlayUrlM3u8 {

    /**
     * errno : 0
     * msg : 成功
     * server_time : 1551247245
     * data : {"ticket_status":"normal","status":"0000","isfree":"true","drm":"0","play_list":[{"id":"44624580","text":"哆啦A梦第四季 第83集","play_time":"2019-02-27 13:56:18","end_time":"2019-02-27 14:03:54","duration":"456","part_id":"4110039","url":"http://disp.titan.mgtv.com/vod.do?fmt=2&pm=KNincG4k~tahCQKuI3C2wsHi9sdgZjWZPBJqfGJJJ~oa0O8KRx2C_o0TBWJl3GwV3LiluAEunVb2I2Tgkqlbsyz5utFe_bx0SY4m0fp6mgR6ktpPgGhiIdyq8meoMJLgX0vs5B9G2DISaitOhuIs4PeLKbEt8Js9xq61ModQFK0Sc1vtfsuWdq1ErcKszH5I6OLQp1E4xKfy_AqN3AUP9Gb2uAu4HhapSFW1RvbaZPo-&fid=55318651407D376CEFE3D97C8082A6E4&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&&mf=Xiaomi&mod=","source_id":"","drmToken":"","drmFlag":"0","drmCid":""},{"id":"44624583","text":"哆啦A梦第四季 第84集","play_time":"2019-02-27 14:03:54","end_time":"2019-02-27 14:11:35","duration":"461","part_id":"4110040","url":"http://disp.titan.mgtv.com/vod.do?fmt=2&pm=1bOUY73auikU48hOUB81smUw1nzppsgfW~zEv_orDVCpYGIOKZEoRs5nhsCRCFA~2cMFdbYra22xPSGgcvG2W8Y93kiRnkjvZd0n_ylyF3FhHcQzelFyUZqWDX9qLVs_~FdKRravlhD_SLcwcp9xrEsGJ_nVH5T0E029g~M77Tp6uHuPd5H3gSVeBYo1y8beH0kSyCw0Ht09hKHbTRzCsE22bO5hmBaRMOFvCnWGAfw-&fid=4AA88EAC7008D072255A4988B6952294&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&&mf=Xiaomi&mod=","source_id":"","drmToken":"","drmFlag":"0","drmCid":""}]}
     */

    private String errno;
    private String msg;
    private int server_time;
    private DataBean data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getServer_time() {
        return server_time;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ticket_status : normal
         * status : 0000
         * isfree : true
         * drm : 0
         * play_list : [{"id":"44624580","text":"哆啦A梦第四季 第83集","play_time":"2019-02-27 13:56:18","end_time":"2019-02-27 14:03:54","duration":"456","part_id":"4110039","url":"http://disp.titan.mgtv.com/vod.do?fmt=2&pm=KNincG4k~tahCQKuI3C2wsHi9sdgZjWZPBJqfGJJJ~oa0O8KRx2C_o0TBWJl3GwV3LiluAEunVb2I2Tgkqlbsyz5utFe_bx0SY4m0fp6mgR6ktpPgGhiIdyq8meoMJLgX0vs5B9G2DISaitOhuIs4PeLKbEt8Js9xq61ModQFK0Sc1vtfsuWdq1ErcKszH5I6OLQp1E4xKfy_AqN3AUP9Gb2uAu4HhapSFW1RvbaZPo-&fid=55318651407D376CEFE3D97C8082A6E4&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&&mf=Xiaomi&mod=","source_id":"","drmToken":"","drmFlag":"0","drmCid":""},{"id":"44624583","text":"哆啦A梦第四季 第84集","play_time":"2019-02-27 14:03:54","end_time":"2019-02-27 14:11:35","duration":"461","part_id":"4110040","url":"http://disp.titan.mgtv.com/vod.do?fmt=2&pm=1bOUY73auikU48hOUB81smUw1nzppsgfW~zEv_orDVCpYGIOKZEoRs5nhsCRCFA~2cMFdbYra22xPSGgcvG2W8Y93kiRnkjvZd0n_ylyF3FhHcQzelFyUZqWDX9qLVs_~FdKRravlhD_SLcwcp9xrEsGJ_nVH5T0E029g~M77Tp6uHuPd5H3gSVeBYo1y8beH0kSyCw0Ht09hKHbTRzCsE22bO5hmBaRMOFvCnWGAfw-&fid=4AA88EAC7008D072255A4988B6952294&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&&mf=Xiaomi&mod=","source_id":"","drmToken":"","drmFlag":"0","drmCid":""}]
         */

        private String ticket_status;
        private String status;
        private String isfree;
        private String drm;
        private List<PlayListBean> play_list;

        public String getTicket_status() {
            return ticket_status;
        }

        public void setTicket_status(String ticket_status) {
            this.ticket_status = ticket_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsfree() {
            return isfree;
        }

        public void setIsfree(String isfree) {
            this.isfree = isfree;
        }

        public String getDrm() {
            return drm;
        }

        public void setDrm(String drm) {
            this.drm = drm;
        }

        public List<PlayListBean> getPlay_list() {
            return play_list;
        }

        public void setPlay_list(List<PlayListBean> play_list) {
            this.play_list = play_list;
        }

        public static class PlayListBean {
            /**
             * id : 44624580
             * text : 哆啦A梦第四季 第83集
             * play_time : 2019-02-27 13:56:18
             * end_time : 2019-02-27 14:03:54
             * duration : 456
             * part_id : 4110039
             * url : http://disp.titan.mgtv.com/vod.do?fmt=2&pm=KNincG4k~tahCQKuI3C2wsHi9sdgZjWZPBJqfGJJJ~oa0O8KRx2C_o0TBWJl3GwV3LiluAEunVb2I2Tgkqlbsyz5utFe_bx0SY4m0fp6mgR6ktpPgGhiIdyq8meoMJLgX0vs5B9G2DISaitOhuIs4PeLKbEt8Js9xq61ModQFK0Sc1vtfsuWdq1ErcKszH5I6OLQp1E4xKfy_AqN3AUP9Gb2uAu4HhapSFW1RvbaZPo-&fid=55318651407D376CEFE3D97C8082A6E4&mac_id=F4-60-E2-AE-B8-92&version=5.6.307.200.2.DBEI.0.0_Release&&mf=Xiaomi&mod=
             * source_id :
             * drmToken :
             * drmFlag : 0
             * drmCid :
             */

            private String id;
            private String text;
            private String play_time;
            private String end_time;
            private String duration;
            private String part_id;
            private String url;
            private String source_id;
            private String drmToken;
            private String drmFlag;
            private String drmCid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getPlay_time() {
                return play_time;
            }

            public void setPlay_time(String play_time) {
                this.play_time = play_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getPart_id() {
                return part_id;
            }

            public void setPart_id(String part_id) {
                this.part_id = part_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSource_id() {
                return source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id;
            }

            public String getDrmToken() {
                return drmToken;
            }

            public void setDrmToken(String drmToken) {
                this.drmToken = drmToken;
            }

            public String getDrmFlag() {
                return drmFlag;
            }

            public void setDrmFlag(String drmFlag) {
                this.drmFlag = drmFlag;
            }

            public String getDrmCid() {
                return drmCid;
            }

            public void setDrmCid(String drmCid) {
                this.drmCid = drmCid;
            }
        }
    }

}
