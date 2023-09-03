package CMS;
public class movieSession {
    private String movieName;
    private double ticketPrice;
    private String videoHall;
    private String showtime;
    private int totalSeats;
    private int availableSeats;
    private  String[][] seatMap;


    public movieSession(String movieName,double ticketPrice, String videoHall, String showtime, int totalSeats,int availableSeats,String [][]seatMap) {
        this.movieName=movieName;
        this.ticketPrice = ticketPrice;
        this.videoHall = videoHall;
        this.showtime = showtime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.seatMap=seatMap;
    }
    public movieSession(String movieName,double ticketPrice, String videoHall, String showtime, int totalSeats,int availableSeats) {
        this.movieName=movieName;
        this.ticketPrice = ticketPrice;
        this.videoHall = videoHall;
        this.showtime = showtime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
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
        this.seatMap=new String[numRows+1][numCols+1];
        seatMap[0][0] = " ";
        int row, col;
        for (row = 1; row < numRows + 1; row++) {
            for (col = 1; col < numCols + 1; col++) {
                seatMap[row][col] = "O";
            }
        }

        for (row = 0, col = 1; col < numCols + 1; col++) {
            seatMap[row][col] = String.format("%2d", col);
        }

        for (row = 1, col = 0; row < numRows + 1; row++) {
            seatMap[row][col] = String.valueOf(row);
        }
        return seatMap;
    }
    public void displaySeatMap(int numRows,int numCols) {
        this.seatMap=initializeSeatMap(numRows,numCols);
        System.out.println("==============座位图==============");
        for (int row = 0; row < numRows + 1; row++) {
            for (int col = 0; col < numCols + 1; col++) {
                System.out.print(String.format("%2s", seatMap[row][col]) + " ");
            }
            System.out.println();
        }
    }
}

