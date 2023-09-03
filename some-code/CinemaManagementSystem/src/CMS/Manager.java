package CMS;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Manager extends Person {
    private LocalDateTime registrationTime; // 注册时间
    private String phoneNumber; // 手机号
    private String email;// 邮箱
    private String type;
    private String username;
    private String password;
    private boolean locked;
    private String userID;


    public Manager() {
    }

    public Manager(String userID,String username,LocalDateTime registrationTime,String type,String password,  String phoneNumber, String email) {
        this.userID=userID;
        this.username=username;
        this.password=password;
        this.registrationTime = registrationTime;
        this.phoneNumber = phoneNumber;
        this.type=type;
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

    //经理登录
    public void loginManager(String username, String password) {
        int userIndex=findUserIndexByUsername(managerList,username);
        if (userIndex!=-1) {
            Manager manager = (Manager) managerList.get(userIndex);
            if (!manager.isLocked()) {
                if (manager.getPassword().equals(password)) {
                    loginAttempts.remove(username);
                    System.out.println("登录成功，欢迎：" + username + "!");
                    managerMenu1(username,password);
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
    public void managerMainMenu() {
        Scanner sc=new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("亲爱的经理，请输入下列数字选择你想要办理的服务：         ");
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
                    loginManager(username,password);}
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
    public void managerMenu1(String username,String password) {
        Scanner sc=new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     登陆成功，请选择你需要的服务            ");
            System.out.println("           1.密码管理                ");
            System.out.println("           2.影片管理              ");
            System.out.println("           3.排片管理              ");
            System.out.println("           4.用户（消费者）管理              ");
            System.out.println("           e.退出              ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> managerMenu2(username,password);
                case "2" -> managerMenu3();
                case "3" -> managerMenu4();
                case "4" -> managerMenu5();
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
    public void managerMenu2(String username,String password) {
        Scanner sc=new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("      密码管理，请选择你需要的服务            ");
            System.out.println("           1.修改自身密码               ");
            System.out.println("           2.重置指定用户（消费者）的密码              ");
            System.out.println("           e.退出              ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> changePassword(managerList,username,password);
                case "2" -> {
                    System.out.println("请输入需要重置密码的用户名：");
                    username = sc.next();
                    resetPassword2(username,3);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
    public void managerMenu3() {
        Scanner sc=new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     影片管理，请选择你需要的服务            ");
            System.out.println("         1.列出所有正在上映影片的信息              ");
            System.out.println("         2.添加影片的信息                            ");
            System.out.println("         3.修改影片的信息                        ");
            System.out.println("         4.删除影片的信息                          ");
            System.out.println("         5.查询影片的信息                          ");
            System.out.println("         e.退出                                 ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> printMovieInfo();
                case "2" -> addNewMovie();
                case "3" -> {
                    System.out.println("请输入你要修改信息的影片名：");
                    String movieName=sc.next();
                    modifyMovieInfo(movieName);
                }
                case "4" -> {
                    System.out.println("请输入你要删除信息的影片名：");
                    String movieName=sc.next();
                    deleteMovieInfo(movieName);
                }
                case "5"->{
                    System.out.println("请输入你的已知信息：");
                    String info=sc.next();
                    findMovieInfo(info);
                }
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
    public void managerMenu4() {
        Scanner sc=new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     排片管理，请选择你需要的服务            ");
            System.out.println("          1.增加场次                ");
            System.out.println("          2.修改场次                        ");
            System.out.println("          3.删除场次                 ");
            System.out.println("          4.列出所有场次信息          ");
            System.out.println("          e.退出              ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> addNewSession();
                case "2" -> modifySession();
                case "3" -> deleteSession();
                case "4" -> printSessionInfo();
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }
    public void managerMenu5() {
        Scanner sc=new Scanner(System.in);
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("=====================================");
            System.out.println("     用户（消费者）管理，请选择你需要的服务            ");
            System.out.println("           1.列出所有用户（消费者）信息                ");
            System.out.println("           2.查询用户（消费者）信息             ");
            System.out.println("           e.退出              ");
            System.out.println("=====================================");
            String choice = sc.next();
            switch (choice) {
                case "1" -> printUserInfo(userList);
                case "2" -> findUserInfo(userList);
                case "e" -> shouldExit = true;
                default -> System.out.println("无效的选项，请重新选择。");
            }
        }
    }

    //经理添加影片的信息
    public void addNewMovie(){
        System.out.println("请开始输入你要增加的影片信息：");
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入你要添加的片名：");
        String movieName=sc.next();
        System.out.println("请输入你添加影片的导演：");
        String director=sc.next();
        System.out.println("请输入你要添加影片的主演：");
        String leadingRole=sc.next();
        System.out.println("请输入你要添加影片的剧情简介：");
        String synopsis=sc.next();
        System.out.println("请输入你要添加影片的时长：");
        String duration=sc.next();
        movieList.add(new Movie(movieName,director,leadingRole,synopsis,duration));
        System.out.println("影片添加成功！");
    }
    //经理修改影片信息
    public void modifyMovieInfo(String MovieName) {
        Scanner sc = new Scanner(System.in);
        int movieIndex;
        movieIndex = findMovieIndexByMovieName(movieList, MovieName);
        if (movieIndex != -1) {
            System.out.println("请选择你想要修改的影片信息：");
            System.out.println("1.片名");
            System.out.println("2.导演");
            System.out.println("3.主演");
            System.out.println("4.剧情简介");
            System.out.println("5.时长");
            int num = sc.nextInt();
            switch (num) {
                case 1 -> {
                    System.out.println("请输入新的影片名");
                    String newMovieName = sc.next();
                    movieList.get(movieIndex).setMovieName(newMovieName);
                }
                case 2 -> {
                    System.out.println("请输入影片新的导演");
                    String newDirector = sc.next();
                    movieList.get(movieIndex).setDirector(newDirector);
                }
                case 3 -> {
                    System.out.println("请输入影片新的主演");
                    String newLeadingRole = sc.next();
                    movieList.get(movieIndex).setLeadingRole(newLeadingRole);
                }
                case 4 -> {
                    System.out.println("请输入影片新的剧情简介");
                    String newSynopsis = sc.next();
                    movieList.get(movieIndex).setSynopsis(newSynopsis);
                }
                case 5 -> {
                    System.out.println("请输入影片新的时长");
                    String newDuration = sc.next();
                    movieList.get(movieIndex).setDuration(newDuration);
                }
            }
            System.out.println("影片的信息已成功修改。");
        } else {
            System.out.println("影片名不存在");
        }
    }
    //经理删除影片信息
    public void deleteMovieInfo(String MovieName){
        int movieIndex;
        movieIndex=findMovieIndexByMovieName(movieList,MovieName);
        if (movieIndex!=-1){
            movieList.remove(movieIndex);
        }else System.out.println("影片名不存在。");
    }
    //经理查询影片信息
    public void findMovieInfo(String info){
        for (Movie movie: movieList) {
            if(movie.getMovieName().equals(info)||movie.getDirector().equals(info)||movie.getLeadingRole().equals(info)){
                System.out.println("片名：" + movie.getMovieName() + "，导演：" + movie.getDirector()+ "，主演：" + movie.getLeadingRole() + "，剧情简介：" + movie.getSynopsis()+ "，时长："+movie.getDuration());
            }else System.out.println("没有查询到任何信息");
        }
    }
    //经理增加场次
    public void addNewSession(){
        System.out.println("请开始输入你要增加的场次信息：");
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入该场次放映的影片名：");
        String movieName=sc.next();
        System.out.println("请输入该场次的价格：");
        double ticketPrice=sc.nextDouble();
        System.out.println("请输入该场次的放映厅：");
        String videoHall=sc.next();
        System.out.println("请输入该场次的放映时间：");
        String showtime=sc.next();
        System.out.println("请输入该场次的总座位：");
        int totalSeats= sc.nextInt();
        System.out.println("请输入行数：");
        int numRows=sc.nextInt();
        System.out.println("请输入列数：");
        int numCols= sc.nextInt();
        movieSession session=new movieSession(movieName,ticketPrice,videoHall,showtime,totalSeats,totalSeats);
        sessionList.add(new movieSession(movieName,ticketPrice,videoHall,showtime,totalSeats,totalSeats,session.initializeSeatMap(numRows,numCols)));
        System.out.println("该场次已成功添加！");
    }
    //经理修改场次
    public void modifySession() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你想要修改的场次：电影名，放映厅，放映时间");
        String movieName=sc.next();
        String videoHall=sc.next();
        String showtime=sc.next();
        int sessionIndex=findSessionIndexByMovieName(sessionList,movieName,videoHall,showtime);
        if (sessionIndex!= -1) {
            System.out.println("请选择你想要修改的场次信息：");
            System.out.println("1.将该场次改放其他电影");
            System.out.println("2.空场");
            int num = sc.nextInt();
            switch (num) {
                case 1 -> {
                    System.out.println("请输入改放的影片名");
                    String newMovieName = sc.next();
                    sessionList.get(sessionIndex).setMovieName(newMovieName);
                }
                case 2 -> sessionList.remove(sessionIndex);
            }
            System.out.println("场次信息已成功修改。");
        } else System.out.println("该场次不存在");
    }
    //经理删除场次
    public void deleteSession(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你想要删除的场次：电影名，放映厅，放映时间");
        String movieName=sc.next();
        String videoHall=sc.next();
        String showtime=sc.next();
        int sessionIndex=findSessionIndexByMovieName(sessionList,movieName,videoHall,showtime);
        if(sessionIndex!=-1){
            sessionList.remove(sessionIndex);
        }else System.out.println("该场次不存在");
    }
    //经理列出所有场次信息
    public void printSessionInfo() {
        for (movieSession session: sessionList) {
            System.out.println("片名：" + session.getMovieName() + "，放映厅：" + session.getVideoHall()+ "，票价：" + session.getTicketPrice() + "，放映时间：" + session.getShowtime());
        }
    }
}
