package org.example;

import java.util.*;

import static org.example.MemoApplication.displayMainPage;

public class MemoList {
    private List<Memo> memoList;

    // 생성자 메서드
    public MemoList() {
        memoList = new ArrayList<>();
    }

    // setter() , getter()
/*    public void setMomoList(List<Memo> memoList) {
        this.memoList = memoList;
    }*/
    public List<Memo> getMemoList() {
        return memoList;
    }

    // list에 memo 추가
    public void NewMemo(Memo memo) {
        this.memoList.add(memo);
    }

    // 메모 정보 출력
    public void printMemoList() {

        List<Memo> sortMemoList = new ArrayList<>();
        sortMemoList.addAll(memoList);

        Collections.sort(sortMemoList, new idComparator());

        for (Memo memo : sortMemoList) {
            if (memo != null) {
                System.out.println("\n=====================================");
                System.out.printf(" [[ %-2d번째 메모 ]] ", memo.getId());
                memo.printMemo();
                System.out.println();
            } else {// if() of the end
                System.out.println("저장된 메모가 없습니다.");
            } // if ~ else() of the end
        }// for() of the end
    }

    // 번호를 넘겨받아 해당 memo 반환하는 메소드
    public Memo getMemo(int num) {
        num--; // 1번째 메모는 0번 인덱스이므로 감소시킴
        return memoList.get(num);
    }

    // 해당되는 글 1건 수정하는 메서드
    public void editMemo(int editNum) {
        Memo editMemo = getMemo(editNum); // 수정할 Memo

        if(editMemo != null){
            Scanner scanner = new Scanner(System.in);
            System.out.println("비밀번호를 입력하세요: ");
            String password = scanner.nextLine();

            if (password.equals(editMemo.getPassword())) {
                System.out.print("수정할 이름을 입력해주세요: ");
                String modifyName = scanner.nextLine();

                System.out.print("수정할 메모를 입력해주세요: ");
                String modifyContent = scanner.nextLine();
                Date modifyDate = new Date(); // 현재 날짜 지정.

                //id랑 date는 MemoPage에서 할 예정
                Memo memo = new Memo();

                memo.setId(editNum);
                memo.setName(modifyName);
                memo.setContent(modifyContent);
                memo.setDate(modifyDate);
                memo.setPassword(editMemo.getPassword());

                int key = editNum-1;

                memoList.set(key, memo);
                System.out.println("메모가 수정되었습니다.");
                displayMainPage();
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("존재하지 않는 글입니다.");
        }
    }

    // 해당 글 삭제하는 메서드
    public void deleteMemo(int delNum) {
        Memo deleteMemo = getMemo(delNum); // 삭제할 Memo

        if (deleteMemo != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("비밀번호를 입력하세요: ");
            String password = scanner.nextLine();

            if (password.equals(deleteMemo.getPassword())) {
                memoList.remove(deleteMemo);
                reindexMemo(); // 글 삭제 후 글 번호 다시 붙여주기
                System.out.println("글이 삭제되었습니다.");
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("존재하지 않는 글입니다.");
        }
    }

    // 삭제후 글 번호 다시 붙여주기.
    // 글이 삭제된 후 새 글이 입력될 때 idx가 기존 idx값에 이어서 1씩 증가할 수 있도록 count의 값을 수정한다.
    private void reindexMemo() {
        int count = 1;
        List<Memo> newMemoList = new ArrayList<>();

        for (Memo memo : memoList) {
            if (memo != null) {
                memo.setId(count++);
                newMemoList.add(memo);
            }
        }
        memoList = newMemoList;
    }
}

class idComparator implements Comparator<Memo> {
    @Override
    public int compare(Memo o1, Memo o2) {
        if(o1.getDate().before(o2.getDate())){
            return 1;
        } else if (o2.getDate().before(o1.getDate())) {
            return -1;
        }
        return 0;
    }
}