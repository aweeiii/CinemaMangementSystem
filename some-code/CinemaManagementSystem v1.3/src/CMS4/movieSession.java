package CMS4;

public class movieSession {
    private String movieName;
    private double ticketPrice;
    private String videoHall;
    private String showtime;
    private int totalSeats;
    private int availableSeats;
    private String[][] seatMap;


    public movieSession(String movieName, double ticketPrice, String videoHall, String showtime, int totalSeats, int availableSeats, String[][] seatMap) {
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
        this.videoHall = videoHall;
        this.showtime = showtime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.seatMap = seatMap;
    }

    public movieSession(String movieName, double ticketPrice, String videoHall, String showtime, int totalSeats, int availableSeats) {
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
        this.videoHall = videoHall;
        this.showtime = showtime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("movieSession{");
        sb.append("movieName='").append(movieName).append("',");
        sb.append("ticketPrice=").append("'").append(ticketPrice).append("',");
        sb.append("videoHall='").append(videoHall).append("',");
        sb.append("showtime='").append(showtime).append("',");
        sb.append("totalSeats=").append("'").append(totalSeats).append("',");
        sb.append("availableSeats=").append("'").append(availableSeats).append("',");
        sb.append("seatMap=[,\n");

        for (String[] row : seatMap) {
            sb.append("  [");
            boolean firstSeat = true;
            for (String seat : row) {
                if (!firstSeat) {
                    sb.append(" ");
                }
                // 替换单引号和逗号为空格
                String sanitizedSeat = seat.replace("'", "").replace(",", "");

                sb.append(sanitizedSeat);
                firstSeat = false;
            }
            sb.append("],\n");
        }
        sb.append("]}");
        return sb.toString();
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getVideoHall() {
        return videoHall;
    }

    public void setVideoHall(String videoHall) {
        this.videoHall = videoHall;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String[][] getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(String[][] seatMap) {
        this.seatMap = seatMap;
    }


    public String[][] initializeSeatMap(int numRows, int numCols) {
        this.seatMap = new String[numRows + 1][numCols + 1];
        seatMap[0][0] = " ";
        int row, col;
        for (row = 1; row < numRows + 1; row++) {
            for (col = 1; col < numCols + 1; col++) {
                seatMap[row][col] = "O";
            }
        }

        for (row = 0, col = 1; col < numCols + 1; col++) {
            seatMap[row][col] = String.valueOf(col);
        }

        for (row = 1, col = 0; row < numRows + 1; row++) {
            seatMap[row][col] = String.valueOf(row);
        }
        return seatMap;
    }

    public void displaySeatMap(int numRows, int numCols, String[][] seatMap) {
        System.out.println("==============座位图==============");
        for (int row = 0; row < numRows + 1; row++) {
            for (int col = 0; col < numCols + 1; col++) {
                // 检查元素是否为null，如果为null则打印空格
                String seat = (seatMap[row][col] != null) ? seatMap[row][col] : " ";
                System.out.print(String.format("%2s", seat) + " ");
            }
            System.out.println();
        }
    }

}

