package com.example.gamebackend.service;
import com.example.gamebackend.dto.OmokDTO;
/*import org.apache.jasper.tagplugins.jstl.core.If;*/
import org.springframework.stereotype.Service;

@Service
public class OmokService {

    public String judge(OmokDTO omokDTO) {

        String color = omokDTO.getColor();                  //검은돌(1),흰돌(2)
        String location = omokDTO.getLocation();            // 좌표값(어디에 놨는지)
        String situation = omokDTO.getSituation();          //현재상황

        String[][] numbers = OmokBoard(situation);

        if (isEmptySpot(color, location, numbers)) {
            return " 돌을 놓을 수 없습니다";
        } else if (is4X3(color, location, numbers)) {
            return "4*3 판정입니다";
        } else if (isBlackWin(color, location, numbers)) {
            return "흑돌 승리입니다";
        } else if (isWhiteWin(color, location, numbers)) {
            return "백돌 승리입니다";
        } else if (is3X3(color, location, numbers)) {
            return "3*3판정입니다";
        }

        return "그냥 놔도됨";
    }

    //프->백 오목판의 현재상황
    public static String[][] OmokBoard(String situation) {

        String[] rows = situation.split("/");
        String[][] numbers = new String[rows.length][rows[0].length()];

        for (int i = 0; i < rows.length; i++) {
            numbers[i] = rows[i].split("");
        }

        return numbers;

    }

    //빈 공간에만 돌을 둘 수 잇는 판정
    public boolean isEmptySpot(String color, String location, String[][] numbers) {

        int x = Integer.parseInt(location.split(",")[0]);
        int y = Integer.parseInt(location.split(",")[1]);

        return !numbers[x][y].equals("0");

    }
    // 주변 돌을 검사하는 함수

    //3*3 판정
    public boolean is3X3(String color, String location, String[][] numbers) {

        int x = Integer.parseInt(location.split(",")[0]);       //좌표값의 x축
        int y = Integer.parseInt(location.split(",")[1]);       //좌표값의 y축

        //color가 1이면 검은돌, color가 2면 흰돌
        String targetColor = (color.equals("1")) ? "1" : "2";


        //나를 중심으로 하는 막대기 함수 호출

        // 가로 막대기 검사
        int horizontalCount = countSameColor1(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(가로)
        boolean hasDifferentStones1 = checkDifferentStones1(x, y, targetColor, numbers);

        // 세로 막대기 검사
        int verticalCount = countSameColor2(x, y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(세로)
        boolean hasDifferentStones2 = checkDifferentStones2(x, y, targetColor, numbers);

        //우상향 대각선
        int upWordRight = countSameColor3(x, y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(우상향 대각선)
        boolean hasDifferentStones3 = checkDifferentStones3(x, y, targetColor, numbers);

        // 좌상향대각선
        int upWordLeft = countSameColor4(x, y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(좌상향 대각선)
        boolean hasDifferentStones4 = checkDifferentStones4(x, y, targetColor, numbers);



        //나를 시작으로 하는 막대기 함수 호출

        // 오른쪽 향한 막대기
        int rightBar = countSameColor5(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(오른쪽)
        boolean hasDifferentStones5 = checkDifferentStones5(x, y, targetColor, numbers);

        //우하향 대각선
        int downwardRight = countSameColor6(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(우하향)
        boolean hasDifferentStones6 = checkDifferentStones6(x, y, targetColor, numbers);

        //하향 세로 막대기
        int downWard = countSameColor7(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(하향)
        boolean hasDifferentStones7 = checkDifferentStones7(x, y, targetColor, numbers);

        //좌하향 대각선 막대기
        int downLeft = countSameColor8(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(하향)
        boolean hasDifferentStones8 = checkDifferentStones8(x, y, targetColor, numbers);

        //왼쪽 가로 막대기
        int leftHorizontal = countSameColor9(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(하향)
        boolean hasDifferentStones9 = checkDifferentStones9(x, y, targetColor, numbers);

        //좌상향 대각선 막대기
        int upwordLeft = countSameColor10(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(하향)
        boolean hasDifferentStones10 = checkDifferentStones10(x, y, targetColor, numbers);

        //상향 세로 막대기
        int lift = countSameColor11(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(하향)
        boolean hasDifferentStones11 = checkDifferentStones11(x, y, targetColor, numbers);

        //우상향 세로 막대기
        int upWardRight = countSameColor12(x,y, targetColor, numbers);
        // 양 옆에 다른 돌 찾기(하향)
        boolean hasDifferentStones12 = checkDifferentStones12(x, y, targetColor, numbers);



        // 내 주변 1칸 차이 : 나를 중심으로 세 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 2개 이상이면 33

        int countBarCenter = 0;

        //가로막대기,양옆에 다른돌찾기(가로)
        if ((horizontalCount == 2 && !hasDifferentStones1) ) {
            countBarCenter++;
            System.out.println("count1"+countBarCenter);
        }
        //세로막대기,양옆에 다른돌찾기(세로)
        if (verticalCount == 2 && !hasDifferentStones2) {
            countBarCenter++;
            System.out.println("count2"+countBarCenter);
        }
        //우상향 대각선,양옆에 다른돌찾기(우상향대각선)
        if (upWordRight == 2 && !hasDifferentStones3) {
            countBarCenter++;
            System.out.println("count3"+countBarCenter);
        }
        //좌상향 대각선,양옆에 다른돌찾기(좌상향대각선)
        if (upWordLeft == 2 && !hasDifferentStones4) {
            countBarCenter++;
            System.out.println("count4"+countBarCenter);
        }


        // 내 주변 2칸 차이 : 나를 시작으로 세 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 8개 중 2개 이상이면 33

        int countBarStart = 0;

        //오른쪽 막대기,양옆에 다른돌 찾기(오른쪽)
        if (rightBar == 2 && !hasDifferentStones5) {
            countBarStart++;
            System.out.println("count1"+countBarStart);
        }

        //우하향 대각선 ,양옆에 다른돌 찾기(우하향)
        if (downwardRight == 2 && !hasDifferentStones6) {
            countBarStart++;
            System.out.println("count2"+countBarStart);
        }

        //하향 세로 막대기 ,양옆에 다른돌 찾기(하향)
        if (downWard == 2 && !hasDifferentStones7) {
            countBarStart++;
            System.out.println("count33"+countBarStart);
        }

        //좌하향 대각선 막대기 ,양옆에 다른돌 찾기 (좌하향)
        if (downLeft== 2 && !hasDifferentStones8) {
            countBarStart++;
            System.out.println("count4"+countBarStart);
        }

        //왼쪽 가로 막대기 ,양옆에 다른돌 찾기 (왼쪽 가로)
        if (leftHorizontal== 2 && !hasDifferentStones9) {
            countBarStart++;
            System.out.println("count5"+countBarStart);
        }

        //좌상향 대각선 막대기 ,양옆에 다른돌 찾기 (좌상향)
        if (upwordLeft== 2 && !hasDifferentStones10) {
            countBarStart++;
            System.out.println("count6"+countBarStart);
        }

        //상향 세로 막대기 ,양옆에 다른돌 찾기 (상향)
        if (lift== 2 && !hasDifferentStones11) {
            countBarStart++;
            System.out.println("count7"+countBarStart);
        }

        //우상향 대각선, 양옆에 다른돌 찾기(우상향)
        if (upWardRight== 2 && !hasDifferentStones12) {
            countBarStart++;
            System.out.println("count8"+countBarStart);
        }

        if (countBarCenter == 2) {
            return true;
        }else if(countBarStart == 2){
            return true;
        }

        return false;
    }

    // 나를 중심으로 시작하는 함수

    //가로 막대기
    public int countSameColor1(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = -1; dy <= 1; dy++) {
            int nx = x;
            int ny = y + dy;

            if (nx >= 0 && nx < numbers.length && ny >= 0 && ny < numbers[0].length) {
                if (x == nx && y != ny) {
                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }

        return sameColorCount;
    }
    // 양 옆에 다른 돌 찾기
    public boolean checkDifferentStones1(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones = false;

        for (int dx = -2; dx <= 2; dx++) {

            int nx = x;
            int ny = y + dx;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (y != nx && x == nx && y != ny) {

                    if (!numbers[nx][ny].equals(targetColor) && !numbers[nx][ny].equals("0")) {

                        hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                    }
                }
            }
        }

        return hasDifferentStones;

    }


    //새로 막대기
    public int countSameColor2(int x, int y, String targetColor, String[][] numbers) {
       int sameColorCount = 0;

        for (int dy = -1; dy <= 1; dy++) {

            int nx = x + dy;
            int ny = y;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y == ny) {

                }
                if (numbers[nx][ny].equals(targetColor)) {

                    sameColorCount++;

                }
            }
        }
            return sameColorCount;
        }
    // 양 옆에 다른 돌 찾기(세로)
    public boolean checkDifferentStones2(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -2; dx <= 2; dx++) {

            int nx = x+dx;
            int ny = y ;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y == ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }

        return hasDifferentStones;
    }


    //우상향 대각선 막대기
    public int countSameColor3(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = -1; dy <= 1; dy++) {

            int nx = x + dy;
            int ny = y - dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {
                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones3(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -2; dx <= 2; dx++) {

            int nx = x+dx ;
            int ny = y-dx ;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //좌상향 대각선 막대기
    public int countSameColor4(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = -1; dy <= 1; dy++) {

            int nx = x + dy;
            int ny = y + dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {
                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones4(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -2; dx <= 2; dx++) {

            int nx = x + dx ;
            int ny = y + dx ;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //나를 시작으로 하는 함수

    //오른쪾 가로 막대기
    public int countSameColor5(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x;
            int ny = y + dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x == nx && y != ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones5(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x ;
            int ny = y + dx ;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x == nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //우하향 대각선
    public int countSameColor6(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x + dy;
            int ny = y + dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones6(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x + dx;
            int ny = y + dx;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //하향 세로 막대기
    public int countSameColor7(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x + dy;
            int ny = y ;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y == ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones7(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x + dx;
            int ny = y ;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y == ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //좌하향 대각선
    public int countSameColor8(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x + dy;
            int ny = y - dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {


                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones8(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x + dx;
            int ny = y - dx;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //왼쪽 가로 막대기
    public int countSameColor9(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x;
            int ny = y - dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x == nx && y != ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones9(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x;
            int ny = y -dx;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x == nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    // 좌상향 대각선 막대기
    public int countSameColor10(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x - dy;
            int ny = y - dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones10(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x -dx;
            int ny = y -dx;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //상향 새로 막대기
    public int countSameColor11(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x - dy;
            int ny = y;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y == ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones11(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x - dx;
            int ny = y;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y == ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }


    //우상향 대각선
    public int countSameColor12(int x, int y, String targetColor, String[][] numbers) {

        int sameColorCount = 0;

        for (int dy = 0; dy <= 2; dy++) {

            int nx = x - dy;
            int ny = y + dy;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                    // 추가적인 조건이나 작업이 필요한 경우 여기에 작성
                }
                if (numbers[nx][ny].equals(targetColor)) {
                    sameColorCount++;
                }
            }
        }
        return sameColorCount;
    }
    public boolean checkDifferentStones12(int x, int y, String targetColor, String[][] numbers) {

        boolean hasDifferentStones=false;

        for (int dx = -1; dx <= 3; dx++) {

            int nx = x -dx;
            int ny = y +dx;

            if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                if (x != nx && y != ny) {

                }
                if (!numbers[nx][ny].equals(targetColor)&& !numbers[nx][ny].equals("0")) {

                    hasDifferentStones = true; // true -> 양쪽에 내가 놓은 돌의 다른 색의 돌이 놓여있는지

                }
            }
        }
        return hasDifferentStones;
    }




        //4*3 판정
        public boolean is4X3 (String color, String location, String[][]numbers){

            int x = Integer.parseInt(location.split(",")[0]);       //좌표값의 x축
            int y = Integer.parseInt(location.split(",")[1]);       //좌표값의 y축
            //color가 1이면 검은돌, color가 2면 흰돌
            String targetColor = (color.equals("1")) ? "1" : "2";

            int sameColorCount = 0;

            //연속되는 같은 숫자갯수가 3개이고,연속되면 countbar4를 올려줌
            int CenterCountBar4 = 0;
            //연속되는 같은 숫자갯수가 2개이고,연속되면 countbar3를 올려줌
            int CenterCountBar3 = 0;

        /* 내 주변 4칸 차이 : 나를 중심으로 5 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중
        (자신포함) 연속되는 1의 갯수가 4개인 막대기 한개, (자신포함) 연속되는 1의 갯수가 3개인 막대기 한개
        이두개가 해당되면 4*3 판정*/

            // dx,dy는 움직이려는 좌표값 (한칸한칸씩)
            // nx,ny는 돌의 위치

            //가로 막대기

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                CenterCountBar4++;
            }
            if (sameColorCount == 2) {
                CenterCountBar3++;
            }

            //세로 막대기

            sameColorCount = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x + dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                CenterCountBar4++;
            }
            if (sameColorCount == 2) {
                CenterCountBar3++;
            }

            //좌상향 대각선 막대기

            sameColorCount = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x + dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                CenterCountBar4++;
            }
            if (sameColorCount == 2) {
                CenterCountBar3++;
            }

            //우상향 대각선 막대기

            sameColorCount = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x + dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                CenterCountBar4++;
            }
            if (sameColorCount == 2) {
                CenterCountBar3++;
            }

       /* 나를 시작으로 4칸짜리 막대기 8개 중
        (자신포함)연속되는 1의 갯수가 4개인 막대기 한개, (자신포함) 연속되는 1의 갯수가 3개인 막대기 1개
         */

            //다른 방법의 countbar는 카운트 초기화
            int StartCountBar4 = 0;
            int StartCountBar3 = 0;

            //오른쪽 가로 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //우하향 대각선 막대기
            sameColorCount = 0;
            for (int dy = 0; dy <= 3; dy++) {

                int nx = x + dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 0으로 초기화 해준다.
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //새로(아래) 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x + dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3)  {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //좌하향 대각선 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x + dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //왼쪽 가로 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {

                    if (x == nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //좌상향 대각선 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //세로(위) 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //우상향 대각선 막대기

            sameColorCount = 0;

            for (int dy = 0; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColorCount++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
            }
            if (sameColorCount == 3) {
                StartCountBar4++;
            }
            if (sameColorCount == 2) {
                StartCountBar3++;
            }

            //연속되는 같은 돌의 색상의 갯수가 (자신포함)3개인것과 2개인것이 한개씩있어야만 비로소 4*3판정

            //나를 중심으로 하는 첫번째케이스
            if (CenterCountBar4 == 1 && CenterCountBar3 == 1) {
                return true;
            }
            //나를 시작으로 하는 두번째케이스
            if (StartCountBar4 == 1 && StartCountBar3 == 1) {
                return true;
            }

            return false;

        }


        public boolean isBlackWin (String color, String location, String[][]numbers){

            int x = Integer.parseInt(location.split(",")[0]);       //좌표값의 x축
            int y = Integer.parseInt(location.split(",")[1]);       //좌표값의 y축
            //color가 1이면 검은돌, color가 2면 흰돌
            String targetColor = "1";

            int countBar1 = 0;
            int countBar2 = 0;
            int countBar3 = 0;
            int countBar4 = 0;
            int countBar5 = 0;
            int countBar6 = 0;
            int countBar7 = 0;
            int sameColor = 0;
            //1번 내 주변 2칸 차이 : 나를 중심으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 1개 이상이면 흑돌승
            //가로 다섯칸짜리 막대기
            for (int dy = -2; dy <= 2; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            //우하향 대각선
            sameColor = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            //세로 막대기
            sameColor = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }

                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            //좌하향 대각선
            sameColor = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            // 2번 내 주변 5칸 차이 : 나를 시작(아래 왼쪽 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌 승

            //꼭짓점에서 오른쪽 향하는막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar2++;
                }
            }

            //우상향 대각선
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar2++;
                }
            }

            //꼭짓점으로 부터 세로
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar2++;
                }
            }

            // 3번 내 주변 5칸 차이 : 나를 시작(아래 오른쪽 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌승

            //꼭짓점으로부터 왼쪽으로 향하는 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar3++;
                }
            }

            //좌상향 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar3++;
                }
            }

            //새로 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar3++;
                }
            }

            // 4번 내 주변 5칸 차이 : 나를 시작(오른쪽 위 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌승

            //꼭짓점으로부터 왼쪽으로 향하는 막대기

            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar4++;
                }
            }

            //좌하향 대각선
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y - dy;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar4++;
                }
            }

            //세로 막대기
            sameColor = 0;
            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar4++;
                }
            }

            // 5번 내 주변 5칸 차이 : 나를 시작(오른쪽 위 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌승

            //꼭짓점으로부터 오른쪽으로 향하는 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar5++;
                }
            }

            //꼭짓점으로부터 우하향으로 향하는 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar5++;
                }
            }

            //꼭짓점으로부터 새로(아래) 향함
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar5++;
                }
            }

            // 6번 나를 중심으로 한칸 세칸 으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 1개 이상이면 흑돌승

            //가로 막대기 11101
            sameColor = 0;

            for (int dy = -3; dy <= 1; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }

            //세로 막대기
            sameColor = 0;
            for (int dy = -3; dy <= 1; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }

            //좌하향 막대기
            sameColor = 0;
            for (int dy = -3; dy <= 1; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }

            //우하향 막대기
            sameColor = 0;
            for (int dy = -3; dy <= 1; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }
            //7번 나를 중심으로 한칸 세칸 으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 1개 이상이면 흑돌승

            //가로 막대기 10111
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            //세로 막대기
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            //우상향 막대기
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            //좌상향 막대기
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y - dy;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            if (countBar1 == 1) {
                return true;
            } else if (countBar2 == 1) {
                return true;
            } else if (countBar3 == 1) {
                return true;
            } else if (countBar4 == 1) {
                return true;
            } else if (countBar5 == 1) {
                return true;
            } else if (countBar6 == 1) {
                return true;
            } else if (countBar7 == 1) {
                return true;
            }

            return false;

        }


        public boolean isWhiteWin (String color, String location, String[][]numbers){

            int x = Integer.parseInt(location.split(",")[0]);       //좌표값의 x축
            int y = Integer.parseInt(location.split(",")[1]);       //좌표값의 y축
            //color가 1이면 검은돌, color가 2면 흰돌
            String targetColor = "2";

            int countBar1 = 0;
            int countBar2 = 0;
            int countBar3 = 0;
            int countBar4 = 0;
            int countBar5 = 0;
            int countBar6 = 0;
            int countBar7 = 0;
            int sameColor = 0;
            //1번 내 주변 2칸 차이 : 나를 중심으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 1개 이상이면 흑돌승
            //가로 다섯칸짜리 막대기
            for (int dy = -2; dy <= 2; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            //우하향 대각선
            sameColor = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            //세로 막대기
            sameColor = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }

                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            //좌하향 대각선
            sameColor = 0;

            for (int dy = -2; dy <= 2; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar1++;
                }
            }

            // 2번 내 주변 5칸 차이 : 나를 시작(아래 왼쪽 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌 승

            //꼭짓점에서 오른쪽 향하는막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar2++;
                }
            }

            //우상향 대각선
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar2++;
                }
            }

            //꼭짓점으로 부터 세로
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar2++;
                }
            }

            // 3번 내 주변 5칸 차이 : 나를 시작(아래 오른쪽 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌승

            //꼭짓점으로부터 왼쪽으로 향하는 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar3++;
                }
            }

            //좌상향 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar3++;
                }
            }

            //새로 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x - dy;
                int ny = y;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar3++;
                }
            }

            // 4번 내 주변 5칸 차이 : 나를 시작(오른쪽 위 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌승

            //꼭짓점으로부터 왼쪽으로 향하는 막대기

            sameColor = 0;
            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar4++;
                }
            }

            //좌하향 대각선
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y - dy;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar4++;
                }
            }

            //세로 막대기
            sameColor = 0;
            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar4++;
                }
            }

            // 5번 내 주변 5칸 차이 : 나를 시작(오른쪽 위 꼭짓접)으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 3개 중 1개 이상이면 흑돌승

            //꼭짓점으로부터 오른쪽으로 향하는 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar5++;
                }
            }

            //꼭짓점으로부터 우하향으로 향하는 막대기
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar5++;
                }
            }

            //꼭짓점으로부터 새로(아래) 향함
            sameColor = 0;

            for (int dy = 0; dy <= 4; dy++) {

                int nx = x + dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    } else {
                        //연속되는 값이 아닐 경우 더이상 탐색 안해도됨
                        continue;
                    }
                }
                if (sameColor == 4) {
                    countBar5++;
                }
            }

            // 6번 나를 중심으로 한칸 세칸 으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 1개 이상이면 흑돌승

            //가로 막대기 11101
            sameColor = 0;

            for (int dy = -3; dy <= 1; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }

            //세로 막대기
            sameColor = 0;
            for (int dy = -3; dy <= 1; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y == ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }

            //좌하향 막대기
            sameColor = 0;
            for (int dy = -3; dy <= 1; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }

            //우하향 막대기
            sameColor = 0;
            for (int dy = -3; dy <= 1; dy++) {

                int nx = x - dy;
                int ny = y - dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x != nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar6++;
                }
            }
            //7번 나를 중심으로 한칸 세칸 으로 다섯 칸짜리 막대기 (내 색깔로 가득차있는) 가 총 4개 중 1개 이상이면 흑돌승

            //가로 막대기 10111
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            //세로 막대기
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            //우상향 막대기
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y + dy;

                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            //좌상향 막대기
            sameColor = 0;

            for (int dy = -1; dy <= 3; dy++) {

                int nx = x - dy;
                int ny = y - dy;
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13) {
                    if (x == nx && y != ny) {

                    }
                    if (numbers[nx][ny].equals(targetColor)) {
                        sameColor++;
                    }
                }
                if (sameColor == 4) {
                    countBar7++;
                }
            }

            if (countBar1 == 1) {
                return true;
            } else if (countBar2 == 1) {
                return true;
            } else if (countBar3 == 1) {
                return true;
            } else if (countBar4 == 1) {
                return true;
            } else if (countBar5 == 1) {
                return true;
            } else if (countBar6 == 1) {
                return true;
            } else if (countBar7 == 1) {
                return true;
            }

            return false;

        }
    }




