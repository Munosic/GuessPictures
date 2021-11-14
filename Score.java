import java.util.ArrayList;
import java.io.*;


class Score{
    int [] score,score2;
    int player_num;

    public Score(int player_num){ //プレイヤーの人数(1～)
      this.player_num = player_num;
      score = new int[player_num];
      score2 = new int[player_num];
    }

    public void plus(int player,int plus){ //プレイヤー番号(0～)
      score[player]+=plus;
      score2[player]+=plus;
    }

    public void minus(int player){ //プレイヤー番号(0～)
      score[player]--;
      score2[player]--;
    }

    public void setScore(int player, int s) {
      score[player] = s;
      score2[player] = s;
    }

    public ArrayList<String> rank(){
      //int max=0;
      ArrayList<String> ranking = new ArrayList<String>();
      String [] playerName = {"Player1","Player2","Player3","Player4","Player5"};
      int tmp_score;
      String tmp_name;
      /*名前とスコア両方並べ替え*/
      for ( int i = 0; i < player_num - 1; i++ ) {
        for ( int j = player_num - 1; j > i; j-- ) {
          if ( score2[j - 1] < score2[j] ) {
            /*スコア入れかえ*/
            tmp_score = score2[j - 1];
            score2[j - 1] = score2[j];
            score2[j] = tmp_score;
            /*名前入れかえ*/
            tmp_name = playerName[j - 1];
            playerName[j - 1] = playerName[j];
            playerName[j] = tmp_name;
          }
        }
      }
      /*ランキング結果をファイル出力*/
      String filename = "score.txt";
      try{
        // テキストファイル出力
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        for(int i=0;i<player_num;i++){
             pw.println("name:"+playerName[i]+" score:"+score2[i]);
             ranking.add("name:"+playerName[i]+" score:"+score2[i]);
        }
        pw.close();
        }catch(IOException e){
        System.out.println("IO error");
      }
      return ranking;
    }

    public int getScore(int player){ //プレイヤー番号(0～)
        return score[player];
    }

    public void resetScore(){ //プレイヤー人数(1～)
      for(int i=0;i<player_num;i++){
        score[i]=0;
        score2[i]=0;
      }
    }
}
