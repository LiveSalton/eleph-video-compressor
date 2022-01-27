package com.salton123.video.bean;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/27 13:59
 * ModifyTime: 13:59
 * Description:
 */

// 获取指定changid详情：http://ott.liveapi.mgtv.com/v1/epg/turnplay/getLiveChannelInfo?mac_id=F4-60-E2-AE-B8-92&net_id=&device_id=71f110234939485693ecdef05cf7f2a93ddaaa6b&uuid=mgtvmacF460E2AEB892&license=ZgOOgo5MjkyOTAUOvw4OBkuqtA2qDbR8hyANqnY7e3a%2FBoc7vwW%2FS5UNqnt7lZWVIJmqjkyOTI5MZgOOgg%3D%3D&ticket=&buss_id=1000014&version=5.6.307.200.2.DBEI.0.0_Release&platform=3&type=3&mf=Xiaomi&mode=Redmi+6+Pro&_support=00100000011&channel_id=1092
public class LiveChannelInfo {

    /**
     * errno : 0
     * msg : 成功
     * server_time : 1551247195
     * data : {"channel_id":"1092","channel_name":"哆啦A梦","live_type_id":"29","live_type_name":"轮播(TVOS)","channel_flag":"1","channel_type":"0","channel_number":"62","channel_image":"","order_no":"0","activity_id":"1092","sub_name":"","channel_image_pc":"","begin_time":"0001-01-01 00:00:00","end_time":"0001-01-01 00:00:00","part_id":"0","charge_info":"0","payicon":"0","mpp_charge_info":"0","mpp_payicon":"0","room_id":"0","is_p2p":"1","corner_mark_img":"","sources":[{"source_id":"3041","definition":"hd","definition_desc":"高清","source_file_type":"2","is_default":"0"}],"assets":[{"asset_id":"RollingBroadcast_niulai","category_id":"2000000"},{"asset_id":"RollingBroadcast","category_id":"2000010"},{"asset_id":"RollingBroadcast_niulai","category_id":"2000010"},{"asset_id":"RollingBroadcast","category_id":"2000000"}]}
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
         * channel_id : 1092
         * channel_name : 哆啦A梦
         * live_type_id : 29
         * live_type_name : 轮播(TVOS)
         * channel_flag : 1
         * channel_type : 0
         * channel_number : 62
         * channel_image :
         * order_no : 0
         * activity_id : 1092
         * sub_name :
         * channel_image_pc :
         * begin_time : 0001-01-01 00:00:00
         * end_time : 0001-01-01 00:00:00
         * part_id : 0
         * charge_info : 0
         * payicon : 0
         * mpp_charge_info : 0
         * mpp_payicon : 0
         * room_id : 0
         * is_p2p : 1
         * corner_mark_img :
         * sources : [{"source_id":"3041","definition":"hd","definition_desc":"高清","source_file_type":"2","is_default":"0"}]
         * assets : [{"asset_id":"RollingBroadcast_niulai","category_id":"2000000"},{"asset_id":"RollingBroadcast","category_id":"2000010"},{"asset_id":"RollingBroadcast_niulai","category_id":"2000010"},{"asset_id":"RollingBroadcast","category_id":"2000000"}]
         */

        private String channel_id;
        private String channel_name;
        private String live_type_id;
        private String live_type_name;
        private String channel_flag;
        private String channel_type;
        private String channel_number;
        private String channel_image;
        private String order_no;
        private String activity_id;
        private String sub_name;
        private String channel_image_pc;
        private String begin_time;
        private String end_time;
        private String part_id;
        private String charge_info;
        private String payicon;
        private String mpp_charge_info;
        private String mpp_payicon;
        private String room_id;
        private String is_p2p;
        private String corner_mark_img;
        private List<SourcesBean> sources;
        private List<AssetsBean> assets;

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public String getLive_type_id() {
            return live_type_id;
        }

        public void setLive_type_id(String live_type_id) {
            this.live_type_id = live_type_id;
        }

        public String getLive_type_name() {
            return live_type_name;
        }

        public void setLive_type_name(String live_type_name) {
            this.live_type_name = live_type_name;
        }

        public String getChannel_flag() {
            return channel_flag;
        }

        public void setChannel_flag(String channel_flag) {
            this.channel_flag = channel_flag;
        }

        public String getChannel_type() {
            return channel_type;
        }

        public void setChannel_type(String channel_type) {
            this.channel_type = channel_type;
        }

        public String getChannel_number() {
            return channel_number;
        }

        public void setChannel_number(String channel_number) {
            this.channel_number = channel_number;
        }

        public String getChannel_image() {
            return channel_image;
        }

        public void setChannel_image(String channel_image) {
            this.channel_image = channel_image;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getSub_name() {
            return sub_name;
        }

        public void setSub_name(String sub_name) {
            this.sub_name = sub_name;
        }

        public String getChannel_image_pc() {
            return channel_image_pc;
        }

        public void setChannel_image_pc(String channel_image_pc) {
            this.channel_image_pc = channel_image_pc;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPart_id() {
            return part_id;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public String getCharge_info() {
            return charge_info;
        }

        public void setCharge_info(String charge_info) {
            this.charge_info = charge_info;
        }

        public String getPayicon() {
            return payicon;
        }

        public void setPayicon(String payicon) {
            this.payicon = payicon;
        }

        public String getMpp_charge_info() {
            return mpp_charge_info;
        }

        public void setMpp_charge_info(String mpp_charge_info) {
            this.mpp_charge_info = mpp_charge_info;
        }

        public String getMpp_payicon() {
            return mpp_payicon;
        }

        public void setMpp_payicon(String mpp_payicon) {
            this.mpp_payicon = mpp_payicon;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getIs_p2p() {
            return is_p2p;
        }

        public void setIs_p2p(String is_p2p) {
            this.is_p2p = is_p2p;
        }

        public String getCorner_mark_img() {
            return corner_mark_img;
        }

        public void setCorner_mark_img(String corner_mark_img) {
            this.corner_mark_img = corner_mark_img;
        }

        public List<SourcesBean> getSources() {
            return sources;
        }

        public void setSources(List<SourcesBean> sources) {
            this.sources = sources;
        }

        public List<AssetsBean> getAssets() {
            return assets;
        }

        public void setAssets(List<AssetsBean> assets) {
            this.assets = assets;
        }

        public static class SourcesBean {
            /**
             * source_id : 3041
             * definition : hd
             * definition_desc : 高清
             * source_file_type : 2
             * is_default : 0
             */

            private String source_id;
            private String definition;
            private String definition_desc;
            private String source_file_type;
            private String is_default;

            public String getSource_id() {
                return source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id;
            }

            public String getDefinition() {
                return definition;
            }

            public void setDefinition(String definition) {
                this.definition = definition;
            }

            public String getDefinition_desc() {
                return definition_desc;
            }

            public void setDefinition_desc(String definition_desc) {
                this.definition_desc = definition_desc;
            }

            public String getSource_file_type() {
                return source_file_type;
            }

            public void setSource_file_type(String source_file_type) {
                this.source_file_type = source_file_type;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }
        }

        public static class AssetsBean {
            /**
             * asset_id : RollingBroadcast_niulai
             * category_id : 2000000
             */

            private String asset_id;
            private String category_id;

            public String getAsset_id() {
                return asset_id;
            }

            public void setAsset_id(String asset_id) {
                this.asset_id = asset_id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }
        }
    }
}
