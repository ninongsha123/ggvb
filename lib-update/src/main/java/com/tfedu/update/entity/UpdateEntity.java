package com.tfedu.update.entity;

import java.util.List;

/**
 * desc :
 * author：panyy
 * data：2018/5/8
 */
public class UpdateEntity {

    private String code;
    private String message;
    private DataEntity data;
    private String sign;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class DataEntity {

        private LatestversionEntity latestversion;
        private List<UpdateitemsEntity> updateitems;

        public LatestversionEntity getLatestversion() {
            return latestversion;
        }

        public void setLatestversion(LatestversionEntity latestversion) {
            this.latestversion = latestversion;
        }

        public List<UpdateitemsEntity> getUpdateitems() {
            return updateitems;
        }

        public void setUpdateitems(List<UpdateitemsEntity> updateitems) {
            this.updateitems = updateitems;
        }

        public static class LatestversionEntity {

            private Object productcode;
            private String code;
            private String name;
            private String description;
            private String versionid;
            private String createtime;
            private boolean mandatory;
            private boolean whole;

            public Object getProductcode() {
                return productcode;
            }

            public void setProductcode(Object productcode) {
                this.productcode = productcode;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getVersionid() {
                return versionid;
            }

            public void setVersionid(String versionid) {
                this.versionid = versionid;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public boolean isMandatory() {
                return mandatory;
            }

            public void setMandatory(boolean mandatory) {
                this.mandatory = mandatory;
            }

            public boolean isWhole() {
                return whole;
            }

            public void setWhole(boolean whole) {
                this.whole = whole;
            }
        }

        public static class UpdateitemsEntity {

            private int id;
            private String versionid;
            private String filename;
            private String address;
            private String location;
            private int filetype;
            private int filesize;
            private int isreg;
            private int deccompressfile;
            private long createtime;
            private int decompressfile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVersionid() {
                return versionid;
            }

            public void setVersionid(String versionid) {
                this.versionid = versionid;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getFiletype() {
                return filetype;
            }

            public void setFiletype(int filetype) {
                this.filetype = filetype;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public int getIsreg() {
                return isreg;
            }

            public void setIsreg(int isreg) {
                this.isreg = isreg;
            }

            public int getDeccompressfile() {
                return deccompressfile;
            }

            public void setDeccompressfile(int deccompressfile) {
                this.deccompressfile = deccompressfile;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public int getDecompressfile() {
                return decompressfile;
            }

            public void setDecompressfile(int decompressfile) {
                this.decompressfile = decompressfile;
            }
        }
    }
}
