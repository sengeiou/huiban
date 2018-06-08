package com.bshuiban.baselibrary.model;

/**
 * Created by xinheng on 2018/6/8.<br/>
 * describe：图片上传结果
 */
public class ImageUploadResult extends ResultBean {
    /**
     * img : group2/M00/04/0A/ag4bNlsZ3TuAC-VNAAEOKWhtr1Q733.jpg
     * imgSmall : group2/M00/04/0A/ag4bNlsZ3TuAC-VNAAEOKWhtr1Q733.jpg
     */

    private String img;
    private String imgSmall;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }
}
