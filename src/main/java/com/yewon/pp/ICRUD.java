package com.yewon.pp;

public interface ICRUD {
    public Object add(); // data 추가할 때 사용
    public int update(Object obj);
    public int delete(Object obj);
    public void selectOne(int id); //data 한 개 조회
}
