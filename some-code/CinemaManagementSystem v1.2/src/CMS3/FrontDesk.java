package CMS3;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class FrontDesk extends Person {
    private String registrationTime;  // 注册时间
    private String phoneNumber;    // 手机号
    private String email;// 邮箱
    private String type;
    private String username;
    private String password;
    private boolean locked;
    private String userID;

    public FrontDesk() {
    }

    public FrontDesk(String userID, String username, String registrationTime, String type, String password, String phoneNumber, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.registrationTime = registrationTime;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.email = email;
    }

    @Override
    public String toString() {
        return "FrontDesk{" +
                "registrationTime='" + registrationTime + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void loginFrontDesk(String username, String password) {
        int userIndex = findUserIndexByUsername(FrontDeskList, username);
        if (userIndex != -1) {
            FrontDesk frontDesk = (FrontDesk) FrontDeskList.get(userIndex);
            if (!frontDesk.isLocked()) {
                // 获取存储在数据库或文件中的密码哈希
                String storedHashedPassword = frontDesk.getPassword();
                // 比较用户提供的密码哈希与存储的密码哈希
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    loginAttempts.remove(username);
                    System.out.println("登录成功，欢迎：" + username + "!");
                    frontDeskMenu();
                } else {
                    int attempts = loginAttempts.getOrDefault(username, 0) + 1;
                    loginAttempts.put(username, attempts);
                    if (attempts >= 5) {
                        //frontDesk.lockAccount();
                        System.out.println("由于登录次数过多，账户已被锁定");
                    } else {
                        System.out.println("密码错误。剩余尝试次数：" + (5 - attempts));
                    }
                }
            } else {
                System.out.println("账户已被锁定，请联系客服支持。");
            }
        } else {
            System.out.println("用户不存在");
        }
    }

    public void frontDeskMainMenu() {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("亲爱的前台，请输入下列数字选择你想要办理的服务：         ");
            System.out.println("      1.登录您的账户                 ");
            System.out.println("      e.退出                 ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    System.out.println("请输入你的用户名：");
                    String username = sc.next();
                    System.out.println("请输入你的密码：");
                    String password = sc.next();
                    loginFrontDesk(username, password);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void frontDeskMenu() {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     登陆成功，请选择你需要的服务            ");
            System.out.println("           1.列出所有正在上映影片的信息                ");
            System.out.println("           2.列出指定电影和场次的信息              ");
            System.out.println("           3.售票              ");
            System.out.println("           e.退出              ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> printMovieInfo();
                case "2" -> viewAppointedMovieInfo();
                case "3" -> sellTicket();
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void sellTicket() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入顾客想要观看的场次：电影名，放映厅，放映时间");
        String movieName = sc.next();
        String videoHall = sc.next();
        String showtime = sc.next();
        int sessionIndex = findSessionIndexByMovieName(sessionList, movieName, videoHall, showtime);
        if (sessionIndex != -1) {
            movieSession session = sessionList.get(sessionIndex);
            System.out.println("请输入顾客的用户名或手机号：");
            String input = sc.next();
            int userIndex;
            int userIndex1 = findUserIndexByUsername(userList, input);
            int userIndex2 = findUserIndexByPhoneNumber(userList, input);
            if (userIndex1 == -1 && userIndex2 == -1) {
                System.out.println("用户不存在");
                return;
            } else if (userIndex1 != -1) {
                userIndex = userIndex1;
            } else {
                userIndex = userIndex2;
            }
            User user = (User) userList.get(userIndex);
            System.out.println("请输入顾客想要购买的票数：");
            int numSeats = sc.nextInt();
            if (numSeats <= session.getAvailableSeats()) {
                session.displaySeatMap(7, 12, session.getSeatMap());
                int[] selectedRows = new int[numSeats];
                int[] selectedCols = new int[numSeats];
                String[] ticketID = new String[numSeats];
                for (int i = 0; i < numSeats; i++) {
                    System.out.print("请输入你选择的行数" + ": ");
                    selectedRows[i] = sc.nextInt();
                    System.out.print("请输入你选择的列数" + ": ");
                    selectedCols[i] = sc.nextInt();
                    if (!isValidSeat(selectedRows[i], selectedCols[i], session)) {
                        System.out.println("选择的座位无效或该位置已有人预定");
                        return;
                    }
                }
                double totalTicketsPrice = user.judgeUserLevel(user.getUsername(), user.getCumulativeConsumptionExpense()) * session.getTicketPrice() * numSeats;
                System.out.println("顾客需要支付的金额：" + totalTicketsPrice);
                System.out.println("请输入顾客想要使用的支付方式");
                System.out.println("1.支付宝");
                System.out.println("2.微信");
                System.out.println("3.银行卡");
                int num = sc.nextInt();
                if (num >= 1 && num <= 3) {
                    System.out.println("顾客支付成功！");
                    int movieIndex = findMovieIndexByMovieName(movieList, movieName);
                    Movie movie = movieList.get(movieIndex);
                    for (int i = 0; i < numSeats; i++) {
                        session.getSeatMap()[selectedRows[i]][selectedCols[i]] = "X";// 将已购座位状态变为 "X"
                        ticketID[i] = getTicketID();
                    }
                    for (int j = 0; j < numSeats; j++) {
                        user.getPurchaseHistory().add("购票时间：" + generateRegistrationTime() + "，片名：" + movieName + "，片长：" + movie.getDuration() + "，放映厅：" + session.getVideoHall() + "，放映时间：" + session.getShowtime() + "，座位信息：" + selectedRows[j] + "-" + selectedCols[j] + "，电影票的电子ID编号：" + ticketID[j]);
                    }
                    double cumulativeConsumptionExpense = user.getCumulativeConsumptionExpense() + totalTicketsPrice;
                    user.setCumulativeConsumptionExpense(cumulativeConsumptionExpense);
                    user.judgeUserLevel(user.getUsername(), cumulativeConsumptionExpense);
                    session.setAvailableSeats(session.getAvailableSeats() - numSeats);
                    int ConsumptionNum = user.getCumulativeConsumptionNum();
                    ConsumptionNum++;
                    user.setCumulativeConsumptionNum(ConsumptionNum);
                    for (int i = 0; i < numSeats; i++) {
                        System.out.println("片名：" + movieName + "，片长：" + movie.getDuration() + "，放映厅：" + session.getVideoHall() + "，放映时间：" + session.getShowtime() + "，座位信息：" + selectedRows[i] + "-" + selectedCols[i] + "，电影票的电子ID编号：" + ticketID[i]);
                    }
                }
            } else {
                System.out.println("订购数量超出空闲座位数");
            }
        } else System.out.println("该场次不存在");
    }
}