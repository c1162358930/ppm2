var defaultData = [
    {
        text:"型号",
        id:"afe",
        type:"model",
        nodes:[
            {
                text:"产品1",
                type:"product",
                id:"joefe"
            },
            {
                text:"产品2",
                id:"joefe1515",
                type:"product",
            }
        ]
    },
    {
        text:"型号",
        id:"afe333",
        type:"model",
        nodes:[
            {
                text:"产品1",
                id:"joefe",
                type:"product",
            },
            {
                text:"产品2",
                id:"joefe1515",
                type:"product",
            }
        ]
    }
];
//treeview
$(function(){
    var treeview=$("#tree").treeview({
        data:defaultData,
        onNodeSelected:function(event,data){
            $("#log").text(JSON.stringify(data));
            if("model"==data.type){
                var currentNode=$("#tree").treeview('collapseAll',{ silent: true });//关闭所有节点
                var currentNodeId=data.nodeId;
                $('#tree').treeview('expandNode', [ currentNodeId, { levels: 2, silent: true } ]);//展开首节点
            }
        }

    });
    $("#tree").treeview('collapseAll',{ silent: true });//全部关闭
    $('#tree').treeview('expandNode', [ 0, { levels: 2, silent: true } ]);//展开首节点

})
$(function(){
    var option={
        data:[
            {
                "id":12,
                "name":"产品1"
            },
            {
                "id":13,
                "name":"产品2"
            },
            {
                "id":14,
                "name":"产品3"
            },
            {
                "id":15,
                "name":"产品4"
            }
        ],
        "columns": [
            { "data": "id" ,"title":"唯一标识符"},
            { "data": "name","title" : "名称", }
        ],
        language:{"decimal":"","emptyTable":"No data available in table","info":"显示 _START_ 到 _END_ 页共 _TOTAL_ 条","infoEmpty":"显示 0 到 0 页共 0 条","infoFiltered":"(filtered from _MAX_ total entries)","infoPostFix":"","thousands":",","lengthMenu":"显示 _MENU_ 条","loadingRecords":"加载中...","processing":"Processing...","search":"搜索:","zeroRecords":"没有匹配项","paginate":{"first":"首页","last":"尾页","next":"下页","previous":"上页"},"aria":{"sortAscending":": activate to sort column ascending","sortDescending":": activate to sort column descending"},}

    }
    var myformTable=$("#myform").DataTable(option);
});
