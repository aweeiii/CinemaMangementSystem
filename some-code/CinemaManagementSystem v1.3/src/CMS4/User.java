package CMS4;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class User extends Person {
    private String userID; // 用户ID
    private String username; // 用户名
    private String userLevel; // 用户级别
    private String registrationTime; // 注册时间
    private double CumulativeConsumptionExpense; // 累计消费总金额
    private int CumulativeConsumptionNum; // 累计消费次数
    private String phoneNumber; // 手机号
    private String email; // 邮箱
    private String password; // 密码
    private ArrayList<String> purchaseHistory; // 购买历史
    private boolean locked;
    private Timer timer;

    public User() {
    }

    public User(String username, String password) {
        super(username, password);
    }

    public User(String username, String password, String email) {
        super(username, password);
        this.email = email;
    }

    public User(String userID, String username, String phoneNumber, String email, String password) {
        super(username, password);
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String userID, String username, String userLevel, String registrationTime, double cumulativeConsumptionExpense, int cumulativeConsumptionNum, String phoneNumber, String email, String password, ArrayList<String> purchaseHistory) {
        this.username = username;
        this.password = password;
        this.userID = userID;
        this.userLevel = userLevel;
        this.registrationTime = registrationTime;
        CumulativeConsumptionExpense = cumulativeConsumptionExpense;
        CumulativeConsumptionNum = cumulativeConsumptionNum;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.timer = new Timer();
        this.purchaseHistory = purchaseHistory;
    }


    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public double getCumulativeConsumptionExpense() {
        return CumulativeConsumptionExpense;
    }

    public void setCumulativeConsumptionExpense(double cumulativeConsumptionExpense) {
        CumulativeConsumptionExpense = cumulativeConsumptionExpense;
    }

    public int getCumulativeConsumptionNum() {
        return CumulativeConsumptionNum;
    }

    public void setCumulativeConsumptionNum(int cumulativeConsumptionNum) {
        CumulativeConsumptionNum = cumulativeConsumptionNum;
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

    public ArrayList<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(ArrayList<String> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void lockAccount() {
        locked = true;
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
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

    public Timer getTimer() {
        return timer;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", registrationTime='" + registrationTime + '\'' +
                ", CumulativeConsumptionExpense='" + CumulativeConsumptionExpense + "'" +
                ", CumulativeConsumptionNum='" + CumulativeConsumptionNum + "'" +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", purchaseHistory='" + purchaseHistory + "'" +
                '}';
    }

    //消费者注册
    public boolean registerUser(String username, String password, String email, String phoneNumber) {
        if (username.length() >= 5 && isPasswordValid(password)) {
            String userID = getRandomUserID();
            String userLevel = "铜牌用户";
            double CumulativeConsumptionExpense = 0;
            int CumulativeConsumptionNum = 0;
            ArrayList<String> purchaseHistory = new ArrayList<>();
            userList.add(new User(userID, username, userLevel, generateRegistrationTime(), CumulativeConsumptionExpense, CumulativeConsumptionNum, phoneNumber, email, password, purchaseHistory));
            writePersonsToExcel(userList, userFile);
            System.out.println("用户注册成功：" + username);
            return true;
        } else return false;
    }

    //消费者登录
    public void loginUser(String username, String password) {
        int userIndex = findUserIndexByUsername(userList, username);
        if (userIndex != -1) {
            User user = (User) userList.get(userIndex);
            if (!user.isLocked()) {
                // 获取存储在数据库或文件中的密码哈希
                String storedHashedPassword = user.getPassword();
                // 比较用户提供的密码哈希与存储的密码哈希
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    loginAttempts.remove(username);
                    System.out.println("登录成功，欢迎：" + username + "!");
                    userMenu1(username, password);
                } else {
                    int attempts = loginAttempts.getOrDefault(username, 0) + 1;
                    loginAttempts.put(username, attempts);
                    if (attempts >= 5) {
                        user.lockAccount(); // 锁定用户账户
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


    public void userMainMenu() {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("==========================================");
            System.out.println("亲爱的用户，请输入下列数字选择你想要办理的服务  ");
            System.out.println("            1.注册新的账户                 ");
            System.out.println("            2.登录您的账户                 ");
            System.out.println("            e.退出                      ");
            System.out.println("==========================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> {
                    do {
                        System.out.println("请输入你想创建的用户名：");
                        String username = sc.next();
                        System.out.println("请输入你要绑定的邮箱：");
                        String email = sc.next();
                        System.out.println("请输入你的手机号：");
                        String phoneNumber = sc.next();
                        System.out.println("请输入密码：");
                        String password1 = sc.next();
                        System.out.println("请确认密码：");
                        String password2 = sc.next();
                        if (password1.equals(password2) && registerUser(username, password1, email, phoneNumber)) {
                            break;
                        } else {
                            System.out.println("可能是两次输入的密码不一致或输入了无效的用户名或密码，请重新输入（注意：用户名长度需大于等于5，密码的长度大于8，包含大写字母、小写字母、数字和特殊字符）");
                        }
                    } while (true);
                }
                //登录账户
                case "2" -> {
                    System.out.println("请输入你的用户名：");
                    String username = sc.next();
                    System.out.println("请输入你的密码：");
                    String password = sc.next();
                    loginUser(username, password);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void userMenu1(String username, String password) {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     登陆成功，请选择你需要的服务            ");
            System.out.println("           1.密码管理                ");
            System.out.println("           2.购票               ");
            System.out.println("           e.退出               ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> userMenu2(username, password);
                case "2" -> userMenu3(username);
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void userMenu2(String username, String password) {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     密码管理，请选择你需要的服务            ");
            System.out.println("           1.修改密码               ");
            System.out.println("           2.忘记密码               ");
            System.out.println("           e.退出               ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> changePassword(userList, username, password);
                case "2" -> {
                    System.out.println("请输入你注册时用的电子邮箱：");
                    String email = sc.next();
                    resetPassword1(username, email);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    public void userMenu3(String username) {
        Scanner sc = new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("          购票，请选择你需要的服务            ");
            System.out.println("           1.查看所有电影的放映信息               ");
            System.out.println("           2.查看指定电影的放映信息              ");
            System.out.println("           3.购票                              ");
            System.out.println("           4.取票                              ");
            System.out.println("           5.查看购票历史                              ");
            System.out.println("           e.退出                            ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> printMovieInfo();
                case "2" -> viewAppointedMovieInfo();
                case "3" -> buyTicket(username);
                case "4" -> takeTicket(username);
                case "5" -> printUserPurchaseHistory(username);
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    //消费者忘记密码，重置密码
    public void resetPassword1(String username, String email) {
        int userIndex = findUserIndexByUsername(userList, username);
        if (userIndex != -1) {
            User user = (User) userList.get(userIndex);
            if (user.getEmail().equals(email)) {
                String newPassword = getRandomPassword();
                user.setPassword(newPassword);
                System.out.println("新密码已发送到您的电子邮箱");
            } else {
                System.out.println("电子邮箱与注册时不符。");
            }
        } else {
            System.out.println("用户不存在");
        }
    }

    //购票
    public void buyTicket(String username) {
        Scanner sc = new Scanner(System.in);
        System.out.println("获取你想要购买的场次，我得知道这些信息：电影名，放映厅，放映时间");
        System.out.println("请输入电影名：");
        String movieName = sc.next();
        System.out.println("请输入放映厅：");
        String videoHall = sc.next();
        System.out.println("请输入放映时间：");
        String showtime = sc.next();
        System.out.println("请输入你想要购买的票数：");
        int numSeats = sc.nextInt();
        int sessionIndex = findSessionIndexByMovieName(sessionList, movieName, videoHall, showtime);
        if(sessionIndex!=-1){
        movieSession session = sessionList.get(sessionIndex);
        Timer timer = new Timer();
        int userIndex = findUserIndexByUsername(userList, username);
        Person person = userList.get(userIndex);
        User user = (User) person;
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
                session.getSeatMap()[selectedRows[i]][selectedCols[i]] = "L";             // 'L' 表示锁定
                System.out.println("座位：" + selectedRows[i] + "-" + selectedCols[i] + " 锁定两分钟");
                TimerTask task = new SeatUnlockTask(selectedRows[i], selectedCols[i], session.getSeatMap());
                timer.schedule(task, 120000);                 // 120000毫秒=2分钟
            }
            double totalTicketsPrice = judgeUserLevel(username, user.getCumulativeConsumptionExpense()) * session.getTicketPrice() * numSeats;
            System.out.println("座位锁定两分钟，" + "一共需支付" + totalTicketsPrice + "元，等待您支付...");
            System.out.println("请输入你想要使用的支付方式");
            System.out.println("1.支付宝");
            System.out.println("2.微信");
            System.out.println("3.银行卡");
            int num = sc.nextInt();
            if (num >= 1 && num <= 3) {
                System.out.println("支付成功");
                timer.cancel();
                for (int i = 0; i < numSeats; i++) {
                    session.getSeatMap()[selectedRows[i]][selectedCols[i]] = "X";// 将已购座位状态变为 "X"
                    ticketID[i] = getTicketID();
                }
                for (int j = 0; j < numSeats; j++) {
                    user.getPurchaseHistory().add("购票时间：" + generateRegistrationTime() + "，片名：" + movieName + "，片长：" + movieList.get(findMovieIndexByMovieName(movieList, movieName)).getDuration() + "，放映厅：" + session.getVideoHall() + "，放映时间：" + session.getShowtime() + "，座位信息：" + selectedRows[j] + "-" + selectedCols[j] + "，电影票的电子ID编号：" + ticketID[j]);
                }
                double cumulativeConsumptionExpense = user.getCumulativeConsumptionExpense() + totalTicketsPrice;
                user.setCumulativeConsumptionExpense(cumulativeConsumptionExpense);
                user.judgeUserLevel(username, cumulativeConsumptionExpense);
                session.setAvailableSeats(session.getAvailableSeats() - numSeats);
                int ConsumptionNum = user.getCumulativeConsumptionNum();
                ConsumptionNum++;
                user.setCumulativeConsumptionNum(ConsumptionNum);
                for (int i = 0; i < numSeats; i++) {
                    System.out.println("片名：" + movieName + "，片长：" + movieList.get(findMovieIndexByMovieName(movieList, movieName)).getDuration() + "，放映厅：" + session.getVideoHall() + "，放映时间：" + session.getShowtime() + "，座位信息：" + selectedRows[i] + "-" + selectedCols[i] + "，电影票的电子ID编号：" + ticketID[i]);
                }
            }

        } else {
            System.out.println("订购数量超出空闲座位数");
        }}else System.out.println("该场次不存在！");
    }

    public void takeTicket(String username) {
        int userIndex = findUserIndexByUsername(userList, username);
        Person person = userList.get(userIndex);
        User user = (User) person;
        System.out.println("请输入电影票的电子ID：");
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        boolean found = false;
        for (String history : user.getPurchaseHistory()) {
            String[] ticketIDs = history.split("电影票的电子ID编号：")[1].split("，");
            for (String ticketID : ticketIDs) {
                if (ticketID.equals(input)) {
                    System.out.println("取票成功！");
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (!found) {
            System.out.println("电影票的电子ID编号错误。");
        }
    }

    public double judgeUserLevel(String username, double cumulativeConsumptionExpense) {
        int userIndex = findUserIndexByUsername(userList, username);
        Person person = userList.get(userIndex);
        double discount = 0;
        User user = (User) person;
        if (cumulativeConsumptionExpense >= 0 && cumulativeConsumptionExpense <= 300) {
            user.setUserLevel("铜牌用户");
            discount = 1.0;
        } else if (cumulativeConsumptionExpense > 300 && cumulativeConsumptionExpense <= 600) {
            user.setUserLevel("银牌用户");
            discount = 0.95;
        } else if (cumulativeConsumptionExpense > 600) {
            user.setUserLevel("金牌用户");
            discount = 0.88;
        }
        return discount;
    }

    public void printUserPurchaseHistory(String username) {
        int userIndex = findUserIndexByUsername(userList, username);
        Person person = userList.get(userIndex);
        User user = (User) person;
        StringBuilder output = new StringBuilder();
        for (String ticketInfo : user.getPurchaseHistory()) {
            output.append(ticketInfo).append(", ");
        }
        System.out.println(output);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}

