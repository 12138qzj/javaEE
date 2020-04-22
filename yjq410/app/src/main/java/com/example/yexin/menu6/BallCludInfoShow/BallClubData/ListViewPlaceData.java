package com.example.yexin.menu6.BallCludInfoShow.BallClubData;

/**
 * Created by DELL on 2020/4/20.
 */

public class ListViewPlaceData {

    final static int KEYOK=121213;
    final static int KEYNO=121214;

    private String PlaceName;
    private int isHave;

    public ListViewPlaceData(String placeName,boolean mishave){
        PlaceName=placeName;
        if(mishave){
            isHave=KEYOK;
        }else{
            isHave=KEYNO;
        }
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public int getIsHave() {
        return isHave;
    }

    public void setIsHave(int isHave) {
        this.isHave = isHave;
    }
}
