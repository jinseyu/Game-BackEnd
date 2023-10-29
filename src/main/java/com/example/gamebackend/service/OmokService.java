package com.example.gamebackend.service;
import java.util.Arrays;
import com.example.gamebackend.dto.OmokDTO;
import org.apache.jasper.tagplugins.jstl.core.If;
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
        } else if (is3X3(color,location,numbers)) {
            return "3*3판정입니다";
        } else if (is4X3(color,location,numbers)) {
            return "4*3판정입니다";
        } else if(isBlackWin(color,location,numbers)) {
            return "흑돌승리 판정";
        }

        // 백돌승리판정
        // else 그냥놔도됨



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
        System.out.println("x: " + x + ", y: " + y);

        System.out.println("Value at x, y: " + numbers[x][y]);
        return !numbers[x][y].equals("0");

    }

    //3*3 판정
    public boolean is3X3(String color, String location, String[][] numbers) {

        int x = Integer.parseInt(location.split(",")[0]);       //좌표값의 x축
        int y = Integer.parseInt(location.split(",")[1]);       //좌표값의 y축
        //color가 1이면 검은돌, color가 2면 흰돌
        String targetColor = (color.equals("1")) ? "1" : "2";

        int count = 0;

        for (int dx = -1; dx <= 1; dx++) {           //x축 변화량
            for (int dy = -1; dy <= 1; dy++) {      //y축 변화량

                //현재위치
                if (dx == 0 && dy == 0) continue;
                //이동한 만큼의 위치 계산
                int nx = x + dx;
                int ny = y + dy;
                System.out.println("nx: " + nx + ", ny: " + ny);
                System.out.println("numbers[nx][ny]: " + numbers[nx][ny]);
                //만약 새로운 위치가 따로 있고,targetcolor와 같다면 count 증가
                if (nx >= 0 && nx < 13 && ny >= 0 && ny < 13 && numbers[nx][ny].equals(targetColor)) {
                    count++;
                }
            }
        }
        System.out.println("count: " + count);
        //주변에 같은돌이 3개이상이면 true , 그렇지않으면 false
        return count >= 3;

    }

    //4*3 판정
    public boolean is4X3(String color,String location,String[][] numbers) {

        int x = Integer.parseInt(location.split(",")[0]);       //좌표값의 x축
        int y = Integer.parseInt(location.split(",")[1]);       //좌표값의 y축
        //color가 1이면 검은돌, color가 2면 흰돌
        String targetColor = (color.equals("1")) ? "1" : "2";

        int count = 0;

        //가로방향으로 4개의 돌이 연속되는지 확인,가로방향으로 -2,+2옮겨가며 확인
        for (int i = x - 2; i <= x + 2; i++) {
                //바둑판을 벗어나면 건너띈다
                if (i < 0 || i > 12) continue;
                //연속된 같은 돌색인지 판정
                boolean isSameColor = true;
                //가로로 3칸을 검사하고
                for (int j = i; j < i + 3; j++) {
                if (j < 0 || j > 12) continue;
                //만약 연속되는 색이 다르다면 false
                if (!numbers[j][y].equals(targetColor)) {
                    isSameColor = false;
                    break;
                }
            }
                //가로로 연속된값이면 count증가
                if (isSameColor) {
                    count++;
                    System.out.println("Horizontal: " + count);
                    break;
                }
            }

        // 세로 방향으로 4개가 연속되는지 확인
        for (int j = y - 2; j <= y+2; j++) {

                if (j < 0 || j+2 > 12) continue;
                boolean isSameColor = true;

                for (int i = j ; i < j + 3; i++) {

                if (!numbers[x][i].equals(targetColor)) {
                    isSameColor = false;
                    break;
                }
            }
                if (isSameColor) {

                count++;
                System.out.println("Vertical: " + count);
                break;

            }
        }

        return count > 0;
    }

    public boolean isBlackWin(String color,String location,String[][]numbers){
        int x = Integer.parseInt(location.split(",")[0]);
        int y = Integer.parseInt(location.split(",")[1]);
        String targetColor = (color.equals("1")) ? "1" : "2";

        int count = 0;

        //가로 방향으로 5개가 연속되는지 확인
        for(int i=x-4; i<=x; i++){
            //오목판 안에서 연속되는값 i가 13*13 오목판에서 놀면 통과
            if (i<0 || i+4>12) continue;
            //연속되는 오목 색깔이 동일한지
            boolean isSameColor = true;
            //지금 위에서 검사한 i의 값에 5개 돌을 검사위한 반복문
            for (int j=i; j<+5 ;j++){
                //현재 검사하는 돌색과 현재 돌색이 다르면 false
                if(!numbers[j][y].equals(targetColor)){
                    isSameColor=false;
                    break;
                }
            }
            //같은 색이라면 true
            if (isSameColor){
                return true;
            }
        }

       //세로 방향으로 5개가 연속되는지 확인
        for(int j=y-4;j<=y; j++){
            //j가 원하는 범위내에서 있는지 확인하고 넘어감
            if(j<0 || j+4>12)continue;
            //세로로 5개 돌이 모두 동일한 색인지
            boolean isSameColor = true;

            for(int i=j; i<j+5 ;i++){
                //만약 현재 돌 색(x)이 검사하려는 현재 검사하려는 돌의 색과 다르다면 false
                if (!numbers[x][i].equals(targetColor)){
                    isSameColor=false;
                    break;
                }
            }
            if (isSameColor){
                return true;
            }
        }

        // 대각선 방향 (우상향)으로 5개가 연속되는지 확인
        //대각선에 4칸 위치한 방향으로 현재 놓인돌에 5칸까지 검사
        for (int i = x - 4, j = y + 4; i <= x && j >= y; i++, j--) {
            //만약,i가 오목(13*13)판에 있거나 음수이면 검사할필요없으니 통과
            if (i < 0 || j < 0 || i + 4 > 12 || j + 4 > 12) continue;

            boolean isSameColor = true;

            for (int k = 0; k < 5; k++) {
                if (!numbers[i + k][j - k].equals(targetColor)) {
                    isSameColor = false;
                    break;
                }
            }
            if (isSameColor) {
                return true;
            }
        }

        // 대각선 방향 (우하향)으로 5개가 연속되는지 확인
        for (int i = x - 4, j = y - 4; i <= x && j <= y; i++, j++) {
            if (i < 0 || j < 0 || i + 4 > 12 || j + 4 > 12) continue;
            boolean isSameColor = true;
            for (int k = 0; k < 5; k++) {
                if (!numbers[i + k][j + k].equals(targetColor)) {
                    isSameColor = false;
                    break;
                }
            }
            if (isSameColor) {
                return true;
            }
        }

        return false;
    }



}

