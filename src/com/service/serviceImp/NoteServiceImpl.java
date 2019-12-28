package com.service.serviceImp;

import com.dao.NoteDao;
import com.domain.*;
import com.service.NoteService;
import com.utils.MD5Utils;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/21 - 16:45
 */

@Transactional
public class NoteServiceImpl implements NoteService {

    //自动注入Dao
    private NoteDao noteDao;

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    /**
     * 发表学长寄语
     * @throws Exception
     */
    @Override
    public void published(NoteDistrict noteDistrict) throws Exception {
        noteDao.published(noteDistrict);
    }

    /**
     * 查看单条寄语
     * @param noteId
     * @throws Exception
     */
    @Override
    public NoteDistrict showOne(String noteId) throws Exception {
        NoteDistrict noteDistrict = noteDao.showOne(noteId);
        return noteDistrict;
    }

    /**
     * 查看最新的寄语
     * @param curNum
     * @return
     * @throws Exception
     */
    @Override
    public PageModel showNew(int curNum,String uid) throws Exception {
        //创建PageModel对象 目的:计算分页参数
        int totalRecords = noteDao.counOfNote();//获取当前数据库中有多少条寄语
        PageModel pm = new PageModel(curNum,totalRecords,8);
        //查找当前分页的寄语的信息
        List<NoteDistrict> noteList_new = noteDao.showNew(pm.getStartIndex(), pm.getPageSize());
        pm.setList(noteList_new);
        //返回本页面的寄语用户点击“有用”按钮的情况
        showUsefuled(noteList_new,uid,pm);
        //返回本页面的寄语用户点击“提出异议”按钮的情况
        showObjectioned(noteList_new,uid,pm);
        //返回本页面的寄语用户点击“举报”按钮的情况
        showToreported(noteList_new,uid,pm);
        //关联url,后面的?是为了连接页码
        pm.setUrl("note_showNew.action?");
        return pm;
    }

    /**
     * 查看热门寄语
     * @throws Exception
     */
    @Override
    public PageModel showHot(int curNum,String uid) throws Exception {
        //1_创建PageModel对象 目的:计算分页参数
        int totalRecords = noteDao.counOfNote_hot();//获取当前数据库中有多少条寄语
        PageModel pm = new PageModel(curNum,totalRecords,8);
        //2_关联集合
        List<NoteDistrict> noteList_hot = noteDao.showHot(pm.getStartIndex(), pm.getPageSize());
        pm.setList(noteList_hot);
        //返回本页面的寄语用户点击“有用”按钮的情况
        showUsefuled(noteList_hot,uid,pm);
        //返回本页面的寄语用户点击“提出异议”按钮的情况
        showObjectioned(noteList_hot,uid,pm);
        //返回本页面的寄语用户点击“举报”按钮的情况
        showToreported(noteList_hot,uid,pm);
        //3_关联url,后面的?是为了连接页码
        pm.setUrl("note_showHot.action?");
        return pm;
    }

    /**
     * 用户点击“有用”按钮
     * @param noteUseful
     * @throws Exception
     */
    @Override
    public void clickUseful(NoteUseful noteUseful,String usefuled) throws Exception {
        //从note_useful表中保存/删除noteUseful数据
        noteDao.clickUseful(noteUseful,usefuled);
        //从note_district表中的usefulCoun字段的数值加1/减1
        noteDao.changeUsefulCoun(noteUseful,usefuled);
    }


    /**
     * 返回本页面的寄语用户点击“有用”按钮的情况
     * @param uid
     */
    public void showUsefuled(List<NoteDistrict> noteList,String uid,PageModel pm) throws Exception{
        String usefulIds = null;//本页面所有寄语的usefulId,hibernate使用
        List<String> usefulIdList1 = new ArrayList<String>();//存放本页面所有寄语的usefulId
        List<String> usefulIdList2 = new ArrayList<String>();//存放本页面所有寄语的usefulId(从数据库中查询)
        List<String> ClickMarkList1 = new ArrayList<String>();//将登陆点击本页的寄语的情况存放在PageMedel中
        for (NoteDistrict noteDistrict : noteList) {
            String usefulId = MD5Utils.md5(noteDistrict.getNoteId() + uid);
            if (null == usefulIds){
                usefulIds ="'"+usefulId+"'";
            }else {
                usefulIds = usefulIds + ",'" +usefulId+ "'";
            }
            usefulIdList1.add(usefulId);
        }
        List<NoteUseful> noteUsefulList = noteDao.findUsefulByUsefulId(usefulIds);
        for (NoteUseful noteUseful : noteUsefulList) {
            usefulIdList2.add(noteUseful.getUsefulId());
        }
        for (String usefulId : usefulIdList1) {
            if (usefulIdList2.contains(usefulId)){
                ClickMarkList1.add("yes");
            }else {
                ClickMarkList1.add("no");
            }
        }
        //关联PageModel
        pm.setClickMarkList1(ClickMarkList1);
    }

    /**
     * 用户点击“提出异议”按钮
     * @param noteObjection
     * @throws Exception
     */
    @Override
    public void clickObjection(NoteObjection noteObjection) throws Exception {
        //保存数据到note_objection表中
        noteDao.clickObjection(noteObjection);
        //note_district表中的objectionCoun字段的数值加1
        noteDao.changeObjectionCoun(noteObjection);
    }


    /**
     * 返回本页面的寄语用户点击“提出异议”按钮的情况
     * @param uid
     */
    public void showObjectioned(List<NoteDistrict> noteList,String uid,PageModel pm) throws Exception{
        String objectionIds = null;//本页面所有寄语的objectionId,hibernate使用
        List<String> objectionIdList1 = new ArrayList<String>();//存放本页面所有寄语的objectionId
        List<String> objectionIdList2 = new ArrayList<String>();//存放本页面所有寄语的objectionId(从数据库中查询)
        List<String> ClickMarkList2 = new ArrayList<String>();//将登陆点击本页的寄语的情况存放在PageMedel中
        for (NoteDistrict noteDistrict : noteList) {
            String objectionId = MD5Utils.md5(noteDistrict.getNoteId() + uid);
            if (null == objectionIds){
                objectionIds ="'"+objectionId+"'";
            }else {
                objectionIds = objectionIds + ",'" +objectionId+ "'";
            }
            objectionIdList1.add(objectionId);
        }
        List<NoteObjection> noteobjectionList = noteDao.findObjectionByObjectionId(objectionIds);
        for (NoteObjection noteObjection : noteobjectionList) {
            objectionIdList2.add(noteObjection.getObjectionId());
        }
        for (String objectionId : objectionIdList1) {
            if (objectionIdList2.contains(objectionId)){
                ClickMarkList2.add("yes");
            }else {
                ClickMarkList2.add("no");
            }
        }
        //关联PageModel
        pm.setClickMarkList2(ClickMarkList2);
    }

    /**
     * 用户点击“举报”按钮
     * @param noteToreport
     * @throws Exception
     */
    @Override
    public void clickToreport(NoteToreport noteToreport) throws Exception {
        //保存数据到note_objection表中
        noteDao.clickToreport(noteToreport);
        //note_district表中的objectionCoun字段的数值加1
        noteDao.changeToreportCoun(noteToreport);
    }


    /**
     * 返回本页面的寄语用户点击“举报”按钮的情况
     * @param uid
     */
    public void showToreported(List<NoteDistrict> noteList,String uid,PageModel pm) throws Exception{
        String toreportIds = null;//本页面所有寄语的toreportId,hibernate使用
        List<String> toreportIdList1 = new ArrayList<String>();//存放本页面所有寄语的toreportId
        List<String> toreportIdList2 = new ArrayList<String>();//存放本页面所有寄语的toreportId(从数据库中查询)
        List<String> ClickMarkList3 = new ArrayList<String>();//将登陆点击本页的寄语的情况存放在PageMedel中
        for (NoteDistrict noteDistrict : noteList) {
            String toreportId = MD5Utils.md5(noteDistrict.getNoteId() + uid);
            if (null == toreportIds){
                toreportIds ="'"+toreportId+"'";
            }else {
                toreportIds = toreportIds + ",'" +toreportId+ "'";
            }
            toreportIdList1.add(toreportId);
        }
        List<NoteToreport> notetoreportList = noteDao.findToreportByToreportId(toreportIds);
        for (NoteToreport noteToreport : notetoreportList) {
            toreportIdList2.add(noteToreport.getToreportId());
        }
        for (String toreportId : toreportIdList1) {
            if (toreportIdList2.contains(toreportId)){
                ClickMarkList3.add("yes");
            }else {
                ClickMarkList3.add("no");
            }
        }
        //关联PageModel
        pm.setClickMarkList3(ClickMarkList3);
    }

    /**
     * 显示登陆用户发表的寄语
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showMyNote(String uid) throws Exception {
        List<NoteDistrict> myNoteList = noteDao.showMyNote(uid);
        return myNoteList;
    }

    /**
     * 删除寄语
     * @param noteId
     * @throws Exception
     */
    @Override
    public void deleteNote(String noteId) throws Exception {
        noteDao.deleteNote(noteId);
    }

    /**
     * 查看我的消息
     * @throws Exception
     * @return
     */
    @Override
    public Map showMyMessages(String uid) throws Exception {
        //根据用户的ID查找该用户发表过的所有寄语的ID,存放在myNoteIds中
        String myNoteIds = null;
        List<NoteDistrict> noteDistricts = noteDao.showMyNote(uid);
        for (NoteDistrict noteDistrict : noteDistricts) {
            if (null == myNoteIds){
                myNoteIds ="'"+noteDistrict.getNoteId()+"'";
            }else {
                myNoteIds = myNoteIds + ",'" +noteDistrict.getNoteId()+ "'";
            }
        }
        //再根据myNoteIds中的id去查找信息
        Map myMessageMap = noteDao.showMyMessages(myNoteIds);
        return myMessageMap;
    }

    /**
     * 修改寄语的内容
     * @param noteDistrict
     * @throws Exception
     */
    @Override
    public void alterNote(NoteDistrict noteDistrict) throws Exception {
        noteDao.alterNote(noteDistrict);
    }

    /**
     * 更改为已读
     * @param tableKey
     * @param databaseTable
     */
    @Override
    public void changeIsRead(String tableKey, String databaseTable) throws Exception {
        noteDao.changeIsRead(tableKey,databaseTable);
    }

}
