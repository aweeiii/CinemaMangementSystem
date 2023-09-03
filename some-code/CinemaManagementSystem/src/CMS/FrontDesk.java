package CMS;

import java.time.LocalDateTime;
import java.util.Scanner;

public class FrontDesk extends Person {
    private LocalDateTime registrationTime;  // 注册时间
    private String phoneNumber;    // 手机号
    private String email;// 邮箱
    private String type;
    private String username;
    private String password;
    private boolean locked;
    private String userID;

    public FrontDesk() {
    }

    public FrontDesk(String userID, String username, LocalDateTime registrationTime, String type, String password, String phoneNumber, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.registrationTime = registrationTime;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.email = email;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
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
                if (frontDesk.getPassword().equals(password)) {
                    loginAttempts.remove(username);
                    System.out.println("登录成功，欢迎：" + username + "!");
                    frontDeskMenu();
                } else {
                    int attempts = loginAttempts.getOrDefault(username, 0) + 1;
                    loginAttempts.put(username, attempts);
                    if (attempts >= 5) {
                        //user.lockAccount();
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
        Scanner sc=new Scanner(System.in);
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
                    loginFrontDesk(username, password);}
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
    public void frontDeskMenu() {
        Scanner sc=new Scanner(System.in);
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
    public void sellTicket(){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入顾客想要观看的场次：电影名，放映厅，放映时间");
        String movieName=sc.next();
        String videoHall=sc.next();
        String showtime=sc.next();
        System.out.println("请输入顾客的用户名或手机号：");
        String input=sc.next();
        System.out.println("请输入顾客需要支付的金额：");
        double money=sc.nextDouble();
        int movieIndex=findMovieIndexByMovieName(movieList,movieName);
        Movie movie=movieList.get(movieIndex);
        for (Person user:userList) {
            if(user.getUsername().equals(input)||user.getPhoneNumber().equals(input)){
                System.out.println("片名：" + movie.getMovieName() + "，导演：" + movie.getDirector()+ "，主演：" + movie.getLeadingRole() + "，剧情简介：" + movie.getSynopsis()+ "，时长："+movie.getDuration()+"，电影票ID："+getTicketID());
            }
        }
    }
}