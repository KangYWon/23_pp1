package com.yewon.pp;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//WordCRUD는 ICRUD를 구현한 구현체이다.

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner s;
    final String fname = "Dictionary.txt";

    WordCRUD(Scanner s){
        list = new ArrayList<>();
        this.s = s;
    }
    @Override
    public Object add() { // 사용자에게 입력받는 부분
        System.out.print("==> 난이도(1,2,3) & 새 단어 입력 : ");
        int level = s.nextInt();
        String word = s.nextLine();

        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();
        System.out.println();

        return new Word(0, level, word, meaning);
    }

    public void addItem() { // 입력받은 데이타를 리스트에 추가하는 부분
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다 !!! \n");
    }

    public void listAll(){
        System.out.println("---------------------");
        for(int i=0; i<list.size(); i++ ){
            System.out.print((i+1)+ " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("---------------------");
    }
    public ArrayList<Integer> listAll(String keyword){
        ArrayList<Integer> idlist = new ArrayList();
        int j =0;
        System.out.println("---------------------");
        for(int i=0; i<list.size(); i++ ){
            String word = list.get(i).getWord();
            if(!word.contains(keyword)) continue;
            System.out.print((j+1)+ " ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("---------------------");
        return idlist;
    }

    public void  updateItem() {
        System.out.print("=> 수정할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        if(idlist.isEmpty()){
            System.out.println("검색한 단어가 없습니다.");
            return ;
        }
        System.out.print("=> 수정할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();

        System.out.print("=> 영어 단어 수정 : ");
        String changeWord = s.nextLine();
        Word word = list.get(idlist.get(id-1));
        word.setWord(changeWord);

        System.out.print("=> 뜻 입력 : ");
        String meaning = s.nextLine();
        //Word wordM= list.get(idlist.get(id-1));
        word.setMeaning(meaning);
        System.out.println("단어가 수정되었습니다.");
    }

    public void deleteItem() {
        System.out.print("=> 삭제할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        if(idlist.isEmpty()){
            System.out.println("검색한 단어가 없습니다.");
            return ;
        }
        System.out.print("=> 삭제할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        Word word = list.get(idlist.get(id-1));

        System.out.print("=> 정말로 삭제하실래요?(Y/N) ");
        String ans = s.nextLine();
        if(ans.equalsIgnoreCase("Y")) {
            list.remove((int)idlist.get(id - 1));
            System.out.println("단어가 삭제되었습니다.");
        }else
            System.out.println("취소되었습니다.");
    }

    public void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fname));
            String line;
            int count =0;

            while(true){
                line = br.readLine();
                if(line==null) break;
                String data[] = line.split("\\|");
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning = data[2];
                list.add(new Word(0, level,word, meaning));
                count++;
            }
            br.close();
            System.out.println("==> "+count + "개 로딩 완료!!!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile() {
        try {
            PrintWriter pr = new PrintWriter(new FileWriter("fname"));
            for(Word one : list){
                pr.write(one.toFileString() + "\n");
            }
            pr.close();
            System.out.println("==> 데이터 저장 완료 !!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listAll(int level){
        int j =0;
        System.out.println("---------------------");
        for(int i=0; i<list.size(); i++ ){
            int ilevel = list.get(i).getLevel();
            if(ilevel!=level) continue;
            System.out.print((j+1)+ " ");
            System.out.println(list.get(i).toString());
            j++;
        }
        System.out.println("---------------------");
    }

    public void searchLevel() {
        System.out.print("=> 원하는 레벨은? (1~3) ");
        int level = s.nextInt();
        listAll(level);
    }

    public void searchWord() {
        System.out.print("=> 원하는 단어는? ");
        String keyword = s.next();
        listAll(keyword);
    }

}
