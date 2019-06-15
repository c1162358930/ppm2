
//获取左侧树的数据
function getData(){
    var treeViewData=getModel();

}
//获取型号
function getModel() {
    $.ajax({
        url:"/Windchill/servlet/Navigation/model",
        data:{"actionName":"getListByWindch"},
        type:'get',
        dataType:"json",
        success:function (result) {
            if(result.success){
                var modelList=result.data;
                getProductByModel(modelList);

            }else{
                alert(result.message);
            }

        },
        error:function (a,b,c,d) {
            alert(b);
        }
    });
}
//根据模型获取产品
function getProductByModel(modelList) {
    $.each(modelList,function (i,n) {
        $.ajax({
            url:"",
            type:"get",
            dataType:"json",
            data:{"actionName":--},
            success:function(result){
                if(result.success){
                    $(n).productList=result.data;
                }else{
                    alert(result.message)
                }
            },
            error:function (a, b, c, d) {
                alert(b);
            }
        })
    })
}






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
//dataTable
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

//user
$(function(){
    //注册按钮
    bindBtn();
    bindEvent();


})
//绑定按钮
function bindBtn() {
    //新增产品按钮
    $("#addModelBtn").click(function () {
        var currentNode=$('#tree').treeview('getSelected');
        if(currentNode.type!="product"){
            alert("请选择型号");
            return false;
        }else{
            currentNode.id;
        }

    });
}
//注册事件
function bindEvent() {

}
