package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/5/23/023.
 */

public class SpecificationBean {


    /**
     * code : 0
     * msg : OK
     * result : {"skuId":36600750847168512,"name":"Friso/美素佳儿幼儿配方奶粉3段罐装900g","price":200,"promoPrice":0.01,"stock":0,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599299949334528_330x330.png?330,330","video":"","banner":"http://img.tiancaijiazu.com/2019/08/10/36599726904315904_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726933676032_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726900121600_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726841401344_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726753320960_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726799458304_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599728657534976_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599728879833088_750x750.png?750,750","appDescription":"http://img.tiancaijiazu.com/2019/08/10/36600653346377728_750x0.jpg?750,760|http://img.tiancaijiazu.com/2019/08/10/36600653690310656_750x0.jpg?750,1462|http://img.tiancaijiazu.com/2019/08/10/36600653719670784_750x0.jpg?750,2038|http://img.tiancaijiazu.com/2019/08/10/36600654558531584_750x0.jpg?750,1871|http://img.tiancaijiazu.com/2019/08/10/36600654025854976_750x0.jpg?750,1878|http://img.tiancaijiazu.com/2019/08/10/36600654243958784_750x0.jpg?750,1328|http://img.tiancaijiazu.com/2019/08/10/36600653912608768_750x0.jpg?750,1919|http://img.tiancaijiazu.com/2019/08/10/36600656034926592_750x0.jpg?750,1531|http://img.tiancaijiazu.com/2019/08/10/36600658199187456_750x0.jpg?750,1411|http://img.tiancaijiazu.com/2019/08/10/36600658333405184_750x0.jpg?750,299|http://img.tiancaijiazu.com/2019/08/10/36600658274684928_750x0.jpg?750,427|http://img.tiancaijiazu.com/2019/08/10/36600658798972928_750x0.jpg?750,1443|http://img.tiancaijiazu.com/2019/08/10/36600659067408384_750x0.jpg?750,1216|http://img.tiancaijiazu.com/2019/08/10/36600659168071680_750x0.jpg?750,309|http://img.tiancaijiazu.com/2019/08/10/36600660015321088_750x0.jpg?750,1719","skuList":[{"skuId":36600750796836864,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599207280381952_88x88.png?88,88","keyOptionId":36598185317240832},{"skuId":36600750822002688,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599240788676608_88x88.png?88,88","keyOptionId":36598216212484096},{"skuId":36600750834585600,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599273957232640_88x88.png?88,88","keyOptionId":36598283312959488},{"skuId":36600750847168512,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599299949334528_88x88.png?88,88","keyOptionId":36598324240977920}],"stockList":[{"stockId":36600750880722944,"optionIds":"36598185317240832,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750889111552,"optionIds":"36598185317240832,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750901694464,"optionIds":"36598216212484096,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750910083072,"optionIds":"36598216212484096,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750918471680,"optionIds":"36598283312959488,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750926860288,"optionIds":"36598283312959488,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750939443200,"optionIds":"36598324240977920,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750952026112,"optionIds":"36598324240977920,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9}],"sellProperties":[{"name":"段位","optionList":[{"optionsId":36598185317240832,"name":"1段"},{"optionsId":36598216212484096,"name":"2段"},{"optionsId":36598283312959488,"name":"3段"},{"optionsId":36598324240977920,"name":"4段"}]},{"name":"国家","optionList":[{"optionsId":36598356092522496,"name":"国产"},{"optionsId":36598392729767936,"name":"进口"}]}],"properties":[{"name":"颜色","optionList":[{"name":"黄色"}]}]}
     */

    private String code;
    private String msg;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * skuId : 36600750847168512
         * name : Friso/美素佳儿幼儿配方奶粉3段罐装900g
         * price : 200.0
         * promoPrice : 0.01
         * stock : 0
         * picUri : http://img.tiancaijiazu.com/2019/08/10/36599299949334528_330x330.png?330,330
         * video :
         * banner : http://img.tiancaijiazu.com/2019/08/10/36599726904315904_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726933676032_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726900121600_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726841401344_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726753320960_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599726799458304_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599728657534976_750x750.png?750,750|http://img.tiancaijiazu.com/2019/08/10/36599728879833088_750x750.png?750,750
         * appDescription : http://img.tiancaijiazu.com/2019/08/10/36600653346377728_750x0.jpg?750,760|http://img.tiancaijiazu.com/2019/08/10/36600653690310656_750x0.jpg?750,1462|http://img.tiancaijiazu.com/2019/08/10/36600653719670784_750x0.jpg?750,2038|http://img.tiancaijiazu.com/2019/08/10/36600654558531584_750x0.jpg?750,1871|http://img.tiancaijiazu.com/2019/08/10/36600654025854976_750x0.jpg?750,1878|http://img.tiancaijiazu.com/2019/08/10/36600654243958784_750x0.jpg?750,1328|http://img.tiancaijiazu.com/2019/08/10/36600653912608768_750x0.jpg?750,1919|http://img.tiancaijiazu.com/2019/08/10/36600656034926592_750x0.jpg?750,1531|http://img.tiancaijiazu.com/2019/08/10/36600658199187456_750x0.jpg?750,1411|http://img.tiancaijiazu.com/2019/08/10/36600658333405184_750x0.jpg?750,299|http://img.tiancaijiazu.com/2019/08/10/36600658274684928_750x0.jpg?750,427|http://img.tiancaijiazu.com/2019/08/10/36600658798972928_750x0.jpg?750,1443|http://img.tiancaijiazu.com/2019/08/10/36600659067408384_750x0.jpg?750,1216|http://img.tiancaijiazu.com/2019/08/10/36600659168071680_750x0.jpg?750,309|http://img.tiancaijiazu.com/2019/08/10/36600660015321088_750x0.jpg?750,1719
         * skuList : [{"skuId":36600750796836864,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599207280381952_88x88.png?88,88","keyOptionId":36598185317240832},{"skuId":36600750822002688,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599240788676608_88x88.png?88,88","keyOptionId":36598216212484096},{"skuId":36600750834585600,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599273957232640_88x88.png?88,88","keyOptionId":36598283312959488},{"skuId":36600750847168512,"picUri":"http://img.tiancaijiazu.com/2019/08/10/36599299949334528_88x88.png?88,88","keyOptionId":36598324240977920}]
         * stockList : [{"stockId":36600750880722944,"optionIds":"36598185317240832,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750889111552,"optionIds":"36598185317240832,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750901694464,"optionIds":"36598216212484096,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750910083072,"optionIds":"36598216212484096,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750918471680,"optionIds":"36598283312959488,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750926860288,"optionIds":"36598283312959488,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750939443200,"optionIds":"36598324240977920,36598356092522496","optionNames":"","price":200,"promoPrice":0.01,"stock":9},{"stockId":36600750952026112,"optionIds":"36598324240977920,36598392729767936","optionNames":"","price":200,"promoPrice":0.01,"stock":9}]
         * sellProperties : [{"name":"段位","optionList":[{"optionsId":36598185317240832,"name":"1段"},{"optionsId":36598216212484096,"name":"2段"},{"optionsId":36598283312959488,"name":"3段"},{"optionsId":36598324240977920,"name":"4段"}]},{"name":"国家","optionList":[{"optionsId":36598356092522496,"name":"国产"},{"optionsId":36598392729767936,"name":"进口"}]}]
         * properties : [{"name":"颜色","optionList":[{"name":"黄色"}]}]
         */

        private long skuId;
        private String name;
        private String shareUrl;
        private double price;
        private double promoPrice;
        private int stock;
        private String picUri;
        private String video;
        private String banner;
        private String appDescription;
        private String previewUrl;
        private List<SkuListBean> skuList;
        private List<StockListBean> stockList;
        private List<SellPropertiesBean> sellProperties;
        private List<PropertiesBean> properties;

        public String getPreviewUrl() {
            return previewUrl;
        }

        public void setPreviewUrl(String previewUrl) {
            this.previewUrl = previewUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(double promoPrice) {
            this.promoPrice = promoPrice;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getAppDescription() {
            return appDescription;
        }

        public void setAppDescription(String appDescription) {
            this.appDescription = appDescription;
        }

        public List<SkuListBean> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuListBean> skuList) {
            this.skuList = skuList;
        }

        public List<StockListBean> getStockList() {
            return stockList;
        }

        public void setStockList(List<StockListBean> stockList) {
            this.stockList = stockList;
        }

        public List<SellPropertiesBean> getSellProperties() {
            return sellProperties;
        }

        public void setSellProperties(List<SellPropertiesBean> sellProperties) {
            this.sellProperties = sellProperties;
        }

        public List<PropertiesBean> getProperties() {
            return properties;
        }

        public void setProperties(List<PropertiesBean> properties) {
            this.properties = properties;
        }

        public static class SkuListBean {
            /**
             * skuId : 36600750796836864
             * picUri : http://img.tiancaijiazu.com/2019/08/10/36599207280381952_88x88.png?88,88
             * keyOptionId : 36598185317240832
             */

            private long skuId;
            private String picUri;
            private long keyOptionId;

            public long getSkuId() {
                return skuId;
            }

            public void setSkuId(long skuId) {
                this.skuId = skuId;
            }

            public String getPicUri() {
                return picUri;
            }

            public void setPicUri(String picUri) {
                this.picUri = picUri;
            }

            public long getKeyOptionId() {
                return keyOptionId;
            }

            public void setKeyOptionId(long keyOptionId) {
                this.keyOptionId = keyOptionId;
            }
        }

        public static class StockListBean {
            /**
             * stockId : 36600750880722944
             * optionIds : 36598185317240832,36598356092522496
             * optionNames :
             * price : 200.0
             * promoPrice : 0.01
             * stock : 9
             */

            private long stockId;
            private String optionIds;
            private String optionNames;
            private double price;
            private double promoPrice;
            private int stock;

            public long getStockId() {
                return stockId;
            }

            public void setStockId(long stockId) {
                this.stockId = stockId;
            }

            public String getOptionIds() {
                return optionIds;
            }

            public void setOptionIds(String optionIds) {
                this.optionIds = optionIds;
            }

            public String getOptionNames() {
                return optionNames;
            }

            public void setOptionNames(String optionNames) {
                this.optionNames = optionNames;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPromoPrice() {
                return promoPrice;
            }

            public void setPromoPrice(double promoPrice) {
                this.promoPrice = promoPrice;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }
        }

        public static class SellPropertiesBean {
            /**
             * name : 段位
             * optionList : [{"optionsId":36598185317240832,"name":"1段"},{"optionsId":36598216212484096,"name":"2段"},{"optionsId":36598283312959488,"name":"3段"},{"optionsId":36598324240977920,"name":"4段"}]
             */

            private String name;
            private List<OptionListBean> optionList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<OptionListBean> getOptionList() {
                return optionList;
            }

            public void setOptionList(List<OptionListBean> optionList) {
                this.optionList = optionList;
            }

            public static class OptionListBean {
                /**
                 * optionsId : 36598185317240832
                 * name : 1段
                 */

                private long optionsId;
                private String name;
                private boolean isbo;

                public long getOptionsId() {
                    return optionsId;
                }

                public void setOptionsId(long optionsId) {
                    this.optionsId = optionsId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public boolean isIsbo() {
                    return isbo;
                }

                public void setIsbo(boolean isbo) {
                    this.isbo = isbo;
                }
            }
        }

        public static class PropertiesBean {
            /**
             * name : 颜色
             * optionList : [{"name":"黄色"}]
             */

            private String name;
            private List<OptionListBeanX> optionList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<OptionListBeanX> getOptionList() {
                return optionList;
            }

            public void setOptionList(List<OptionListBeanX> optionList) {
                this.optionList = optionList;
            }

            public static class OptionListBeanX {
                /**
                 * name : 黄色
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
