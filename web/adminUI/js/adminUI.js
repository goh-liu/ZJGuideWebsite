var app = new Vue({
    el: "#app",
    data: function() {
        return {
            visible: false,
            activeIndex: '1',
            activeIndex2: '1',
            tableData: [{
                num: '1',
                name: 'goh_liu',
                content: '普范德萨范德萨范德萨陀区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '2',
                name: 'goh_liu',
                content: '普陀个梵蒂冈的非官方的广泛地区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '3',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨发区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '4',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '4',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '4',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '4',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '4',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '4',
                name: 'goh_liu',
                content: '普陀范德萨范德萨范德萨区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            },{
                num: '5',
                name: 'goh_liu',
                content: '普范德萨范德萨普范德萨范德萨发生的陀区普范德萨范德萨发生的陀区发生的陀区',
                time: '2019-08-08 12:43:46',
                likeCoun: '100',
                complainCoun: '10'
            }]

        }
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        deleteRow(index, rows) {
            rows.splice(index, 1);
        }
    }
})