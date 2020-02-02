var app = new Vue({
    el: "#app",
    data: function() {
        return {
            visible: false,
            activeIndex: '1',
            activeIndex2: '1',
            //存放面包屑的所有字段
            breadcrumbTitle:[
                ['','匿名友人','学长寄语','志同道合','用户管理'],
                ['','查看说说','查看热门','查看评论'],
                ['','查看寄语','查看热门','查看异议','查看举报'],
                ['','查看队伍','查看队员','查看举报','已满队伍'],
                ['','普通用户','管理用户']
            ],
            //存放面包屑的具体数据（一级、二级）
            crumbsTitle1: '当前位置：首页',
            crumbsTitle2: '',
            //分页显示的当前页
            currentPage: 1,
            pageSize: 10,
            //数据库中的数据
            tableData: [],
            //当前表标题
            tableTitle:[],
            //学长寄语的表的标题
            tableTitle1:[
                [
                    {lable:'寄语ID',key:'noteId'},
                    {lable:'寄语内容',key:'note'},
                    {lable:'发表时间',key:'publishedTime'},
                    {lable:'被提出异议数',key:'objectionCoun'},
                    {lable:'被喜欢数',key:'usefulCoun'},
                    {lable:'被投诉',key:'toreportCoun'}
                 ],
                [
                    {lable:'寄语ID',key:'noteId'},
                    {lable:'寄语内容',key:'note'},
                    {lable:'发表时间',key:'publishedTime'},
                    {lable:'被提出异议数',key:'objectionCoun'},
                    {lable:'被喜欢数',key:'usefulCoun'},
                    {lable:'被投诉',key:'toreportCoun'}
                 ],
                [
                    {lable:'寄语ID',key:'noteId'},
                    {lable:'用户ID',key:'uid'},
                    {lable:'异议内容',key:'objectionText'},
                    {lable:'发表时间',key:'objectionTime'},
                 ],
                [
                    {lable:'寄语ID',key:'noteId'},
                    {lable:'用户ID',key:'uid'},
                    {lable:'举报内容',key:'toreportText'},
                    {lable:'举报时间',key:'toreportTime'},
                 ]

            ],
            //志同道合的表的标题
            tableTitle2:[
                [
                    {lable:'队伍序号',key:'counter'},
                    {lable:'队长ID',key:'uid'},
                    {lable:'队伍名称',key:'teamName'},
                    {lable:'队伍类型',key:'teamType'},
                    {lable:'队伍总人数',key:'teamPeopleNum'},
                    {lable:'当前人数',key:'currPeopleNum'},
                    {lable:'队伍简介',key:'teamIntroduction'},
                    {lable:'加入条件',key:'joinCondition'},
                    {lable:'创建时间',key:'createTime'}

                 ],
                [
                    {lable:'队伍ID',key:'teamId'},
                    {lable:'队长ID',key:'teamCaptainUid'},
                    {lable:'申请人ID',key:'teamMemberUid'},
                    {lable:'申请状态',key:'consentJoin'},
                    {lable:'加入时间',key:'joinTime'}
                 ],
                [
                    {lable:'队伍ID',key:'teamId'},
                    {lable:'队长ID',key:'teamCaptainUid'},
                    {lable:'举报人ID',key:'uid'},
                    {lable:'举报内容',key:'toreportText'},
                    {lable:'举报时间',key:'toreportTime'}
                 ],
                [
                    {lable:'队伍序号',key:'counter'},
                    {lable:'队长ID',key:'uid'},
                    {lable:'队伍名称',key:'teamName'},
                    {lable:'队伍类型',key:'teamType'},
                    {lable:'队伍总人数',key:'teamPeopleNum'},
                    {lable:'当前人数',key:'currPeopleNum'},
                    {lable:'队伍简介',key:'teamIntroduction'},
                    {lable:'加入条件',key:'joinCondition'},
                    {lable:'创建时间',key:'createTime'}
                ]

            ],
            //用户管理的表的标题
            tableTitle3:[
                [
                    {lable:'用户 ID',key:'uid'},
                    {lable:'用户名字',key:'uname'},
                    {lable:'用户性别',key:'sex'},
                    {lable:'用户电话',key:'telephone'},
                    {lable:'所在学校',key:'school'},
                    {lable:'所在学院',key:'secondarySchool'},
                    {lable:'所在年级',key:'ugrade'},
                    {lable:'所在班级',key:'uclass'},
                    {lable:'账号状态',key:'status'}
                 ],
                [
                    {lable:'用户 ID',key:'uid'},
                    {lable:'用户名字',key:'uname'},
                    {lable:'用户性别',key:'sex'},
                    {lable:'用户电话',key:'telephone'},
                    {lable:'所在学校',key:'school'},
                    {lable:'所在学院',key:'secondarySchool'},
                    {lable:'所在年级',key:'ugrade'},
                    {lable:'所在班级',key:'uclass'},
                    {lable:'账号状态',key:'status'}
                 ]

            ],
            //匿名说说的表的标题
            tableTitle4:[
                [
                    {lable:'说说序号',key:'counter'},
                    {lable:'说说ID',key:'anonID'},
                    {lable:'用户ID',key:'uid'},
                    {lable:'说说内容',key:'anonContent'},
                    {lable:'发表时间',key:'deliveryTime'},
                    {lable:'喜欢的人数',key:'likeCoun'},
                    {lable:'说说状态',key:'status'}
                 ],
                [
                    {lable:'说说序号',key:'counter'},
                    {lable:'说说ID',key:'anonID'},
                    {lable:'用户ID',key:'uid'},
                    {lable:'说说内容',key:'anonContent'},
                    {lable:'发表时间',key:'deliveryTime'},
                    {lable:'喜欢的人数',key:'likeCoun'},
                    {lable:'说说状态',key:'status'}
                 ],
                [
                    {lable:'评论序号',key:'counter'},
                    {lable:'说说ID',key:'anonID'},
                    {lable:'评论人ID',key:'sourceUid'},
                    {lable:'评论人名称',key:'sourceUname'},
                    {lable:'目标人ID',key:'destUid'},
                    {lable:'目标人名称',key:'destUname'},
                    {lable:'评论内容',key:'commentText'},
                    {lable:'评论时间',key:'commentTime'},
                    {lable:'评论/回复',key:'commentOrReply'}
                 ]

            ],
            //数据库中共xx条
            totalNum:10,
            //存放当前点击的是哪个
            index1:1,
            index2:1,
            //存放点击页码需要访问的action的路径
            actionURL:"",
            //点击删除数据访问的路径
            deleteURL:"",
            //点击删除时用于查询数据库的索引
            deleteId:[
                ['anonID','anonID','counter'],
                ['noteId','noteId','objectionId','toreportId'],
                ['teamId','teamMemberId','toreportId','teamId'],
                ['uid','']
            ]
        }
    },
    methods: {
        //选择左边导航栏时更改显示面包屑中的内容
        handleSelect(key, keyPath) {
            $('.indexpanel').css("display","none");
            this.currentPage = 1;
            //面包屑一级标题
            let index1 = key.substr(0,1);
            //面包屑二级标题
            let index2 = key.substr(2,1);
            //显示当前一二级的面包屑
            this.crumbsTitle1 = "当前位置："+this.breadcrumbTitle[0][index1];
            this.crumbsTitle2 = this.breadcrumbTitle[index1][index2];

            switch (index1) {
                /**
                 * 匿名友人
                 */
                case '1' :
                    //统计表中的数据条数
                    axios.post('ZJGuideWebsite_war_exploded/admin_showAnonCoun.action',
                        "&index="+index2,
                    ).then(response => (this.totalNum = response.data));
                    //查找表中的数据
                    axios.post('ZJGuideWebsite_war_exploded/admin_showAnon.action',
                        "currentPage="+this.currentPage+"&pageSize="+this.pageSize+"&index="+index2,
                    ).then(response => (this.tableData = response.data));
                    //将学长寄语的表标题绑定在当前表标题中
                    this.tableTitle = this.tableTitle4;
                    //指定分页栏的地址
                    this.actionURL = "ZJGuideWebsite_war_exploded/admin_showAnon.action";
                    this.deleteURL = "ZJGuideWebsite_war_exploded/admin_deleteAnon.action";
                    break;
                /**
                 * 学长寄语
                 */
                case '2' :
                    //统计表中的数据条数
                    axios.post('ZJGuideWebsite_war_exploded/admin_showNoteCoun.action',
                        "&index="+index2,
                    ).then(response => (this.totalNum = response.data));
                    //查找表中的数据
                    axios.post('ZJGuideWebsite_war_exploded/admin_showNote.action',
                        "currentPage="+this.currentPage+"&pageSize="+this.pageSize+"&index="+index2,
                    ).then(response => (this.tableData = response.data));
                    //将学长寄语的表标题绑定在当前表标题中
                    this.tableTitle = this.tableTitle1;
                    //指定分页栏的地址
                    this.actionURL = "ZJGuideWebsite_war_exploded/admin_showNote.action";
                    this.deleteURL = "ZJGuideWebsite_war_exploded/admin_deleteNote.action";
                    break;
                /**
                 * 志同道合
                  */
                case '3' :
                    //统计表中的数据条数
                    axios.post('ZJGuideWebsite_war_exploded/admin_showTeamCoun.action',
                        "&index="+index2,
                    ).then(response => (this.totalNum = response.data));
                    //查找表中的数据
                    axios.post('ZJGuideWebsite_war_exploded/admin_showTeam.action',
                        "currentPage="+this.currentPage+"&pageSize="+this.pageSize+"&index="+index2,
                    ).then(response => (this.tableData = response.data));
                    //将学长寄语的表标题绑定在当前表标题中
                    this.tableTitle = this.tableTitle2;
                    //指定分页栏的地址
                    this.actionURL = "ZJGuideWebsite_war_exploded/admin_showTeam.action";
                    this.deleteURL = "ZJGuideWebsite_war_exploded/admin_deleteTeam.action";
                    break;
                /**
                 * 用户管理
                  */
                case '4' :
                    //统计表中的数据条数
                    axios.post('ZJGuideWebsite_war_exploded/admin_showUserCoun.action',
                        "&index="+index2,
                    ).then(response => (this.totalNum = response.data));
                    //查找表中的数据
                    axios.post('ZJGuideWebsite_war_exploded/admin_showUser.action',
                        "currentPage="+this.currentPage+"&pageSize="+this.pageSize+"&index="+index2,
                    ).then(response => (this.tableData = response.data));
                    //将学长寄语的表标题绑定在当前表标题中
                    this.tableTitle = this.tableTitle3;
                    //指定分页栏的地址
                    this.actionURL = "ZJGuideWebsite_war_exploded/admin_showUser.action";
                    this.deleteURL = "ZJGuideWebsite_war_exploded/admin_deleteUser.action";
                    break;
            }
            //存放用户点击哪个，显示对应的表格标题
            this.index1 = index1-1;
            this.index2 = index2-1;
        },
        //点击删除按钮
        deleteRow(index, rows) {
            if (confirm("您确定删除吗？删除后无法恢复！！")){
                let deleteId = "rows[index]."+this.deleteId[this.index1][this.index2];
                let index2 = this.index2 + 1;
                axios.post( this.deleteURL,
                    "deleteId="+eval(deleteId)+"&index="+index2,
                ).then(function (data) {
                    console.log(data.data);
                    if ("true" == data.data){
                        rows.splice(index, 1);
                        alert("删除成功！");
                    } else if ("false" == data.data) {
                        alert("删除失败！");
                    }else if ("isTeamCaptain" == data.data) {
                        alert("队员为该队伍的队长，删除请删除队伍！！");
                    }
                });
            }

        },
        //改变页面显示条数
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageSize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            let index2 = this.index2 + 1;
            axios.post( this.actionURL,
                "currentPage="+this.currentPage+"&pageSize="+this.pageSize+"&index="+index2,
            ).then(response => (this.tableData = response.data));
        },

    }
})



// 转化时间戳
function time(time = +new Date()) {
    let date = new Date(time + 8 * 3600 * 1000); // 增加8小时
    return date.toJSON().substr(0, 19).replace('T', ' ');
}

