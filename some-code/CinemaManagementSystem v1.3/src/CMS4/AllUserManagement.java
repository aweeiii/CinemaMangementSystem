package CMS4;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AllUserManagement {
    public static List<Person> userList = new ArrayList<>();
    public static List<Person> administratorList = new ArrayList<>();
    public static List<Person> managerList = new ArrayList<>();
    public static List<Person> FrontDeskList = new ArrayList<>();
    public static List<Movie> movieList = new ArrayList<>();
    public static List<movieSession> sessionList = new ArrayList<>();
    public Map<String, Integer> loginAttempts = new HashMap<>();

    static String userFile = "user.xlsx";

    static String managerFile = "manager.xlsx";

    static String frontDeskFile = "frontdesk.xlsx";

    static String adminFile = "admin.xlsx";

    static String movieFile = "movie.xlsx";
    static String movieSessionFile = "movieSession.xlsx";

    public static void writePersonsToExcel(List<Person> list, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Persons");
            // 创建表头行
            Row headerRow = sheet.createRow(0);
            int rowIdx = 1;
            for (Person person : list) {
                Row row = sheet.createRow(rowIdx++);
                // 根据对象类型设置不同的列值
                if (person instanceof User user) {
                    // 创建列标题
                    headerRow.createCell(0).setCellValue("userID");
                    headerRow.createCell(1).setCellValue("username");
                    headerRow.createCell(2).setCellValue("userLevel");
                    headerRow.createCell(3).setCellValue("registrationTime");
                    headerRow.createCell(4).setCellValue("cumulativeConsumptionExpense");
                    headerRow.createCell(5).setCellValue("cumulativeConsumptionNum");
                    headerRow.createCell(6).setCellValue("phoneNumber");
                    headerRow.createCell(7).setCellValue("email");
                    headerRow.createCell(8).setCellValue("password");
                    headerRow.createCell(9).setCellValue("purchaseHistory");
                    row.createCell(0).setCellValue(user.getUserID());
                    row.createCell(1).setCellValue(user.getUsername());
                    row.createCell(2).setCellValue(user.getUserLevel());
                    row.createCell(3).setCellValue(user.getRegistrationTime());
                    row.createCell(4).setCellValue(user.getCumulativeConsumptionExpense());
                    row.createCell(5).setCellValue(user.getCumulativeConsumptionNum());
                    row.createCell(6).setCellValue(user.getPhoneNumber());
                    row.createCell(7).setCellValue(user.getEmail());
                    if (user.getPassword().length() != 60) {
                        // 加密用户密码
                        String encryptedPassword = encryptPassword(user.getPassword());
                        user.setPassword(encryptedPassword);
                    }
                    row.createCell(8).setCellValue(user.getPassword());
                    row.createCell(9).setCellValue(user.getPurchaseHistory().toString());
                } else if (person instanceof Manager manager) {
                    headerRow.createCell(0).setCellValue("type");
                    headerRow.createCell(1).setCellValue("userID");
                    headerRow.createCell(2).setCellValue("username");
                    headerRow.createCell(3).setCellValue("registrationTime");
                    headerRow.createCell(4).setCellValue("phoneNumber");
                    headerRow.createCell(5).setCellValue("email");
                    headerRow.createCell(6).setCellValue("password");
                    row.createCell(0).setCellValue(manager.getType());
                    row.createCell(1).setCellValue(manager.getUserID());
                    row.createCell(2).setCellValue(manager.getUsername());
                    row.createCell(3).setCellValue(manager.getRegistrationTime());
                    row.createCell(4).setCellValue(manager.getPhoneNumber());
                    row.createCell(5).setCellValue(manager.getEmail());
                    if (manager.getPassword().length() != 60) {
                        //加密经理密码
                        String encryptedPassword = encryptPassword(manager.getPassword());
                        manager.setPassword(encryptedPassword);
                    }
                    row.createCell(6).setCellValue(manager.getPassword());
                } else if (person instanceof FrontDesk frontDesk) {
                    headerRow.createCell(0).setCellValue("type");
                    headerRow.createCell(1).setCellValue("userID");
                    headerRow.createCell(2).setCellValue("username");
                    headerRow.createCell(3).setCellValue("registrationTime");
                    headerRow.createCell(4).setCellValue("phoneNumber");
                    headerRow.createCell(5).setCellValue("email");
                    headerRow.createCell(6).setCellValue("password");
                    row.createCell(0).setCellValue(frontDesk.getType());
                    row.createCell(1).setCellValue(frontDesk.getUserID());
                    row.createCell(2).setCellValue(frontDesk.getUsername());
                    row.createCell(3).setCellValue(frontDesk.getRegistrationTime());
                    row.createCell(4).setCellValue(frontDesk.getPhoneNumber());
                    row.createCell(5).setCellValue(frontDesk.getEmail());
                    if (frontDesk.getPassword().length() != 60) {
                        // 加密前台密码
                        String encryptedPassword = encryptPassword(frontDesk.getPassword());
                        frontDesk.setPassword(encryptedPassword);
                    }
                    row.createCell(6).setCellValue(frontDesk.getPassword());
                } else if (person instanceof Administrator administrator) {
                    headerRow.createCell(0).setCellValue("Username");
                    headerRow.createCell(1).setCellValue("Password");
                    row.createCell(0).setCellValue(administrator.getUsername());
                    if (administrator.getPassword().length() != 60) {
                        // 加密管理员密码
                        String encryptedPassword = encryptPassword(administrator.getPassword());
                        administrator.setPassword(encryptedPassword);
                    }
                    row.createCell(1).setCellValue(administrator.getPassword());
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static void writeMovieToExcel(List<Movie> list, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Movies");
            // 创建表头行
            Row headerRow = sheet.createRow(0);
            // 创建列标题
            headerRow.createCell(0).setCellValue("movieName");
            headerRow.createCell(1).setCellValue("director");
            headerRow.createCell(2).setCellValue("leadingRole");
            headerRow.createCell(3).setCellValue("synopsis");
            headerRow.createCell(4).setCellValue("duration");
            int rowIdx = 1;
            for (Movie movie : list) {
                Row row = sheet.createRow(rowIdx++);
                // 根据对象类型设置不同的列值
                    row.createCell(0).setCellValue(movie.getMovieName());
                    row.createCell(1).setCellValue(movie.getDirector());
                    row.createCell(2).setCellValue(movie.getLeadingRole());
                    row.createCell(3).setCellValue(movie.getSynopsis());
                    row.createCell(4).setCellValue(movie.getDuration());
            }

            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMovieSessionToExcel(List<movieSession> list, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("movieSessions");
            // 创建表头行
            Row headerRow = sheet.createRow(0);
            // 创建列标题
            headerRow.createCell(0).setCellValue("movieName");
            headerRow.createCell(1).setCellValue("ticketPrice");
            headerRow.createCell(2).setCellValue("videoHall");
            headerRow.createCell(3).setCellValue("showTime");
            headerRow.createCell(4).setCellValue("totalSeats");
            headerRow.createCell(5).setCellValue("availableSeats");
            int maxColumns = 0;
            for (movieSession session : list) {
                if (session.getSeatMap().length > maxColumns) {
                    maxColumns = session.getSeatMap().length;
                }
            }
            int rowIdx = 1;
            for (movieSession session : list) {
                Row row = sheet.createRow(rowIdx++);
                // 根据对象类型设置不同的列值
                row.createCell(0).setCellValue(session.getMovieName());
                row.createCell(1).setCellValue(session.getTicketPrice());
                row.createCell(2).setCellValue(session.getVideoHall());
                row.createCell(3).setCellValue(session.getShowtime());
                row.createCell(4).setCellValue(session.getTotalSeats());
                row.createCell(5).setCellValue(session.getAvailableSeats());
                String[][] seatMap = session.getSeatMap();
                int numCols = seatMap[0].length;
                headerRow.createCell(numCols/2+5).setCellValue("seatMap");
                for (String[] strings : seatMap) {
                    Row seatRow = sheet.createRow(rowIdx++);
                    for (int j = 0; j < numCols; j++) {
                        seatRow.createCell(j + 6).setCellValue(strings[j]);
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPersonsFromExcel(String fileName) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(fileName))) {
            Sheet sheet = workbook.getSheet("Persons");
            int rowCount = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(0);
            if (headerRow==null) return;
            int colCount = headerRow.getLastCellNum();
            if (colCount==0) return;
            String[] userData = new String[10];
            ArrayList<String> purchaseHistory = new ArrayList<>();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    String data = headerRow.getCell(j).getStringCellValue();
                    switch (data) {
                        case "userID" -> userData[0] = row.getCell(j).getStringCellValue();
                        case "username" -> userData[1] = row.getCell(j).getStringCellValue();
                        case "userLevel" -> userData[2]= row.getCell(j).getStringCellValue();
                        case "registrationTime" -> userData[3]=row.getCell(j).getStringCellValue();
                        case "cumulativeConsumptionExpense" -> userData[4]= String.valueOf(row.getCell(j).getNumericCellValue());
                        case "cumulativeConsumptionNum" -> {
                            double numericValue = row.getCell(j).getNumericCellValue();
                            userData[5]= String.valueOf((int) numericValue);
                        }
                        case "phoneNumber" -> userData[6] = row.getCell(j).getStringCellValue();
                        case "email" -> userData[7] =row.getCell(j).getStringCellValue() ;
                        case "password" -> userData[8] = row.getCell(j).getStringCellValue(); // 保存加密后的密码
                        case "type" -> userData[9] = row.getCell(j).getStringCellValue();
                        case "purchaseHistory" -> purchaseHistory.add(row.getCell(j).getStringCellValue());
                    }
                }
                if (fileName.contains("user") && userData[0] != null) {
                    User user = new User(userData[0], userData[1], userData[2], userData[3], Double.parseDouble(userData[4]), Integer.parseInt(userData[5]), userData[6], userData[7], userData[8], purchaseHistory);
                    userList.add(user);
                } else if (fileName.contains("admin") && userData[1] != null) {
                    Administrator administrator = new Administrator(userData[1], userData[8]);
                    administratorList.add(administrator);
                } else if (fileName.contains("manager") && userData[0] != null) {
                    Manager manager = new Manager(userData[0], userData[1], userData[3], userData[9], userData[8], userData[6], userData[7]);
                    managerList.add(manager);
                } else if (fileName.contains("frontdesk") && userData[0] != null) {
                    FrontDesk frontDesk = new FrontDesk(userData[0], userData[1], userData[3], userData[9], userData[8], userData[6], userData[7]);
                    FrontDeskList.add(frontDesk);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMoviesFromExcel(String fileName) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(fileName))) {
            Sheet sheet = workbook.getSheet("Movies");
            int rowCount = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(0);
            if(headerRow==null) return;
            int colCount = headerRow.getLastCellNum();
            String[] movieData = new String[5];
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    String data = headerRow.getCell(j).getStringCellValue();
                    switch (data) {
                        case "movieName" -> movieData[0] = row.getCell(j).getStringCellValue();
                        case "director" -> movieData[1] = row.getCell(j).getStringCellValue();
                        case "leadingRole" -> movieData[2] = row.getCell(j).getStringCellValue();
                        case "synopsis" -> movieData[3] = row.getCell(j).getStringCellValue();
                        case "duration" -> movieData[4] = row.getCell(j).getStringCellValue();
                    }
                }
                Movie movie = new Movie(movieData[0], movieData[1], movieData[2], movieData[3], movieData[4]);
                movieList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMovieSessionsFromExcel(String fileName) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(fileName))) {
            Sheet sheet = workbook.getSheet("movieSessions");
            if(sheet.getRow(0)==null) return;
            int totalRows = sheet.getLastRowNum() + 1;
            for (int i=0;i<totalRows-1;i+=9) {
                int numRows = 10;
                int numCols = sheet.getRow(2).getLastCellNum();
                String movieName;
                double ticketPrice;
                String videoHall;
                String showtime;
                int totalSeats;
                int availableSeats;
                Row row = sheet.getRow(i+1);
                movieName = row.getCell(0).getStringCellValue();
                ticketPrice = row.getCell(1).getNumericCellValue();
                videoHall = row.getCell(2).getStringCellValue();
                showtime = row.getCell(3).getStringCellValue();
                totalSeats = (int) row.getCell(4).getNumericCellValue();
                availableSeats = (int) row.getCell(5).getNumericCellValue();
                String[][] seatMap = new String[numRows - 2][numCols - 6];
                for (int j = i+2; j < numRows+i; j++) {
                    for (int k = 6; k < numCols; k++) {
                        Row row1 = sheet.getRow(j);
                        String seatValue = row1.getCell(k).getStringCellValue();
                        seatMap[j-i - 2][k - 6] = seatValue;
                    }
                }
                movieSession session = new movieSession(movieName, ticketPrice, videoHall, showtime, totalSeats, availableSeats, seatMap);
                sessionList.add(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //重置所有用户密码
    public void resetPassword2(String username, int num) {
        Scanner sc = new Scanner(System.in);
        if (num == 1) {
            int userIndex = findUserIndexByUsername(managerList, username);
            if (userIndex != -1) {
                Manager manager = (Manager) managerList.get(userIndex);
                System.out.println("请输入新密码：");
                String newPassword = sc.next();
                manager.setPassword(newPassword);
                System.out.println("新密码： " + newPassword);
            } else {
                System.out.println("用户不存在");
            }
        }
        if (num == 2) {
            int userIndex = findUserIndexByUsername(FrontDeskList, username);
            if (userIndex != -1) {
                FrontDesk frontDesk = (FrontDesk) FrontDeskList.get(userIndex);
                System.out.println("请输入新密码：");
                String newPassword = sc.next();
                frontDesk.setPassword(newPassword);
                System.out.println("新密码： " + newPassword);
            } else {
                System.out.println("用户不存在");
            }
        }
        if (num == 3) {
            int userIndex = findUserIndexByUsername(userList, username);
            if (userIndex != -1) {
                User user = (User) userList.get(userIndex);
                System.out.println("请输入新密码：");
                String newPassword = sc.next();
                user.setPassword(newPassword);
                System.out.println("新密码： " + newPassword);
            } else {
                System.out.println("用户不存在");
            }
        }
    }

    //所有用户的修改密码
    public void changePassword(List<Person> list, String username, String password) {
        Scanner sc = new Scanner(System.in);
        int userIndex = findUserIndexByUsername(list, username);
        while (true) {
            if (userIndex != -1) {
                User user = (User) userList.get(userIndex);
                // 获取存储在数据库或文件中的密码哈希
                String storedHashedPassword = user.getPassword();
                // 比较用户提供的密码哈希与存储的密码哈希
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    System.out.println("请输入新密码：");
                    String newPassword1 = sc.next();
                    System.out.println("请确认新密码：");
                    String newPassword2 = sc.next();
                    if (newPassword1.equals(newPassword2) && isPasswordValid(newPassword1)) {
                        System.out.println("密码修改成功。");
                        user.setPassword(newPassword1);
                        break;           // 跳出循环
                    } else {
                        System.out.println("两次密码输入不一致，请重新输入");
                    }
                }
            } else {
                System.out.println("用户名不存在，请重新输入用户名或输入 'e' 退出：");
                String input = sc.next();
                if (input.equalsIgnoreCase("e")) {
                    break;                 // 退出
                } else {
                    username = input; // 更新用户名
                    changePassword(list, username, password);
                }
            }
        }
    }

    //列出所有种类的用户信息
    public void printUserInfo(List<Person> list) {
        for (Person person : list) {
            if (person instanceof User user) {
                System.out.println("用户ID：" + user.getUserID() + "，用户名：" + user.getUsername() + "，用户级别：" + user.getUserLevel() + "，用户注册时间：" + user.getRegistrationTime() + "，用户累计消费金额：" + user.getCumulativeConsumptionExpense() + "，用户累计消费次数：" + user.getCumulativeConsumptionNum() + "，用户手机号：" + user.getPhoneNumber() + "，用户邮箱：" + user.getEmail());
            } else if (person instanceof Manager manager) {
                System.out.println("用户ID：" + manager.getUserID() + "，用户名：" + manager.getUsername() + "，用户注册时间：" + manager.getRegistrationTime() + "，用户类型：" + manager.getType() + "，用户手机号：" + manager.getPhoneNumber() + "，用户邮箱：" + manager.getEmail());
            } else if (person instanceof FrontDesk frontDesk) {
                System.out.println("用户ID：" + frontDesk.getUserID() + "，用户名：" + frontDesk.getUsername() + "，用户注册时间：" + frontDesk.getRegistrationTime() + "，用户类型：" + frontDesk.getType() + "，用户手机号：" + frontDesk.getPhoneNumber() + "，用户邮箱：" + frontDesk.getEmail());
            }
        }
    }

    //通过用户名或用户ID查找所有种类的用户信息
    public void findUserInfo(List<Person> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您已知的信息（userID或username），我将为您查找");
        String input = sc.next();
        int userIndex;
        int userIndex1 = findUserIndexByUsername(list, input);
        int userIndex2 = findUserIndexByUserID(list, input);
        if (userIndex1 == -1 && userIndex2 == -1) {
            System.out.println("用户不存在");
            return;
        } else if (userIndex1 != -1) {
            userIndex = userIndex1;
        } else {
            userIndex = userIndex2;
        }
        Person user = list.get(userIndex);
        if (user instanceof User userObj) {
            System.out.println("用户ID：" + userObj.getUserID() + "，用户名：" + userObj.getUsername() + "，用户级别：" + userObj.getUserLevel() + "，用户注册时间：" + userObj.getRegistrationTime() + "，用户累计消费金额：" + userObj.getCumulativeConsumptionExpense() + "，用户累计消费次数：" + userObj.getCumulativeConsumptionNum() + "，用户手机号：" + userObj.getPhoneNumber() + "，用户邮箱：" + userObj.getEmail());
        } else if (user instanceof Manager managerObj) {
            System.out.println("用户ID：" + managerObj.getUserID() + "，用户名：" + managerObj.getUsername() + "，用户注册时间：" + managerObj.getRegistrationTime() + "，用户类型：" + managerObj.getType() + "，用户手机号：" + managerObj.getPhoneNumber() + "，用户邮箱：" + managerObj.getEmail());
        } else if (user instanceof FrontDesk frontDeskObj) {
            System.out.println("用户ID：" + frontDeskObj.getUserID() + "，用户名：" + frontDeskObj.getUsername() + "，用户注册时间：" + frontDeskObj.getRegistrationTime() + "，用户类型：" + frontDeskObj.getType() + "，用户手机号：" + frontDeskObj.getPhoneNumber() + "，用户邮箱：" + frontDeskObj.getEmail());
        }
    }

    //密码合法性检查
    public boolean isPasswordValid(String password) {
        // (长度大于8，包含大写字母、小写字母、数字和特殊字符)
        return password.length() > 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()].*");
    }

    //随机生成用户密码
    public String getRandomPassword() {
        //所有允许的字符集合
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {            //统一生成12位密码
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }
        return password.toString();
    }

    //随机生成用户ID
    public String getRandomUserID() {
        //所有允许的字符集合
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
        StringBuilder userID = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {            //统一生成8位用户ID
            int index = random.nextInt(allowedChars.length());
            userID.append(allowedChars.charAt(index));
        }
        return userID.toString();
    }

    //通过用户名寻找索引
    public int findUserIndexByUsername(List<Person> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    //通过用户ID寻找索引
    public int findUserIndexByUserID(List<Person> list, String userID) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserID().equals(userID)) {
                return i;
            }
        }
        return -1;
    }

    //通过用户手机号寻找索引
    public int findUserIndexByPhoneNumber(List<Person> list, String phoneNumber) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPhoneNumber().equals(phoneNumber)) {
                return i;
            }
        }
        return -1;
    }
    //通过电影名寻找索引
    public int findMovieIndexByMovieName(List<Movie> list, String MovieName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMovieName().equals(MovieName)) {
                return i;
            }
        }
        return -1;
    }

    public int findSessionIndexByMovieName(List<movieSession> list, String MovieName, String videoHall, String showtime) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMovieName().equals(MovieName) && list.get(i).getVideoHall().equals(videoHall) && list.get(i).getShowtime().equals(showtime)) {
                return i;
            }
        }
        return -1;
    }

    //列出所有正在上映的影片信息
    public void printMovieInfo() {
        for (Movie movie : movieList) {
            System.out.println("片名：" + movie.getMovieName() + "，导演：" + movie.getDirector() + "，主演：" + movie.getLeadingRole() + "，剧情简介：" + movie.getSynopsis() + "，时长：" + movie.getDuration());
        }
    }

    //消费者和前台查看指定电影放映信息
    public void viewAppointedMovieInfo() {
        System.out.println("请输入你要查看的电影名字：");
        Scanner sc = new Scanner(System.in);
        String movieName = sc.next();
        for (movieSession session : sessionList) {
            if (session.getMovieName().equals(movieName)) {
                System.out.println("片名：" + session.getMovieName() + "，放映厅：" + session.getVideoHall() + "，票价：" + session.getTicketPrice() + "，放映时间：" + session.getShowtime() + "，总座位数：" + session.getTotalSeats() + "，空闲座位数：" + session.getAvailableSeats());
                session.displaySeatMap(7, 12, session.getSeatMap());
            }
        }
    }

    //随机生成电影票的ID
    public String getTicketID() {
        StringBuilder ticketID = new StringBuilder();

        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBERS = "0123456789";

        for (int i = 0; i < 3; i++) {
            int randomIndex = new Random().nextInt(CHARACTERS.length());
            ticketID.append(CHARACTERS.charAt(randomIndex));
        }

        ticketID.append("-");

        for (int i = 0; i < 3; i++) {
            int randomIndex = new Random().nextInt(NUMBERS.length());
            ticketID.append(NUMBERS.charAt(randomIndex));
        }

        ticketID.append("-");

        for (int i = 0; i < 3; i++) {
            int randomIndex = new Random().nextInt(NUMBERS.length());
            ticketID.append(NUMBERS.charAt(randomIndex));
        }

        return ticketID.toString();
    }

    //判断购买电影票的座位是否合法
    public boolean isValidSeat(int row, int col, movieSession session) {
        return row >= 1 && row <= 7 && col >= 1 && col <= 12 && session.getSeatMap()[row - 1][col - 1].equals("O");
    }

    public static String generateRegistrationTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String RegistrationTime = dateTime.format(formatter);
        return RegistrationTime;
    }
}