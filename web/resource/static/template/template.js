/*基础模块页面的js文件*/
    $(function(){
        //获取数据
        getModelList();
        //绑定按钮
        bindBtns();
        //绑定事件
        bindEvent();
    })
//获取工序
function getProcedure(){
    $.ajax({
        url:"/Windchill/servlet/Navigation/procedure",
        data:{"actionName":"get"},
        type:"get",
        dataType:"json",
        success:function (result) {
            if(!result.success){
                alert(result.message);
            }else{
                $("#gongxu").children("option").remove();
                $.each(result.data,function (i,n) {
                    var _option=$("<option></option>").text(n.name).prop("value",n.id);
                    $("#gongxu").append(_option);
                });
            }
        },
        error:function (a,b,c,d) {
            alert(c);
        }
    })
}


//绑定按钮
function bindBtns(){
    //"增加模板"按钮
    $("#addTempBtn").click(function(){
        //动态获取所有工序
        getProcedure();
        $("#addModel").modal("show");
    });
    //"选择工序"按钮
    $("#selectProcedureBtn").click(function () {
        var selected=$(this).prev().children("option:selected");
        var _div=$("<div></div>").addClass("form-inline");
        var _input1=$('<input type="text" disabled="disabled" class="form-control " >').val(selected.text());
        var _input2=$('<input type="hidden" name="procedure.id">').val($(this).prev().val());
        var closeBtn='<button id="removeProcedure" type="button" class="myClose">&nbsp;&times;&nbsp;</button>';
        _div.append(_input1).append(_input2).append(closeBtn);
        $("#gongxuContent").append(_div);
    });
    //"删除模板" 按钮
    $("#deleTempBtn").click(function(){
        var deleLi=$("#modelList").find("li.active");
        if(deleLi.length==1){
            var _id=deleLi.prop("id");
            var _data={"actionName":"delete"};
            _data.id=_id;
            $.ajax({
                url:"/Windchill/servlet/Navigation/template",
                data:_data,
                type:"get",
                dataType:"json",
                success:function (result) {
                    if(!result.success){
                        alert(result.message);
                    }
                    getModelList();
                },
                error:function (a,b,c,d) {
                    alert("发生错误，删除失败");
                }
            })
        }else{
            alert("请选中一条模板");
        }
    });
    //修改模板按钮
    $("#modifyTempBtn").click(function () {
        var seleLi=$("#modelList").find("li.active");
        if(seleLi.length==1){
            //带入模板基础信息
            var _id=seleLi.prop("id");
            $("#modelForm").find("input[name=id]").val(_id);
            $("#modelForm").find("input[name=name]").val(seleLi.text());
            //获取全部工序放到下拉框里面
            getProcedure();
            //获取模板下的所有工序
            var _data={"actionName":"getByTemplate","templateId":_id};
            $.ajax({
                url:"/Windchill/servlet/Navigation/procedure",
                type:"get",
                dataType:"json",
                data:_data,
                success:function (result) {
                    if(result.success){
                        $.each(result.data,function (i,n) {
                            var _div=$("<div></div>").addClass("form-inline");
                            var _input1=$('<input type="text" disabled="disabled" class="form-control " >')
                                .val(n.name);
                            var _input2=$('<input type="hidden" name="procedure.id">').val(n.id);
                            var closeBtn='<button id="removeProcedure" type="button" class="myClose">&nbsp;&times;&nbsp;</button>';
                            _div.append(_input1).append(_input2).append(closeBtn);
                            $("#gongxuContent").append(_div);
                        });
                        $("#addModel").modal("show");
                    }else{
                        alert(result.message);
                    }
                },
                error:function (a, b, c, d) {
                    alert(b);
                }
            })

        }else{
            alert("请选中一条模板");
        }
    });
    //“添加工序按钮”
    $("#addProcedureBtn").click(function () {
        var _input=$(this).next();
        var procedureName=_input.val();
        var _data={"actionName":"add"};
        _data.procedureName=procedureName;
        $.ajax({
            url:"/Windchill/servlet/Navigation/procedure",
            data:_data,
            type:"get",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    getProcedure();
                    _input.val("");
                }else{
                    alert(result.message);
                }
            },
            error:function (a, b, c, d) {
                alert(b);
            }
        });
    });
    //去除模板中的工序的按钮
    $("#gongxuContent").on("click","#removeProcedure",function () {
        $(this).parent("div").remove();
    });
    //特性的新增按钮
    $("#addCharBtn").click(function(){
        $("#model2").modal("show");
    });
    //特性修改按钮
    $("#modifyCharBtn").click(function () {
        $("#model2").modal("show");
        //将特性id带过来
        var currentProcedure=$("#procedureList").find("tr.myActive");
        var characId=currentProcedure.find("td:eq(2)").prop("id");
        $("#characForm").find("input[name=id]").val(characId);

        $.ajax({
            url:"/Windchill/servlet/Navigation/characteristic?actionName=getCharac&id="+characId,
            type:"get",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    var charac=result.data;
                    $("#characForm").find("input[name=name]").val(charac.name);
                    $("#characForm").find("input[name=total]").val(charac.total);
                    $("#characForm").find("input[name=coefficient]").val(charac.coefficient);
                }else{
                    alert(result.message);
                }
            },
            error:function (a, b, c, d) {
                alert(b);
            }
        });
    });

    //特性form的提交按钮
    $("#characFormSubmit").click(function () {
        var _data=$("#characForm").serialize();
        $.ajax({
            url:"/Windchill/servlet/Navigation/characteristic?actionName=postCharac",
            type:"get",
            data:_data,
            dataType:"json",
            success:function (result) {
                if(result.success){
                    $("#model2").modal("hide");
                    getProcedureByTemplate();
                }else{
                    alert(result.message);
                }
            },
            error:function (a, b, c, d) {
                alert(b);
            }
        })
    });
    //特性删除按钮
    $("#deleCharBtn").click(function () {
        var characId=$("#procedureList").find("tr.myActive").find("td:eq(2)").prop("id");
        var _data={"actionName":"deleteCharac","id":characId};
        $.ajax({
            url:"/Windchill/servlet/Navigation/characteristic",
            type:"get",
            data:_data,
            dataType:"json",
            success:function (result) {
                if(result.success){
                    getProcedureByTemplate();
                }else{
                    alert(result.message);
                }
            },
            error:function (a, b, c, d) {
                alert(b);
            }
        });
    });


}
//获取全部模板
function getModelList(){
    $("#modelList").html("");
    $.ajax({
        url:"/Windchill/servlet/Navigation/template",
        data:{"actionName":"get"},
        type:"get",
        dataType:"json",
        success:function(result){
            if(result.success){
                $.each(result.data,function(i,n){
                    var listItem=$("<li></li>").addClass("list-group-item").prop("id",n.id).text(n.name);
                    $("#modelList").append(listItem);
                });
            }else{
                addLog("获取模板请求成功，但是未请求到数据。"+result.message);
            }

        },
        error:function (a,b,c,d) {
            alert("获取数据失败:"+b);
        }
    });
}
//表单提交
function templateSubmit(){
    $.ajax({
        url:"/Windchill/servlet/Navigation/template?actionName=post",
        data:$("#modelForm").serialize(),
        type:"post",
        success:function (result) {
            if(!result.success){
                addLog(result.message);
            }
            $("#addModel").modal("hide");
            getModelList();
        },
        error:function (a,b,c,d) {
            alert("保存失败，"+b);
            $("#addModel").modal("hide");
        }
    })
}
//根据模板获取检验特性
function getProcedureByTemplate(){
    var _li=$("#modelList").find("li.active").prop("id");
    var templateId=_li;
    var _data={"actionName":"getByTemplate","templateId":templateId}
    $.ajax({
        url:"/Windchill/servlet/Navigation/procedure",
        type:"get",
        data:_data,
        dataType:"json",
        success:function(result){
            if(result.success){
                $("#procedureList").find("tr:not(:first)").remove();
                $.each(result.data,function (i, n) {
                    var _tr=$("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
                    _tr.prop("id",n.id);
                    _tr.find("td:eq(0)").text(parseInt(i)+1);
                    _tr.find("td:eq(1)").text(n.name);
                    _tr.find("td:eq(2)").text("");
                    _tr.find("td:eq(3)").text("");
                    _tr.find("td:eq(4)").text("");
                    $("#procedureList").append(_tr);
                });
                addCharaByProceId();
            }else{
                alert(result.message);
            }

        },
        error:function(a,b,c,d){
            alert(b);
        }
    });
}

//为模板列表绑定事件
function bindEvent() {
    $("#modelList").on("click","li",function () {
        $(this).prevAll().add($(this).nextAll()).removeClass("active");
        $(this).addClass("active");
        getProcedureByTemplate();

    });
    //模板的模态框暗下去的时候
    $("#addModel").on("hidden.bs.modal",function () {
        $("#gongxuContent").html("");
        $("#modelForm").get(0).reset();
        $("#modelForm").find("input[type=hidden]").val("");
    });

    //特性模态框出来时候需要做的事情
    $("#model2").on("show.bs.modal",function(){
        //拿到当前工序的名称和id
        var currentProcedure=$("#procedureList").find("tr.myActive");
        if(currentProcedure.length!=1){
            alert("请选中一条数据");
            return false;
        }
        //带过来工序id和工序名称
        var procedureId=currentProcedure.prop("id");
        var _idInput=$("#characForm").find("input[name=twId]");
        _idInput.val(procedureId);
        var procedureName=currentProcedure.find("td:eq(1)").text();
        _idInput.prev().text(procedureName);

    });
    //特性编辑模态框暗下去的时候
    $("#model2").on("hidden.bs.modal",function () {
        $("#characForm").get(0).reset();
        $("#characForm").find("input[type=hidden]").val("");
    })

    //工序的点击选择效果
    $("#procedureList").on("click","tr:not(:first)",function () {
        $(this).nextAll().add($(this).prevAll()).removeClass("myActive");
        $(this).addClass("myActive");
    });
}




function addLog(message){
    var _li=$("<li></li>").text(message);
    $("#log").children("ul").append(_li);
}
//得到工序后，为其添加工序特性
function addCharaByProceId() {
    var procedureList=$("#procedureList").find("tr:not(:first)");
    $.each(procedureList,function (i,n) {
        var _proceId=$(n).prop("id");
        var proceName=$(n).find("td:eq(1)").text();

        $.ajax({
            url:"/Windchill/servlet/Navigation/characteristic?actionName=getCharacList&procedureId="+_proceId,
            type:"get",
            dataType:"json",
            success:function (result) {
                if(result.success){
                    $.each(result.data,function (j, m) {
                        var _tr=$("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
                        _tr.prop("id",_proceId);
                        _tr.find("td:eq(0)").text();
                        _tr.find("td:eq(1)").text(proceName);
                        _tr.find("td:eq(2)").prop("id",m.id).text(m.name);
                        _tr.find("td:eq(3)").text(m.total);
                        _tr.find("td:eq(4)").text(m.coefficient);
                        $(n).after(_tr);
                    });
                }else{
                    alert(result.message);
                }
            },
            error:function (a,b,c,d) {
                alert(b);
            }
        });

    });
}


//根据工序id获取工序检验特性
function getCharaByProceId(procedureId){
        var _data={};
        _data.procedureId=procedureId;

}