package CMS1;

import java.util.TimerTask;

public class SeatUnlockTask extends TimerTask {
    private int row;
    private int col;
    private String[][] seatMap;

    public SeatUnlockTask(int row, int col, String[][] seatMap) {
        this.row = row;
        this.col = col;
        this.seatMap = seatMap;
    }

    @Override
    public void run() {
        if (seatMap[row][col].equals("L")) {
            seatMap[row][col] = "O"; // 取消位置锁定
            System.out.println("Seat " + (row + 1) + "-" + (col + 1) + " 支付超时，座位释放");
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}