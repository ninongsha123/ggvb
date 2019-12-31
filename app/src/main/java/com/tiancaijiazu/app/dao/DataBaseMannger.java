package com.tiancaijiazu.app.dao;

import com.tiancaijiazu.app.app.App;

import java.util.List;

/**
 * Created by Administrator on 2019/5/20/020.
 */

public class DataBaseMannger {
    private static DataBaseMannger mDataBaseMannger;
    private final ToKenDaoBeanDao mToKenDaoBeanDao;
    private final HistoryTitleBeanDao mHistoryTitleBeanDao;
    private final TheTopicRecordBeanDao mTheTopicRecordBeanDao;
    private final UserInFoDao userInFoDao;
    private final DownAudioBeanDao mDownAudioBeanDao;

    //单例模式
    public static DataBaseMannger getIntrance() {
        if (mDataBaseMannger == null) {
            synchronized (DataBaseMannger.class) {
                if (mDataBaseMannger == null) {
                    mDataBaseMannger = new DataBaseMannger();
                }
            }
        }
        return mDataBaseMannger;
    }

    DataBaseMannger() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getApplication(), "tobean.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mToKenDaoBeanDao = daoSession.getToKenDaoBeanDao();
        mHistoryTitleBeanDao = daoSession.getHistoryTitleBeanDao();
        mTheTopicRecordBeanDao = daoSession.getTheTopicRecordBeanDao();
        userInFoDao = daoSession.getUserInFoDao();
        mDownAudioBeanDao = daoSession.getDownAudioBeanDao();
    }
    //条件查询
    public List<TheTopicRecordBean> selectTheTopicRecordBeanTiao(String babyId,String subjectId) {
        return mTheTopicRecordBeanDao.queryBuilder().where(TheTopicRecordBeanDao.Properties.BabyId.eq(babyId))
                .where(TheTopicRecordBeanDao.Properties.SubjectId.eq(subjectId)).list();
    }
    //添加
    public void insertDownAudio(List<DownAudioBean> list) {
        mDownAudioBeanDao.insertInTx(list);
    }

    //查询
    public List<DownAudioBean> selectDownAudio() {
        return mDownAudioBeanDao.queryBuilder().list();
    }
    public void deleteDownAudio(DownAudioBean list) {
        mDownAudioBeanDao.delete(list);
    }
    //添加
    public void insertTheTopicRecord(List<TheTopicRecordBean> list) {
        mTheTopicRecordBeanDao.insertInTx(list);
    }

    //查询
    public List<TheTopicRecordBean> selectTheTopicRecordBean() {
        return mTheTopicRecordBeanDao.queryBuilder().list();
    }

    //删除
    public void deleteAllTheTopicRecordBean() {
        mTheTopicRecordBeanDao.deleteAll();
    }

    public void deleteTheTopicRecordBean(TheTopicRecordBean bean) {
        mTheTopicRecordBeanDao.delete(bean);
    }

    public void deleteTheTopicRecordBeanAll() {
        mTheTopicRecordBeanDao.deleteAll();
    }

    //添加
    public void insertHistoryTitle(List<HistoryTitleBean> list) {
        mHistoryTitleBeanDao.insertInTx(list);
    }

    //删除
    public void deleteHistoryTitle(HistoryTitleBean bean) {
        mHistoryTitleBeanDao.delete(bean);
    }

    //删除
    public void deleteHistoryTitleAll() {
        mHistoryTitleBeanDao.deleteAll();
    }

    //删除
    public void deleteAll() {
        mToKenDaoBeanDao.deleteAll();
    }


    //查询
    public List<HistoryTitleBean> selectHistoryTitle() {
        return mHistoryTitleBeanDao.queryBuilder().list();
    }

    //添加
    public void insert(List<ToKenDaoBean> list) {
        mToKenDaoBeanDao.insertInTx(list);
    }

    //删除
    public void delete(ToKenDaoBean bean) {
        mToKenDaoBeanDao.delete(bean);
    }

    //查询
    public List<ToKenDaoBean> select() {
        return mToKenDaoBeanDao.queryBuilder().list();
    }

    //查询
    public List<UserInFo> selectUserInfo(){
        return userInFoDao.queryBuilder().list();
    }

    //添加
    public void insertUserInfo(List<UserInFo> list){
        userInFoDao.insertInTx(list);
    }

    //删除
    public void deleteUserInFo(UserInFo userInFo){
        userInFoDao.delete(userInFo);
    }
}
