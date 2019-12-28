package com.dao.daoImp;

import com.dao.NoteDao;
import com.domain.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/21 - 16:46
 */
public class NoteDaoImpl extends HibernateDaoSupport implements NoteDao {
    /**
     * 发表学长寄语
     * @throws Exception
     */
    @Override
    public void published(NoteDistrict noteDistrict) throws Exception {
        this.getHibernateTemplate().save(noteDistrict);
    }

    /**
     * 计算当前数据库中有多少条寄语
     * @return
     * @throws Exception
     */
    @Override
    public int counOfNote() throws Exception {
        List coun = this.getHibernateTemplate().find("select count(*) from NoteDistrict n where n.status = 1");
        Long counOfNote = (Long)coun.get(0);
        return counOfNote.intValue();
    }
    /**
     * 计算当前数据库中有多少条寄语
     * @return
     * @throws Exception
     */
    @Override
    public int counOfNote_hot() throws Exception {
        List coun = this.getHibernateTemplate().find("select count(*) from NoteDistrict n where n.status = 1 and n.usefulCoun > 0");
        Long counOfNote = (Long)coun.get(0);
        return counOfNote.intValue();
    }

    /**
     * 查看单条寄语
     * @param noteId
     * @throws Exception
     */
    @Override
    public NoteDistrict showOne(String noteId) throws Exception {
        NoteDistrict noteDistrict = (NoteDistrict)this.getHibernateTemplate().get(NoteDistrict.class,noteId);
        return noteDistrict;
    }

    /**
     * 查看最新的寄语
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showNew(int startIndex, int pageSize) throws Exception {
        List<NoteDistrict> noteList_new =
                (List<NoteDistrict>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            @Override
                            public Object doInHibernate(Session session)
                                    throws HibernateException{
                                String hql = "from NoteDistrict n where n.status=1 order by n.publishedTime desc";
                                Query query = session.createQuery(hql);
                                query.setFirstResult(startIndex);
                                query.setMaxResults(pageSize);
                                return query.list();
                            }
                        }
                );
        return noteList_new;
    }

    /**
     * 查看热门寄语
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showHot(int startIndex, int PageSize) throws Exception {
        List<NoteDistrict> noteList_hot =
            (List<NoteDistrict>) this.getHibernateTemplate().execute(
                    new HibernateCallback() {
                        @Override
                        public Object doInHibernate(Session session)
                                throws HibernateException{
                            String hql = "from NoteDistrict n where n.status=1 and n.usefulCoun > 0 order by n.usefulCoun desc";
                            Query query = session.createQuery(hql);
                            query.setFirstResult(startIndex);
                            query.setMaxResults(PageSize);
                            return query.list();
                        }
                    }
            );
        return noteList_hot;
    }

    /**
     * 用户点击“有用”按钮
     * @param noteUseful
     * @throws Exception
     */
    @Override
    public void clickUseful(NoteUseful noteUseful,String usefuled) throws Exception {
        if ("false".equals(usefuled)){
            this.getHibernateTemplate().save(noteUseful);
        }else {
            this.getHibernateTemplate().delete(noteUseful);
        }
    }

    /**
     * 根据noteId查询寄语的“有用”记录
     * @throws Exception
     */
    @Override
    public List<NoteUseful> findUsefulByUsefulId(String usefulIds) throws Exception {
        String hql = "from NoteUseful where usefulId in("+usefulIds+")";
        List<NoteUseful> list = (List<NoteUseful>) this.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * 用户点击“有用”按钮，note_district表中的usefulCoun字段的数值加1
     * @param noteUseful
     * @throws Exception
     */
    @Override
    public void changeUsefulCoun(NoteUseful noteUseful,String usefuled) throws Exception {
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, noteUseful.getNoteId());
        if ("false".equals(usefuled)){
            noteDistrict.setUsefulCoun(noteDistrict.getUsefulCoun()+1);
        }else {
            noteDistrict.setUsefulCoun(noteDistrict.getUsefulCoun()-1);
        }
        this.getHibernateTemplate().update(noteDistrict);
    }

    /**
     * 用户点击“提出异议”按钮
     * @param noteObjection
     * @throws Exception
     */
    @Override
    public void clickObjection(NoteObjection noteObjection) throws Exception {
        this.getHibernateTemplate().save(noteObjection);
    }

    /**
     * 用户点击“提出异议”按钮，note_district表中的objectionCoun字段的数值加1
     * @param noteObjection
     * @throws Exception
     */
    @Override
    public void changeObjectionCoun(NoteObjection noteObjection) throws Exception {
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, noteObjection.getNoteId());
        noteDistrict.setObjectionCoun(noteDistrict.getObjectionCoun()+1);
        this.getHibernateTemplate().update(noteDistrict);
    }

    /**
     * 根据objectionIds查询寄语的“提出异议”记录
     * @param objectionIds
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteObjection> findObjectionByObjectionId(String objectionIds) throws Exception{
        String hql = "from NoteObjection n where n.objectionId in("+objectionIds+")";
        List<NoteObjection> list = (List<NoteObjection>) this.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * 用户点击“举报”按钮
     * @param noteToreport
     * @throws Exception
     */
    @Override
    public void clickToreport(NoteToreport noteToreport) throws Exception {
        this.getHibernateTemplate().save(noteToreport);
    }

    /**
     * 用户点击“举报”按钮，note_district表中的ToreportCoun字段的数值加1
     * @param noteToreport
     * @throws Exception
     */
    @Override
    public void changeToreportCoun(NoteToreport noteToreport) throws Exception {
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, noteToreport.getNoteId());
        noteDistrict.setToreportCoun(noteDistrict.getToreportCoun()+1);
        this.getHibernateTemplate().update(noteDistrict);
    }

    /**
     * 根据toreportIds查询寄语的“提出异议”记录
     * @param toreportIds
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteToreport> findToreportByToreportId(String toreportIds) throws Exception {
        String hql = "from NoteToreport n where n.toreportId in("+toreportIds+")";
        List<NoteToreport> list = (List<NoteToreport>) this.getHibernateTemplate().find(hql);
        return list;
    }

    /**
     * 显示登陆用户发表的寄语
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showMyNote(String uid) throws Exception {
        String hql = "select new NoteDistrict(noteId,note,publishedTime) from NoteDistrict n where n.status=1 and n.uid = ? order by n.publishedTime desc";
        List<NoteDistrict> myNoteList =
                (List<NoteDistrict>) this.getHibernateTemplate().find(hql,uid);
        return myNoteList;
    }

    /**
     * 删除寄语
     * @param noteId
     * @throws Exception
     */
    @Override
    public void deleteNote(String noteId) throws Exception {
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, noteId);
        noteDistrict.setStatus(0);
        this.getHibernateTemplate().update(noteDistrict);
    }

    /**
     * 查看我的消息
     * @throws Exception
     * @return
     */
    @Override
    public Map showMyMessages(String myNoteIds) throws Exception {

        Map myMessageMap = new HashMap();

        //查找是否有“有用”信息
        String hql1 = "from NoteObjection n where n.noteId in("+myNoteIds+") and n.isRead = 'false'";
        List<NoteObjection> noteObjectionList = (List<NoteObjection>) this.getHibernateTemplate().find(hql1);
        List<UserIdAndName> noteObjectionUserName = new ArrayList<UserIdAndName>();
        for (NoteObjection noteObjection : noteObjectionList) {
            //根据信息的uid查找用户名字
            UserIdAndName userIdAndName = findUserNameByUid(noteObjection.getUid());
            noteObjectionUserName.add(userIdAndName);
        }
        //查找是否有“提出异议”信息
        String hql2 = "from NoteUseful n where n.noteId in("+myNoteIds+") and n.isRead = 'false'";
        List<NoteUseful> noteUsefulList = (List<NoteUseful>) this.getHibernateTemplate().find(hql2);
        List<UserIdAndName> noteUsefulUserName = new ArrayList<UserIdAndName>();
        for (NoteUseful noteUseful : noteUsefulList) {
            //根据信息的uid查找用户名字
            UserIdAndName userIdAndName = findUserNameByUid(noteUseful.getUid());
            noteUsefulUserName.add(userIdAndName);
        }
        //查找是否有“举报”信息
        String hql3 = "from NoteToreport n where n.noteId in("+myNoteIds+") and n.isRead = 'false'";
        List<NoteToreport> noteToreportList = (List<NoteToreport>) this.getHibernateTemplate().find(hql3);
        /*当寄语被举报的时候，无需显示举报人的名字
        List<UserIdAndName> noteToreportUserName = new ArrayList<UserIdAndName>();
        for (NoteToreport noteToreport : noteToreportList) {
            //根据信息的uid查找用户名字
            UserIdAndName userIdAndName = findUserNameByUid(noteToreport.getUid());
            noteToreportUserName.add(userIdAndName);
        }*/
        //将所有信息存放在myMessageMap中
        myMessageMap.put("noteObjectionList",noteObjectionList);
        myMessageMap.put("noteObjectionUserName",noteObjectionUserName);
        myMessageMap.put("noteUsefulList",noteUsefulList);
        myMessageMap.put("noteUsefulUserName",noteUsefulUserName);
        myMessageMap.put("noteToreportList",noteToreportList);
        /*myMessageMap.put("noteToreportUserName",noteToreportUserName);*/

        return myMessageMap;

    }

    /**
     * 根据用户ID查找用户名
     * @param uid
     * @return
     */
    public UserIdAndName findUserNameByUid(String uid){
        UserIdAndName userIdAndName = this.getHibernateTemplate().get(UserIdAndName.class, uid);
        return userIdAndName;
    }

    /**
     * 修改寄语的内容
     * @param noteDistrict
     * @throws Exception
     */
    @Override
    public void alterNote(NoteDistrict noteDistrict) throws Exception {
        NoteDistrict noteDistrict1 = this.getHibernateTemplate().get(NoteDistrict.class,noteDistrict.getNoteId());
        noteDistrict1.setNote(noteDistrict.getNote());
        this.getHibernateTemplate().update(noteDistrict1);
    }

    /**
     * 更改为已读
     * @param tableKey
     * @param databaseTable
     * @throws Exception
     */
    @Override
    public void changeIsRead(String tableKey, String databaseTable) throws Exception {
        if ("noteUseful".equals(databaseTable)){
            NoteUseful noteUseful = this.getHibernateTemplate().get(NoteUseful.class, tableKey);
            noteUseful.setIsRead("true");
            this.getHibernateTemplate().update(noteUseful);
        }else if ("noteObjection".equals(databaseTable)){
            NoteObjection noteObjection = this.getHibernateTemplate().get(NoteObjection.class, tableKey);
            noteObjection.setIsRead("true");
            this.getHibernateTemplate().update(noteObjection);
        }else if ("noteToreport".equals(databaseTable)){
            NoteToreport noteToreport = this.getHibernateTemplate().get(NoteToreport.class, tableKey);
            noteToreport.setIsRead("true");
            this.getHibernateTemplate().update(noteToreport);
        }

    }

}
