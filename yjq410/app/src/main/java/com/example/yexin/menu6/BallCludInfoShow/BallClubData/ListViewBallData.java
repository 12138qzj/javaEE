package com.example.yexin.menu6.BallCludInfoShow.BallClubData;

/**
 * Created by DELL on 2020/4/20.
 */

public class ListViewBallData {
    private int BallIcon;
    private String BallName;
    private String BallPrice;
    private boolean BallChoose;

    public ListViewBallData(int mBallIcon, String mBallName, String mBallPrice, boolean mBallChoose){
        this.BallIcon=mBallIcon;
        this.BallName=mBallName;
        this.BallPrice=mBallPrice;
        this.BallChoose=mBallChoose;
    }

    public int getBallIcon() {
        return BallIcon;
    }

    public void setBallIcon(int ballIcon) {
        BallIcon = ballIcon;
    }

    public String getBallName() {
        return BallName;
    }

    public void setBallName(String ballName) {
        BallName = ballName;
    }

    public String getBallPrice() {
        return BallPrice;
    }

    public void setBallPrice(String ballPrice) {
        BallPrice = ballPrice;
    }

    public boolean isBallChoose() {
        return BallChoose;
    }

    public void setBallChoose(boolean ballChoose) {
        BallChoose = ballChoose;
    }
}
